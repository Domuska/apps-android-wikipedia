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

    private String newArticleName = TestDataSource.fullLinkText1;
    private String newArticleNameCapitalized = TestDataSource.fullLinkTextCapitalized;
    private String openInNewTabText;

    @Before
    public void setUp(){
        startActivity = myActivityRule.getActivity();
        openInNewTabText = startActivity.getString(R.string.menu_long_press_open_in_new_tab);
        Espresso.registerIdlingResources(TabAnimationIdlingResource.getIdlingResource());
    }

    @After
    public void tearDown(){
        Espresso.unregisterIdlingResources(TabAnimationIdlingResource.getIdlingResource());
    }

    @Test
    public void testOpenArticleInNewTab(){

        //open article
        Utils.openSearchFromStartScreen();
        Utils.searchAndOpenArticleWithName(articleName1, articleToString1, startActivity);

        //open article preview
        onWebView()
                .withElement(findElement(Locator.LINK_TEXT, newArticleName))
                .perform(webClick());

        //open article in new tab, have to use inRoot to handle a popup
        onView(withId(R.id.link_preview_overflow_button)).perform(click());
        //TabAnimationIdlingResource is used here
        onView(withText(openInNewTabText))
                .inRoot(isPlatformPopup())
                .perform(click());

        //open the article from tabs
        onView(withId(R.id.menu_page_show_tabs)).perform(click());
        onView(withText(containsString(newArticleNameCapitalized))).perform(click());

        //assert the title is correct
        Utils.assertArticleTitleContains(newArticleNameCapitalized);
    }
}
