import os
import requests
from requests.exceptions import RequestException
from bs4 import BeautifulSoup
from selenium import webdriver
import json
import random
import time
import re
import pymysql

from selenium.common.exceptions import TimeoutException

url='https://movie.douban.com/j/new_search_subjects?sort=U&range=0,10&tags=&start='


headers = {
        "user-agent": 'ozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36'}


if __name__ == "__main__":
    option = webdriver.ChromeOptions()
    prefs = {'profile.managed_default_content_settings.images': 2}
    option.add_experimental_option("prefs", prefs)
    driver = webdriver.Chrome(chrome_options=option)
    driver.get("https://accounts.douban.com/passport/login?redir=https%3A%2F%2Fmovie.douban.com%2Fj%2Fnew_search_subjects%3Fsort%3DU%26range%3D0%2C10%26tags%3D%26start%3D0")
    driver.find_element_by_class_name("account-tab-account").click()
    driver.find_element_by_id("username").send_keys("18851822792")
    driver.find_element_by_id("password").send_keys("Z96928k")
    driver.find_element_by_link_text("登录豆瓣").click()
    result = list()
    id = 0
    for i in range(0, 80):
        print(i)
        driver.get(url + str(i * 20))
        response = BeautifulSoup(driver.page_source, "lxml")
        print(response.find('pre').string)
        jsonObject = json.loads(response.find('pre').string)
        for one in jsonObject['data']:
            id = id + 1
            film = {'title': one['title'],
                    'directors': one['directors'],
                    'rate': one['rate'],
                    'casts': one['casts'],
                    'id': id
                    }
            url2 = one['url']
            driver.get(url2)
            soup = BeautifulSoup(driver.page_source.replace('<br>','\n'), "lxml")
            type = '/'.join([k.string for k in soup.find_all('span', property='v:genre')])
            nation = soup.find(lambda e: e.name == 'span' and '制片国家/地区:' in e.text).next_sibling.replace(' ','').replace('\n','')
            language = soup.find(lambda e: e.name == 'span' and '语言:' in e.text).next_sibling.replace(' ','').replace('\n','')
            date = '/'.join([k.string for k in soup.find_all('span', property='v:initialReleaseDate')])
            if(soup.find('span',property='v:runtime')!=None):
                time = soup.find('span',property='v:runtime').string
            else:
                time='none'
            if(soup.find('span',property='v:votes')!=None):
                peopleNumber = soup.find('span', property='v:votes').string
            else:
                peopleNumber = '?'
            if(soup.find('span',property='v:summary').string!=None):
                introduction = soup.find('span',property='v:summary').string.replace(' ','').replace('\n','')
            else:
                introduction = 'none'
            film['type']=type
            film['nation']=nation
            film['language']=language
            film['date']=date
            film['time']=time
            film['peopleNumber']=peopleNumber
            film['introduction']=introduction

            #热评
            hotCommentList = list()
            hotCommentNumber = len(soup.find_all('div',class_='comment-item'))
            hotCommentAuthor = soup.find_all('span',class_='comment-info')
            hotCommentDate = soup.find_all('span',class_='comment-time')
            hotCommentContents = soup.find_all('span',class_='short')
            for number in range(0,hotCommentNumber-1):
                date=hotCommentDate[number].string.replace('\n','').replace(' ','')
                hotCommentAuthor1=hotCommentAuthor[number].find('a')
                hotComment={'hotCommentAuthor':hotCommentAuthor1.string,
                            'hotCommentDate': date,
                            'hotCommentContent': hotCommentContents[number].string}
                hotCommentList.append(hotComment)
            film['hotCommentList'] = hotCommentList
            result.append(film)
    resultString=json.dumps(result,ensure_ascii=False)
    print(resultString)
    with open('douban.txt', 'w', encoding='utf-8') as f:
        f.write(json.dumps(resultString, ensure_ascii=False))
        f.close()


