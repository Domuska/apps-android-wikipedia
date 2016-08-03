package org.wikipedia.uiautomator_tests;

import android.graphics.Point;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.widget.LinearLayout;

import org.junit.Before;
import org.junit.Test;
import org.wikipedia.R;
import org.wikipedia.uiautomator_tests.Utils.TestDataSource;
import org.wikipedia.uiautomator_tests.Utils.Utils;

import static junit.framework.Assert.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringStartsWith.startsWith;

public class ArticleSearchTests extends BaseTestClass{

    private String recentSearchesText;
    private String article1_referenceSubHeading = TestDataSource.article1_referenceSubHeading;

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

    @Test
    public void testOpeningAndClosingReferences() throws Exception{

        fail("this test doesn't work, can't find the references element");

        //navigate to the article
        Utils.openSearchFromStartScreen(device);
        Utils.searchAndOpenArticleWithName(device, articleName1);
        Utils.openToc(device);

        //open references
        UiScrollable tocList =
                new UiScrollable(new UiSelector().resourceId("org.wikipedia.alpha:id/page_toc_list"));
        tocList.getChildByText(new UiSelector().className(LinearLayout.class),
                article1_referenceSubHeading)
                .click();

        //expand references
        device.waitForIdle(1000);

        Point start = new Point(device.getDisplayWidth()/2, device.getDisplayHeight()/3);
        Double endY = device.getDisplayWidth()/0.5;
        Point end = new Point(start.x, endY.intValue());
        device.drag(start.x, start.y, end.x, end.y, 4);

        device.findObject(By.desc("References"))
                .click();
//        device.findObject(new UiSelector().description("References")).click();
        device.wait(Until.findObject(By.text("sfdsfds")), GENERAL_TIMEOUT);

    }

}
