# -*- coding: utf-8 -*-
from Utilities import TestDataSource, Utils
class HistoryTests (UITestCase):

    def setUp(self):
        global historyText, articleName1, articleName2, articleName3
        historyText = "History"
        articleName1 = TestDataSource.articleName1
        articleName2 = TestDataSource.articleName2
        articleName3 = TestDataSource.articleName3
        
        launch.activity('org.wikipedia.alpha', 'org.wikipedia.MainActivity', verify=False)

    def tearDown(self):
        packages.clearData('org.wikipedia.alpha')

    @testCaseInfo('<history test>', deviceCount=1)
    def testHistory(self):
       
        log("open couple of articles")
        Utils.openSearchFromStartScreen()
        Utils.searchAndOpenArticleWithName(articleName1)
        
        Utils.openSearchFromArticle()
        Utils.searchAndOpenArticleWithName(articleName2)
        
        Utils.openSearchFromArticle()
        Utils.searchAndOpenArticleWithName(articleName3)
        
        log("go to history to see that they are visible")
        Utils.openDrawer()
        tap.text(historyText)
        
        log("make assertions")
        verify.text(articleName1)
        verify.text(articleName2)
        verify.text(articleName3)
                
        