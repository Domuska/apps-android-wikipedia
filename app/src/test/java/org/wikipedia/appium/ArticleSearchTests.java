package org.wikipedia.appium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.wikipedia.appium.Utilities.Utils;

import io.appium.java_client.android.AndroidDriver;

import static junit.framework.Assert.assertTrue;

public class ArticleSearchTests extends BaseTestClass{

    @Test
    public void testSearchArticle_checkTitleShown(){
        //open the article
        Utils.openSearchFromStartScreen(driver);
        Utils.searchAndOpenArticleWithName(driver, articleName1);

        //check that title is displayed and shows the article name
        Utils.assertArticleTitleVisibleAndContains(driver, articleName1);
    }

    


}
