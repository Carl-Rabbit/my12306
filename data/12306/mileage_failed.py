import requests
import time
from urllib import request
import datetime

from colorama import init, Fore

from prettytable import PrettyTable
from bs4 import BeautifulSoup as bs
import json
import random

f = open('failed_mileage.txt','r')
f1 =open('train_code_time_mileage_1.txt','w')
f2 = open('failed_mileage_1.txt','w')
line  = f.readline()
while line:
    train_code = line.split(',')[0]
    try:
        url = line.split(',')[1].replace('\n', '')
        # print(url)
        header = {
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36'}
        soup = bs(requests.get(url, headers=header).content.decode(), 'lxml')
        timetable = soup.find_all('tr', onmouseout="this.bgColor=''")
        for station in timetable:
            cols = station.find_all('td')
            s = ((train_code).replace('\n', '')) + ','
            for i in range(0, 6):
                s = s + cols[i].string + ','
            s = s + '\n'
            print(s)
            f1.write(s)
        line = f.readline()
    except Exception:
        print('------------------------------------------获取数据失败----------------------------------------------')
        failed = (train_code)+','+url+'\n'
        print(failed)
        f2.write(failed)

f.close()
f1.close()
f2.close()

