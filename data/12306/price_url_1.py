import requests
import time
from urllib import request
import datetime

from colorama import init, Fore

from prettytable import PrettyTable
from bs4 import BeautifulSoup as bs
import json
import random

f = open('price_interval_1.txt')
f1 = open('price_data_1.txt','w',encoding='utf-8')
f2 = open('station_code_dic.txt','r')
f3 = open('train_code_no_dic.txt','r')
f4 = open('failed_url_1.txt','w')
station_code_dic = eval((f2.read()))
train_code_no_dic = eval((f3.read()))
print(train_code_no_dic)
line = f.readline()
number = 98
ps = '870842634.50215.0000'
s = '1289289994.24610.0000'
while line:

    try:
        array = line.split(',')
        train_no = train_code_no_dic[str(array[0])]
        from_station_name = array[2]
        to_station_name = (array[4]).replace('\n', '')
        from_station_code = station_code_dic[from_station_name]
        to_station_code = station_code_dic[to_station_name]
        url = 'https://kyfw.12306.cn/otn/leftTicket/queryTicketPrice?train_no={}&from_station_no={}&to_station_no={}&seat_types=OMO9FJIA&train_date=2020-05-15'.format(
            train_no, array[1].zfill(2), array[3].zfill(2))
        c = 'JSESSIONID=06B92B40A074258CB97CAC55E66A7ABA; _jc_save_wfdc_flag=dc; RAIL_DEVICEID=Xbr2o8n1iDsGmUKcBQmrm3SDa8z0yqzZ_c5sg5EuMTbvJ51ZNJw31HiVYTa0sMukSeS6krmo4Npkij41EGL_M6z6R0R6XyL1gxUoTGBT4S9MhBYUx8yjozHcR7qKqEtudPq6O_Ol7vESMBhmB8fU8fgJIXGIFgPr; RAIL_EXPIRATION=1589147970550; _jc_save_zwdch_cxlx=0; _jc_save_zwdch_fromStation=%u897F%u5B89%2CXAY; _jc_save_toDate=2020-05-09; BIGipServerpool_passport='+ps+'; route=495c805987d0f5c8c84b14f60212447d; BIGipServerotn='+s+'; _jc_save_fromStation={}%2C{}; _jc_save_toStation={}%2C{}; _jc_save_fromDate=2020-05-23'.format(
            str(from_station_name.encode('unicode_escape')).replace('\\\\', '%').replace('b', '').replace('\'','').upper().replace('%U', '%u'), from_station_code,
            str(to_station_name.encode('unicode_escape')).replace('\\\\', '%').replace('b', '').replace('\'','').upper().replace('%U', '%u'), to_station_code)
        cookie = {'Cookie': c}
        header = {'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36'}
        r = requests.get(url, headers=header, cookies=cookie)
        content = r.content.decode()
        if(content[0]=='{'):
            print(array[0] + ',' + from_station_name + ',' + to_station_name + ',' + content + '\n')
            f1.write(array[0] + ',' + from_station_name + ',' + to_station_name + ',' + content + '\n')
        else:
            f4.write(array[0] + ',' + train_no + ',' + from_station_name + ',' + from_station_code + ',' + to_station_name + ',' + to_station_code + ',' + 'OMO9FJIA' + '\n')
            print("--------------------------------------------------------------------------------------------获取数据失败-----------------------------------------------------------------------------")

    except Exception:
        print(Exception)

    try:
        array = line.split(',')
        train_no = train_code_no_dic[str(array[0])]
        from_station_name = array[2]
        to_station_name = (array[4]).replace('\n', '')
        from_station_code = station_code_dic[from_station_name]
        to_station_code = station_code_dic[to_station_name]
        url = 'https://kyfw.12306.cn/otn/leftTicket/queryTicketPrice?train_no={}&from_station_no={}&to_station_no={}&seat_types=34112&train_date=2020-05-15'.format(
            train_no, array[1].zfill(2), array[3].zfill(2))
        c = 'JSESSIONID=06B92B40A074258CB97CAC55E66A7ABA; _jc_save_wfdc_flag=dc; RAIL_DEVICEID=Xbr2o8n1iDsGmUKcBQmrm3SDa8z0yqzZ_c5sg5EuMTbvJ51ZNJw31HiVYTa0sMukSeS6krmo4Npkij41EGL_M6z6R0R6XyL1gxUoTGBT4S9MhBYUx8yjozHcR7qKqEtudPq6O_Ol7vESMBhmB8fU8fgJIXGIFgPr; RAIL_EXPIRATION=1589147970550; _jc_save_zwdch_cxlx=0; _jc_save_zwdch_fromStation=%u897F%u5B89%2CXAY; _jc_save_toDate=2020-05-09; BIGipServerpool_passport=' + ps + '; route=495c805987d0f5c8c84b14f60212447d; BIGipServerotn=' + s + '; _jc_save_fromStation={}%2C{}; _jc_save_toStation={}%2C{}; _jc_save_fromDate=2020-05-23'.format(
            str(from_station_name.encode('unicode_escape')).replace('\\\\', '%').replace('b', '').replace('\'','').upper().replace('%U', '%u'), from_station_code,
            str(to_station_name.encode('unicode_escape')).replace('\\\\', '%').replace('b', '').replace('\'','').upper().replace('%U', '%u'), to_station_code)
        cookie = {'Cookie': c}
        header = {'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36'}
        r = requests.get(url, headers=header, cookies=cookie)
        content = r.content.decode()
        if (content[0] == '{'):
            print(array[0] + ',' + from_station_name + ',' + to_station_name + ',' + content + '\n')
            f1.write(array[0] + ',' + from_station_name + ',' + to_station_name + ',' + content + '\n')
        else:
            f4.write(array[0] + ',' + train_no + ',' + from_station_name + ',' + from_station_code + ',' + to_station_name + ',' + to_station_code + ',' + 'OMO9FJIA' + '\n')
            print(
                "--------------------------------------------------------------------------------------------获取数据失败-----------------------------------------------------------------------------")

    except Exception:
        print(Exception)


    line = f.readline()

f.close()
f1.close()
f2.close()
f3.close()
f4.close()
