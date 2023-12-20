import os
from flask import Flask, request, jsonify
from app.prediction.prediction import recommend_by_content_based_filtering, get_scholarship_details, load_model_and_data

app = Flask(__name__)

try:
    MODEL, DATA_CONTENT_BASED_FILTERING = load_model_and_data(local=os.environ.get('GCS_ENV') == 'local')
except Exception as e:
    print(f"Error loading model and data: {e}")


def create_success_response(data):
    return jsonify({"data": data, "status_code": 200}), 200


def create_error_response(error_message, status_code):
    return jsonify({"error": error_message, "status_code": status_code}), status_code


@app.route("/")
def api_info():
    return create_success_response({
        "api_name": "Schero API",
        "owner": "Aditya Prabowo",
        "description": "Scholarship recommendation API based on education level, funding type, and continent.",
        "contact": {
            "email": "adityaprabowo2001@gmail.com",
            "github": "https://github.com/zshditya/schero"
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
    })


@app.route("/predict")
def predict_scholarships():
    jenjang_pendidikan = request.args.get('jenjang_pendidikan')
    pendanaan = request.args.get('pendanaan')
    benua = request.args.get('benua')

    valid_education_levels = ["S1", "S2", "S3"]
    valid_funding_types = ["Fully Funded", "Partial Funded"]
    valid_continents = ["Eropa", "Asia", "Australia", "Amerika Selatan", "Amerika Utara"]

    if jenjang_pendidikan not in valid_education_levels or pendanaan not in valid_funding_types or benua not in valid_continents:
        return create_error_response("Invalid input", 400)

    query = {
        'jenjang pendidikan': jenjang_pendidikan,
        'pendanaan': pendanaan,
        'benua': benua
    }

    try:
        recommendations = recommend_by_content_based_filtering(query, DATA_CONTENT_BASED_FILTERING)

        if not recommendations:
            return create_error_response("Tidak ada rekomendasi beasiswa yang sesuai", 404)

        return create_success_response(recommendations)
    except Exception as prediction_error:
        print(f"Error predicting scholarships: {prediction_error}")
        return create_error_response("Terjadi kesalahan saat memprediksi beasiswa", 500)


@app.route("/scholarship_details")
def get_details():
    scholarship_name = request.args.get('scholarship_name')

    if not scholarship_name:
        return create_error_response("Parameter scholarship_name tidak ditemukan", 400)

    try:
        details = get_scholarship_details(scholarship_name, DATA_CONTENT_BASED_FILTERING)

        if not details:
            return create_error_response("Beasiswa tidak ditemukan", 404)

        return jsonify(details)
    except Exception as prediction_error:
        print(f"Error getting scholarship details: {prediction_error}")
        return create_error_response("Terjadi kesalahan saat mengambil detail beasiswa", 500)


# uncomment if you want to run project on local
# if __name__ == '__main__':
#     app.run(debug=True)
