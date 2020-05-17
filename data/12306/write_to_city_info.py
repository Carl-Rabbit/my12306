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
filepath = "city_info.csv"


sqlSentence3 = "create table city_info (city_code varchar(6),province_name varchar(20),city_name varchar(20),county_name varchar(20),complete_name varchar(50))"
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
        sqlSentence4 = "insert into city_info (city_code,province_name,city_name ,county_name,complete_name ) values ('%s','%s','%s','%s','%s')" % record
        print(sqlSentence4.replace('\'\'','null'))
        cursor.execute(sqlSentence4)
    except Exception as e:
        print("插入数据失败!")
        print(e)
        continue



#close

conn.commit()
cursor.close()
conn.close()