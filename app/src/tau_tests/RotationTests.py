# -*- coding: utf-8 -*-
from Utilities import TestDataSource, Utils
class RotationTests (UITestCase):
    
    def setUp(self):
        
        global articleName1, firstSubHeading, link1, link1ArticleName
        articleName1 = TestDataSource.articleName1
        firstSubHeading = TestDataSource.article1_firstSubHeading
        link1 = TestDataSource.fullLinkText1
        link1ArticleName = TestDataSource.link1ArticleName
        
        launch.activity('org.wikipedia.alpha', 'org.wikipedia.MainActivity', verify=False)
                
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
        
        
    @testCaseInfo('<rotate phone when tabs is open>', deviceCount=1)
    def testOpenTab_rotatePhone(self):
        
        #this test works, somehow. Might not work in future.
        #just happened that the link 1 can be clicked with tap.description
        log("open article")
        Utils.openSearchFromStartScreen()
        Utils.searchAndOpenArticleWithName(articleName1)
        wait(1500)
        
        log("open link in a new tab")
        tap.description(link1)
        tap.resourceId('org.wikipedia.alpha:id/link_preview_overflow_button')
        tap.text('Open in new tab')
        
        log("go to tabs and assert list is visible")
        tap.description('Show tabs')
        verify.resourceId("org.wikipedia.alpha:id/tabs_list")
        verify.text(articleName1, regex = True)
        verify.text(link1ArticleName)
        
        log("rotate phone")
        orientation.left()
        
        log("assert list still visible")
        verify.resourceId("org.wikipedia.alpha:id/tabs_list")
        verify.text(articleName1, regex = True)
        verify.text(link1ArticleName)
        
        
        