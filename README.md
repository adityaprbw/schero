# Schero Machine Learning Model

During the developmental phase, we autonomously carry out data collection processes and employ content-based filtration system algorithms to facilitate the retrieval of pertinent scholarship outcomes corresponding to user inputs.
#### Development environment : Google Colab, Jupyter Notebook
#### Production environment : Google Cloud Platform

## Work steps

#### 1. Train, test, and export the model in Schero.ipynb on Google Colab
#### 2. Upload the exported model to Google Cloud Storage
```bash
on cloud
https://storage.googleapis.com/zshdityaschero/tfidf_matrix.pkl

on local
schero/model/tfidf_matrix.pkl
```

#### 2. Load the exported model in prediction/prediction.py

```bash
on GitHub
https://github.com/zshditya/schero/blob/Cloud-Computing/app/prediction/prediction.py

on local
schero/prediction/prediction.py
```
