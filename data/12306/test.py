import requests
import time
from urllib import request
import datetime

from colorama import init, Fore

from prettytable import PrettyTable
from bs4 import BeautifulSoup as bs, BeautifulSoup
import json
from selenium import webdriver

import requests
def IPList_61():
  for q in [1,9]:
      url='http://www.66ip.cn/'+str(q)+'.html'
      html=requests.get(url)
      if html!=None:
          print(html)
          iplist=BeautifulSoup(html,'lxml')
          iplist=iplist.find_all('tr')
          i=2

      time.sleep(1)

