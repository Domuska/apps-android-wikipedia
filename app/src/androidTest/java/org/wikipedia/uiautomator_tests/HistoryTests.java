package org.wikipedia.uiautomator_tests;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.wikipedia.R;
import org.wikipedia.uiautomator_tests.Utils.Utils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

public class HistoryTests extends BaseTestClass{

    private String historyText;

    @Before
    public void setUp(){
        historyText = InstrumentationRegistry.getTargetContext().getString(R.string.nav_item_history);
    }

    @Test(timeout=50000)
    public void testHistory(){

        //open a couple of articles
        Utils.openSearchFromStartScreen(device);
        Utils.searchAndOpenArticleWithName(device, articleName1);

        Utils.openSearchFromArticle(device);
        Utils.searchAndOpenArticleWithName(device, articleName2);

        Utils.openSearchFromArticle(device);
        Utils.searchAndOpenArticleWithName(device, articleName3);

        //go to history to see that they are visible
        Utils.openDrawer(device);
        device.findObject(By.text(historyText)).click();

        //assertions
        UiObject2 article = device.wait(Until.findObject(
                By.text(articleName1)), GENERAL_TIMEOUT);
        assertThat(article, notNullValue());
        article = device.findObject(By.text(articleName2));
        assertThat(article, notNullValue());
        article = device.findObject(By.text(articleName3));
        assertThat(article, notNullValue());
    }
}
