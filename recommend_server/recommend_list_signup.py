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
    return(get_recommend_difficulty(uid, data))

def get_recommend_difficulty(uid, df) :
    member = db.reference('/Level Us/UserAccount/' + uid).get()

    is_lv1 = (df['difficulty'] == "1")
    is_lv2 = (df['difficulty'] == ("1" or "2" ))
    is_lv3 = (df['difficulty'] == ("1" or "2" or "3")) 

    if member['level'] < 10 :
        result = df[is_lv1]
        print(result)
    elif member['level'] < 20 :
        result = df[is_lv2]
    else :
        result = df[is_lv3]

    result = result.sort_values('done', ascending=False)[:10]
    result = result.sort_values(by=['quest_num'], axis=0)

    # 2) index reset하기
    result = result.reset_index(drop=True)
    js = result.to_dict('records')
    
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

def get_recommend_bucket_list_refresh(uid, df, user_log, category_c_sim, top=30):
    toprate = 0
    quest_index = 0

    is_lv1 = (df['difficulty'] == "1")
    is_lv2 = (df['difficulty'] == ("1" or "2" ))
    is_lv3 = (df['difficulty'] == ("1" or "2" or "3")) 

    for index, row in user_log.iterrows() : 
        if (toprate <= int(row['rating'])) :
            toprate = int(row['rating'])
            quest_index = row['quest_num']
   
    member = db.reference('/Level Us/UserAccount/' + uid).get()
    user_log = pd.DataFrame(db.reference('/quest_log/' + uid).get(),columns=["quest_num","title_ko","category","accepted_date","finished_date","rating"])
    
    if member['level'] < 10 :
        df = df[is_lv1]
    elif member['level'] < 20 :
        df = df[is_lv2]
    else :
        df = df[is_lv3]

    for index in user_log['quest_num'] :  
        df = df[df['quest_num'] != index]
        print(df)


    target_bucketlist_index = df[df['quest_num'] == quest_index].index.values
    sim_index = category_c_sim[target_bucketlist_index, :top].reshape(-1)

    for index in user_log['quest_num'] :  
        sim_index = sim_index[sim_index != index]
    
    result = df.iloc[sim_index].sort_values('done', ascending=False)[:10]
    result = result.sort_values(by=['quest_num'], axis=0)

    # 2) index reset하기
    result = result.reset_index(drop=True)
    js = result.to_dict('records')
    
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
    app.run(host="0.0.0.0", port=5000)
    



    