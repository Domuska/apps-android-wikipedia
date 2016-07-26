package org.wikipedia.espresso_test;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;

import org.junit.Before;
import org.junit.Test;
import org.wikipedia.R;
import org.wikipedia.espresso_test.Utilities.Utils;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;

public class HistoryTests extends BaseTestClass{

    private String historyText;

    @Before
    public void setUp(){
        startActivity = myActivityRule.getActivity();
        historyText = startActivity.getString(R.string.nav_item_history);
    }

    @Test
    public void testHistory(){

        //open couple of articles
        Utils.openSearchFromStartScreen();
        Utils.searchAndOpenArticleWithName(articleName1, articleToString1, startActivity);

        Utils.openSearchFromArticle();
        Utils.searchAndOpenArticleWithName(articleName2, articleToString2, startActivity);

        Utils.openSearchFromArticle();
        Utils.searchAndOpenArticleWithName(articleName3, articleToString3, startActivity);

        //go to history to see that they are visible
        Utils.openNavDrawer();
        //the app seems to use design library nav drawer that hides IDs and such
        //from outside, issue in here: https://code.google.com/p/android/issues/detail?id=187701,
        //can't use onData or RecyclerViewAction because of this
        onView(allOf(withText(historyText), withClassName(endsWith("CheckedTextView"))))
                .perform(click());

        //assertions
        onView(allOf(withText(articleName1), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
                .check(matches(isDisplayed()));
        onView(allOf(withText(articleName2), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
                .check(matches(isDisplayed()));
        onView(allOf(withText(articleName3), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
                .check(matches(isDisplayed()));
    }
}