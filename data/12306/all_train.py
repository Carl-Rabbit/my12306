from ast import literal_eval

import requests
import re
import requests,json,sys


url = 'https://kyfw.12306.cn/otn/resources/js/query/train_list.js?scriptVersion=5.0'
r = requests.get(url).text
print(type(r))
f = open('train_code_train_no.txt', 'w')
f.write(r)
f.close()