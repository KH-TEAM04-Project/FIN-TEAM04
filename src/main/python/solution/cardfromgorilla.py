import datetime
from bs4 import BeautifulSoup
from openpyxl import Workbook
import selenium 
from selenium import webdriver 
from selenium.webdriver.common.keys import Keys 
import numpy as np
import datafilter
import tooracle
import json
import schedule
import time
import re

def clean_sheet_name(name):
    # Remove invalid characters from the sheet name
    cleaned_name = re.sub(r'[<>:"/\\|?*]', '', name)
    return cleaned_name[:31]

driver = webdriver.Chrome()

# url = "https://www.card-gorilla.com/chart/check100?term=weekly"


    """ url = "https://www.card-gorilla.com/chart/check100?term=weekly"

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

    print(cardNumList)  """
    # 카드 번호 추출 완료.

    # 테스트용
    cardNumList = [435, 618, 646, 2432]


    detail = "https://www.card-gorilla.com/card/detail/"

    cardInfoList =[]

    for a in cardNumList:
        detailurl = detail + str(a)
        driver.get(detailurl)
        html = driver.page_source
        soup = BeautifulSoup(html, 'html.parser')


# for a in sele:
#     href = a.attrs['href'][13:]
#     # print(num, href)
#     # num = num + 1
#     cardNumList = np.append(cardNumList, href)

# cardNumList = np.delete(cardNumList, 0)


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

    while True:

        # Convert the cardInfoList2 to JSON
        json_data = json.dumps(cardInfoList)
        print(cardInfoList)
        
        json_file_path = r'C:\dev2\fin\src\main\python\json\card_info'
        json_file_path2 = r'C:\dev2\fin\src\main\python\excel\card_info'

        # 날짜 지정(파일명 설정을 위해)
        now = datetime.datetime.now()
        nowDate = now.strftime('%Y_%m_%d')
        nds = str(nowDate)
        
        with open(json_file_path + str(nds), 'w') as file:
            file.write(json_data)
            print(json_data)

            print(cardInfoList)
    
        # 엑셀 저장용
        wb = Workbook()
        ws2 = wb.active

        sheet_name = clean_sheet_name(json_file_path2 + str(nowDate))
        ws2.title = sheet_name

        headers = ["카드사", "카드명", "카드이미지", "혜택1", "혜택1내용", "혜택2", "혜택2내용", "혜택3", "혜택3내용"]
        ws2.append(headers)

        for card in cardInfoList:
            row = [
                card["카드사"], card["카드명"], card["카드이미지"],
                card["혜택1"], card["혜택1내용"], card["혜택2"],
                card["혜택2내용"], card["혜택3"], card["혜택3내용"]
            ]
            ws2.append(row)

        excel_file_path = json_file_path2 + "_" + str(nowDate) + ".xlsx"
        wb.save(filename=excel_file_path)

        for item in cardInfoList:
            print(item)


        """ json_data2 = json.dumps(item) """
        # Write the JSON data to a file
        """ with open(json_file_path, 'w') as file:
            file.write(json_data2)    
        print(json_data2) """

        time.sleep(schedule_interval)

# schedule_interval = 4 * 7 * 24 * 60 * 60 
schedule_interval = 5


scraping()

