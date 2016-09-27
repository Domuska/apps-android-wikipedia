package org.wikipedia.uiautomator_tests.Utils;

import android.graphics.Point;
import android.support.test.espresso.action.GeneralClickAction;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.widget.ListView;

import net.hockeyapp.android.metrics.model.Base;

import org.wikipedia.uiautomator_tests.BaseTestClass;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.core.StringStartsWith.startsWith;

public class Utils {

    private static final int ONE_MINUTE_TIMEOUT = 60000;

    public static void assertArticleTitleContains(UiDevice device, String articleName1) {
        device.waitForIdle();
        UiObject2 header;
        do {
            header = device.wait(Until.findObject(
                    By.res("org.wikipedia.alpha:id/view_article_header_text")
            ), BaseTestClass.GENERAL_TIMEOUT);
        }while(header == null);
        assertThat(header.getText(), startsWith(articleName1));
    }

    public static void openSearchFromStartScreen(UiDevice device) {
        device.wait(Until.findObject(By.res("org.wikipedia.alpha:id/search_container")),
                BaseTestClass.GENERAL_TIMEOUT)
                .click();
    }

    public static void openSearchFromArticle(UiDevice device) {
        device.waitForIdle();
        device.wait(Until.findObject(
                By.res("org.wikipedia.alpha:id/main_search_bar_text")),
                BaseTestClass.GENERAL_TIMEOUT)
                .click();
    }

    public static void searchAndOpenArticleWithName(UiDevice device, String name){

        UiObject2 searchBar;
        do{
            device.waitForIdle();
            searchBar = device.findObject(By.res("org.wikipedia.alpha:id/search_src_text"));
        }while(searchBar == null);
        searchBar.setText(name);

        //device.wait(Until.findObject(
        //        By.res("org.wikipedia.alpha:id/search_src_text")), BaseTestClass.GENERAL_TIMEOUT)
        //        .setText(name);



        device.wait(Until.hasObject(By.res("org.wikipedia.alpha:id/search_results_list")),
                BaseTestClass.GENERAL_TIMEOUT);

        UiObject resultsList;
        //do this since if we just wait for the UiObject2 it is often not found
        do{
            device.waitForIdle();
            resultsList = device.findObject(new UiSelector()
                    .resourceId("org.wikipedia.alpha:id/search_results_list"));
        }while(resultsList == null);

        

        //click the (hopefully) correct article in results list
        try {
            resultsList.getChild(new UiSelector().text(name)).click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }

        //wait for the loading bar to disappear - if it is visible and we continue, all goes to hell
        device.wait(Until.gone(By.res("org.wikipedia.alpha:id/main_progressbar")), ONE_MINUTE_TIMEOUT);

        UiObject2 headerText = device.findObject(By.res("org.wikipedia.alpha:id/view_article_header_text"));
        assertThat(headerText, is(not(nullValue())));
        //device.wait(Until.hasObject(
        //        By.res("org.wikipedia.alpha:id/view_article_header_text")
        //), BaseTestClass.GENERAL_TIMEOUT);
    }

    //open table of contents in article
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
