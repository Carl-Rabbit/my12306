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



sqlSentence3 = "create table time_details (station_train_code varchar(10),station_no varchar(2),station_name varchar(10),arrive_day_str varchar(6),arrive_day_diff varchar(2),arrive_time time(0) without time zone,running_time interval)"
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
        sqlSentence4 = "insert into time_details (station_train_code,station_no,station_name,arrive_day_str,arrive_day_diff,arrive_time,running_time) values ('%s','%s','%s','%s','%s','%s','%s')" % record
        sqlSentence4 = sqlSentence4.replace('\'----\'', 'null')
        print(sqlSentence4)
        print(record[0])
        if record[0]!='station_train_code':
            cursor.execute(sqlSentence4)
            continue

    except Exception as e:
        print("插入数据失败!")
        print(e)
        continue



#close

conn.commit()
cursor.close()
conn.close()