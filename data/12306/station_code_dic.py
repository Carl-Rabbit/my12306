import re, requests

f = open('station_code_dic.txt','w')
# 访问12306存有的所有车站
url = 'https://kyfw.12306.cn/otn/resources/js/framework/station_name.js?station_version=1.8971'
response = requests.get(url)
# 提取车站名字和代码
stations = re.findall(r'([\u4e00-\u9fa5]+)\|([A-Z]+)', response.text)
station_codes = dict(stations)
# 把车站名字和代码，交换一下，重新建立键值对
# station_names = dict(zip(station_codes.values(), station_codes.keys()))
print(station_codes)
f.write(str(station_codes))
f.close()
