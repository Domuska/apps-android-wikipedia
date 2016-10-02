# -*- coding: utf-8 -*-

from Utilities import TestDataSource, Utils
class ArticleTests (UITestCase):
    
    def setUp(self):
        global articleName1, article1_referenceSubHeading

        articleName1 = TestDataSource.articleName1
        article1_referenceSubHeading = TestDataSource.article1_referenceSubHeading
        
        launch.activity('org.wikipedia.alpha', 'org.wikipedia.MainActivity', verify=False)
            
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