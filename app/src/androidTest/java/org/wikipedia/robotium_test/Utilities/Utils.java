package org.wikipedia.robotium_test.Utilities;

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
        if(solo.waitForText(name, 2, BaseTestClass.TIMEOUT_FIFTEEN_SECONDS)){
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


}

