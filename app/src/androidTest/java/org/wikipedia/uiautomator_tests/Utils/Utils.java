package org.wikipedia.uiautomator_tests.Utils;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import org.wikipedia.uiautomator_tests.BaseTestClass;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;

public class Utils {

    public static void assertArticleTitleContains(UiDevice device, String articleName1) {
        UiObject2 header = device.findObject(By.res("org.wikipedia.alpha:id/view_article_header_text"));
        assertThat(header.getText(), startsWith(articleName1));
    }

    public static void openSearchFromStartScreen(UiDevice device) {
        device.findObject(By.res("org.wikipedia.alpha:id/search_container"))
                .click();
    }

    public static void searchAndOpenArticleWithName(UiDevice device, String name) {
        device.findObject(By.res("org.wikipedia.alpha:id/search_src_text")).setText(name);
        UiObject2 resultsList = device.wait(Until.findObject(
                By.res("org.wikipedia.alpha:id/search_results_list")
        ), BaseTestClass.GENERAL_TIMEOUT);
        resultsList.findObject(By.text(name)).click();

        device.wait(Until.hasObject(
                By.res("org.wikipedia.alpha:id/view_article_header_text")
        ), BaseTestClass.GENERAL_TIMEOUT);
    }

}
