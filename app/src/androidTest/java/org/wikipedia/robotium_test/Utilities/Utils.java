package org.wikipedia.robotium_test.Utilities;

import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.robotium.solo.Solo;

import org.wikipedia.R;
import org.wikipedia.robotium_test.BaseTestClass;

import java.util.List;

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

        Log.d("openToC", "screen width: " + screenWidth + ", screen height: " + screenHeight);

        int fromX = screenWidth - 5;
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
        int fromX = screenWidth - solo.getView(R.id.page_toc_list).getWidth();
        int toX = screenWidth-5;
        int fromY = screenHeight/2;
        int toY = fromY;

        solo.drag(fromX, toX, fromY, toY, 4);
    }

    //this way to do searching is dirty-ish, but it seems there is no way to search
    //in a specific list with Robotium. This way also would not work if the nav drawer list
    //was long enough. Could be made smarter but as a whole, bad idea.
    // issue done in github: https://github.com/RobotiumTech/robotium/issues/844
    public static boolean isElementFoundInToC(Solo solo, String elementName) {
        boolean elementFound = false;
        int j = 0;

        do{
            //scroll the list down except on the first run
            if(j != 0)
                solo.scrollDownList(0);

            //search if visible Views have the text we're searching for
            List<View> drawerElements = solo.getViews(solo.getView(R.id.page_toc_list));
            for(View view : drawerElements){
                if(view.getId() == R.id.page_toc_item_text){
                    if(((TextView)view).getText().toString().equals(elementName)) {
                        elementFound = true;
                    }
                }
            }
            j++;
        }while(!elementFound && j < 10);

        return elementFound;
    }

}

