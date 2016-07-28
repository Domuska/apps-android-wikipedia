package org.wikipedia.robotium_test;

import org.wikipedia.robotium_test.Utilities.Utils;

public class ArticleSearchTests extends BaseTestClass{

    public void setUp() throws Exception{
        super.setUp();
    }

    public void tearDown() throws Exception{
        super.tearDown();
    }

    public void testSearchArticle_checkTitleShown(){
        Utils.openSearchFromStartScreen(solo);
    }
}
