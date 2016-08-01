# -*- coding: utf-8 -*-
from Utilities import TestDataSource, Utils

class ArticleSearchTests (UITestCase):
    
    def setUp(self):
        global articleName1
        articleName1 = TestDataSource.articleName1
        
        launch.activity('org.wikipedia.alpha', 'org.wikipedia.MainActivity', verify=False)
        
    def tearDown(self):
        packages.clearData('org.wikipedia.alpha')

    @testCaseInfo('<Search Article>', deviceCount=1)
    def testSearchArticle_checkTitleShown(self):
        
        #open the article
        Utils.openSearchFromStartScreen()
        Utils.searchAndOpenArticleWithName(articleName1)
        
        #check the title is displayed in the title view
        Utils.assertArticleTitleContains(articleName1)
        