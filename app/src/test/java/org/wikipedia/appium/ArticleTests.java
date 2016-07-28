package org.wikipedia.appium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.wikipedia.appium.Utilities.TestDataSource;
import org.wikipedia.appium.Utilities.Utils;

import java.util.ArrayList;
import java.util.List;

import io.appium.java_client.MobileElement;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public class ArticleTests extends BaseTestClass{


    private String article1_referenceSubHeading = TestDataSource.article1_referenceSubHeading;
    private String overflowMenuContentDescription = "More options";
    private String changeLanguageText = "Change language";

    @Test
    // it seems chromeDriver update is not out yet, as of 28.7.2016.
    // see if fixable:
    // https://github.com/appium/appium/issues/5689
    // https://github.com/appium/appium/issues/6634
    // https://bugs.chromium.org/p/chromedriver/issues/detail?id=1378#c4
    public void testTableOfContents_checkSubTitles(){

        String subHeading1 = "Gameplay";
        String subHeading2 = "Plot";
        String subHeading3 = "Development";

        Utils.openSearchFromStartScreen(driver);
        Utils.searchAndOpenArticleWithName(driver, articleName1);
        stareAtPixies.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("org.wikipedia.alpha:id/view_article_header_text")
        ));
        Utils.openToC(driver);

        //check that certain 3 topmost subheadings in table of contents show up, see Utils for matcher
        Utils.searchInVisibleListWithName(driver, subHeading1).isDisplayed();
        Utils.searchInVisibleListWithName(driver, subHeading2).isDisplayed();
        Utils.searchInVisibleListWithName(driver, subHeading3).isDisplayed();

        //make sure those same three subtitles are visible in the webview
        Utils.closeToC(driver);
        stareAtPixies.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("android.webkit.WebView")
        ));
        Utils.switchToWebContext(driver);

        driver.findElementById(subHeading1);
        driver.findElementById(subHeading2);
        driver.findElementById(subHeading3);


        //set context back to NATIVE_APP
        Utils.switchToNativeContext(driver);
    }

    @Test
    public void testScrollingToC_clickSubHeading(){
        //open article and table of contents
        Utils.openSearchFromStartScreen(driver);
        Utils.searchAndOpenArticleWithName(driver, articleName1);
        stareAtPixies.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("org.wikipedia.alpha:id/view_article_header_text")
        ));
        Utils.openToC(driver);

        //scroll to subheading
        Utils.searchInVisibleListWithName(driver, article1_referenceSubHeading).click();

        //make sure we the title bar is no longer visible (so we at least moved somewhere)
        List<MobileElement> elements =
                driver.findElementsById("org.wikipedia.alpha:id/view_article_header_text");

        assertTrue("Header should not be visible ", elements.size() == 0);
    }

    @Test
    public void testClickLink_fullText_assertPreviewShown(){
        fail("not yet implemented");
    }

    @Test
    public void testClickLink_partialText_assertPreviewShown(){
        fail("not yet implemented");
    }

    @Test
    public void testChangeLanguage(){
        //open article
        Utils.openSearchFromStartScreen(driver);
        Utils.searchAndOpenArticleWithName(driver, articleName3);

        //open overflow menu in toolbar
        driver.findElementByAccessibilityId(overflowMenuContentDescription).click();
        stareAtPixies.until(ExpectedConditions.visibilityOfElementLocated(
                By.name(changeLanguageText)
        )).click();

        //change language to finnish
        stareAtPixies.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("org.wikipedia.alpha:id/langlinks_list")
        ));
        Utils.searchInVisibleListWithName(driver, articleName3_finnish).click();

        //assert article changed to finnish language one
        WebElement titleView = stareAtPixies.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("org.wikipedia.alpha:id/view_article_header_text")
        ));

        assertTrue("Title text should contain title in finnish: " + articleName3_finnish,
                titleView.getText().contains(articleName3_finnish));
    }

    @Test
    public void testOpeningAndClosingReferences(){
        fail("not yet implemented");
    }
}
