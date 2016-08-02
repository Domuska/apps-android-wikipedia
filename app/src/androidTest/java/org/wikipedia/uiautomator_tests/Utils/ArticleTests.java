package org.wikipedia.uiautomator_tests.Utils;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.wikipedia.R;
import org.wikipedia.uiautomator_tests.BaseTestClass;

import static junit.framework.Assert.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

public class ArticleTests extends BaseTestClass{

    String subHeading1 = TestDataSource.article1_subheading1;
    String subHeading2 = TestDataSource.article1_subheading2;
    String subHeading3 = TestDataSource.article1_subheading3;

    String changeLanguageText;

    @Before
    public void setUp(){
        changeLanguageText = InstrumentationRegistry.getTargetContext()
                .getString(R.string.menu_page_other_languages);
    }

    @Test
    public void testTableOfContents_checkSubTitles(){

        //requires webview, which is not available with uiautomator
        fail("not implemented");

        Utils.openSearchFromStartScreen(device);
        Utils.searchAndOpenArticleWithName(device, articleName1);
        Utils.openToc(device);

        //check that certain 3 topmost subheadings in table of contents show up
        UiObject2 tocList = device.findObject(By.res("org.wikipedia.alpha:id/page_toc_list"));
        assertThat(tocList.findObject(By.text(subHeading1)), notNullValue());
        assertThat(tocList.findObject(By.text(subHeading2)), notNullValue());
        assertThat(tocList.findObject(By.text(subHeading3)), notNullValue());

        //make sure those same three subtitles are visible in the webview
        Utils.closeToC(device);
        UiObject2 webView = device.findObject(By.clazz("android.webkit.WebView"));
        assertThat(webView.findObject(By.text(subHeading1)), notNullValue());

    }

    @Test
    public void testScrollingToC_clickSubHeading(){
        fail("not implemented");
    }

    @Test
    public void testClickLink_fullText_assertPreviewShown(){
        fail("not implemented");
    }

    @Test
    public void testClickLink_partialText_assertPreviewShown(){
        fail("not implemented");
    }

    @Test
    public void testChangeLanguage() throws Exception{

        //open article
        Utils.openSearchFromStartScreen(device);
        Utils.searchAndOpenArticleWithName(device, articleName3);

        //open overflow menu in toolbar
        device.findObject(By.desc("More options")).click();
        device.wait(Until.findObject(
                By.text(changeLanguageText)), GENERAL_TIMEOUT)
                .click();
//        device.findObject(By.text(changeLanguageText)).click();

        //change language to finnish
        device.wait(Until.hasObject(
                By.res("org.wikipedia.alpha:id/langlinks_list")), GENERAL_TIMEOUT);
        UiScrollable languageList = new UiScrollable(
                new UiSelector().resourceId("org.wikipedia.alpha:id/langlinks_list"));
        languageList.getChildByText(
                new UiSelector().className("android.widget.LinearLayout"), articleName3_finnish)
                .click();

        //assert article changed language
        device.wait(Until.hasObject(
                By.res("org.wikipedia.alpha:id/view_article_header_text")), GENERAL_TIMEOUT);
        Utils.assertArticleTitleContains(device, articleName3_finnish);
    }

    @Test
    public void testOpeningAndClosingReferences(){
        fail("not implemented");
    }


}
