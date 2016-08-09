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

    private String newArticleName = TestDataSource.fullLinkText1;
    private String newArticleNameCapitalized = TestDataSource.fullLinkTextCapitalized;
    private String openInNewTabText = "Open in new tab";

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

        stareAtPixies.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@text='"+ articleName1 + "']")
        )).click();

        WebElement titleView = stareAtPixies.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("org.wikipedia.alpha:id/view_article_header_text")
        ));

        assertTrue("Title should contain article name: " + articleName1,
                titleView.getText().contains(articleName1));
    }

    @Test
    public void testOpenArticleInNewTab(){

        //open article
        Utils.openSearchFromStartScreen(driver);
        Utils.searchAndOpenArticleWithName(driver, articleName1);

        //open article preview
        Utils.switchToWebContext(driver);
        driver.findElementByLinkText(newArticleName).click();

        //open the article in new tab
        Utils.switchToNativeContext(driver);
        driver.findElementById("org.wikipedia.alpha:id/link_preview_overflow_button").click();
        stareAtPixies.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@text='" + openInNewTabText+"']")))
                .click();

        //open the article from tabs
        stareAtPixies.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("org.wikipedia.alpha:id/menu_page_show_tabs")))
                .click();
        Utils.findElementByName(driver, newArticleNameCapitalized).click();

        //assert the title is correct
        Utils.assertArticleTitleVisibleAndContains(driver, newArticleNameCapitalized);
    }
}
