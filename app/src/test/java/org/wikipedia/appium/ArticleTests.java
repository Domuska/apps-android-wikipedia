package org.wikipedia.appium;

import org.junit.Test;
import org.wikipedia.appium.Utilities.TestDataSource;
import org.wikipedia.appium.Utilities.Utils;

import java.util.List;

import io.appium.java_client.MobileElement;

import static junit.framework.Assert.assertTrue;

public class ArticleTests extends BaseTestClass{


    private String article1_referenceSubHeading = TestDataSource.article1_referenceSubHeading;

    @Test
    public void testScrollingToC_clickSubHeading(){
        //open article and table of contents
        Utils.openSearchFromStartScreen(driver);
        Utils.searchAndOpenArticleWithName(driver, articleName1);
        Utils.openToC(driver);

        //scroll to subheading
        Utils.searchInVisibleListWithName(driver, article1_referenceSubHeading).click();

        //make sure we the title bar is no longer visible (so we at least moved somewhere)
        List<MobileElement> elements =
                driver.findElementsById("org.wikipedia.alpha:id/view_article_header_text");

        assertTrue("Header should not be visible ", elements.size() == 0);
    }
}
