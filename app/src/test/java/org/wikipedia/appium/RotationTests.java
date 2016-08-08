package org.wikipedia.appium;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.wikipedia.appium.Utilities.TestDataSource;
import org.wikipedia.appium.Utilities.Utils;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public class RotationTests extends BaseTestClass{

    private String firstSubHeading = TestDataSource.article1_firstSubHeading;

    private String articleLink = TestDataSource.fullLinkText1;
    private String openInNewTabText = "Open in new tab";
    private String link1ArticleName = TestDataSource.fullLinkTextCapitalized;

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

    @Test
    public void testOpenTab_rotatePhone(){

        //open a few articles in tabs
        Utils.openSearchFromStartScreen(driver);
        Utils.searchAndOpenArticleWithName(driver, articleName1);

        //open link in a new tab
        Utils.switchToWebContext(driver);
        driver.findElementByLinkText(articleLink).click();
        Utils.switchToNativeContext(driver);
        driver.findElementById("org.wikipedia.alpha:id/link_preview_overflow_button").click();
        stareAtPixies.until(ExpectedConditions.visibilityOfElementLocated(
                By.name(openInNewTabText)))
                .click();

        //open tabs
        stareAtPixies.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("org.wikipedia.alpha:id/menu_page_show_tabs")))
                .click();

        //assert the list and elements are visible
        assertListAndTabsVisible();

        //rotate phone
        driver.rotate(ScreenOrientation.LANDSCAPE);

        //assert the same views are visible
        assertListAndTabsVisible();

    }

    private void assertListAndTabsVisible() {
        WebElement tabList = stareAtPixies.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("org.wikipedia.alpha:id/tabs_list")
        ));
        assertTrue("List of tabs is not visible", tabList.isDisplayed());
        assertTrue("tab: " + articleName1 + " should be visible",
                driver.findElementByName(articleName1).isDisplayed());
        assertTrue("tab: " + link1ArticleName + " should be visible",
                driver.findElementByName(link1ArticleName).isDisplayed());
    }

    private void assertListAndFirstElementDisplayed() {
        assertTrue("Table of Contents list is not visible",
                driver.findElementById("org.wikipedia.alpha:id/page_toc_list").isDisplayed());
        //assert first element is visible (so list is populated)
        assertTrue("Element with name " + firstSubHeading + " is not visible",
                Utils.searchInVisibleListWithName(driver, firstSubHeading).isDisplayed());
    }
}
