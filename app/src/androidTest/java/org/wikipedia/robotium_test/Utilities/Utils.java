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

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public class Utils {

    public static final int PAGE_LOAD_WAIT = 3000;
    public static final int SLEEP_TIME_1500_MS = 1500;

    public static void searchAndOpenArticleWithName(Solo solo, String name){
        EditText searchView = (EditText) solo.getView(R.id.search_src_text);
        solo.clearEditText(searchView);
        solo.enterText(searchView, name);

        //wait for the text to be present two times (search field and results), click the second one
//        if(solo.waitForText(name, 2, BaseTestClass.TIMEOUT_FIFTEEN_SECONDS_LONG)){
        if(solo.waitForView(R.id.search_results_list)){
            //robotium clicks blindly on first text it finds, so we have to make sure right text is clicked
//            clickElementInSearchResultsList(solo, name);
            solo.clickOnView(solo.getText("^" + name + "$"));
            //give the article a couple seconds to load up
//            solo.sleep(PAGE_LOAD_WAIT);
        }
        else
            fail("search results list not visible");
    }

    public static void openSearchFromStartScreen(Solo solo){
        solo.clickOnView(solo.getView(R.id.search_container));

    }

    public static void openSearchFromArticle(Solo solo){
        solo.clickOnView(solo.getView(R.id.main_search_bar_text));
    }

    public static void assertArticleTitleContains(Solo solo, String name){
        //wait for article
//        if(solo.waitForText(name)) {
        if(solo.waitForView(R.id.view_article_header_text)){
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

        assertTrue("ToC elements not visible",
                solo.getView(R.id.page_toc_item_text).isShown());
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

        assertFalse("ToC elements still visible",
                solo.getView(R.id.page_toc_item_text).isShown());
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
        }while(!elementFound && j < 5);

        return elementFound;
    }

    public static void clickElementInSearchResultsList(Solo solo, String elementName){
        boolean elementFound = false;
        int j = 0;

        do{
            //scroll the list down except on the first run
            if(j != 0)
                solo.scrollDownList(0);

            //search if visible Views have the text we're searching for
            List<View> drawerElements = solo.getViews(solo.getView(R.id.search_results_list));
            for(View view : drawerElements){
                if(view.getId() == R.id.page_list_item_title && view.isShown()){
                    if(((TextView)view).getText().toString().equals(elementName)) {
                        elementFound = true;
                        solo.clickOnView(view);
                    }
                }
            }
            j++;
        }while(!elementFound || j > 5);
    }

    public static void openOverflowMenu(Solo solo){
        /*not the best way to do this, but since commands like
        solo.clickOnMenuItem();
        or
        solo.sendKey(Solo.MENU);
        don't work, this seems to be the only option
        */
        List<View> views = solo.getViews();
        for(View view : views){
            if(view.getContentDescription() != null &&
                    view.getContentDescription().toString().equals("More options")) {
                solo.clickOnView(view);
            }
        }
    }

    public static void openNavDrawer(Solo solo) {
        solo.clickOnActionBarHomeButton();
    }
}

