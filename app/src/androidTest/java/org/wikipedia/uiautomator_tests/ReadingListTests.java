package org.wikipedia.uiautomator_tests;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.wikipedia.R;
import org.wikipedia.uiautomator_tests.Utils.TestDataSource;
import org.wikipedia.uiautomator_tests.Utils.Utils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

public class ReadingListTests extends BaseTestClass{

    private String readingListName = TestDataSource.readingListName;
    private String gotItText;
    private String readingListText;

    @Before
    public void setUp(){
        gotItText = InstrumentationRegistry.getTargetContext().getString(R.string.reading_lists_onboarding_got_it);
        readingListText = InstrumentationRegistry.getTargetContext().getString(R.string.nav_item_reading_lists);
    }

    @Test
    public void testAddArticleToReadingList(){

        //open the article
        Utils.openSearchFromStartScreen(device);
        Utils.searchAndOpenArticleWithName(device, articleName1);

        //add it to reading list
        device.wait(Until.findObject(
                By.res("org.wikipedia.alpha:id/view_article_menu_bar_bookmark")),
                GENERAL_TIMEOUT)
                .click();

        device.wait(Until.findObject(
                By.text(gotItText)), GENERAL_TIMEOUT)
                .click();

        device.wait(Until.findObject(
                By.res("org.wikipedia.alpha:id/reading_list_title")), GENERAL_TIMEOUT)
                .setText(readingListName);
        device.findObject(By.res("android:id/button1")).click();

        //open reading lists and assert article is visible in the list
        device.wait(Until.hasObject(
                By.res("org.wikipedia.alpha:id/view_article_header_text")), GENERAL_TIMEOUT);
        Utils.openDrawer(device);
        device.findObject(By.text(readingListText)).click();
        device.wait(Until.findObject(By.text(readingListName)), GENERAL_TIMEOUT)
                .click();
        device.findObject(By.text(readingListName)).click();

        UiObject2 article = device.wait(Until.findObject(By.text(articleName1)), GENERAL_TIMEOUT);
        assertThat(article, notNullValue());

        //open the article and assert title is correct
        article.click();
        Utils.assertArticleTitleContains(device, articleName1);
    }
}
