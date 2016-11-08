package org.wikipedia.uiautomator_tests;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.widget.LinearLayout;

import org.junit.Test;
import org.wikipedia.uiautomator_tests.Utils.TestDataSource;
import org.wikipedia.uiautomator_tests.Utils.Utils;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ArticleTests extends BaseTestClass{

    private String article1_referenceSubHeading = TestDataSource.article1_referenceSubHeading;

    @Test(timeout=50000)
    public void testScrollingToC_clickSubHeading() throws Exception{

        //open article and table of contents
        Utils.openSearchFromStartScreen(device);
        Utils.searchAndOpenArticleWithName(device, articleName1);
        Utils.openToc(device);

        //click subtitles subheading
        device.wait(Until.findObject(By.res("org.wikipedia.alpha:id/page_toc_list")), GENERAL_TIMEOUT);
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
