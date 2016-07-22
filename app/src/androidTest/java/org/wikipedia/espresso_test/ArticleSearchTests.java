package org.wikipedia.espresso_test;


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

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.preference.PreferenceManager;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;


@RunWith(AndroidJUnit4.class)
public class ArticleSearchTests {

    private String articleName = "Final Fantasy XII";

    @Rule
    public ActivityTestRule<MainActivity> myActivityRule =
            new ActivityTestRule<MainActivity>(MainActivity.class) {
    };

    @Before
    public void setUp(){
        Espresso.registerIdlingResources(SearchIdlingResource.getIdlingResource());
    }

    @After
    public void tearDown(){
        Espresso.unregisterIdlingResources(SearchIdlingResource.getIdlingResource());

        PreferenceManager.
                getDefaultSharedPreferences(
                        myActivityRule.getActivity().getApplicationContext())
                .edit().clear().commit();

        Database.clearDatabase(myActivityRule.getActivity().getApplicationContext());
    }

    @Test
    public void testSearchArticle_checkTitleShown(){

        //open the article
        Utils.searchAndOpenArticleWith(articleName, myActivityRule.getActivity());

        //check the title is displayed in the title view
        onView(allOf(withId(R.id.view_article_header_text), withText(containsString(articleName))))
                .check(matches(isDisplayed()));

//        onWebView().forceJavascriptEnabled();
//
//        onWebView()
//                .check(webContent(containingTextInBody(articleName)));

//        onWebView()
//                .check(webMatches(getCurrentUrl(), is(articleName)));


//        onView(withId(R.id.floating_toc_button)).perform(click());

//        onWebView()
//                .check(webContent(hasElementWithId("content_block_19")));
    }

    
}
