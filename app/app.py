import sys
import os
from flask import Flask, request, jsonify

sys.path.append(os.path.dirname(os.path.abspath(__file__)))
from prediction import predict, get_scholarship_details, load_model_and_data

app = Flask(__name__)

MODEL, DATA_CONTENT_BASED_FILTERING = load_model_and_data()

BUCKET_NAME = 'zshdityaschero'

@app.route('/')
def api_info():
    info = {
        "api_name": "Schero API",
        "owner": "Aditya Prabowo",
        "description": "This API provides scholarship recommendations based on education level, funding type, and continent.",
        "endpoints": {
            "/predict": "POST - Get scholarship recommendations",
            "/scholarship_details": "GET - Get details about a specific scholarship"
        }
    }
    return jsonify(info)

@app.route('/predict', methods=['POST'])
def predict_scholarships():
    try:
        input_data = request.json
        education = input_data.get('jenjang pendidikan', '')
        funding_type = input_data.get('pendanaan', '') 
        continent = input_data.get('benua', '')

        recommendations = predict(education, funding_type, continent, DATA_CONTENT_BASED_FILTERING)

        result = {"recommendations": recommendations}
        return jsonify(result)

    except Exception as e:
        return jsonify({"error": str(e), "api_info": api_info()}), 400  

@app.route('/scholarship_details', methods=['GET'])
def get_details():
    try:
        scholarship_name = request.args.get('scholarship_name', '')
        details = get_scholarship_details(scholarship_name, DATA_CONTENT_BASED_FILTERING)
        return jsonify(details)

    except Exception as e:
        return jsonify({"error": str(e), "api_info": api_info()}), 400 


"""
to run on local uncomment this code

if __name__ == '__main__':
    app.run(port=5000, debug=True) 
"""
