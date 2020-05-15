from bs4 import BeautifulSoup as bs

f = open('station_info_0.txt',encoding='utf-8')
f1 = open('station_info_1.txt','w',encoding='utf-8')
soup = bs(f.read(),'lxml')
companys = soup.find_all('mark')
for company in companys:
    stations = company.find_all('tr')
    for station in stations:
        cols = station.find_all('td')
        for i in range(0,len(cols)):
            f1.write(str(cols[i].string)+',')
        f1.write('|'+'\n')





f.close()
f1.close()