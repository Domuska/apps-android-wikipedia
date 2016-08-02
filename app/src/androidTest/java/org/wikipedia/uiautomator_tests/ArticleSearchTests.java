package org.wikipedia.uiautomator_tests;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.wikipedia.R;
import org.wikipedia.uiautomator_tests.Utils.Utils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringStartsWith.startsWith;

public class ArticleSearchTests extends BaseTestClass{

    private String recentSearchesText;

    @Before
    public void setUp(){
        recentSearchesText = InstrumentationRegistry.getTargetContext().getString(R.string.search_recent_header);
    }

    @Test
    public void testSearchArticle_checkTitleShown(){
        //open the article
        Utils.openSearchFromStartScreen(device);
        Utils.searchAndOpenArticleWithName(device, articleName1);

        //check the title is displayed in the title view
        Utils.assertArticleTitleContains(device, articleName1);
    }

    @Test
    public void testSearchArticle_checkRecentsShown(){

        //search and open article in starting fragment
        Utils.openSearchFromStartScreen(device);
        Utils.searchAndOpenArticleWithName(device, articleName1);

        //search and open articles inside an article
        Utils.openSearchFromArticle(device);
        Utils.searchAndOpenArticleWithName(device, articleName2);

        Utils.openSearchFromArticle(device);
        Utils.searchAndOpenArticleWithName(device, articleName3);

        //go to the search screen
        Utils.openSearchFromArticle(device);

        device.wait(Until.findObject(
                By.res("org.wikipedia.alpha:id/search_close_btn")), GENERAL_TIMEOUT)
                .click();
        device.findObject(By.text(recentSearchesText)); //fail if not found

        //assert that the recent searches show the articles searched
        device.findObject(By.text(articleName1));
        device.findObject(By.text(articleName2));
        device.findObject(By.text(articleName3));
    }

    @Test
    public void testSearchArticle_changeLanguageInSearch(){

        Utils.openSearchFromStartScreen(device);
        Utils.searchAndOpenArticleWithName(device, articleName3);

        //check title is shown in default language
        Utils.assertArticleTitleContains(device, articleName3);

        //change language in search
        Utils.openSearchFromArticle(device);

        device.wait(Until.findObject(
                By.res("org.wikipedia.alpha:id/search_lang_button")), GENERAL_TIMEOUT)
                .click();
        device.findObject(By.res("org.wikipedia.alpha:id/preference_languages_filter"))
                .setText(finnishLanguage);
        device.findObject(By.res("org.wikipedia.alpha:id/localized_language_name"))
                .click();

        //open article again
        device.wait(Until.findObject(
                By.text(articleName3_finnish)), GENERAL_TIMEOUT)
                .click();

        //check language is changed
        Utils.assertArticleTitleContains(device, articleName3_finnish);
    }


}
