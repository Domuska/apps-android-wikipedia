package org.wikipedia.uiautomator_tests;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;

import org.junit.Before;
import org.junit.Test;
import org.wikipedia.R;

public class ArticleSearchTests extends BaseTestClass{

    private String recentSearchesText;

    @Before
    public void setUp(){
        recentSearchesText = InstrumentationRegistry.getTargetContext().getString(R.string.search_recent_header);
    }

    @Test
    public void testSearchArticle_checkTitleShown(){
        device.findObject(By.res("org.wikipedia.alpha:id/search_container"))
                .click();

        device.findObject(By.res("org.wikipedia.alpha:id/search_src_text")).setText(articleName1);
    }
}
