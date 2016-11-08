package org.wikipedia.espresso_test.Utilities;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.Gravity;
import android.widget.EditText;

import org.hamcrest.Matcher;
import org.wikipedia.R;
import org.wikipedia.page.Section;
import org.wikipedia.search.SearchResult;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

public class Utils {

    // helper method to enter text into the search box when we're already in the serach screen
    public static void searchAndOpenArticleWithName(
            final String name, String articleToString, Activity currentActivity){

        //have to do a dirty trick like this since otherwise IdlingResource will stop after each letter
        //to wait for response to network call
        final EditText searchField = (EditText) currentActivity.findViewById(R.id.search_src_text);
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                searchField.setText(name);
            }
        });

        //open the article in results
        onData(allOf(instanceOf(SearchResult.class), hasToString(is(articleToString))))
                .inAdapterView(withId(R.id.search_results_list))
                .perform(click());
    }

    public static void openSearchFromStartScreen(){
        onView(withId(R.id.search_container)).perform(click());
    }

    public static void openSearchFromArticle() {
        onView(withId(R.id.main_search_bar_text)).perform(click());
    }

    public static void closeToC(){
        onView(withId(R.id.page_toc_drawer)).perform(DrawerActions.close(Gravity.END));
    }

    public static void openToC(){
        onView(withId(R.id.page_toc_drawer)).perform(DrawerActions.open(Gravity.END));
    }

    public static void assertArticleTitleContains(String articleName1) {
        onView(allOf(withId(R.id.view_article_header_text), withText(containsString(articleName1))))
                .check(matches(isDisplayed()));
    }

    public static void openNavDrawer(){
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open(Gravity.START));
    }

    //custom matcher for table of contents rows
    public static Matcher<Object> withToCLine(final String text){
        return new BoundedMatcher<Object, Section>(Section.class) {
            @Override
            public void describeTo(org.hamcrest.Description description) {
                description.appendText("has text of " + text);
            }
            @Override
            protected boolean matchesSafely(Section item) {
                return item.getHeading().equals(text);
            }
        };
    }
}
