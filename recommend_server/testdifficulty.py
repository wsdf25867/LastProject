import json
import pandas as pd 
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import firebase_admin
from firebase_admin import credentials
from firebase_admin import db
from flask import Flask, escape, request


    #,columns=["quest_num","title_ko","category","accepted_date","finished_date","rating"]

def get_recommend_difficulty(uid, df) :
    member = db.reference('/Level Us/UserAccount/' + uid).get()
    user_log = pd.DataFrame(db.reference('/quest_log/' + uid).get()).transpose()
    print(member)
    print(user_log)
    for index in user_log['quest_num'] :  
        print('------------------------index------------------')
        print(index)
        print('------------------------index end------------------')
        df = df[df['quest_num'] != index]
        print('------------------------df------------------')
        print(df)
        print('------------------------df end------------------')

    is_lv1 = (df['difficulty'] == "1")
    is_lv2 = (df['difficulty'] == "2")
    is_lv3 = (df['difficulty'] == "3") 
    
    if member['level'] < 10 :
        result = df[is_lv1]
        print('------------------------result------------------')
        print(result)
        print('------------------------result end------------------')
    elif member['level'] < 20 :
        result =  pd.concat([df[is_lv1],df[is_lv2]])
        print('------------------------result------------------')
        print(result)
        print('------------------------result end------------------')
    else :
        result = pd.concat([df[is_lv1],df[is_lv2],df[is_lv3]])
        print('------------------------result------------------')
        print(result)
        print('------------------------result end------------------')

    result = result.sort_values('done', ascending=False)[:10]
    result = result.sort_values(by=['quest_num'], axis=0)

    # 2) index reset하기
    #result = result.reset_index(drop=True)
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

    uid = "80QCXnA2EUP0CC4DS9CNQh00nsx2"
    print(get_recommend_difficulty(uid, data))

   