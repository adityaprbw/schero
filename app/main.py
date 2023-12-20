import os
import uvicorn
from fastapi import FastAPI, Query, HTTPException, Path
from fastapi.responses import JSONResponse
from prediction.prediction import (
    recommend_by_content_based_filtering,
    get_scholarship_details,
    load_model_and_data,
)

app = FastAPI(title="Schero API")

# Load model and data
MODEL, DATA_CONTENT_BASED_FILTERING = load_model_and_data(local=os.environ.get('GCS_ENV') == 'local')


def get_port():
    return int(os.environ.get("PORT", 8080 if 'K_SERVICE' in os.environ else 5000))


@app.get("/")
def api_info():
    return {
        "api_name": "Schero API",
        "owner": "Aditya Prabowo",
        "description": "Scholarship recommendation API based on education level, funding type, and continent.",
        "contact": {
            "email": "adityaprabowo2001@gmail.com",
            "github": "https://github.com/zshditya/schero"
        },
        "documentation": {
            "openapi": "/docs",
            "redoc": "/redoc"
        },
        "endpoints": {
            "/predict": {
                "method": "GET",
                "description": "Get scholarship recommendations",
                "sample_request": {
                    "jenjang_pendidikan": "S2",
                    "pendanaan": "Fully Funded",
                    "benua": "Eropa"
                }
            },
            "/scholarship_details": {
                "method": "GET",
                "description": "Get details about a specific scholarship",
                "sample_request": {
                    "scholarship_name": "Chevening UK Scholarships for International Students"
                }
            }
        }
    }


@app.get("/predict")
def predict_scholarships(
        jenjang_pendidikan: str = Query(..., description="Education level", regex="^(S1|S2|S3)$"),
        pendanaan: str = Query(..., description="Funding type", regex="^(Fully Funded|Partial Funded)$"),
        benua: str = Query(..., description="Continent",
                           pattern="^(Eropa|Asia|Australia|Amerika Selatan|Amerika Utara)$"),
):
    query = {
        'jenjang pendidikan': jenjang_pendidikan,
        'pendanaan': pendanaan,
        'benua': benua
    }

    try:
        recommendations = recommend_by_content_based_filtering(query, DATA_CONTENT_BASED_FILTERING)
        return {"recommendations": recommendations}
    except HTTPException as he:
        raise he
    except Exception:
        raise


@app.get("/scholarship_details/{scholarship_name}")
def get_details(scholarship_name: str = Path(..., description="Scholarship name")):
    details = get_scholarship_details(scholarship_name, DATA_CONTENT_BASED_FILTERING)

    if not details:
        return JSONResponse(content={"detail": "Beasiswa tidak tersedia"}, status_code=404)

    return {"details": details or None}


if __name__ == '__main__':
    # Use import string for uvicorn.run to avoid the warning
    uvicorn.run("main:app", host="0.0.0.0", port=get_port(), reload=not ('K_SERVICE' in os.environ))
