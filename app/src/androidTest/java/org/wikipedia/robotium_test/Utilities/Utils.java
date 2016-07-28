package org.wikipedia.robotium_test.Utilities;

import android.graphics.Point;
import android.widget.EditText;
import android.widget.TextView;

import com.robotium.solo.Solo;

import org.wikipedia.R;
import org.wikipedia.robotium_test.BaseTestClass;

import static junit.framework.Assert.assertTrue;

public class Utils {

    public static void searchAndOpenArticleWithName(Solo solo, String name){
        EditText searchView = (EditText) solo.getView(R.id.search_src_text);
        solo.typeText(searchView, name);

        //wait for the text to be present two times (search field and results), click the second one
        if(solo.waitForText(name, 2, BaseTestClass.TIMEOUT_FIFTEEN_SECONDS_LONG)){
            solo.clickOnText(name, 1);
        }
    }

    public static void openSearchFromStartScreen(Solo solo){
        solo.clickOnView(solo.getView(R.id.search_container));

    }

    public static void openSearchFromArticle(Solo solo){
        solo.clickOnView(solo.getView(R.id.main_search_bar_text));
    }

    public static void assertArticleTitleContains(Solo solo, String name){
        //wait for article
        if(solo.waitForText(name)) {
            String titleText = ((TextView) solo.getView(R.id.view_article_header_text))
                    .getText().toString();
            assertTrue("Title does not contain text: " + name + ", contains instead " + titleText,
                    titleText.contains(name));
        }
    }

    public static void openToC(Solo solo){
        Point deviceSize = new Point();
        solo.getCurrentActivity().getWindowManager().getDefaultDisplay().getSize(deviceSize);

        int screenWidth = deviceSize.x;
        int screenHeight = deviceSize.y;

        int fromX = screenWidth;
        int toX = screenWidth/2;
        int fromY = screenHeight/2;
        int toY = fromY;

        solo.drag(fromX, toX, fromY, toY, 4);
    }

    public static void closeToC(Solo solo){
        Point deviceSize = new Point();
        solo.getCurrentActivity().getWindowManager().getDefaultDisplay().getSize(deviceSize);

        int screenWidth = deviceSize.x;
        int screenHeight = deviceSize.y;
        int startX = screenWidth - solo.getView(R.id.page_toc_list).getWidth();
        int toX = screenWidth;
        int startY = screenHeight/2;
        int toY = startY;
    }

}

