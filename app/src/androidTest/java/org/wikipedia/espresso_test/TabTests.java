package org.wikipedia.espresso_test;

import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wikipedia.R;
import org.wikipedia.espresso_test.Utilities.Utils;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TabTests extends BaseTestClass{


    @Before
    public void setUp(){
        startActivity = myActivityRule.getActivity();
    }

    @Test
    public void testOpenMultipleTabs(){

        //open one article
        Utils.enterSearchScreenFromStartingFragment();
        Utils.searchAndOpenArticleWithName(articleName1, articleToString1, startActivity);

        //open a new tab
        onView(withId(R.id.menu_page_show_tabs)).perform(click());
        onView(withContentDescription("New tab")).perform(click());

        //navigate to another article
        onView(withId(R.id.main_search_bar_text))
                .perform(click());
        Utils.searchAndOpenArticleWithName(articleName2, articleToString2, startActivity);

        //assert you can change between them
        onView(withId(R.id.menu_page_show_tabs)).perform(click());
        onView(withText(articleName1)).perform(click());

        onView(allOf(withId(R.id.view_article_header_text),
                withText(containsString(articleName1))))
                .check(matches(isDisplayed()));

    }


}
