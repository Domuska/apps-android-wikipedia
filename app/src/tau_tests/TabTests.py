# -*- coding: utf-8 -*-

from Utilities import TestDataSource, Utils
class TabTests (UITestCase):

    def setUp(self):
        global articleName1, articleName2, newTabDefaultText
        
        articleName1 = TestDataSource.articleName1
        articleName2 = TestDataSource.articleName2
        newTabDefaultText = TestDataSource.newTabDefaultText
        
        launch.activity('org.wikipedia.alpha', 'org.wikipedia.MainActivity', verify=False)
                
    def tearDown(self):
        packages.clearData('org.wikipedia.alpha')

    @testCaseInfo('<open multiple tabs>', deviceCount=1)
    def testOpenMultipleTabs(self):
       
        log("open one article")
        Utils.openSearchFromStartScreen()
        Utils.searchAndOpenArticleWithName(articleName1)
        
        log("open a new tab")
        tap.resourceId("org.wikipedia.alpha:id/menu_page_show_tabs")
        tap.description('More options')
        tap.text(newTabDefaultText)
        
        log("navigate to another article")
        Utils.openSearchFromArticle()
        Utils.searchAndOpenArticleWithName(articleName2)
        
        log("assert you can change between them")
        tap.resourceId('org.wikipedia.alpha:id/menu_page_show_tabs')
        tap.text(articleName1)
        
        Utils.assertArticleTitleContains(articleName1)
        
        
        
        
        
        
        