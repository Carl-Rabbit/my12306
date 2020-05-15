from ast import literal_eval

import requests
import re
import requests,json,sys
from bs4 import BeautifulSoup as bs
from selenium import webdriver

f = open('station_info_url.txt','r')
f1 = open('station_info_1.txt','w',encoding='utf-8')
browser = webdriver.Chrome()
lines = f.readlines()
for line in lines:
    c = browser.get(line)
    content = browser.page_source
    f1.write(browser.page_source+'\n')
    print('\n\n\n\n\n\n\n')
    # rows = soup0.find_all('tr')
    # print(str(rows) + '\n')












# soup1 = bs(rows[2],'lxml')
# print(soup1.find_all('td')[2])


f.close()
f1.close()
