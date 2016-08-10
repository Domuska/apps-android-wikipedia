# -*- coding: utf-8 -*-

from Utilities import TestDataSource, Utils
class ArticleTests (UITestCase):
    
    def setUp(self):
        global articleName1, articleName3, subHeading1, subHeading2,\
        subHeading3, article1_referenceSubHeading, fullLinkText, \
        articleName3_finnish, finnishLanguage, newArticleTitle, partialLinkText
        
        articleName1 = TestDataSource.articleName1
        subHeading1 = TestDataSource.article1_subheading1
        subHeading2 = TestDataSource.article1_subheading2
        subHeading3 = TestDataSource.article1_subheading3
        article1_referenceSubHeading = TestDataSource.article1_referenceSubHeading
        
        fullLinkText = TestDataSource.fullLinkText1
        newArticleTitle = TestDataSource.newArticleTitle
        
        partialLinkText = TestDataSource.partialLinkText
        
        articleName3 = TestDataSource.articleName3
        articleName3_finnish = TestDataSource.articleName3_finnish
        finnishLanguage = TestDataSource.finnishLanguage
        
        launch.activity('org.wikipedia.alpha', 'org.wikipedia.MainActivity', verify=False)
            
    def tearDown(self):
        packages.clearData('org.wikipedia.alpha')

    @testCaseInfo('<check table of contents subtitles>', deviceCount=1)
    def testTableOfContents_checkSubTitles(self):
        
        #test does not work - seems webview is problematic
        fail("WebView cannot be handled - failing test")
        Utils.openSearchFromStartScreen()
        Utils.searchAndOpenArticleWithName(articleName1)
        wait(500)
        Utils.openToC()
        
        log("check that certain 3 topmost subheadings in table of contents show up")
        
        verify.text(subHeading1, scroll = False, area = "org.wikipedia.alpha:id/page_toc_list")
        verify.text(subHeading2, scroll = False, area = "org.wikipedia.alpha:id/page_toc_list")
        verify.text(subHeading3, scroll = False, area = "org.wikipedia.alpha:id/page_toc_list")
        
        log("make sure those same three subtitles are visible in the webview")
        Utils.closeToC()
        
        verify.description(subHeading1, scroll = True)
        verify.resourceId(subHeading2, scroll = True)
        verify.resourceId(subHeading3, scroll = True)
        
    @testCaseInfo('<open and close references>', deviceCount=1)
    def testOpeningAndClosingReferences(self):
        fail("failing test, Tau can't handle webview")
        
    @testCaseInfo('<click subheading, see screen moved>', deviceCount=1)
    def testScrollingToC_clickSubHeading(self):
        
        log("open article and table of contents")
        Utils.openSearchFromStartScreen()
        Utils.searchAndOpenArticleWithName(articleName1)
        Utils.openToC()
        
        tap.text(article1_referenceSubHeading, area = "org.wikipedia.alpha:id/page_toc_list")
        
        log("make sure we the title bar is no longer visible")
        verify.no.resourceId("org.wikipedia.alpha:id/view_article_header_text")
        
        
    @testCaseInfo('<click full link>', deviceCount=1)
    def testClickLink_fullText_assertPreviewShown(self):
        
        log("open article")
        Utils.openSearchFromStartScreen()
        Utils.searchAndOpenArticleWithName(articleName1)
        
        log("click on link")
        tap.description(fullLinkText)
        
        log("assert a popup showing preview of new article shows")
        articleTitleText = get.item.resourceId("org.wikipedia.alpha:id/link_preview_title")
        verify.text(newArticleTitle, area = articleTitleText)
        
        
    @testCaseInfo('<click partial link>', deviceCount=1)
    def testClickLink_partialText_assertPreviewShown(self):
        
        fail("Fail test - Tau does not know how to handle partial links")
        log("open article")
        Utils.openSearchFromStartScreen()
        Utils.searchAndOpenArticleWithName(articleName1)
        
        log("click on partial link")
        tap.description(partialLinkText)
        
        log("assert a popup showing preview of new article shows")
        articleTitleText = get.item.resourceId("org.wikipedia.alpha:id/link_preview_title")
        verify.text(newArticleTitle, area = articleTitleText)
        
        
    @testCaseInfo('<change language in article>', deviceCount=1)
    def testChangeLanguage(self):
        
        log("open article")
        Utils.openSearchFromStartScreen()
        Utils.searchAndOpenArticleWithName(articleName3)
        
        log("open overflow menu in toolbar")
        tap.description('More options')
        tap.text('Change language')
        
        log("change language to finnish")
        
        tap.text(articleName3_finnish, direction = "vertical")
        
        log("assert article changed to finnish language one")
        Utils.assertArticleTitleContains(articleName3_finnish)
        
        
        
        
        
        
        
        
        
        
        