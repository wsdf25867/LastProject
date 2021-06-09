import json
import pandas as pd 
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import firebase_admin
from firebase_admin import credentials
from firebase_admin import db


#사용자 평점이 가장 높은 퀘스트의 유사 퀘스트를 추천(완료된 퀘스트는 제거)
def get_recommend_bucket_list_refresh(uid, df, user_log, category_c_sim, top=30):
    toprate = 0
    quest_index = 0
    for index, row in user_log.iterrows() : 
        if (toprate <= int(row['rating'])) :
            toprate = int(row['rating'])
            quest_index = row['quest_num']
   
    target_bucketlist_index = df[df['quest_num'] == quest_index].index.values
    sim_index = category_c_sim[target_bucketlist_index, :top].reshape(-1)

    for index in user_log['quest_num'] :  
        sim_index = sim_index[sim_index != int(index)]
    
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
    df = pd.DataFrame(dir.get())
    
    uid = 'qEyWe2xINORTYNzUBqjwavFFtCz1'
    user_log = pd.DataFrame(db.reference('/quest_log/' + uid).get())

    count_vector = CountVectorizer(ngram_range=(1,3))
    c_vector_category= count_vector.fit_transform(df['category'])
    category_c_sim = cosine_similarity(c_vector_category, c_vector_category).argsort()[:, ::-1]
    get_recommend_bucket_list_refresh(uid, df, user_log, category_c_sim)
    



    