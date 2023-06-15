from bs4 import BeautifulSoup
import selenium 
from selenium import webdriver 
from selenium.webdriver.common.keys import Keys 
import numpy as np
import json

def scraping():

    driver = webdriver.Chrome()

    url = "https://www.card-gorilla.com/chart/check100?term=weekly"

    driver.get(url)
    html = driver.page_source
    soup = BeautifulSoup(html, 'html.parser')
    sele = soup.select('div.card_img>p>a')

    # num = 0
    cardNumList = np.array([])

    for a in sele:
        href = a.attrs['href'][13:]
        # print(num, href)
        # num = num + 1
        cardNumList = np.append(cardNumList, href)

    cardNumList = np.delete(cardNumList, 0)

    print(cardNumList)
    # 카드 번호 추출 완료.

    # 테스트용
    # cardNumList = [435, 618, 646, 2432]


    detail = "https://www.card-gorilla.com/card/detail/"

    cardInfoList =[]
    cardInfoList2 =[]

    for a in cardNumList:
        detailurl = detail + str(a)
        driver.get(detailurl)
        html = driver.page_source
        soup = BeautifulSoup(html, 'html.parser')

        # 이미지 담기
        img = soup.select_one('div.card_img>img')['src']

        # 카드이름 및 은행이름
        name = soup.select_one('div.tit>strong.card').text
        brand = soup.select_one('div.tit>p').text

        # 혜택 1
        if soup.select_one('div.bnf1>dl.bnf11>dt') == None:
            bename1 = ''
            beval1 = ''
        else :
            bename1 = soup.select_one('div.bnf1>dl.bnf11>dt').text
            beval1 = soup.select_one('div.bnf1>dl.bnf11>dd>strong').text

        # 혜택 2
        if soup.select_one('div.bnf1>dl.bnf12>dt') == None:
            bename2 = ''
            beval2 = ''
        else :
            bename2 = soup.select_one('div.bnf1>dl.bnf12>dt').text
            beval2 = soup.select_one('div.bnf1>dl.bnf12>dd>strong').text

        # 혜택 3
        if soup.select_one('div.bnf1>dl.bnf13>dt') == None:
            bename3 = ''
            beval3 = ''
        else :
            bename3 = soup.select_one('div.bnf1>dl.bnf13>dt').text
            beval3 = soup.select_one('div.bnf1>dl.bnf13>dd>strong').text


        cardinfo = [brand, name, img, bename1, beval1, bename2, beval2, bename3, beval3]

        cardInfoList.append({"카드사" : cardinfo[0], "카드명" : cardinfo[1], "카드이미지" : cardinfo[2], "혜택1" : cardinfo[3], "혜택1내용" : cardinfo[4], "혜택2" : cardinfo[5], "혜택2내용" : cardinfo[6], "혜택3" : cardinfo[7], "혜택3내용" : cardinfo[8]})


    card_info_dict = {}


    # Convert the cardInfoList2 to JSON
    json_data = json.dumps(cardInfoList)
    print(cardInfoList)
    # Write the JSON data to a file
    """ with open('card_info.json', 'w') as file:
        file.write(json_data)

    # print(cardInfoList)
    print(json_data) """

    for item in cardInfoList:
        print(item)

    json_data2 = json.dumps(item)

    # Write the JSON data to a file
    """ with open('card_info2.json', 'w') as file:
        file.write(json_data2)
    print(json_data2) """


    return cardInfoList