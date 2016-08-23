package org.wikipedia.uiautomator_tests;

import android.graphics.Point;
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
import org.junit.Ignore;
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

    private String fullLinkText = TestDataSource.fullLinkText1;
    private String partialLinkText = TestDataSource.partialLinkText;
    private String newArticleText = TestDataSource.fullLinkTextCapitalized;

    private String article1_referenceSubHeading = TestDataSource.article1_referenceSubHeading;

    @Test
    @Ignore
    public void testTableOfContents_checkSubTitles() throws Exception{

        ////https://android.googlesource.com/platform/cts/+/5c8645606f7812d981e0d0111a8971f3e1cef949/tests/uiautomator/src/com/android/cts/uiautomatortest/CtsUiAutomatorTest.java
        fail("this test doesn't work, can't search subtitles from the webview");

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
    }

    @Test(timeout=90000)
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

    @Test(timeout=90000)
    public void testClickLink_fullText_assertPreviewShown(){

        //open article
        Utils.openSearchFromStartScreen(device);
        Utils.searchAndOpenArticleWithName(device, articleName1);

        //click on link
        device.findObject(By.desc(fullLinkText)).click();

        //assert popup is shown
        UiObject2 popUpTitle = device.wait(Until.findObject(By
                .res("org.wikipedia.alpha:id/link_preview_title")
                .text(newArticleText)), GENERAL_TIMEOUT);
        assertThat(popUpTitle, notNullValue());
    }

    @Test
    @Ignore
    public void testClickLink_partialText_assertPreviewShown(){
        //open article
        Utils.openSearchFromStartScreen(device);
        Utils.searchAndOpenArticleWithName(device, articleName1);

        //click on link
        device.findObject(By.descContains(partialLinkText)).click();

        //assert popup is shown
        UiObject2 popUpTitle = device.wait(Until.findObject(By
                .res("org.wikipedia.alpha:id/link_preview_title")
                .text(newArticleText)), GENERAL_TIMEOUT);
        assertThat(popUpTitle, notNullValue());
    }

    @Test
    @Ignore
    public void testOpeningAndClosingReferences() throws Exception{

        fail("this test doesn't work, can't find the references element in webview");

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
        device.wait(Until.findObject(By.text("sfdsfds")), GENERAL_TIMEOUT);
    }

}
