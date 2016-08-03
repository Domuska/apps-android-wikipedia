package org.wikipedia.uiautomator_tests;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.wikipedia.R;
import org.wikipedia.uiautomator_tests.Utils.TestDataSource;
import org.wikipedia.uiautomator_tests.Utils.Utils;

public class TabTests  extends BaseTestClass{


    private String newTabContentDesc;
    private String openInNewTabText;
    private String newTabDefaultText = TestDataSource.newTabDefaultText;
    private String newArticleName = TestDataSource.fullLinkText1;
    private String newArticleNameCapitalized = TestDataSource.fullLinkTextCapitalized;

    @Before
    public void setUp(){
        newTabContentDesc =
                InstrumentationRegistry.getTargetContext().getString(R.string.menu_new_tab);
        openInNewTabText =
                InstrumentationRegistry.getTargetContext().getString(R.string.menu_long_press_open_in_new_tab);
    }

    @Test
    public void testOpenMultipleTabs(){

        //open one article
        Utils.openSearchFromStartScreen(device);
        Utils.searchAndOpenArticleWithName(device, articleName1);

        //open a new tab
        device.findObject(By.res("org.wikipedia.alpha:id/menu_page_show_tabs"))
                .click();

        device.wait(Until.findObject(
                By.desc(newTabContentDesc)), GENERAL_TIMEOUT)
                .click();

        device.wait(Until.findObject(
                By.text(newTabDefaultText)), GENERAL_TIMEOUT)
                .click();
//        device.findObject(By.text(newTabDefaultText)).click();

        //navigate to another article
        Utils.openSearchFromArticle(device);
        Utils.searchAndOpenArticleWithName(device, articleName2);

        //assert you can change between them
        device.findObject(By.res("org.wikipedia.alpha:id/menu_page_show_tabs"))
                .click();
        device.findObject(By.text(articleName1)).click();

        Utils.assertArticleTitleContains(device, articleName1);

    }

    @Test
    public void testOpenArticleInNewTab(){

        //        int headerHeight = device.findObject(
//                By.res("org.wikipedia.alpha:id/view_article_header_text")).
//        Point startPoint = new Point(device.getDisplayWidth()/2, device.getDisplayHeight()/5);
//        Double endY = device.getDisplayHeight()*0.8;
//        Point endPoint = new Point(startPoint.x, endY.intValue());

//        device.drag(startPoint.x, startPoint.y, endPoint.x, endPoint.y, 4);
//        device.drag(endPoint.x, endPoint.y, startPoint.x, startPoint.y, 5);

        //tällä tavalla linkin klikkaaminen toimii kyllä, en tiedä miksi mutta se toimii.
        //uiautomatorviewerin dumpissa kyllä näkyy elementit näin. Voisiko sivua scrollata...?

        //open one article
        Utils.openSearchFromStartScreen(device);
        Utils.searchAndOpenArticleWithName(device, articleName1);

        //open article preview
        device.findObject(By.desc(newArticleName)).click();

        device.wait(Until.findObject(
            By.res("org.wikipedia.alpha:id/link_preview_overflow_button")), GENERAL_TIMEOUT)
            .click();

        device.wait(Until.findObject(
                By.text(openInNewTabText)), GENERAL_TIMEOUT)
                .click();

        device.findObject(By.res("org.wikipedia.alpha:id/menu_page_show_tabs")).click();

        device.wait(Until.findObject(
                By.text(newArticleNameCapitalized)), GENERAL_TIMEOUT)
                .click();
//        device.findObject(By.text(newArticleNameCapitalized)).click();

        //assert title contains right text
        Utils.assertArticleTitleContains(device, newArticleNameCapitalized);
    }
}
