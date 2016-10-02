# -*- coding: utf-8 -*-
from Utilities import TestDataSource, Utils
class LanguageTests(UITestCase):

    def setUp(self):
        global articleName, finnishLanguage, articleName_finnish
        articleName = TestDataSource.articleName3
        finnishLanguage = TestDataSource.finnishLanguage
        articleName_finnish = TestDataSource.articleName3_finnish
        launch.activity('org.wikipedia.alpha', 'org.wikipedia.MainActivity', verify=False)
        
    def tearDown(self):
        packages.clearData('org.wikipedia.alpha')

    @testCaseInfo('<Change language in search>', deviceCount=1)
    def testSearchArticle_changeLanguageInSearch(self):
            
        Utils.openSearchFromStartScreen()
        Utils.searchAndOpenArticleWithName(articleName)
        
        log("check title is shown in default language")
        Utils.assertArticleTitleContains(articleName)
        
        log("change language in search")
        Utils.openSearchFromArticle()
        tap.resourceId("org.wikipedia.alpha:id/search_lang_button")
        input.text(finnishLanguage)
        tap.resourceId("org.wikipedia.alpha:id/localized_language_name")
        
        log("open article again")
        tap.text(articleName_finnish, resourceId="org.wikipedia.alpha:id/page_list_item_title")
        
        log("check language is changed")
        Utils.assertArticleTitleContains(articleName_finnish)
        
    
    @testCaseInfo('<change language in article>', deviceCount=1)
    def testSearchArticle_changeLanguageInArticle(self):
        
        log("open article")
        Utils.openSearchFromStartScreen()
        Utils.searchAndOpenArticleWithName(articleName)
        
        log("open overflow menu in toolbar")
        tap.description('More options')
        tap.text('Change language')
        
        log("change language to finnish")
        
        tap.text(articleName_finnish, direction = "vertical")
        
        log("assert article changed to finnish language one")
        Utils.assertArticleTitleContains(articleName_finnish)
        
    