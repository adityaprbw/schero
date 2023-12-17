import sys
import os
from flask import Flask, request, jsonify
from prediction import predict, get_scholarship_details, load_model_and_data

sys.path.append(os.path.dirname(os.path.abspath(__file__)))

app = Flask(__name__)

MODEL, DATA_CONTENT_BASED_FILTERING = load_model_and_data()

BUCKET_NAME = 'zshdityaschero' 

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
        return jsonify({"error": str(e)}), 500  

@app.route('/scholarship_details', methods=['GET'])
def get_details():
    try:
        scholarship_name = request.args.get('scholarship_name', '')
        details = get_scholarship_details(scholarship_name, DATA_CONTENT_BASED_FILTERING)
        return jsonify(details)

    except Exception as e:
        return jsonify({"error": str(e)}), 500 

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080, debug=True) # run on GCP
    # app.run(port=5000, debug=True) # run on local