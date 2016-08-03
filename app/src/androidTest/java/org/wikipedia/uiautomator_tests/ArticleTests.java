package org.wikipedia.uiautomator_tests;

import android.graphics.Point;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import org.junit.Test;
import org.wikipedia.uiautomator_tests.Utils.TestDataSource;
import org.wikipedia.uiautomator_tests.Utils.Utils;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ArticleTests extends BaseTestClass{

    //https://android.googlesource.com/platform/cts/+/5c8645606f7812d981e0d0111a8971f3e1cef949/tests/uiautomator/src/com/android/cts/uiautomatortest/CtsUiAutomatorTest.java

    private String subHeading1 = TestDataSource.article1_subheading1;
    private String subHeading2 = TestDataSource.article1_subheading2;
    private String subHeading3 = TestDataSource.article1_subheading3;

    private String article1_referenceSubHeading = TestDataSource.article1_referenceSubHeading;


    @Test
    public void testTableOfContents_checkSubTitles() throws Exception{

        fail("this test dunt work");

        Utils.openSearchFromStartScreen(device);
        Utils.searchAndOpenArticleWithName(device, articleName1);

        //searching the subheadings does not work - maybe because uiautomatorviewer finds views
        //that are behind the visible screen, and they come on top of the subheadings
        UiScrollable webView = new UiScrollable(new UiSelector().className(WebView.class));
        webView.getChildByDescription(new UiSelector().className(View.class), subHeading2);
//        webView.getChildByDescription(new UiSelector().className(View.class), "fictional land");


//        Utils.openToc(device);
//
//        UiScrollable tocList = new UiScrollable(
//                new UiSelector().resourceId("org.wikipedia.alpha:id/page_toc_list"));
//
//        tocList.getChildByText(new UiSelector().className("android.widget.LinearLayout"), subHeading1);
//
//        Utils.closeToC(device);

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

}
