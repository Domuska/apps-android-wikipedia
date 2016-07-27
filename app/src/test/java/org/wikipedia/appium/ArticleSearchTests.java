package org.wikipedia.appium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.wikipedia.appium.Utilities.Utils;

import static junit.framework.Assert.assertTrue;


//todo note: driver.switch() tms komennolla pitää varmaan vaihtaa kontekstia kun siirrytään natiivista webviewiin
//https://github.com/appium/appium/issues/3784
public class ArticleSearchTests extends BaseTestClass{

    private String recentSearchesText = "Recent searches:";

    @Test
    public void testSearchArticle_checkTitleShown(){
        //open the article
        Utils.openSearchFromStartScreen(driver);
        Utils.searchAndOpenArticleWithName(driver, articleName1);

        //check that title is displayed and shows the article name
        Utils.assertArticleTitleVisibleAndContains(driver, articleName1);
    }

    @Test
    public void testSearchArticle_checkRecentsShown(){

        //search and open articles
        Utils.openSearchFromStartScreen(driver);
        Utils.searchAndOpenArticleWithName(driver, articleName1);

        Utils.openSearchFromArticle(driver);
        Utils.searchAndOpenArticleWithName(driver, articleName2);

        Utils.openSearchFromArticle(driver);
        Utils.searchAndOpenArticleWithName(driver, articleName3);

        //go to the search screen
        Utils.openSearchFromArticle(driver);
        driver.findElementById("org.wikipedia.alpha:id/search_close_btn").click();
        assertTrue("Recent searches is not displayed",
                driver.findElementByName(recentSearchesText).isDisplayed());

        //assert that the recent searches show the articles
        assertTrue("Article " + articleName1 + " is not visible in recent searches",
                Utils.searchInVisibleListWithName(driver, articleName1).isDisplayed());
        assertTrue("Article " + articleName2 + " is not visible in recent searches",
                Utils.searchInVisibleListWithName(driver, articleName2).isDisplayed());
        assertTrue("Article " + articleName3 + " is not visible in recent searches",
                Utils.searchInVisibleListWithName(driver, articleName3).isDisplayed());

    }

    @Test
    public void testSearchArticle_changeLanguageInSearch(){
        Utils.openSearchFromStartScreen(driver);
        Utils.searchAndOpenArticleWithName(driver, articleName3);

        //check title is shown in default language
        Utils.assertArticleTitleVisibleAndContains(driver, articleName3);

        //change language in search
        Utils.openSearchFromArticle(driver);
        driver.findElementById("org.wikipedia.alpha:id/search_lang_button").click();
        driver.findElementById("org.wikipedia.alpha:id/preference_languages_filter")
                .sendKeys(finnishLanguage);
        driver.findElementById("org.wikipedia.alpha:id/localized_language_name").click();

        //open article again
        stareAtPixies.until(ExpectedConditions.visibilityOfElementLocated(
                By.name(articleName3_finnish)))
        .click();

        //check language is changed
        Utils.assertArticleTitleVisibleAndContains(driver, articleName3_finnish);
    }


}
