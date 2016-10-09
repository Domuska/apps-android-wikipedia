# -*- coding: utf-8 -*-
from datetime import datetime, time
from Utilities import TestDataSource, Utils
class ArticleTests (UITestCase):
    
    def setUp(self):
        
        #FOLLOWING ROWS ONLY FOR TEST LOGGING PURPOSES, NOT PART OF TEST
        log("write starting time to .csv for measurement purposes")
        timeNow = datetime.now()
        hourNow = str(timeNow.hour)
        minuteNow = str(timeNow.minute)
        secondNow = str(timeNow.second)
        
        if timeNow.hour < 10:
            hourNow = "0" + hourNow
        if timeNow.minute < 10:
            minuteNow = "0" + minuteNow
        if timeNow.second < 10:
            secondNow = "0" + secondNow
        
        #read time to temp file, used in the last test to calculate test execution time
        tempFile = open(r"C:\Users\\Tomi\testAutomation\measurements\wikipedia\wifi\native\tau\temp.txt", "w")
        tempFile.write(hourNow + "\n")
        tempFile.write(minuteNow + "\n")
        tempFile.write(secondNow)
        tempFile.close()
        #TEST CODE STARTS FROM HERE
        
        global articleName1, article1_referenceSubHeading

        articleName1 = TestDataSource.articleName1
        article1_referenceSubHeading = TestDataSource.article1_referenceSubHeading
        
        launch.activity('org.wikipedia.alpha', 'org.wikipedia.MainActivity', verify=True)
            
    def tearDown(self):
        packages.clearData('org.wikipedia.alpha')
        
        
    @testCaseInfo('<click subheading, see screen moved>', deviceCount=1)
    def testScrollingToC_clickSubHeading(self):
        
        log("open article and table of contents")
        Utils.openSearchFromStartScreen()
        Utils.searchAndOpenArticleWithName(articleName1)
        Utils.openToC()
        
        tap.text(article1_referenceSubHeading, area = "org.wikipedia.alpha:id/page_toc_list")
        
        log("make sure we the title bar is no longer visible")
        verify.no.resourceId("org.wikipedia.alpha:id/view_article_header_text")