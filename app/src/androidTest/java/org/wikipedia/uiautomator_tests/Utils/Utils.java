package org.wikipedia.uiautomator_tests.Utils;

import android.graphics.Point;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import org.wikipedia.uiautomator_tests.BaseTestClass;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;

public class Utils {

    public static void assertArticleTitleContains(UiDevice device, String articleName1) {
        UiObject2 header = device.wait(Until.findObject(
                By.res("org.wikipedia.alpha:id/view_article_header_text")
        ), BaseTestClass.GENERAL_TIMEOUT);
        assertThat(header.getText(), startsWith(articleName1));
    }

    public static void openSearchFromStartScreen(UiDevice device) {
        device.findObject(By.res("org.wikipedia.alpha:id/search_container"))
                .click();
    }

    public static void openSearchFromArticle(UiDevice device) {
        device.findObject(By.res("org.wikipedia.alpha:id/main_search_bar_text")).click();
    }

    public static void searchAndOpenArticleWithName(UiDevice device, String name){

        device.wait(Until.findObject(
                By.res("org.wikipedia.alpha:id/search_src_text")), BaseTestClass.GENERAL_TIMEOUT)
                .setText(name);
        //small wait so the list will be properly loaded, reduces flakiness
        try{
            device.wait(500);
        }
        catch(Exception e){}

        UiObject2 resultsList = device.wait(Until.findObject(
                By.res("org.wikipedia.alpha:id/search_results_list")
        ), BaseTestClass.GENERAL_TIMEOUT);

        resultsList.wait(Until.findObject(
                By.text(name)), BaseTestClass.GENERAL_TIMEOUT)
                .click();

        device.wait(Until.hasObject(
                By.res("org.wikipedia.alpha:id/view_article_header_text")
        ), BaseTestClass.GENERAL_TIMEOUT);
    }


    public static void openToc(UiDevice device){
        Point startPoint = new Point(device.getDisplayWidth()-5, device.getDisplayHeight()/2);
        Point endPoint = new Point(device.getDisplayWidth()/2, startPoint.y);
        device.drag(startPoint.x, startPoint.y, endPoint.x, endPoint.y, 5);
        device.waitForIdle(BaseTestClass.GENERAL_TIMEOUT);
    }

    public static void closeToC(UiDevice device){
        device.findObject(By.res("org.wikipedia.alpha:id/page_toc_list")).swipe(Direction.RIGHT, 0.95f);
    }

    public static void openDrawer(UiDevice device){
        Point startPoint = new Point(0, device.getDisplayHeight()/2);
        Point endPoint = new Point(device.getDisplayWidth()/2, device.getDisplayHeight()/2);

        //wait that all popup windows and such are closed
        device.drag(startPoint.x, startPoint.y, endPoint.x, endPoint.y, 5);
        device.waitForIdle(5000);
    }
}
