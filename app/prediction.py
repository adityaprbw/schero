import pandas as pd
from fuzzywuzzy import fuzz
from nltk.tokenize import word_tokenize
from sklearn.feature_extraction.text import ENGLISH_STOP_WORDS
import joblib
from google.cloud import storage

def download_file_from_gcs(bucket_name, source_blob_name, destination_file_name):
    storage_client = storage.Client()
    bucket = storage_client.bucket(bucket_name)
    blob = bucket.blob(source_blob_name)
    blob.download_to_filename(destination_file_name)

def load_model_and_data():
    bucket_name = 'zshdityaschero'  
    model_file_name = 'tfidf_matrix.pkl'
    dataset_file_name = 'beasiswa.csv'

    download_file_from_gcs(bucket_name, model_file_name, f'app/model/{model_file_name}')
    download_file_from_gcs(bucket_name, dataset_file_name, f'app/data/{dataset_file_name}')

    data_content_based_filtering = pd.read_csv(f'app/data/{dataset_file_name}')
    model = joblib.load(f'app/model/{model_file_name}')

    return model, data_content_based_filtering

def preprocess_query(query):
    stop_words_english = set(ENGLISH_STOP_WORDS)
    tokens = word_tokenize(query)
    filtered_query = ' '.join([word.lower() for word in tokens if word.lower() not in stop_words_english])
    return filtered_query

def recommend_by_content_based_filtering(query, data_content_based_filtering):
    matched_beasiswas = []

    for index, beasiswa in data_content_based_filtering.iterrows():
        jenjang_similarity = fuzz.token_set_ratio(query['jenjang pendidikan'], str(beasiswa['Jenjang']).lower())
        benua_similarity = fuzz.token_set_ratio(query['benua'], str(beasiswa['Benua']).lower())
        funding_similarity = fuzz.token_set_ratio(query['pendanaan'], str(beasiswa['Jenis Pendanaan']).lower())

        FEATURE_SIMILARITY_WEIGHT = 3
        combined_similarity = (jenjang_similarity + benua_similarity + funding_similarity) / FEATURE_SIMILARITY_WEIGHT
        matched_beasiswas.append((index, combined_similarity))

    matched_beasiswas = sorted(matched_beasiswas, key=lambda x: x[1], reverse=True)

    recommended_nama_beasiswas = []
    for match in matched_beasiswas:
        beasiswa_index = match[0]
        beasiswa_name = data_content_based_filtering.iloc[beasiswa_index]['Nama Beasiswa']
        recommended_nama_beasiswas.append(beasiswa_name)

    return recommended_nama_beasiswas

def get_scholarship_details(scholarship_name, data_content_based_filtering):
    scholarship_details = data_content_based_filtering[data_content_based_filtering['Nama Beasiswa'] == scholarship_name]
    return scholarship_details.to_dict(orient='records')

def predict(education, funding_type, continent, data_content_based_filtering):
    query = {
        'jenjang pendidikan': education,
        'pendanaan': funding_type,
        'benua': continent
    }

    recommendations = recommend_by_content_based_filtering(query, data_content_based_filtering)
    return recommendations