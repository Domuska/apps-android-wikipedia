package org.wikipedia.appium;

import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.wikipedia.appium.Utilities.TestDataSource;
import org.wikipedia.appium.Utilities.Utils;

import static junit.framework.Assert.assertTrue;

public class ReadingListTests extends BaseTestClass{

    private String readingListName = TestDataSource.readingListName;
    private String gotItText = "Got it";
    private String readingListText = "Reading lists";

    @Test
    public void testAddArticleToReadingList(){
        //open article
        Utils.openSearchFromStartScreen(driver);
        Utils.searchAndOpenArticleWithName(driver, articleName1);

        //add it to reading list
        driver.findElementById("org.wikipedia.alpha:id/view_article_menu_bar_bookmark")
                .click();
        Utils.findElementByName(driver, gotItText)
                .click();
        WebElement textView = driver.findElementById("org.wikipedia.alpha:id/reading_list_title");
        textView.clear();
        textView.sendKeys(readingListName);
        driver.findElementById("android:id/button1")
                .click();

        //open reading lists and assert article is visible
        Utils.openDrawer(driver);
        Utils.searchInVisibleListWithName(driver, readingListText)
                .click();
        Utils.findElementByName(driver, readingListName)
                .click();

        WebElement article = driver.findElementById("org.wikipedia.alpha:id/contents_list")
                .findElementByXPath("//*[@text='"+ articleName1 +"']");
        assertTrue("Article: " + articleName1 + " not visible in reading list: " + readingListName,
                article.isDisplayed());

        //open the article
        article.click();
        Utils.assertArticleTitleVisibleAndContains(driver, articleName1);

    }
}
