package org.wikipedia.appium.Utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.wikipedia.appium.BaseTestClass;

import java.util.Set;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import static junit.framework.Assert.assertTrue;

public class Utils {

    private static WebDriverWait webDriverWait;

    public static void openSearchFromStartScreen(AndroidDriver driver){
        webDriverWait = new WebDriverWait(driver, BaseTestClass.TIMEOUT_FIFTEEN_SECONDS);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("org.wikipedia.alpha:id/search_container")
        )).click();
    }

    public static void openSearchFromArticle(AndroidDriver driver){
        webDriverWait = new WebDriverWait(driver, BaseTestClass.TIMEOUT_FIFTEEN_SECONDS);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("org.wikipedia.alpha:id/main_search_bar_text")))
        .click();

    }

    //todo kysy mikan mielipidettä, käytetäänkö APIa vai omaa kikkaa?
    public static void searchAndOpenArticleWithName (AndroidDriver driver, String name){

        //send keycode backspace so the current search term is removed
        driver.pressKeyCode(67);
        webDriverWait = new WebDriverWait(driver, BaseTestClass.TIMEOUT_FIFTEEN_SECONDS);
        driver.findElementById("org.wikipedia.alpha:id/search_src_text").sendKeys(name);
//        textField.clear();
//        textField.sendKeys(name);

        WebElement resultList = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("org.wikipedia.alpha:id/search_results_list")
        ));


        webDriverWait.until(ExpectedConditions.visibilityOf(
                resultList.findElement(By.name(name))
        )).click();
    }

    public static WebElement searchInVisibleListWithName(AndroidDriver driver, String elementName){
        //apparently this is the way to do it, according to
        // https://github.com/appium/java-client/issues/421
        return driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)" +
                ".instance(0)).scrollIntoView(new UiSelector()" +
                ".textContains(\"" + elementName + "\").instance(0))");
    }

    public static void assertArticleTitleVisibleAndContains(AndroidDriver driver, String articleName) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, BaseTestClass.TIMEOUT_FIFTEEN_SECONDS);
        WebElement headerTextField =  webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("org.wikipedia.alpha:id/view_article_header_text")));

        String headerString = headerTextField.getText();
        boolean headerVisible = headerTextField.isDisplayed();

        assertTrue("Header is not visible", headerVisible);
        assertTrue("Page header does not contain string " + articleName,
                headerString.contains(articleName));
    }

    public static void openToC(AndroidDriver driver){
        //swipe the right drawer open

        Dimension screen = driver.manage().window().getSize();
        int startX = screen.width-5;
        int startY=  screen.height/2;
        int endX = screen.width/2;
        int endY = startY;

        driver.swipe(startX, startY, endX, endY, 300);
    }

    public static void closeToC(AndroidDriver driver){
        WebElement tocList = driver.findElementById("org.wikipedia.alpha:id/page_toc_list");

        //get middle point of drawer
        Dimension dimension = tocList.getSize();
        Point startPoint = new Point(dimension.getWidth()/2, dimension.getHeight()/2);
        Point endPoint = new Point(driver.manage().window().getSize().width-5,
                startPoint.getY());

        //swipe from middle point to edge of screen
        driver.swipe(startPoint.x, startPoint.y, endPoint.x, endPoint.y, 300);
    }

    public static void openDrawer(AndroidDriver driver){
        driver.findElementByAccessibilityId("Wikipedia Alpha").click();
    }

    public static void switchToWebContext(AndroidDriver driver){

//        Set contextNames = driver.getContextHandles();
//        driver.context((String)contextNames.toArray()[1]);
        driver.context("WEBVIEW_org.wikipedia.alpha");


//        for(String context : contextNames) {
//            System.out.println(context);
//        }
    }

    public static void switchToNativeContext(AndroidDriver driver){
        driver.context("NATIVE_APP");
    }
}
