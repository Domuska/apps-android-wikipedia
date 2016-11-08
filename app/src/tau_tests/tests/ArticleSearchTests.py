# -*- coding: utf-8 -*-
from Utilities import TestDataSource, Utils
class ArticleSearchTests (UITestCase):
    
    def setUp(self):
        global articleName1, articleName2, articleName3, recentSearchesText,\
        finnishLanguage, articleName3_finnish
        articleName1 = TestDataSource.articleName1
        articleName2 = TestDataSource.articleName2
        articleName3 = TestDataSource.articleName3
        
        recentSearchesText = "Recent searches:"
        
        finnishLanguage = TestDataSource.finnishLanguage
        articleName3_finnish = TestDataSource.articleName3_finnish
        
        launch.activity('org.wikipedia.alpha', 'org.wikipedia.MainActivity', verify=True)
        
    def tearDown(self):
        packages.clearData('org.wikipedia.alpha')

    @testCaseInfo('<Search Article>', deviceCount=1)
    def testSearchArticle_checkTitleShown(self):
        
        #open the article
        Utils.openSearchFromStartScreen()
        Utils.searchAndOpenArticleWithName(articleName1)
        
        #check the title is displayed in the title view
        Utils.assertArticleTitleContains(articleName1)
        
        
    @testCaseInfo('<Check recents>', deviceCount=1)
    def testSearchArticle_checkRecentsShown(self):
        
        log("open some articles")
        Utils.openSearchFromStartScreen()
        Utils.searchAndOpenArticleWithName(articleName1)
        
        Utils.openSearchFromArticle()
        Utils.searchAndOpenArticleWithName(articleName2)
        
        Utils.openSearchFromArticle()
        Utils.searchAndOpenArticleWithName(articleName3)
        
        log("go to the search screen")
        Utils.openSearchFromArticle()
        tap.resourceId("org.wikipedia.alpha:id/search_close_btn")
        verify.text(recentSearchesText)
        
        log("assert that the recent searches show the articles searched")
        verify.text(articleName1)
        verify.text(articleName2)
        verify.text(articleName3)
        
        
        
        
        
        