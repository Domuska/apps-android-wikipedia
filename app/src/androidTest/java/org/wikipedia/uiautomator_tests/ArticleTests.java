package org.wikipedia.uiautomator_tests;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import org.junit.Before;
import org.junit.Test;
import org.wikipedia.R;
import org.wikipedia.uiautomator_tests.BaseTestClass;
import org.wikipedia.uiautomator_tests.Utils.TestDataSource;
import org.wikipedia.uiautomator_tests.Utils.Utils;

import java.util.List;

import static junit.framework.Assert.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class ArticleTests extends BaseTestClass{

    String subHeading1 = TestDataSource.article1_subheading1;
    String subHeading2 = TestDataSource.article1_subheading2;
    String subHeading3 = TestDataSource.article1_subheading3;

    String changeLanguageText;

    private String fullLinkText = TestDataSource.fullLinkText1;
    private String partialLinkText = TestDataSource.partialLinkText;
    private String newArticleText = TestDataSource.newArticleTitle;

    private String article1_referenceSubHeading = TestDataSource.article1_referenceSubHeading;

    @Before
    public void setUp(){
        changeLanguageText = InstrumentationRegistry.getTargetContext()
                .getString(R.string.menu_page_other_languages);
    }

    @Test
    public void testTableOfContents_checkSubTitles() throws Exception{

        ////https://android.googlesource.com/platform/cts/+/5c8645606f7812d981e0d0111a8971f3e1cef949/tests/uiautomator/src/com/android/cts/uiautomatortest/CtsUiAutomatorTest.java
        fail("can't search subtitles from the webview");

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


        //searching the subheadings does not work - maybe because uiautomatorviewer finds views
        //that are behind the visible screen, and they come on top of the subheadings
        UiScrollable webView = new UiScrollable(new UiSelector().className(WebView.class));
        webView.getChildByDescription(new UiSelector().className(View.class), subHeading2);
//        webView.getChildByDescription(new UiSelector().className(View.class), "fictional land");


    }

    @Test
    public void testScrollingToC_clickSubHeading() throws Exception{

        //open article and table of contents
        Utils.openSearchFromStartScreen(device);
        Utils.searchAndOpenArticleWithName(device, articleName1);
        Utils.openToc(device);

        //click subtitles subheading
        UiScrollable tocList = new UiScrollable(new UiSelector()
                .resourceId("org.wikipedia.alpha:id/page_toc_list"));

        tocList.getChildByText(new UiSelector().className(LinearLayout.class),
                article1_referenceSubHeading)
                .click();

        //make sure we the title bar is no longer visible (so we at least moved somewhere)
        List<UiObject2> elements =
                device.findObjects(By.res("org.wikipedia.alpha:id/view_article_header_text"));
        assertThat(elements.size(), is(0));
    }

    @Test
    public void testClickLink_fullText_assertPreviewShown(){

        //open article
        Utils.openSearchFromStartScreen(device);
        Utils.searchAndOpenArticleWithName(device, articleName1);

        //click on link
        device.findObject(By.desc(fullLinkText)).click();

        //assert popup is shown
        device.findObject(new UiSelector()
                .resourceId("org.wikipedia.alpha:id/link_preview_title")
                .text(newArticleText));
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
