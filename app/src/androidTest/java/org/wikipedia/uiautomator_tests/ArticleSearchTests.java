package org.wikipedia.uiautomator_tests;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.wikipedia.R;
import org.wikipedia.uiautomator_tests.Utils.Utils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringStartsWith.startsWith;

public class ArticleSearchTests extends BaseTestClass{

    private String recentSearchesText;

    @Before
    public void setUp(){
        recentSearchesText = InstrumentationRegistry.getTargetContext().getString(R.string.search_recent_header);
    }

    @Test
    public void testSearchArticle_checkTitleShown(){
        //open the article
        Utils.openSearchFromStartScreen(device);
        Utils.searchAndOpenArticleWithName(device, articleName1);

        //check the title is displayed in the title view
        Utils.assertArticleTitleContains(device, articleName1);
    }


}
