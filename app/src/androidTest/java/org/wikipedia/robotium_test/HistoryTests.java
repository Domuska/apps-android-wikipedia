package org.wikipedia.robotium_test;

import org.wikipedia.R;
import org.wikipedia.robotium_test.Utilities.Utils;

public class HistoryTests extends BaseTestClass{

    private String historyText;

    public void setUp() throws Exception{
        super.setUp();
        historyText = getActivity().getString(R.string.nav_item_history);
    }

    public void tearDown() throws Exception{

        super.tearDown();
    }

    //todo page goes blank... again? what is up with this thing?
    public void testHistory(){

        //open couple of articles
        Utils.openSearchFromStartScreen(solo);
        Utils.searchAndOpenArticleWithName(solo, articleName1);

        Utils.openSearchFromArticle(solo);
        Utils.searchAndOpenArticleWithName(solo, articleName2);

        Utils.openSearchFromArticle(solo);
        Utils.searchAndOpenArticleWithName(solo, articleName3);

        //go to history to see that they are visible
        Utils.openNavDrawer(solo);
        solo.clickOnText(historyText);

        //assertions
        assertTrue("Article: " + articleName1 + " not visible in history",
                solo.searchText(articleName1));
        assertTrue("Article: " + articleName2 + " not visible in history",
                solo.searchText(articleName2));
        assertTrue("Article: " + articleName3 + " not visible in history",
                solo.searchText(articleName3));

    }
}
