package org.wikipedia.espresso_test;


import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wikipedia.MainActivity;
import org.wikipedia.R;
import org.wikipedia.TestingHelpers.SearchIdlingResource;
import org.wikipedia.database.Database;
import org.wikipedia.espresso_test.Utilities.Utils;
import org.wikipedia.page.Section;
import org.wikipedia.search.RecentSearch;

import android.app.Activity;
import android.database.Cursor;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.espresso.web.model.Atom;
import android.support.test.espresso.web.model.ElementReference;
import android.support.test.espresso.web.webdriver.Locator;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.preference.PreferenceManager;

import net.hockeyapp.android.metrics.model.Base;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findMultipleElements;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;


@RunWith(AndroidJUnit4.class)
public class ArticleSearchTests extends BaseTestClass{

    private String recentSearchesText;
    private String mainFragmentSearchHint;

//    @Rule
//    public ActivityTestRule<MainActivity> myActivityRule =
//            new ActivityTestRule<MainActivity>(MainActivity.class) {
//    };
//
//    @Before
//    public void setUp(){
//        Espresso.registerIdlingResources(SearchIdlingResource.getIdlingResource());
//        startActivity = myActivityRule.getActivity();
//        recentSearchesText = startActivity.getString(R.string.search_recent_header);
//        mainFragmentSearchHint = startActivity.getString(R.string.search_hint);
//    }
//
//    @After
//    public void tearDown(){
//        Espresso.unregisterIdlingResources(SearchIdlingResource.getIdlingResource());
//
//        PreferenceManager.
//                getDefaultSharedPreferences(
//                        myActivityRule.getActivity().getApplicationContext())
//                .edit().clear().commit();
//
//        Database.clearDatabase(myActivityRule.getActivity().getApplicationContext());
//    }

    @Before
    public void setUp(){
        startActivity = myActivityRule.getActivity();
    }

    @Test
    public void testSearchArticle_checkTitleShown(){

        //open the article
        Utils.enterSearchScreenFromStartingFragment();
        Utils.enterSearchTermAndOpenArticle(articleName1, articleToString1, startActivity);

        //check the title is displayed in the title view
        onView(allOf(withId(R.id.view_article_header_text), withText(containsString(articleName1))))
                .check(matches(isDisplayed()));

//        onWebView().forceJavascriptEnabled();
//
//        onWebView()
//                .check(webContent(containingTextInBody(articleName1)));

//        onWebView()
//                .check(webMatches(getCurrentUrl(), is(articleName1)));


//        onView(withId(R.id.floating_toc_button)).perform(click());

//        onWebView()
//                .check(webContent(hasElementWithId("content_block_19")));
    }

    @Test
    public void testSearchArticle_checkRecentsShown(){

        //search and open article in starting fragment
        Utils.enterSearchScreenFromStartingFragment();
        Utils.enterSearchTermAndOpenArticle(articleName1, articleToString1, startActivity);

        //search and open articles inside an article
        onView(withId(R.id.main_search_bar_text))
                .perform(click());
        Utils.enterSearchTermAndOpenArticle(articleName2, articleToString2, startActivity);

        onView(withId(R.id.main_search_bar_text))
                .perform(click());
        Utils.enterSearchTermAndOpenArticle(articleName3, articleToString3,  startActivity);

        //go to the search screen
        onView(withId(R.id.main_search_bar_text)).perform(click());
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

    @Test
    public void testSearchArticle_changeLanguageInSearch(){
        Utils.enterSearchScreenFromStartingFragment();
        Utils.enterSearchTermAndOpenArticle(articleName3, articleToString3, myActivityRule.getActivity());

        //check title is shown in default language
        onView(allOf(withId(R.id.view_article_header_text), withText(containsString(articleName3))))
                .check(matches(isDisplayed()));

        //change language in search
        onView(withId(R.id.main_search_bar_text)).perform(click());
        onView(withId(R.id.search_lang_button)).perform(click());
        onView(withId(R.id.preference_languages_filter)).perform(typeText(finnishLanguage));
        onView(withId(R.id.localized_language_name)).perform(click());

        //open article again, use position 0 since it should show the best fitting result
        onData(hasToString(articleToString3_finnish))
                .atPosition(0)
                .inAdapterView(withId(R.id.search_results_list))
                .perform(click());

        //check language is changed
        onView(allOf(withId(R.id.view_article_header_text), withText(containsString(articleName3_finnish))))
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
