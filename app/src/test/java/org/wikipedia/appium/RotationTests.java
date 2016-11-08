package org.wikipedia.appium;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.wikipedia.appium.Utilities.TestDataSource;
import org.wikipedia.appium.Utilities.Utils;

import static junit.framework.Assert.assertTrue;

public class RotationTests extends BaseTestClass{

    private String firstSubHeading = TestDataSource.article1_firstSubHeading;

    @After
    public void tearDown(){
        driver.rotate(ScreenOrientation.PORTRAIT);
    }

    @Test
    public void testOpenToC_rotatePhone(){

        Utils.openSearchFromStartScreen(driver);
        Utils.searchAndOpenArticleWithName(driver, articleName1);

        stareAtPixies.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("org.wikipedia.alpha:id/view_article_header_text")
        ));
        Utils.openToC(driver);

        assertListAndFirstElementDisplayed();

        //rotate screen
        driver.rotate(ScreenOrientation.LANDSCAPE);

        assertListAndFirstElementDisplayed();
    }

    private void assertListAndFirstElementDisplayed() {
        assertTrue("Table of Contents list is not visible",
                driver.findElementById("org.wikipedia.alpha:id/page_toc_list").isDisplayed());
        //assert first element is visible (so list is populated)
        assertTrue("Element with name " + firstSubHeading + " is not visible",
                Utils.searchInVisibleListWithName(driver, firstSubHeading).isDisplayed());
    }
}
