# -*- coding: utf-8 -*-
from datetime import datetime, timedelta
from Utilities import TestDataSource, Utils
class TabTests (UITestCase):

    def setUp(self):
        global articleName1, articleName2, newTabDefaultText,\
        newArticleName, newArticleName_full, newTabText
        
        articleName1 = TestDataSource.articleName1
        articleName2 = TestDataSource.articleName2
        newTabDefaultText = TestDataSource.newTabDefaultText
        
        newArticleName = TestDataSource.fullLinkText1
        newArticleName_full = TestDataSource.link1ArticleName
        newTabText = "Open in new tab"
        
        launch.activity('org.wikipedia.alpha', 'org.wikipedia.MainActivity', verify=True)
                
    def tearDown(self):
        packages.clearData('org.wikipedia.alpha')
        
        #FOLLOWING ROWS ONLY FOR TEST LOGGING PURPOSES, NOT PART OF TEST RUN
        #with help from
        #http://stackoverflow.com/questions/3096953/difference-between-two-time-intervals-in-python
        timeNow = datetime.now()
        measurementPath = r"C:\Users\\Tomi\testAutomation\measurements\wikipedia\wifi\native\tau\\"
        hourNow = str(timeNow.hour)
        minuteNow = str(timeNow.minute)
        secondNow = str(timeNow.second)
        
        #times with only 1 number cause problems when doing strptime, add 0
        if timeNow.hour < 10:
            hourNow = "0" + hourNow
        if timeNow.minute < 10:
            minuteNow = "0" + minuteNow
        if timeNow.second < 10:
            secondNow = "0" + secondNow
        
        #format test execution ending time
        endTime = hourNow + ":" + minuteNow + ":" + secondNow
        #read the start time from temp file
        tempFile = open(measurementPath + "temp.txt", "r")
        #strip extra newlines from the end of the strings
        startHour = tempFile.readline().rstrip()
        startMinute = tempFile.readline().rstrip()
        startSecond = tempFile.readline().rstrip()
        startTime = startHour + ":" + startMinute + ":" + startSecond
        log(str(startTime))
        log(str(endTime))
        
        FMT = '%H:%M:%S'
        #calculate the time it took to run the tests
        tDelta = datetime.strptime(endTime, FMT) - datetime.strptime(startTime, FMT)
        #handle the situation if day has changed between tests
        if tDelta.days < 0:
            tDelta = timedelta(days = 0,
                        seconds = tDelta.seconds, microseconds = tDelta.microseconds)
        log(tDelta.total_seconds())
        
        #write the test execution time to .csv file
        testName = "2016_10_7-12_14" + ".csv"
        filename = "tau_tests-"
        filename += testName
        log("opening .csv")
        csvFile = open(measurementPath + filename, "a")
        log("writing to csv")
        
        csvFile.write("\n" + str(tDelta.total_seconds()))
        csvFile.close()

    @testCaseInfo('<open multiple tabs>', deviceCount=1)
    def testOpenMultipleTabs(self):
       
        log("open one article")
        Utils.openSearchFromStartScreen()
        Utils.searchAndOpenArticleWithName(articleName1)
        
        log("open a new tab")
        tap.resourceId("org.wikipedia.alpha:id/menu_page_show_tabs", wait=30000)
        tap.description('More options')
        tap.text(newTabDefaultText, wait=30000)
        
        log("navigate to another article")
        Utils.openSearchFromArticle()
        #tap.resourceId('com.google.android.googlequicksearchbox:id/launcher_search_button')
        Utils.searchAndOpenArticleWithName(articleName2)
        
        log("assert you can change between them")
        tap.resourceId('org.wikipedia.alpha:id/menu_page_show_tabs')
        tap.text(articleName1)
        
        Utils.assertArticleTitleContains(articleName1)
        
        
        
        