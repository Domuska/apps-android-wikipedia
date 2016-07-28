package org.wikipedia.robotium_test.Utilities;

import com.robotium.solo.Solo;

import org.wikipedia.R;

public class Utils {

    public static void searchAndOpenArticleWithName(Solo solo){
//        solo.getView(R.id.search_src_text);
    }

    public static void openSearchFromStartScreen(Solo solo){
        solo.getView(R.id.search_container).performClick();
    }
}

