package org.wikipedia.espresso_test;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.web.webdriver.Locator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wikipedia.R;
import org.wikipedia.TestingHelpers.TabAnimationIdlingResource;
import org.wikipedia.espresso_test.Utilities.TestDataSource;
import org.wikipedia.espresso_test.Utilities.Utils;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.webClick;
import static junit.framework.Assert.fail;



import static org.wikipedia.espresso_test.Utilities.OrientationChangeAction.orientationLandscape;

public class RotationTests extends BaseTestClass{

    private String referencesText = TestDataSource.article1_referenceSubHeading;
    private String openInNewTabText;
    private String link1 = TestDataSource.fullLinkText1;

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
    public void testOpenToC_rotatePhone(){

        Utils.openSearchFromStartScreen();
        Utils.searchAndOpenArticleWithName(articleName1, articleToString1, startActivity);
        Utils.openToC();

        //assert list is visible
        onView(withId(R.id.page_toc_list))
                .check(matches(isDisplayed()));

        //rotate screen
        onView(isRoot()).perform(orientationLandscape());

        //assert list is still visible
        onView(withId(R.id.page_toc_list))
                .check(matches(isDisplayed()));

    }

    @Test
    public void testOpenTab_rotatePhone(){

        //open a few articles in tabs
        Utils.openSearchFromStartScreen();
        Utils.searchAndOpenArticleWithName(articleName1, articleToString1, startActivity);

        //open link in a new tab
        openInNewTabLink(link1);

        //open tabs
        onView(withId(R.id.menu_page_show_tabs)).perform(click());

        //assert the list is visible
        onView(withId(R.id.tabs_list)).check(matches(isDisplayed()));

        //rotate phone
        onView(isRoot()).perform(orientationLandscape());

        //assert the same views are visible
        onView(withId(R.id.tabs_list)).check(matches(isDisplayed()));

    }

    private void openInNewTabLink(String name) {
        onWebView()
                .withElement(findElement(Locator.LINK_TEXT, name))
                .perform(webClick());
        //push overflow button
        onView(withId(R.id.link_preview_overflow_button)).perform(click());
        //TabAnimationIdlingResource is used here
        onView(withText(openInNewTabText))
                .inRoot(isPlatformPopup())
                .perform(click());
    }
}
