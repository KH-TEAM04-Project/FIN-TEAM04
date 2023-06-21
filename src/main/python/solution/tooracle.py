import numpy as np
import oracledb

# DB 접속
oracledb.init_oracle_client(lib_dir=r"C:\Oracle\instantclient_11_2")
con = oracledb.connect(user="team4", password="1234", dsn="localhost:1521/xe")
cursor = con.cursor()
 
# # Insert (Write to DB)
# in_data = "Hello World~ 안녕 오라클-파이썬"
# cursor.execute("insert into TEST_TBL (aa) values (:1)", [in_data])
# con.commit()


def insertDB(data):
    benelist = ['all', 'airport', 'edu', 'tran', 'cultural', 'delivery' ,'stores', 'giftCard', 'food', 'shop', 'post', 'medi', 'cloth', 'commi', 'travel', 'gas', 'coffee', 'commu', 'conveni', 'overseas', 'foreign', 'hotel', 'public']
    columnlist = ['cardno', 'cname', 'bname', 'imgadd', 'allfor', 'airport', 'edu', 'tran', 'cultural', 'delivery' ,'stores', 'giftCard', 'food', 'shop', 'post', 'medi', 'cloth', 'commi', 'travel', 'gas', 'coffee', 'commu', 'conveni', 'overseas', 'foreign', 'hotel', 'publicfee']
    dataset = np.empty((0, 27), str)

    for x in range(0, len(data), 1):
        bankname = data[x][0]
        cardname = data[x][1]
        dataset[1] = cardname
        dataset[2] = bankname

        if data[x][9] != "":
            dataset[3] = data[x][9]

        for y in range(2, 7, 2):
            bene = data[x][y]
            for z in range(0, len(benelist), 1):
                if (bene == benelist[z]):
                    benenum = z + 4

                    dataset[benenum] = benelist[z]
                    dataset[benenum + 1] = data[x][y + 1]
                    
    print(dataset)
    # cursor.execute("insert into Cardinfo (aa) values (:1)", dataset)



    # con.commit()
    

 
# Select (Read from DB)
cursor.execute("select * from members")
out_data = cursor.fetchone()
print("=====>", out_data)
 
con.close()

