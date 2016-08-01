# -*- coding: utf-8 -*-
from Utilities import TestDataSource, Utils
class RotationTests (UITestCase):
    
    def setUp(self):
        
        global articleName1, firstSubHeading, link1
        articleName1 = TestDataSource.articleName1
        firstSubHeading = TestDataSource.article1_firstSubHeading
        link1 = TestDataSource.fullLinkText1
        
        launch.activity('org.wikipedia.alpha', 'org.wikipedia.MainActivity', verify=False)
                
    def tearDown(self):
        packages.clearData('org.wikipedia.alpha')
        

    @testCaseInfo('<rotate phone when Toc open>', deviceCount=1)
    def testOpenToC_rotatePhone(self):
       
        Utils.openSearchFromStartScreen()
        Utils.searchAndOpenArticleWithName(articleName1)
        Utils.openToC()
        
        log("assert list is visible")
        assert exists.resourceId("org.wikipedia.alpha:id/page_toc_list"),\
        "table of contents is not visible"
        assert exists.text(firstSubHeading),\
        "subheading: " + firstSubHeading + " is not visible"
        
        log("rotate screen")
        orientation.left()
        
        log("assert list is still visible")
        assert exists.resourceId("org.wikipedia.alpha:id/page_toc_list"),\
        "table of contents is not visible"
        assert exists.text(firstSubHeading),\
        "subheading: " + firstSubHeading + " is not visible"
        
        #testOpenTab_rotatePhone
        
        
    @testCaseInfo('<rotate phone when tabs is open>', deviceCount=1)
    def testOpenTab_rotatePhone(self):
        
        assert false, "not implemented yet, fool"
        Utils.openSearchFromStartScreen()
        Utils.searchAndOpenArticleWithName(articleName1)
        
        tap.text(link1)
        