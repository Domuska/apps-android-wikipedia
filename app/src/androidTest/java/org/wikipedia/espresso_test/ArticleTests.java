package org.wikipedia.espresso_test;

import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wikipedia.R;
import org.wikipedia.espresso_test.Utilities.TestDataSource;
import org.wikipedia.espresso_test.Utilities.Utils;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsNot.not;
import static org.wikipedia.espresso_test.Utilities.Utils.withToCLine;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ArticleTests extends BaseTestClass{
    private String article1_referenceSubHeading = TestDataSource.article1_referenceSubHeading;

    @Before
    public void setUp(){
        startActivity = myActivityRule.getActivity();
    }

    @Test
    public void testScrollingToC_clickSubHeading(){
        //open article and table of contents
        Utils.openSearchFromStartScreen();
        Utils.searchAndOpenArticleWithName(articleName1, articleToString1, startActivity);
        Utils.openToC();

        //article1_referenceSubHeading
        onData(withToCLine(article1_referenceSubHeading))
                .inAdapterView(withId(R.id.page_toc_list))
                .perform(click());

        //make sure we the title bar is no longer visible (so we at least moved somewhere)
        onView(allOf(withId(R.id.view_article_header_text), withText(containsString(articleName1))))
                .check(matches(not(isDisplayed())));
    }
}
