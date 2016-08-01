# -*- coding: utf-8 -*-

from Utilities import TestDataSource, Utils
class ArticleTests (UITestCase):
    
    def setUp(self):
        global articleName1, subHeading1, subHeading2, subHeading3, \
        article1_referenceSubHeading 
        
        articleName1 = TestDataSource.articleName1
        subHeading1 = TestDataSource.article1_subheading1
        subHeading2 = TestDataSource.article1_subheading2
        subHeading3 = TestDataSource.article1_subheading3
        article1_referenceSubHeading = TestDataSource.article1_referenceSubHeading
        
        launch.activity('org.wikipedia.alpha', 'org.wikipedia.MainActivity', verify=False)
            
    def tearDown(self):
        packages.clearData('org.wikipedia.alpha')

    @testCaseInfo('<check table of contents subtitles>', deviceCount=1)
    def testTableOfContents_checkSubTitles(self):
        
        Utils.openSearchFromStartScreen()
        Utils.searchAndOpenArticleWithName(articleName1)
        Utils.openToC()
        
        log("check that certain 3 topmost subheadings in table of contents show up")
        
        assert exists.text(subHeading1, scroll = False, area = "org.wikipedia.alpha:id/page_toc_list")
        assert exists.text(subHeading2, scroll = False, area = "org.wikipedia.alpha:id/page_toc_list")
        assert exists.text(subHeading3, scroll = False, area = "org.wikipedia.alpha:id/page_toc_list")
        
        log("make sure those same three subtitles are visible in the webview")
        Utils.closeToC()
        
        assert find.description(subHeading1), \
        "subheading: " + subHeading1 + " not found"

        assert find.resourceId(subHeading2), \
        "subheading: " + subHeading2 + " not found"

        assert find.resourceId(subHeading3), \
        "subheading: " + subHeading3 + " not found"   
        
        
    @testCaseInfo('<click subheading, see screen moved>', deviceCount=1)
    def testScrollingToC_clickSubHeading(self):
        
        log("open article and table of contents")
        Utils.openSearchFromStartScreen()
        Utils.searchAndOpenArticleWithName(articleName1)
        Utils.openToC()
        
        tap.text(article1_referenceSubHeading, area = "org.wikipedia.alpha:id/page_toc_list")
        
        assert exists.text(article1_referenceSubHeading), \
        "subheading: " + article1_referenceSubHeading + " not visible on screen"
        
        
        
        
        
        
        