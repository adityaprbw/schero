from flask import Flask, request, jsonify
from prediction.prediction import recommend_by_content_based_filtering, get_scholarship_details, load_model_and_data

app = Flask(__name__)

MODEL, DATA_CONTENT_BASED_FILTERING = load_model_and_data()

@app.route('/')
def api_info():
    info = {
        "api_name": "Schero API",
        "owner": "Aditya Prabowo",
        "description": "This API provides scholarship recommendations based on education level, funding type, and continent.",
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
                    "scholarship_name": "Sample Scholarship"
                }
            }
        }
    }
    return jsonify(info)

@app.route('/predict', methods=['GET'])
def predict_scholarships():
    try:
        education = request.args.get('jenjang_pendidikan', '')
        funding_type = request.args.get('pendanaan', '')
        continent = request.args.get('benua', '')

        query = {
            'jenjang pendidikan': education,
            'pendanaan': funding_type,
            'benua': continent
        }

        recommendations = recommend_by_content_based_filtering(query, DATA_CONTENT_BASED_FILTERING)

        result = {"recommendations": recommendations}
        return jsonify(result)

    except Exception as e:
        print("Error:", str(e))
        return jsonify({"error": str(e), "api_info": api_info()}), 400

@app.route('/scholarship_details', methods=['GET'])
def get_details():
    try:
        scholarship_name = request.args.get('scholarship_name', '')
        details = get_scholarship_details(scholarship_name, DATA_CONTENT_BASED_FILTERING)
        return jsonify(details)

    except Exception as e:
        return jsonify({"error": str(e), "api_info": api_info()}), 400

if __name__ == '__main__':
    app.run(port=5000, debug=True)
