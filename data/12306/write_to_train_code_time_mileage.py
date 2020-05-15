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
filepath = "train_code_time_mileage.csv"


sqlSentence3 = "create table train_code_time_mileage (train_code varchar(10),station_no varchar(3),station_name varchar(20),arrive_time time(0) without time zone,leave_time time(0) without time zone,runtime interval,mileage int)"
try:
    cursor.execute(sqlSentence3)
    print("创建表成功")
except:
    print('数据表已存在！')



# data = pd.read_csv(filepath, encoding="gbk")
# print('正在存储')
# length = len(data)
# for i in range(0, length-1):
#     record = tuple(data.loc[i])
#
#     try:
#         sqlSentence4 = "insert into train_code_time_mileage (train_code,station_no,station_name,arrive_time ,leave_time ,runtime) values ('%s','%s','%s','%s','%s','%s',%d)" % record
#         sqlSentence4.replace('-','null')
#         print(sqlSentence4)
#         cursor.execute(sqlSentence4)
#     except Exception as e:
#         print("插入数据失败!")
#         print(e)
#         continue
#
#
#
# #close

f = open('train_code_time_mileage.txt','r')
print('正在存储')
line = ' '
while line:
    line = f.readline()
    array = line.split(',')
    try:
        mileage = array[6].replace('\n','')
        if(mileage != 'null'):
            mileage1 = int(mileage)
        else:
            mileage1 = 'null'
        if (array[3] == 'null'):
            array[3] = array[4]
        sqlSentence4 = "insert into train_code_time_mileage (train_code,station_no,station_name,arrive_time ,leave_time ,runtime,mileage) values ('{}','{}','{}','{}','{}','{}',{})".format(array[0],array[1],array[2],array[3],array[4],array[5],mileage1)
        print(sqlSentence4)
        cursor.execute(sqlSentence4)
    except Exception as e:
        print("插入数据失败!")
        print(sqlSentence4)
        print(e)
        continue



#close

conn.commit()
cursor.close()
conn.close()