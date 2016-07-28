package org.wikipedia.appium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.wikipedia.appium.Utilities.Utils;

import static junit.framework.Assert.assertTrue;

public class HistoryTests extends BaseTestClass{

    private String historyText = "History";

    @Test
    public void testHistory(){

        //open a couple of articles
        Utils.openSearchFromStartScreen(driver);
        Utils.searchAndOpenArticleWithName(driver, articleName1);

        Utils.openSearchFromArticle(driver);
        Utils.searchAndOpenArticleWithName(driver, articleName2);

        Utils.openSearchFromArticle(driver);
        Utils.searchAndOpenArticleWithName(driver, articleName3);

        //go to history to see they are visible
        Utils.openDrawer(driver);
        Utils.searchInVisibleListWithName(driver, historyText)
                .click();

        //assertions
        WebElement historyList = driver.findElementById("org.wikipedia.alpha:id/history_entry_list");
        assertTrue("Article with name: " + articleName1 + " is not visible in history",
                historyList.findElement(By.name(articleName1)).isDisplayed());
        assertTrue("Article with name: " + articleName2 + " is not visible in history",
                historyList.findElement(By.name(articleName2)).isDisplayed());
        assertTrue("Article with name: " + articleName3 + " is not visible in history",
                historyList.findElement(By.name(articleName3)).isDisplayed());

    }
}
