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
filepath = "station_code.csv"


sqlSentence3 = "create table station_code (station_code varchar(10),station_name varchar(10))"
try:
    cursor.execute(sqlSentence3)
    print("创建表成功")
except:
    print('数据表已存在！')


data = pd.read_csv(filepath, encoding="gbk")
print('正在存储')
length = len(data)
for i in range(0, length-1):
    record = tuple(data.loc[i])
    try:
        sqlSentence4 = "insert into station_code (station_code,station_name) values ('%s','%s')" % record
        print(sqlSentence4.replace('\' ','\''))
        cursor.execute(sqlSentence4.replace('\' ','\''))
    except Exception as e:
        print("插入数据失败!")
        print(e)
        continue

#close

conn.commit()
cursor.close()
conn.close()