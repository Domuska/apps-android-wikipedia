package org.wikipedia.robotium_test;

import android.widget.EditText;
import android.widget.TextView;

import org.junit.Test;
import org.wikipedia.R;
import org.wikipedia.robotium_test.Utilities.Utils;

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

    //something weird going on with this test, always hangs when opening the article in finnish
    public void testSearchArticle_changeLanguageInSearch(){
        Utils.openSearchFromStartScreen(solo);
        Utils.searchAndOpenArticleWithName(solo, articleName3);

        //check title is shown in default language
        Utils.assertArticleTitleContains(solo, articleName3);

        //change language in search
        Utils.openSearchFromArticle(solo);
        solo.clickOnView(solo.getView(R.id.search_lang_button));
        solo.typeText((EditText)solo.getView(R.id.preference_languages_filter), finnishLanguage);
        solo.clickOnView(solo.getView(R.id.localized_language_name));

        //open article again
        solo.clickOnText(articleName3_finnish);

        //check language changed
//        Utils.assertArticleTitleContains(solo, articleName3_finnish);

        if(solo.waitForText(articleName3_finnish, 3, 150000)) {
            String titleText = ((TextView) solo.getView(R.id.view_article_header_text))
                    .getText().toString();
            assertTrue("Title does not contain text: " + articleName3_finnish + ", contains instead " + titleText,
                    titleText.contains(articleName3_finnish));
        }
        else
            fail("no title found");

    }

}
