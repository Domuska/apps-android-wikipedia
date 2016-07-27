package org.wikipedia.appium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.wikipedia.appium.Utilities.Utils;

import static junit.framework.Assert.assertTrue;

public class ArticleSearchTests extends BaseTestClass{

    @Test
    public void testSearchArticle_checkTitleShown(){
        //open the article
        Utils.openSearchFromStartScreen(driver);
        Utils.searchAndOpenArticleWithName(driver, articleName1);

        WebElement headerTextField =  starePixies.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("org.wikipedia.alpha:id/view_article_header_text")));

        String headerString = headerTextField.getText();
        boolean headerVisible = headerTextField.isDisplayed();
//        String headerString = driver.findElementById("org.wikipedia.alpha:id/view_article_header_text")
//                .getText();

        assertTrue("Header is not visible", headerVisible);
        assertTrue("Page header does not contain string " + articleName1,
                headerString.contains(articleName1));
    }
}
