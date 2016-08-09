package org.wikipedia.appium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.wikipedia.appium.Utilities.Utils;

import static junit.framework.Assert.assertTrue;

public class LanguageTests extends BaseTestClass{

    private String changeLanguageText = "Change language";
    private String overflowMenuContentDescription = "More options";

    @Test
    public void testChangeLanguageInArticle(){
        //open article
        Utils.openSearchFromStartScreen(driver);
        Utils.searchAndOpenArticleWithName(driver, articleName3);

        //open overflow menu in toolbar
        driver.findElementByAccessibilityId(overflowMenuContentDescription).click();
        stareAtPixies.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@text='"+changeLanguageText+"']")
        )).click();

        //change language to finnish
        stareAtPixies.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("org.wikipedia.alpha:id/langlinks_list")
        ));
        Utils.searchInVisibleListWithName(driver, articleName3_finnish).click();

        //assert article changed to finnish language one
        WebElement titleView = stareAtPixies.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("org.wikipedia.alpha:id/view_article_header_text")
        ));

        assertTrue("Title text should contain title in finnish: " + articleName3_finnish,
                titleView.getText().contains(articleName3_finnish));
    }

    @Test
    public void testChangeLanguageInSearch(){
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
                By.xpath("//*[@text='"+ articleName3_finnish + "']")))
                .click();

        //check language is changed
        Utils.assertArticleTitleVisibleAndContains(driver, articleName3_finnish);
    }
}
