import requests
import time
import random
from urllib import request
import datetime

from colorama import init, Fore

from prettytable import PrettyTable
from bs4 import BeautifulSoup as bs
import json
from selenium import webdriver

header = {
'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36'
}
cookie = {
'Cookie': 'JSESSIONID=6C2164519F70ACFC6953FCBAD225DFE0; _jc_save_wfdc_flag=dc; RAIL_DEVICEID=Xbr2o8n1iDsGmUKcBQmrm3SDa8z0yqzZ_c5sg5EuMTbvJ51ZNJw31HiVYTa0sMukSeS6krmo4Npkij41EGL_M6z6R0R6XyL1gxUoTGBT4S9MhBYUx8yjozHcR7qKqEtudPq6O_Ol7vESMBhmB8fU8fgJIXGIFgPr; RAIL_EXPIRATION=1589147970550; _jc_save_zwdch_cxlx=0; _jc_save_zwdch_fromStation=%u897F%u5B89%2CXAY; _jc_save_fromStation=%u90A3%u66F2%2CNQO; _jc_save_toStation=%u683C%u5C14%u6728%2CGRO; _jc_save_fromDate=2020-05-09; _jc_save_toDate=2020-05-09; BIGipServerpassport=954728714.50215.0000; route=9036359bb8a8a461c164a04f8f50b252; BIGipServerotn=1977155850.50210.0000'
}

f = open('price_url.txt',encoding='utf-8')
f1 = open('price_data.txt','w',encoding='utf-8')




for i in range(0,10):
    line = f.readline()
    array = line.split(',')
    cookie = {
        'Cookie': 'JSESSIONID=146DED78E9F2EE12D9CFF796A171341A; _jc_save_wfdc_flag=dc; RAIL_DEVICEID=Xbr2o8n1iDsGmUKcBQmrm3SDa8z0yqzZ_c5sg5EuMTbvJ51ZNJw31HiVYTa0sMukSeS6krmo4Npkij41EGL_M6z6R0R6XyL1gxUoTGBT4S9MhBYUx8yjozHcR7qKqEtudPq6O_Ol7vESMBhmB8fU8fgJIXGIFgPr; RAIL_EXPIRATION=1589147970550; _jc_save_zwdch_cxlx=0; _jc_save_zwdch_fromStation=%u897F%u5B89%2CXAY; _jc_save_toDate=2020-05-09; BIGipServerpool_passport=384631306.50215.0000; route=c5c62a339e7744272a54643b3be5bf64; BIGipServerotn=334496266.50210.0000; _jc_save_toStation=; _jc_save_fromStation=; _jc_save_fromDate=2020-05-26'}
    r = requests.get(array[3], headers=header, cookies=cookie)
    print(r.content.decode())
    print(array[3])
    time.sleep(3)











f.close()
f1.close()

