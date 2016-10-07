# -*- coding: utf-8 -*-
from Utilities import TestDataSource, Utils
class ReadingListTests (UITestCase):

    def setUp(self):
        global articleName1, readingListName, gotItText, readingListText
        
        articleName1 = TestDataSource.articleName1
        readingListName = TestDataSource.readingListName
        gotItText = "Got it"
        readingListText = "Reading lists"
        
        launch.activity('org.wikipedia.alpha', 'org.wikipedia.MainActivity', verify=True)
                    
    def tearDown(self):
        packages.clearData('org.wikipedia.alpha')

    @testCaseInfo('<add article to reading list>', deviceCount=1)
    def testAddArticleToReadingList(self):
        
        log("open an article")
        Utils.openSearchFromStartScreen();
        Utils.searchAndOpenArticleWithName(articleName1)
        
        log("add it to reading list")
        tap.resourceId('org.wikipedia.alpha:id/view_article_menu_bar_bookmark')
        tap.text(gotItText)
        input.text(readingListName)
        tap.resourceId('android:id/button1')
        
        log("open reading lists and assert article is visible in the list")
        Utils.openDrawer()
        tap.text(readingListText)
        
        tap.text(readingListName)
        verify.text(articleName1)

        log("open the article and assert title is correct")
        tap.text(articleName1)
        Utils.assertArticleTitleContains(articleName1)

        