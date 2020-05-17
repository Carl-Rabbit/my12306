from ast import literal_eval
import requests
import re
import requests,json,sys
import pandas as pd
f = open('train_code_train_no.txt', 'r')
f1 = open('train_no_0.txt','w')
line = f.readline()
while line:
    d = eval(line)
    print(d['station_train_code']+','+d['train_no']+'\n')
    f1.write(d['station_train_code']+','+d['train_no']+'\n')
    line = f.readline()
f.close()
f1.close()
