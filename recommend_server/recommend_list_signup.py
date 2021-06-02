import json
import pandas as pd 
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import firebase_admin
from firebase_admin import credentials
from firebase_admin import db
from flask import Flask, escape, request

app = Flask(__name__)
    

@app.route('/signup/<string:uid>')
def get_member_data_signup(uid):
    count_vector = CountVectorizer(ngram_range=(1,3))
    c_vector_category= count_vector.fit_transform(data['category'])
    category_c_sim = cosine_similarity(c_vector_category, c_vector_category).argsort()[:, ::-1]
    return(get_recommend_bucket_list_signup(uid, data, category_c_sim, title_ko='메세지 병에 담아 보내기'))

def get_recommend_bucket_list_signup(uid, df, category_c_sim,title_ko, top=30):
    target_bucketlist_index = df[df['title_ko'] == title_ko].index.values
    sim_index = category_c_sim[target_bucketlist_index, :top].reshape(-1)
    
    result = df.iloc[sim_index].sort_values('done', ascending=False)[:10]
    result = result.sort_values(by=['quest_num'], axis=0)

    # 2) index reset하기
    result = result.reset_index(drop=True)
    print(result)
    js = result.to_dict('records')
    print(js)    
    #json 파일로 저장
    with open('test.json', 'w', encoding='cp949') as make_file:
        json.dump(js, make_file)

    # firebase에 json파일 업로드
    jsonData = open("test.json","r",encoding="cp949").read()
    data = json.loads(jsonData)

    dir = db.reference('/recommend_list/'+uid)
    dir.set(data)

    return result    


@app.route('/refresh/<string:uid>')
def get_member_data_refresh(uid):
    member = db.reference('/Level Us/UserAccount/' + uid).get()
    user_log = pd.DataFrame(db.reference('/quest_log/' + uid).get())

    count_vector = CountVectorizer(ngram_range=(1,3))
    c_vector_category= count_vector.fit_transform(data['category'])
    category_c_sim = cosine_similarity(c_vector_category, c_vector_category).argsort()[:, ::-1]
    return(get_recommend_bucket_list_refresh(uid, data, user_log, category_c_sim))

#사용자 평점이 가장 높은 퀘스트의 유사 퀘스트를 추천(완료된 퀘스트는 제거)
def get_recommend_bucket_list_refresh(uid, df, user_log, category_c_sim, top=30):
    top = 0
    quest_index = 0
    for log in user_log :  
        if (top <= log['rating']) :
            quest_index = log['quest_num']
            top = log['rating']

    
    target_bucketlist_index = df[df['quest_num'] == quest_index].index.values
    sim_index = category_c_sim[target_bucketlist_index, :top].reshape(-1)
    
    for index in user_log['quest_num'] :  
        sim_index = sim_index[sim_index != index]
    
    result = df.iloc[sim_index].sort_values('done', ascending=False)[:10]
    print(result)
    
    result = result.sort_values(by=['quest_num'], axis=0)

    # 2) index reset하기
    result = result.reset_index(drop=True)
    print(result)
    js = result.to_dict('records')
    print(js)    
    #json 파일로 저장
    with open('test.json', 'w', encoding='cp949') as make_file:
        json.dump(js, make_file)

    # firebase에 json파일 업로드
    jsonData = open("test.json","r",encoding="cp949").read()
    data = json.loads(jsonData)

    dir = db.reference('/recommend_list/'+uid)
    dir.set(data)

    return result    


if __name__ == "__main__":
    cred = credentials.Certificate("collabtest-71a4d-firebase-adminsdk-ty8fu-79e0a2a74e.json")
    firebase_admin.initialize_app(cred,{
    'databaseURL':'https://collabtest-71a4d-default-rtdb.asia-southeast1.firebasedatabase.app/'})
    dir = db.reference('/quest/ALL')
    #content based filtering 알고리즘을 이용한 유사한 퀘스트 추천
    data = pd.DataFrame(dir.get())
    app.run(host="192.168.0.12", port=8888)
    



    