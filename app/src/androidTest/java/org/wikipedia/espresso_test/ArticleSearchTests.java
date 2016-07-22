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

import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.web.webdriver.Locator;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.preference.PreferenceManager;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.getText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;


@RunWith(AndroidJUnit4.class)
public class ArticleSearchTests {

    private Activity startActivity;

    private String articleName1 = "Final Fantasy XII";
    private String articleToString1 = "Final_Fantasy_XII";
    private String articleName2 = "Google";
    private String articleToString2 = "Google";

    private String articleName3 = "Scotland";
    private String articleName3_finnish = "Skotlanti";
    private String articleToString3 = "Scotland";
    private String articleToString3_finnish = "Skotlanti";

    private String finnishLanguage = "Suomi";

    private String recentSearchesText;

    @Rule
    public ActivityTestRule<MainActivity> myActivityRule =
            new ActivityTestRule<MainActivity>(MainActivity.class) {
    };

    @Before
    public void setUp(){
        Espresso.registerIdlingResources(SearchIdlingResource.getIdlingResource());
        startActivity = myActivityRule.getActivity();
        recentSearchesText = startActivity.getString(R.string.search_recent_header);

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
        Utils.searchAndOpenArticleWith(articleName1, articleToString1, startActivity);

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

        //todo tee tämä testi loppuun kun olet katsonut lisää onDatan juttuja

//        Utils.searchAndOpenArticleWith(articleName1, articleToString1, startActivity);
//        Utils.searchAndOpenArticleWith(articleName2, articleToString2, startActivity);
//        Utils.searchAndOpenArticleWith(articleName3, articleToString3,  startActivity);

        onView(withId(R.id.main_search_bar_text)).perform(click());
        onView(withId(R.id.search_close_btn)).perform(click());
        onView(withText(recentSearchesText)).check(matches(isDisplayed()));

        onData(allOf(withClassName(endsWith("LinearLayout")), withChild(withText(articleName1))))
                .check(matches(isDisplayed()));

    }

    @Test
    public void testSearchArticle_changeLanguageInSearch(){
        Utils.searchAndOpenArticleWith(articleName3, articleToString3, myActivityRule.getActivity());

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

    @Test
    public void testTableOfContents_checkSubTitles(){

        String subHeading1 = "Gameplay";
        String subHeading2 = "Plot";
        String subHeading3 = "Development";

        Utils.searchAndOpenArticleWith(articleName1, articleToString1, startActivity);
        onView(withId(R.id.floating_toc_button)).perform(click());

        //get three topmost subtitles from drawer
//        onData(withChild(withId(R.id.page_toc_item_text)))
//                .inAdapterView(withId(R.id.page_toc_list))
//                .check(matches(isDisplayed()));
        onView(allOf(withId(R.id.page_toc_item_text), withText(subHeading1)))
                .check(matches(isDisplayed()));


        //make sure those three subtitles are visible in the webview
        //maybe get the first three elements with class = "section_heading" ?
        onWebView()
                .withElement(findElement(Locator.ID, subHeading1));
//                .withElement(findElement(Locator.CLASS_NAME, "section_heading"))
//                .check(webMatches(getText(), containsString(subHeading1)));


    }
    // //*[@id="Gameplay"]
}
