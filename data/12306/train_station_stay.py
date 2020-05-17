import requests
import time
from urllib import request
import datetime

from colorama import init, Fore

from prettytable import PrettyTable
from bs4 import BeautifulSoup as bs
import json
import random


f1 =open('train_station_stay.txt','w')
f2 = open('failed_station_stay.txt','w')
f3 = open('train_code_start_end_station_no.txt','r')
station_code_dic = eval(open('station_code_dic.txt','r').read())
# count= 0
line = ' '
while line:
    line = f3.readline()
    try:
        stations = line.split(',')[1].split('-')
        print(line.split(',')[2])
        url = 'https://kyfw.12306.cn/otn/czxx/queryByTrainNo?train_no=' + line.split(',')[2].replace('\n',
                                                                                                     '') + '&from_station_telecode=' + \
              station_code_dic[stations[0]] + '&to_station_telecode=' + station_code_dic[
                  stations[1]] + '&depart_date=2020-05-15'
        r = requests.get(url)
        array = json.loads((r.content.decode()))['data']['data']
        num = len(array)

        if (num != 0):
            s0 = array[0]['station_train_code'] + ',' + array[0]['train_class_name'] + ',' + array[0][
                'service_type'] + ',' + array[0]['start_station_name'] + ',' + array[0]['end_station_name'] + ','
            for item in array:
                s = s0 + item['arrive_time'] + ',' + item['station_name'] + ',' + item['start_time'] + ',' + item[
                    'stopover_time'] + ',' + item['station_no'] + '\n'
                print(s)
                f1.write(s)
        else:
            url = url.replace('2020-05-16')
            r = requests.get(url)
            array = json.loads((r.content.decode()))['data']['data']
            num = len(array)

            if (num != 0):
                s0 = array[0]['station_train_code'] + ',' + array[0]['train_class_name'] + ',' + array[0][
                    'service_type'] + ',' + array[0]['start_station_name'] + ',' + array[0]['end_station_name'] + ','
                for item in array:
                    s = s0 + item['arrive_time'] + ',' + item['station_name'] + ',' + item['start_time'] + ',' + item[
                        'stopover_time'] + ',' + item['station_no'] + '\n'
                    print(s)
                    f1.write(s)
            else:
                print('------------------------------------------------------------------------------------------------------------无相关数据')
        # count=0

    except Exception:
        print(
            '----------------------------------------------------获取数据失败--------------------------------------------------------')
        # count = count+1
        # if (count>5):
        #     time.sleep(30)
        # else:
        #     time.sleep(1)
        print(url)
        f2.write(url + '\n')


f1.close()
f2.close()

