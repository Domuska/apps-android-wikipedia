package org.wikipedia.uiautomator_tests;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wikipedia.R;
import org.wikipedia.WikipediaApp;
import org.wikipedia.uiautomator_tests.Utils.Utils;

public class LanguageTests extends BaseTestClass{

    private String appLanguageCode;
    String changeLanguageText;

    @Before
    public void setUp(){
        appLanguageCode = WikipediaApp.getInstance().getAppOrSystemLanguageCode();
        changeLanguageText = InstrumentationRegistry.getTargetContext()
                .getString(R.string.menu_page_other_languages);
    }

    @After
    public void tearDown(){
        //set app's language back to original
        WikipediaApp.getInstance().setAppLanguageCode(appLanguageCode);
    }

    @Test(timeout=50000)
    public void testChangeLanguageInArticle() throws Exception{

        //open article
        Utils.openSearchFromStartScreen(device);
        Utils.searchAndOpenArticleWithName(device, articleName3);

        //open overflow menu in toolbar
        device.wait(Until.findObject(
                By.desc("More options")), GENERAL_TIMEOUT)
                .click();
        device.wait(Until.findObject(
                By.text(changeLanguageText)), GENERAL_TIMEOUT)
                .click();

        //change language to finnish
        device.wait(Until.hasObject(
                By.res("org.wikipedia.alpha:id/langlinks_list")), GENERAL_TIMEOUT);
        UiScrollable languageList = new UiScrollable(
                new UiSelector().resourceId("org.wikipedia.alpha:id/langlinks_list"));
        languageList.getChildByText(
                new UiSelector().className("android.widget.LinearLayout"), articleName3_finnish)
                .click();

        //assert article changed language
        device.wait(Until.hasObject(
                By.res("org.wikipedia.alpha:id/view_article_header_text")), GENERAL_TIMEOUT);
        Utils.assertArticleTitleContains(device, articleName3_finnish);
    }

    @Test(timeout=50000)
    public void testChangeLanguageInSearch(){

        Utils.openSearchFromStartScreen(device);
        Utils.searchAndOpenArticleWithName(device, articleName3);

        //check title is shown in default language
        Utils.assertArticleTitleContains(device, articleName3);

        //change language in search
        Utils.openSearchFromArticle(device);

        device.wait(Until.findObject(
                By.res("org.wikipedia.alpha:id/search_lang_button")), GENERAL_TIMEOUT)
                .click();
        //device.findObject(By.res("org.wikipedia.alpha:id/preference_languages_filter"))
        //        .setText(finnishLanguage);
        device.wait(Until.findObject(By.res("org.wikipedia.alpha:id/preference_languages_filter")),
                GENERAL_TIMEOUT).setText(finnishLanguage);
        //device.findObject(By.res("org.wikipedia.alpha:id/localized_language_name"))
        //        .click();
        device.wait(Until.findObject(By.res("org.wikipedia.alpha:id/localized_language_name")),
                GENERAL_TIMEOUT).click();

        //open article again
        device.wait(Until.findObject(
                By.text(articleName3_finnish)), GENERAL_TIMEOUT)
                .click();

        //check language is changed
        Utils.assertArticleTitleContains(device, articleName3_finnish);
    }
}
