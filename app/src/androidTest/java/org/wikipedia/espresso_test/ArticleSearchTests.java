package org.wikipedia.espresso_test;


import android.database.Cursor;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wikipedia.R;
import org.wikipedia.espresso_test.Utilities.Utils;
import org.wikipedia.search.RecentSearch;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class ArticleSearchTests extends BaseTestClass{

    private String recentSearchesText;

    @Before
    public void setUp(){
        startActivity = myActivityRule.getActivity();
        recentSearchesText = startActivity.getString(R.string.search_recent_header);
    }

    @Test
    public void testSearchArticle_checkTitleShown(){

        //open the article
        Utils.openSearchFromStartScreen();
        Utils.searchAndOpenArticleWithName(articleName1, articleToString1, startActivity);

        //check the title is displayed in the title view
        Utils.assertArticleTitleContains(articleName1);
    }



    @Test
    public void testSearchArticle_checkRecentsShown(){

        //search and open article in starting fragment
        Utils.openSearchFromStartScreen();
        Utils.searchAndOpenArticleWithName(articleName1, articleToString1, startActivity);

        //search and open articles inside an article
        Utils.openSearchFromArticle();
        Utils.searchAndOpenArticleWithName(articleName2, articleToString2, startActivity);

        Utils.openSearchFromArticle();
        Utils.searchAndOpenArticleWithName(articleName3, articleToString3,  startActivity);

        //go to the search screen
        Utils.openSearchFromArticle();
        onView(withId(R.id.search_close_btn)).perform(click());
        onView(withText(recentSearchesText)).check(matches(isDisplayed()));

        //assert that the recent searches show the articles searched
        onData(withSearchText(articleName1))
                .inAdapterView(withId(R.id.recent_searches_list))
                .check(matches(isDisplayed()));

        onData(withSearchText(articleName2))
                .inAdapterView(withId(R.id.recent_searches_list))
                .check(matches(isDisplayed()));

        onData(withSearchText(articleName2))
                .inAdapterView(withId(R.id.recent_searches_list))
                .check(matches(isDisplayed()));
    }



    //a wizard's spell. Made with help of RecentSearchesFragment.java, adapter at bottom
    //(bindView & getEntry methods)
    public static Matcher<Object> withSearchText(final String text){
        return new BoundedMatcher<Object, Cursor>(Cursor.class) {
            @Override
            public void describeTo(org.hamcrest.Description description) {
                description.appendText("has text of " + text);
            }
            @Override
            protected boolean matchesSafely(Cursor item) {
                return RecentSearch.DATABASE_TABLE.fromCursor(item).getText().contains(text);
            }
        };
    }


}
