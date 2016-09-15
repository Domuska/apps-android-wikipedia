package org.wikipedia.appium;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.wikipedia.appium.Utilities.TestDataSource;
import org.wikipedia.appium.Utilities.Utils;

import java.util.List;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

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
