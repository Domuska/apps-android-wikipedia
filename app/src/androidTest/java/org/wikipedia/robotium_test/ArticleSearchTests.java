package org.wikipedia.robotium_test;

import org.wikipedia.robotium_test.Utilities.Utils;

public class ArticleSearchTests extends BaseTestClass{

    public void setUp() throws Exception{
        super.setUp();
        //do things
    }

    public void tearDown() throws Exception{
        //do things
        super.tearDown();
    }

    public void testSearchArticle_checkTitleShown(){
        //open the article
        Utils.openSearchFromStartScreen(solo);
        Utils.searchAndOpenArticleWithName(solo, articleName1);

        //check that the title is displayed in the title view
        Utils.assertArticleTitleContains(solo, articleName1);
    }
}
