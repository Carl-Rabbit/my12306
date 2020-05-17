#-*- coding:utf-8 -*-
import psycopg2
import pandas as pd
import os
from itertools import islice
import csv
#导入包
conn = psycopg2.connect(database='my_railway',user='super0',password='123456',host='120.76.175.113',port='5432')
#连接配置
cursor = conn.cursor()
'''sqlSentence1 = "create database stockDataBase;"
cur.execute(sqlSentence1)
sqlSentence2 = "use stockDatabase;"
cur.execute(sqlSentence2)'''
filepath = "station_info_1.csv"


sqlSentence3 = "create table station_info (station_name varchar(30),station_address varchar(50),station_service_elevator varchar(5),station_service_luggage varchar(5),station_service_package varchar(5))"
try:
    cursor.execute(sqlSentence3)
    print("创建表成功")
except:
    print('数据表已存在！')


data = pd.read_csv(filepath, encoding="utf-8")
print('正在存储')
length = len(data)
for i in range(0, length-1):
    record = tuple(data.loc[i])
    try:
        sqlSentence4 = "insert into station_info (station_name,station_address,station_service_elevator,station_service_luggage,station_service_package) values ('%s','%s','%s','%s','%s')" % record
        print(sqlSentence4)
        cursor.execute(sqlSentence4)
    except Exception as e:
        print("插入数据失败!")
        print(e)
        continue

#close

conn.commit()
cursor.close()
conn.close()