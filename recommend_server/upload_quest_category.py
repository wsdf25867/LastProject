import firebase_admin
from firebase_admin import credentials
from firebase_admin import db
import json
import csv
import pandas as pd


# csv파일을 json으로 변환하는 메소드
def csv2json(input_file_path, output_file_path):
    with open(input_file_path,"r",encoding="cp949", newline="") as input_file,open(output_file_path,"w",encoding="cp949",newline="") as output_file:
        reader = csv.reader(input_file)
        col_names = next(reader)
        docs = []
        for cols in reader:
            doc = {col_name: col for col_name, col in zip(col_names, cols)}
            docs.append(doc)
        print(json.dumps(docs,ensure_ascii=False),file=output_file)
    
# firebase 연동파트
cred = credentials.Certificate("collabtest-71a4d-firebase-adminsdk-ty8fu-79e0a2a74e.json")
firebase_admin.initialize_app(cred,{
    'databaseURL':'https://collabtest-71a4d-default-rtdb.asia-southeast1.firebasedatabase.app/'
})

#퀘스트 목록
quest_list = {'diy','entertainment','food','health',
'hiking','life_milestone','love','nature','new_skill',
'outdoor','sports','travel',}

# 메소드 호출
for category in quest_list:
    csv2json('quest_'+category+'.csv','quest_'+category+'.json')

# firebase에 json파일 업로드
for category in quest_list:
    jsonData = open('quest_'+category+'.json',"r",encoding="cp949").read()
    data = json.loads(jsonData)

    dir = db.reference('/quest/'+category)
    dir.set(data)
