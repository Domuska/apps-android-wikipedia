# -*- coding: utf-8 -*-
from Utilities import TestDataSource, Utils
class RotationTests (UITestCase):
    
    def setUp(self):
        
        global articleName1, firstSubHeading, link1, link1ArticleName
        articleName1 = TestDataSource.articleName1
        firstSubHeading = TestDataSource.article1_firstSubHeading
        link1 = TestDataSource.fullLinkText1
        link1ArticleName = TestDataSource.link1ArticleName
        
        launch.activity('org.wikipedia.alpha', 'org.wikipedia.MainActivity', verify=True)
                
    def tearDown(self):
        orientation.portrait()
        packages.clearData('org.wikipedia.alpha')
        

    @testCaseInfo('<rotate phone when Toc open>', deviceCount=1)
    def testOpenToC_rotatePhone(self):
       
        Utils.openSearchFromStartScreen()
        Utils.searchAndOpenArticleWithName(articleName1)
        wait(1500)
        Utils.openToC()
        
        log("assert list is visible")
        verify.resourceId("org.wikipedia.alpha:id/page_toc_list")
        verify.text(firstSubHeading)
        
        log("rotate screen")
        orientation.left()
        
        log("assert list is still visible")
        verify.resourceId("org.wikipedia.alpha:id/page_toc_list")
        verify.text(firstSubHeading)
        