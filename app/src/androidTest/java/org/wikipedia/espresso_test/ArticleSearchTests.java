package org.wikipedia.espresso_test;


import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wikipedia.R;
import org.wikipedia.espresso_test.Utilities.Utils;
import org.wikipedia.search.RecentSearch;

import android.database.Cursor;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;


@RunWith(AndroidJUnit4.class)
@LargeTest
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
        Utils.openSearchFromStartScreen();
        Utils.searchAndOpenArticleWithName(articleName1, articleToString1, startActivity);

        //check the title is displayed in the title view
        Utils.assertArticleTitleContains(articleName1);

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

    @Test
    public void testSearchArticle_changeLanguageInSearch(){
        Utils.openSearchFromStartScreen();
        Utils.searchAndOpenArticleWithName(articleName3, articleToString3, myActivityRule.getActivity());

        //check title is shown in default language
        Utils.assertArticleTitleContains(articleName3);

        //change language in search
        Utils.openSearchFromArticle();
        onView(withId(R.id.search_lang_button)).perform(click());
        onView(withId(R.id.preference_languages_filter)).perform(typeText(finnishLanguage));
        onView(withId(R.id.localized_language_name)).perform(click());

        //open article again, use position 0 since it should show the best fitting result
        onData(hasToString(articleToString3_finnish))
                .atPosition(0)
                .inAdapterView(withId(R.id.search_results_list))
                .perform(click());

        //check language is changed
        Utils.assertArticleTitleContains(articleName3_finnish);
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
