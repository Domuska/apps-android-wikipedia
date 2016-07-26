package org.wikipedia.espresso_test;

import android.support.design.widget.NavigationView;
import android.support.test.espresso.contrib.RecyclerViewActions;

import org.junit.Before;
import org.junit.Test;
import org.wikipedia.R;
import org.wikipedia.espresso_test.Utilities.TestDataSource;
import org.wikipedia.espresso_test.Utilities.Utils;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;

public class ReadingListTests extends BaseTestClass{

    private String readingListName = TestDataSource.readingListName;
    private String gotItText;
    private String readingListText;

    @Before
    public void setUp(){
        startActivity = myActivityRule.getActivity();
        gotItText = startActivity.getString(R.string.reading_lists_onboarding_got_it);
        readingListText = startActivity.getString(R.string.nav_item_reading_lists);
    }

    @Test
    public void testAddArticleToReadingList(){
        //open an article
        Utils.openSearchFromStartScreen();
        Utils.searchAndOpenArticleWithName(articleName1, articleToString1, startActivity);

        //add it to reading list
        onView(withId(R.id.view_article_menu_bar_bookmark)).perform(click());
        onView(withText(gotItText)).perform(click());
        onView(withId(R.id.reading_list_title))
                .perform(clearText())
                .perform(typeText(readingListName));
        onView(withId(android.R.id.button1)).perform(click());

        //open reading lists and assert article is visible in the list
        Utils.openNavDrawer();
        //the app seems to use design library nav drawer that hides IDs and such
        //from outside, issue in here: https://code.google.com/p/android/issues/detail?id=187701
        //can't use onData or RecyclerViewAction because of this
        onView(withText(readingListText)).perform(click());
        onView(withText(readingListName)).perform(click());
        onView(allOf(withText(articleName1), isDisplayed())).check(matches(isDisplayed()));
    }


}
