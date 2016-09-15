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

    private String subHeading1 = TestDataSource.article1_subheading1;
    private String subHeading2 = TestDataSource.article1_subheading2;
    private String subHeading3 = TestDataSource.article1_subheading3;

    private String fullLinkText = TestDataSource.fullLinkText1;
    private String partialLinkText = TestDataSource.partialLinkText;
    private String newArticleTitle = TestDataSource.fullLinkTextCapitalized;

    private String referenceJSclassName = TestDataSource.referencesJSClassName;
    private String referenceTextClassName = TestDataSource.firstReferenceJSClassName;
    private String collapseReferencesClassName = TestDataSource.collapseReferenceJSClassName;
    private String article1_firstReference = TestDataSource.article1_firstReference;

    @Test
    public void testTableOfContents_checkSubTitles(){

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
    public void testClickLink_fullText_assertPreviewShown(){
        //open article
        Utils.openSearchFromStartScreen(driver);
        Utils.searchAndOpenArticleWithName(driver, articleName1);

        //click on link
        Utils.switchToWebContext(driver);
        driver.findElementByLinkText(fullLinkText).click();
        Utils.switchToNativeContext(driver);

        //assert a popup showing preview of new article shows
        assertArticlePreviewVisible();
    }

    @Test
    public void testClickLink_partialText_assertPreviewShown(){
        //open article
        Utils.openSearchFromStartScreen(driver);
        Utils.searchAndOpenArticleWithName(driver, articleName1);

        //click on link
        Utils.switchToWebContext(driver);
        driver.findElementByPartialLinkText(partialLinkText).click();
        Utils.switchToNativeContext(driver);

        //assert a popup showing preview of new article shows
        assertArticlePreviewVisible();
    }

    @Test
    public void testOpeningAndClosingReferences(){
        //navigate to the article
        Utils.openSearchFromStartScreen(driver);
        Utils.searchAndOpenArticleWithName(driver, articleName1);
        Utils.openToC(driver);
        Utils.searchInVisibleListWithName(driver, article1_referenceSubHeading).click();

        Utils.switchToWebContext(driver);
        //open references
        driver.findElementByClassName(referenceJSclassName).click();
        //assert reference is visible
        MobileElement firstReference = driver.findElementByXPath(referenceTextClassName);
        assertThat(firstReference.getText(), containsString(article1_firstReference));
        //close references
        driver.findElementByClassName(collapseReferencesClassName).click();
        //assert reference is no longer visible
        firstReference = driver.findElementByXPath(referenceTextClassName);
        assertThat(firstReference.getText(), not(containsString(article1_firstReference)));
    }

    private void assertArticlePreviewVisible() {
        String previewTitle = stareAtPixies.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("org.wikipedia.alpha:id/link_preview_title")))
                .getText();
        assertThat(previewTitle, is(newArticleTitle));
    }
}
