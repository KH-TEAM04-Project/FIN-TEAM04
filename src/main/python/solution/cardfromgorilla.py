from bs4 import BeautifulSoup
import selenium 
from selenium import webdriver 
from selenium.webdriver.common.keys import Keys 
import numpy as np
import datafilter
import tooracle

driver = webdriver.Chrome()

# url = "https://www.card-gorilla.com/chart/check100?term=weekly"

# driver.get(url)
# html = driver.page_source
# soup = BeautifulSoup(html, 'html.parser')
# sele = soup.select('div.card_img>p>a')

# # num = 0
# cardNumList = np.array([])

# for a in sele:
#     href = a.attrs['href'][13:]
#     # print(num, href)
#     # num = num + 1
#     cardNumList = np.append(cardNumList, href)

# cardNumList = np.delete(cardNumList, 0)

# print(cardNumList)
# 카드 번호 추출 완료.

# 테스트용
cardNumList = np.array([435, 618, 646, 2432])


detail = "https://www.card-gorilla.com/card/detail/"

cardInfoList = np.empty((0, 9), str)

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
    

    cardinfo = [brand + ",", name + ",", bename1 + ",", beval1 + ",", bename2 + ",", beval2 + ",", bename3 + ",", beval3 + ",", img]
    cardInfoList = np.append(cardInfoList, [cardinfo], axis=0)
    cardInfoList = datafilter.customFilter(cardInfoList)
    tooracle.insertDB(cardInfoList)
    
print(cardInfoList)
