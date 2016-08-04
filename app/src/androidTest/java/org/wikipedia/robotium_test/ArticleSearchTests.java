package org.wikipedia.robotium_test;

import android.support.test.espresso.web.sugar.Web;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import org.junit.Test;
import org.wikipedia.R;
import org.wikipedia.robotium_test.Utilities.Utils;

import java.util.List;

public class ArticleSearchTests extends BaseTestClass{

    private String recentSearchesText;

    public void setUp() throws Exception{
        super.setUp();

        recentSearchesText = getActivity().getString(R.string.search_recent_header);
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

    public void testSearchArticle_checkRecentsShown(){
        //search and open articles
        Utils.openSearchFromStartScreen(solo);
        Utils.searchAndOpenArticleWithName(solo, articleName1);
        solo.waitForText(articleName1);

        Utils.openSearchFromArticle(solo);
        Utils.searchAndOpenArticleWithName(solo, articleName2);
        solo.waitForText(articleName2);

        Utils.openSearchFromArticle(solo);
        Utils.searchAndOpenArticleWithName(solo, articleName3);
        solo.waitForText(articleName3);

        //go to the search screen
        Utils.openSearchFromArticle(solo);
        solo.clickOnView(solo.getView(R.id.search_close_btn));

        if(!(solo.searchText(recentSearchesText)))
            fail("Text " + recentSearchesText + " should be displayed");

        //assert that the searches are shown in recent searches
        boolean article1Found = solo.searchText(articleName1);
        boolean article2Found = solo.searchText(articleName2);
        boolean article3Found = solo.searchText(articleName3);

        assertTrue("One of the articles not displayed: " +
                articleName1 + " " + article1Found + " " +
                articleName2 + " " + article2Found + " " +
                articleName3 + " " + article3Found,
                article1Found && article2Found && article3Found);
    }


}
