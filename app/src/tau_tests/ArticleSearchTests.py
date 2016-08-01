# -*- coding: utf-8 -*-
from Utilities import TestDataSource, Utils

class ArticleSearchTests (UITestCase):
    
    def setUp(self):
        global articleName1
        articleName1 = TestDataSource.articleName1
        
        launch.activity('org.wikipedia.alpha', 'org.wikipedia.MainActivity', verify=False)

    @testCaseInfo('<Search Article>', deviceCount=1)
    def testSearchArticle_checkTitleShown(self):
        """ Insert brief description of the test case

            1. Insert test step description here
        """
        log('Step1: Insert test step description')
        #open the article
        tap.resourceId("org.wikipedia.alpha:id/search_container")
        
        
        input.text(articleName1)
        tap.text(articleName1)
        
        #check the title is displayed in the title view
        titleView = get.item.resourceId("org.wikipedia.alpha:id/view_article_header_text")
        log(titleView.Text)
        