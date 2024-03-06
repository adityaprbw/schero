# Schero Scholarship Recommendation API

Schero is an open-source Android app that leverages machine learning to offer personalized scholarship recommendations. It enables users to receive suggestions for domestic and international scholarship opportunities based on their input.

## Table of Contents

- [Local Setup](#local-setup)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Running the Application](#run-the-application)
- [Google App Engine (GAE) Deployment](#google-app-engine-gae-setup)
  - [Prerequisites](#prerequisites)
  - [Deployment Steps](#2-deploy-the-app)
- [Test the API](#test-the-api)
- [API Endpoints](#api-endpoints)
- [Contact Information](#contact-information)

## Local Setup

### Prerequisites

- Python 3.6 or higher
- Pip (Python package installer)

### Installation

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/dityapr/schero.git
   cd schero
   git checkout Cloud-Computing 
   
2. **Install Dependencies:**
    ```bash
   pip install -r requirements.txt

### Run the Application
```bash
   in the main.py file :
   change from app.prediction.prediction import recommend_by_content_based_filtering, get_scholarship_details, load_model_and_data
   to
   from prediction.prediction import recommend_by_content_based_filtering, get_scholarship_details, load_model_and_data
   
   uncomment :
   if __name__ == '__main__':
    app.run(debug=True)
   
   in terminal, run :
   python app/main.py
   
   endpoint test :
   GET http://localhost:5000/predict?jenjang_pendidikan=S2&pendanaan=Fully%20Funded&benua=Eropa
````

## Google App Engine (GAE) Setup

### 1. Prerequisites

1. Install and Configure Google Cloud SDK
Follow the instructions to install the Google Cloud SDK on your local machine.

2. Set Up Your Google Cloud Project
Create a new project on the Google Cloud Console.

3. Initialize App Engine
Navigate to your project directory and run:
```bash
gcloud init
```
Follow the prompts to set up your project.

### 2. Deploy the App
```bash
gcloud app deploy
```
#### Access the Deployed App
After deployment, your app will be accessible at https://your-project-id.et.r.appspot.com/

## Test the API
Use tools like Postman or cURL to test your API endpoints on the deployed GAE app.

Example:

GET https://your-project-id.et.r.appspot.com/predict?jenjang_pendidikan=S2&pendanaan=Fully%20Funded&benua=Eropa

## API Endpoints

### /predict
```bash
    Method: GET
    Description: Get scholarship recommendations
    Parameters:
    jenjang_pendidikan (string): Education level (e.g., S2)
    pendanaan (string): Funding type (e.g., Fully Funded)
    benua (string): Continent (e.g., Eropa)
    
    Example:
    
    GET https://your-app-url/predict?jenjang_pendidikan=S2&pendanaan=Fully%20Funded&benua=Eropa
```
### /scholarship_details
```bash
    Method: GET
    Description: Get details about a specific scholarship
    Parameters:
    scholarship_name (string): Name of the scholarship
    
    Example:
    
    GET https://your-app-url/scholarship_details?scholarship_name=Chevening%20UK%20Scholarships%20for%20International%20Students
```

## Contact Information

For any inquiries or issues, please reach out to us through the following channels:

- **Email:** [adityaprabowo2001@gmail.com](mailto:adityaprabowo2001@gmail.com)
- **GitHub Repository:** [Schero GitHub Issues](https://github.com/zshditya/schero/issues)

