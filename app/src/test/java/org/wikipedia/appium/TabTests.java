package org.wikipedia.appium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.wikipedia.appium.Utilities.TestDataSource;
import org.wikipedia.appium.Utilities.Utils;

public class TabTests extends BaseTestClass{

    private String newArticleName = TestDataSource.fullLinkText1;
    private String newArticleNameCapitalized = TestDataSource.fullLinkTextCapitalized;
    private String openInNewTabText = "Open in new tab";


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
