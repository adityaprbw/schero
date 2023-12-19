from fastapi import FastAPI, Query, HTTPException, Path
from starlette.responses import JSONResponse
from prediction.prediction import recommend_by_content_based_filtering, get_scholarship_details, load_model_and_data
import os

app = FastAPI()

if os.environ.get('GCS_ENV') == 'local':
    MODEL, DATA_CONTENT_BASED_FILTERING = load_model_and_data(local=True)
else:
    MODEL, DATA_CONTENT_BASED_FILTERING = load_model_and_data()


@app.get("/")
def api_info():
    info = {
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
    return info


@app.get("/predict")
def predict_scholarships(
        jenjang_pendidikan: str = Query(..., description="Education level", regex="^(S1|S2|S3)$"),
        pendanaan: str = Query(..., description="Funding type", regex="^(Fully Funded|Partial Funded)$"),
        benua: str = Query(..., description="Continent",
                           regex="^(Eropa|Asia|Australia|Amerika Selatan|Amerika Utara)$"),
):
    try:
        query = {
            'jenjang pendidikan': jenjang_pendidikan,
            'pendanaan': pendanaan,
            'benua': benua
        }

        recommendations = recommend_by_content_based_filtering(query, DATA_CONTENT_BASED_FILTERING)

        result = {"recommendations": recommendations}
        return result

    except HTTPException as he:
        raise he
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@app.get("/scholarship_details/{scholarship_name}")
def get_details(scholarship_name: str = Path(..., description="Scholarship name")):
    details = get_scholarship_details(scholarship_name, DATA_CONTENT_BASED_FILTERING)

    if not details:
        return JSONResponse(content={"detail": "Beasiswa tidak tersedia"}, status_code=404)

    return {"details": details or None}


# Uncomment if you want run locally
# if __name__ == '__main__':
#     import uvicorn
#
#     uvicorn.run(app, host="127.0.0.1", port=5000)
