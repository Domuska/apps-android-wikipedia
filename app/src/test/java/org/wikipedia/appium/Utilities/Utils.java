package org.wikipedia.appium.Utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.wikipedia.appium.BaseTestClass;

import io.appium.java_client.android.AndroidDriver;

import static junit.framework.Assert.assertTrue;

public class Utils {

    private static WebDriverWait webDriverWait;

    public static void openSearchFromStartScreen(AndroidDriver driver){
        webDriverWait = new WebDriverWait(driver, BaseTestClass.TIMEOUT_TEN_SECONDS);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("org.wikipedia.alpha:id/search_container")
        )).click();
    }

    public static void searchAndOpenArticleWithName (AndroidDriver driver, String name){
        driver.findElementById("org.wikipedia.alpha:id/search_src_text")
                .sendKeys(name);
        searchInVisibleListWithName(driver, name).click();
    }

    public static WebElement searchInVisibleListWithName(AndroidDriver driver, String elementName){
        //apparently this is the way to do it, according to
        // https://github.com/appium/java-client/issues/421
        return driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)" +
                ".instance(0)).scrollIntoView(new UiSelector()" +
                ".textContains(\"" + elementName + "\").instance(0))");
    }

    public static void assertArticleTitleVisibleAndContains(AndroidDriver driver, String articleName1) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, BaseTestClass.TIMEOUT_TEN_SECONDS);
        WebElement headerTextField =  webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("org.wikipedia.alpha:id/view_article_header_text")));

        String headerString = headerTextField.getText();
        boolean headerVisible = headerTextField.isDisplayed();

        assertTrue("Header is not visible", headerVisible);
        assertTrue("Page header does not contain string " + articleName1,
                headerString.contains(articleName1));
    }
}
