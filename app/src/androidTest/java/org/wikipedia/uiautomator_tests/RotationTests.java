package org.wikipedia.uiautomator_tests;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wikipedia.R;
import org.wikipedia.uiautomator_tests.Utils.TestDataSource;
import org.wikipedia.uiautomator_tests.Utils.Utils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

public class RotationTests extends BaseTestClass{

    private String firstSubHeading = TestDataSource.article1_firstSubHeading;
    private String openInNewTabText;
    private String link1 = TestDataSource.fullLinkText1;
    private String link1ArticleName = TestDataSource.fullLinkTextCapitalized;

    @Before
    public void setUp(){
        openInNewTabText =
                InstrumentationRegistry.getTargetContext().getString(R.string.menu_long_press_open_in_new_tab);
    }

    @After
    public void tearDown() throws Exception{
        device.unfreezeRotation();
        device.setOrientationNatural();
    }

    @Test(timeout=90000)
    public void testOpenToC_rotatePhone() throws Exception{

        Utils.openSearchFromStartScreen(device);
        Utils.searchAndOpenArticleWithName(device, articleName1);
        Utils.openToc(device);

        //assert list and first element are visible
        assertThat(device.findObject(By.res("org.wikipedia.alpha:id/page_toc_list")),
                notNullValue());
        assertThat(device.findObject(By.text(firstSubHeading)),
                notNullValue());

        //rotate screen
        device.setOrientationLeft();

        //assert list and first element are still visible
        assertThat(device.findObject(By.res("org.wikipedia.alpha:id/page_toc_list")),
                notNullValue());
        assertThat(device.findObject(By.text(firstSubHeading)),
                notNullValue());
    }

    @Test(timeout=90000)
    public void testOpenTab_rotatePhone() throws Exception{

        //open a few articles in tabs
        Utils.openSearchFromStartScreen(device);
        Utils.searchAndOpenArticleWithName(device, articleName1);

        //open link in a new tab
        device.findObject(By.desc(link1)).click();

        device.wait(Until.findObject(
                By.res("org.wikipedia.alpha:id/link_preview_overflow_button")), GENERAL_TIMEOUT)
                .click();
        device.wait(Until.findObject(
                By.text(openInNewTabText)), GENERAL_TIMEOUT)
                .click();

        //open tabs
        device.wait(Until.findObject(
                By.res("org.wikipedia.alpha:id/menu_page_show_tabs")), GENERAL_TIMEOUT)
                .click();

        //assert the list and elements are visible
        assertListAndTabsVisible();

        //rotate phone
        device.setOrientationLeft();

        //assert the same views are visible
        assertListAndTabsVisible();
    }

    private void assertListAndTabsVisible() {
        UiObject2 tabsList = device.wait(Until.findObject(
                By.res("org.wikipedia.alpha:id/tabs_list")), GENERAL_TIMEOUT);
        UiObject2 article1 = device.findObject(By.text(articleName1));
        UiObject2 article2 = device.findObject(By.text(link1ArticleName));
        assertThat("list of tabs was not found", tabsList, notNullValue());
        assertThat("article " + articleName1 + " was not found", article1, notNullValue());
        assertThat("article " + link1ArticleName + " was not found", article2, notNullValue());
    }
}
