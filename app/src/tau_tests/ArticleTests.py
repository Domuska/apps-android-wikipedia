# -*- coding: utf-8 -*-

from Utilities import TestDataSource, Utils
class ArticleTests (UITestCase):
    
    def setUp(self):
        global articleName1, articleName3, subHeading1, subHeading2,\
        subHeading3, article1_referenceSubHeading, fullLinkText, \
        articleName3_finnish, finnishLanguage, newArticleTitle
        
        articleName1 = TestDataSource.articleName1
        subHeading1 = TestDataSource.article1_subheading1
        subHeading2 = TestDataSource.article1_subheading2
        subHeading3 = TestDataSource.article1_subheading3
        article1_referenceSubHeading = TestDataSource.article1_referenceSubHeading
        
        fullLinkText = TestDataSource.fullLinkText1
        newArticleTitle = TestDataSource.newArticleTitle
        
        articleName3 = TestDataSource.articleName3
        articleName3_finnish = TestDataSource.articleName3_finnish
        finnishLanguage = TestDataSource.finnishLanguage
        
        launch.activity('org.wikipedia.alpha', 'org.wikipedia.MainActivity', verify=False)
            
    def tearDown(self):
        packages.clearData('org.wikipedia.alpha')

    @testCaseInfo('<check table of contents subtitles>', deviceCount=1)
    def testTableOfContents_checkSubTitles(self):
        
        #test does not work - seems webview is problematic
        
        Utils.openSearchFromStartScreen()
        Utils.searchAndOpenArticleWithName(articleName1)
        Utils.openToC()
        
        log("check that certain 3 topmost subheadings in table of contents show up")
        
        verify.text(subHeading1, scroll = False, area = "org.wikipedia.alpha:id/page_toc_list")
        verifytext(subHeading2, scroll = False, area = "org.wikipedia.alpha:id/page_toc_list")
        verify.text(subHeading3, scroll = False, area = "org.wikipedia.alpha:id/page_toc_list")
        
        log("make sure those same three subtitles are visible in the webview")
        Utils.closeToC()
        
        verify.description(subHeading1)
        verify.resourceId(subHeading2)
        verify.resourceId(subHeading3)
        
        
    @testCaseInfo('<click subheading, see screen moved>', deviceCount=1)
    def testScrollingToC_clickSubHeading(self):
        
        #test does not work - seems webview is problematic
        
        log("open article and table of contents")
        Utils.openSearchFromStartScreen()
        Utils.searchAndOpenArticleWithName(articleName1)
        Utils.openToC()
        
        tap.text(article1_referenceSubHeading, area = "org.wikipedia.alpha:id/page_toc_list")
        
        verify.text(article1_referenceSubHeading)
        
        
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
        #verify.text(item = get.item.resourceId("org.wikipedia.alpha:id/link_preview_title"), newArticleTitle)
        #verify.resourceId("org.wikipedia.alpha:id/link_preview_title")
        
    @testCaseInfo('<click partial link>', deviceCount=1)
    def testClickLink_partialText_assertPreviewShown(self):
        
        assert False, "test not implemented, fool"
       
        
        
    @testCaseInfo('<change language in article>', deviceCount=1)
    def testChangeLanguage(self):
        
        log("open article")
        Utils.openSearchFromStartScreen()
        Utils.searchAndOpenArticleWithName(articleName3)
        
        log("open overflow menu in toolbar")
        tap.description('More options')
        tap.text('Change language')
        
        log("change language to finnish")
        # have to do this differently than in other tests
        # since tau will click a weird language element
        # automatically without scrolling down to a proper element
        
        log(articleName3_finnish)
        tap.resourceId('org.wikipedia.alpha:id/langlinks_filter')
        input.text(finnishLanguage)
        
        tap.text(articleName3_finnish)
        
        log("assert article changed to finnish language one")
        Utils.assertArticleTitleContains(articleName3_finnish)
        
        
        
        
        
        
        
        
        
        
        