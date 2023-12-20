# Use an official Python runtime as a parent image
FROM tiangolo/uvicorn-gunicorn-fastapi:python3.12

# Set the working directory to /app
WORKDIR /app

# Copy only the necessary files to the container
COPY ./app/main.py /app/app/main.py
COPY ./app/credential /app/app/credential
COPY ./app/data /app/app/data
COPY ./app/model /app/app/model
COPY ./app/prediction /app/app/prediction
COPY ./requirements.txt /app

# Install any needed packages specified in requirements.txt
RUN pip install --no-cache-dir -r requirements.txt

# Run main.py when the container launches
CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "80", "--reload"]
