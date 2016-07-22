package org.wikipedia.espresso_test;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wikipedia.MainActivity;
import org.wikipedia.R;
import org.wikipedia.TestingHelpers.SearchIdlingResource;
import org.wikipedia.search.SearchResult;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.TypeTextAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.espresso.web.webdriver.Locator;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.assertion.WebViewAssertions.webContent;
import static android.support.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static android.support.test.espresso.web.matcher.DomMatchers.hasElementWithId;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.webClick;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class ArticleSearchTests {

    @Rule
    public ActivityTestRule<MainActivity> myActivityRule =
            new ActivityTestRule<MainActivity>(MainActivity.class) {
//        @Override
//        protected void afterActivityLaunched() {
//            // Enable JS!
//            onWebView().forceJavascriptEnabled();
//        }
    };

    @Before
    public void setUp(){
        Espresso.registerIdlingResources(SearchIdlingResource.getIdlingResource());
    }

    @After
    public void tearDown(){
        Espresso.unregisterIdlingResources(SearchIdlingResource.getIdlingResource());
    }

    @Test
    public void testStuff(){

        final String stringToBeTyped = "Final Fantasy XII";
        String searchBarHint = myActivityRule.getActivity().getString(R.string.search_hint);
        onView(allOf(withText(searchBarHint), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE))).perform(click());

        //have to do a drity trick like this since otherwise IdlingResource will stop after each letter
        //to wait for response to network call
        final EditText searchField = (EditText) myActivityRule.getActivity().findViewById(R.id.search_src_text);
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                searchField.setText(stringToBeTyped);
            }
        });


//        Espresso.closeSoftKeyboard();
//        onView(withText(stringToBeTyped)).perform(click());

//        onData(hasEntry(equalTo("Data"), is("Final_Fantasy_XII")))
//                .inAdapterView(withId(R.id.search_results_list))
//                .perform(click());

        //workses, precious.
        onData(allOf(instanceOf(SearchResult.class), hasToString(is("Final_Fantasy_XII"))))
                .inAdapterView(withId(R.id.search_results_list))
                .perform(click());

        onWebView().forceJavascriptEnabled();

//        onView(withId(R.id.floating_toc_button)).perform(click());

        //kirjota johonkin siitä että tämä ei anna kovin kummosta errorilogia jos ID:llä ei löydy mitään
        onWebView()
                .check(webContent(hasElementWithId("content_block_19")));



//        onWebView()
//                .withElement(findElement(Locator.XPATH, "//*[@id=\"content_block_19\"]"))
//                .perform(webClick());

//        onWebView()
//                .withElement(findElement(Locator.ID, "content_block_19"))
//                .check(webMatches())



        //  //*[@id="content_block_19"]


    }
}
