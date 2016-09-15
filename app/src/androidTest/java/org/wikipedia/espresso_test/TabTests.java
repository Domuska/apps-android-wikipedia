package org.wikipedia.espresso_test;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.web.webdriver.Locator;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wikipedia.R;
import org.wikipedia.TestingHelpers.TabAnimationIdlingResource;
import org.wikipedia.espresso_test.Utilities.TestDataSource;
import org.wikipedia.espresso_test.Utilities.Utils;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.webClick;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TabTests extends BaseTestClass{

    private String newTabContentDesc;
    private String newTabDefaultText = TestDataSource.newTabDefaultText;

    @Before
    public void setUp(){
        startActivity = myActivityRule.getActivity();
        newTabContentDesc = startActivity.getString(R.string.menu_new_tab);
        Espresso.registerIdlingResources(TabAnimationIdlingResource.getIdlingResource());
    }

    @After
    public void tearDown(){
        Espresso.unregisterIdlingResources(TabAnimationIdlingResource.getIdlingResource());
    }

    @Test
    public void testOpenMultipleTabs(){

        //open one article
        Utils.openSearchFromStartScreen();
        Utils.searchAndOpenArticleWithName(articleName1, articleToString1, startActivity);

        //open a new tab
        onView(withId(R.id.menu_page_show_tabs)).perform(click());
        onView(withContentDescription(newTabContentDesc)).perform(click());
        onView(allOf(withText(newTabDefaultText), withId(R.id.tab_item_title))).perform(click());

        //navigate to another article
        Utils.openSearchFromArticle();
        Utils.searchAndOpenArticleWithName(articleName2, articleToString2, startActivity);

        //assert you can change between them
        onView(withId(R.id.menu_page_show_tabs)).perform(click());
        onView(withText(articleName1)).perform(click());

        Utils.assertArticleTitleContains(articleName1);
//        onView(allOf(withId(R.id.view_article_header_text),
//                withText(containsString(articleName1))))
//                .check(matches(isDisplayed()));

    }
}
