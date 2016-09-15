package org.wikipedia.appium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.wikipedia.appium.Utilities.TestDataSource;
import org.wikipedia.appium.Utilities.Utils;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public class TabTests extends BaseTestClass{

    private String newTabContentDesc = "New tab";
    private String newTabDefaultText = TestDataSource.newTabDefaultText;

    @Test
    public void testOpenMultipleTabs(){

        //open one article
        Utils.openSearchFromStartScreen(driver);
        Utils.searchAndOpenArticleWithName(driver, articleName1);

        //open a new tab
        stareAtPixies.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("org.wikipedia.alpha:id/menu_page_show_tabs")
        )).click();
        driver.findElementByAccessibilityId(newTabContentDesc).click();

        WebElement tabsList = stareAtPixies.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("org.wikipedia.alpha:id/tabs_list")
        ));

        tabsList.findElement(By.xpath("//*[@text='"+ newTabDefaultText + "']")).click();

        //navigate to another article
        Utils.openSearchFromArticle(driver);
        Utils.searchAndOpenArticleWithName(driver, articleName2);

        //assert you can change between them
        stareAtPixies.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("org.wikipedia.alpha:id/menu_page_show_tabs")
        )).click();

        //search the element in this list this way to reduce flakiness
        WebElement tabList = stareAtPixies.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("org.wikipedia.alpha:id/tabs_list")
        ));
        tabList.findElement(By.xpath("//*[@text='"+ articleName1 + "']")).click();

        String titleText = stareAtPixies.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("org.wikipedia.alpha:id/view_article_header_text")))
                .getText();

        assertTrue("Title should contain article name: " + articleName1 + " contains instead " + titleText,
                titleText.contains(articleName1));
    }
}
