import json
import pandas as pd 
import numpy as np
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import firebase_admin
from firebase_admin import credentials
from firebase_admin import db
from flask import Flask, escape, request

app = Flask(__name__)
    

@app.route('/signup/<string:uid>')
def get_member_data_signup(uid):
    dir = db.reference('/quest/ALL')
    data = pd.DataFrame(dir.get())
    count_vector = CountVectorizer(ngram_range=(1,3))
    c_vector_category= count_vector.fit_transform(data['category'])
    category_c_sim = cosine_similarity(c_vector_category, c_vector_category).argsort()[:, ::-1]
    return(get_recommend_difficulty(uid, data))

def get_recommend_difficulty(uid, df) :
    member = db.reference('/Level Us/UserAccount/' + uid).get()

    is_lv1 = (df['difficulty'] == "1")
    result = df[is_lv1]
    

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
    dir = db.reference('/quest/ALL')
    data = pd.DataFrame(dir.get())
    count_vector = CountVectorizer(ngram_range=(1,3))
    c_vector_category= count_vector.fit_transform(data['category'])
    category_c_sim = cosine_similarity(c_vector_category, c_vector_category).argsort()[:, ::-1]
    return(get_recommend_bucket_list_refresh(uid, data, category_c_sim))

def get_recommend_bucket_list_refresh(uid, df, category_c_sim, top=30):
    
    member = db.reference('/Level Us/UserAccount/' + uid).get()       #사용자 정보
    user_log = pd.DataFrame(db.reference('/quest_log/' + uid).get())  #추천 퀘스트 목록
    rj_list = pd.DataFrame(db.reference('/rejected_list/' + uid).get())      #거절 퀘스트 목록

    toprate = 0
    quest_index = 0
    
    if(user_log.empty):          #quest_log 가 없으면
        print(user_log)
        quest_index = str(0)
    else:                         #quest_log의 df가 행열이 바뀌어있다면
        if (user_log.index[0] == "achievement") or (user_log.index[0] == "accepted_date")  :
            user_log = user_log.transpose()
            print(user_log)
        else:
            print(user_log)
        
        for index, row in user_log.iterrows() : #평점이 가장 높은 퀘스트를 찾음
            if (toprate <= float(row['rating'])) :
                toprate = float(row['rating'])
                quest_index = row['quest_num']

    if(rj_list.empty):          #rejected_list 가 없으면
        print(rj_list)
    else:                       #rejected_list의 df가 행열이 바뀌어있다면
        if (rj_list.index[0] == "achievement") or (rj_list.index[0] == "added")  :
            rj_list = rj_list.transpose()
            print(rj_list)
        else:
            print(rj_list)

        for index, row in rj_list.iterrows() : #rejected_list에 있는 quest_num을 제거
            print(row['quest_num'])
            del_index = df[df['quest_num'] == row['quest_num']].index
            df = df.drop(del_index)

    print('여기서 확인 :')
    print(df)    
    
    
    # target_bucketlist_index = df[df['quest_num'] == quest_index].index.values #컨텐츠 기반 필터링
    # sim_index = category_c_sim[target_bucketlist_index, :top].reshape(-1)

    # if(user_log.empty):
    #     print("sim : ")
    #     print(sim_index)
    # else:
    #     for index in user_log['quest_num'] : 
    #         sim_index = np.delete(sim_index, np.where(sim_index == int(index)))
    #     print("sim : ")
    #     print(sim_index)
    
    #result = df.iloc[sim_index].sort_values('done', ascending=False)
    result = df.sort_values('done', ascending=False)
    result = result.sort_values(by=['quest_num'], axis=0)

    # if(user_log is not None):
    #     for index in user_log['quest_num'] :  
    #         result = result[result['quest_num'] != index]

    is_lv1 = (result['difficulty'] == "1")
    is_lv2 = (result['difficulty'] == "2")
    is_lv3 = (result['difficulty'] == "3") 
    
    if int(member['level']) < 10 :  #사용자 레벨에 따른 퀘스트 제공
        result = result[is_lv1]
    elif int(member['level']) < 50 :
        result = pd.concat([result[is_lv1],result[is_lv2]])
    else :
        result = pd.concat([result[is_lv1],result[is_lv2],result[is_lv3]])

    result = result[:10]

    print(result)
    
    #json 파일로 저장
    js = result.to_dict('records')
    with open('test.json', 'w', encoding='cp949') as make_file:
        json.dump(js, make_file)

    # firebase에 json파일 업로드
    jsonData = open("test.json","r",encoding="cp949").read()
    data = json.loads(jsonData)

    print(data)
    dir = db.reference('/recommend_list/'+uid)
    dir.set(data)
   
    return result    

@app.route('/quest_category')
def quest_category():
    dir = db.reference('/quest/ALL')
    quest_df = pd.DataFrame(dir.get())

    #퀘스트 카테고리 목록
    quest_list = ['diy','entertainment','food','health',
    'hiking','life_milestone','love','nature','new_skill',
    'outdoor','sports','travel']

    for category_name in quest_list: 
        print(category_name)
        #json 파일로 저장
        js = quest_df[quest_df['category'] == category_name].to_dict('records')
        with open('test.json', 'w', encoding='cp949') as make_file:
            json.dump(js, make_file)

        # firebase에 json파일 업로드
        jsonData = open("test.json","r",encoding="cp949").read()
        data = json.loads(jsonData)

        print(data)
        dir = db.reference('/quest/' + category_name)
        dir.set(data)



if __name__ == "__main__":
    cred = credentials.Certificate("collabtest-71a4d-firebase-adminsdk-ty8fu-79e0a2a74e.json")
    firebase_admin.initialize_app(cred,{
    'databaseURL':'https://collabtest-71a4d-default-rtdb.asia-southeast1.firebasedatabase.app/'})
    app.run(host="0.0.0.0", port=5000)



    