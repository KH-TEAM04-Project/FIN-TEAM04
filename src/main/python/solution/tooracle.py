import numpy as np
import oracledb
import re

# DB 접속
oracledb.init_oracle_client(lib_dir=r"C:\Oracle\instantclient_11_2")
con = oracledb.connect(user="team4", password="1234", dsn="localhost:1521/xe")
cursor = con.cursor()
 
# # Insert (Write to DB)
def insertDB(data):
    benelist = ['all', 'airport', 'edu', 'tran', 'cultural', 'delivery' ,'stores', 'giftCard', 'food', 'shop', 'post', 'medi', 'cloth', 'commi', 'travel', 'gas', 'coffee', 'commu', 'conveni', 'overseas', 'foreign', 'hotel', 'public']
    columnlist = ['cardno', 'cname', 'bname', 'imgadd', 'allfor', 'airport', 'edu', 'tran', 'cultural', 'delivery' ,'stores', 'giftCard', 'food', 'shop', 'post', 'medi', 'cloth', 'commi', 'travel', 'gas', 'coffee', 'commu', 'conveni', 'overseas', 'foreign', 'hotel', 'publicfee']
    # dataset = np.zeros((0, 27), str)

    for x in range(0, len(data), 1):
        dataset = ['', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '']
        bankname = data[x][0]
        cardname = data[x][1]
        dataset[0] = str(x + 1)
        dataset[1] = cardname
        dataset[2] = bankname

        if data[x][8] != None:
            dataset[3] = data[x][8]

        for y in range(2, 7, 2):
            bene = data[x][y]
            for z in range(0, len(benelist), 1):
                if (bene == benelist[z]):
                    benenum = z + 4

                    # dataset[benenum] = benelist[z]
                    dataset[benenum] = data[x][y + 1]
                    
        print(dataset)
        cursor.execute("insert into Cardinfo(cardno, cname, bname, imgadd, allfor, airport, edu, tran, cultural, delivery, stores, giftCard, food, shop, post, medi, cloth, commi, travel, gas, coffee, commu, conveni, overseas, foreign, hotel, publicfee) values (:1, :2, :3, :4, :5, :6, :7, :8, :9, :10, :11, :12, :13, :14, :15, :16, :17, :18, :19, :20, :21, :22, :23, :24, :25, :26, :27)", dataset)

    con.commit()
    con.close()
    
 
# Select (Read from DB)
cursor.execute("select * from members")
out_data = cursor.fetchone()
print("=====>", out_data)
 

