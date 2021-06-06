from selenium import webdriver
from bs4 import BeautifulSoup
import pandas as pd
import csv
import urllib.request



data = pd.read_csv("bucket_list_ko2.csv", encoding='cp949')
selectdata = pd.DataFrame(data, columns=['title', 'quest_num']) #특정컬럼선택

# selenium에서 사용할 웹 드라이버 절대 경로 정보
chromedriver = 'C:\webdriver\chromedriver.exe'
# selenum의 webdriver에 앞서 설치한 chromedirver를 연동한다.
driver = webdriver.Chrome(chromedriver)
# driver로 특정 페이지를 크롤링한다.
driver.get('https://www.google.co.kr/imghp?hl=ko&ogbl')

for index, row in selectdata[63:].iterrows() :
    driver.get('https://www.google.co.kr/imghp?hl=ko&ogbl')

    #검색창에 검색어 입력
    elem_search = driver.find_element_by_xpath("/html/body/div[2]/div[2]/div[2]/form/div[1]/div[1]/div[1]/div/div[2]/input")
    elem_search.clear()
    elem_search.send_keys(row['title'] + ' + bucketlist.org')

    #검색 버튼 클릭
    btn_search = driver.find_element_by_xpath('/html/body/div[2]/div[2]/div[2]/form/div[1]/div[1]/div[1]/button')
    btn_search.click()
    
    #맨 처음 이미지 클릭
    elem_image_little = driver.find_element_by_xpath('/html/body/div[2]/c-wiz/div[3]/div[1]/div/div/div/div[1]/div[1]/span/div[1]/div[1]/div[1]/a[1]/div[1]/img')
    elem_image_little.click()

    #해당 이미지 다운로드
    elem_image = driver.find_element_by_xpath('/html/body/div[2]/c-wiz/div[3]/div[2]/div[3]/div/div/div[3]/div[2]/c-wiz/div/div[1]/div[1]/div/div[2]/a/img')
    url = elem_image.get_attribute("src")
    print(url)
    imgName = 'C:\img\\' + str(row['quest_num']) + '.jpg'  
    urllib.request.urlretrieve(url,imgName)

print('다운완료')

