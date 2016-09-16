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


import static org.hamcrest.Matchers.allOf;
import static org.wikipedia.espresso_test.Utilities.OrientationChangeAction.orientationLandscape;
import static org.wikipedia.espresso_test.Utilities.Utils.withToCLine;

public class RotationTests extends BaseTestClass{

    private String firstSubHeading = TestDataSource.article1_firstSubHeading;
    private String openInNewTabText;
    private String link1 = TestDataSource.fullLinkText1;
    private String link1ArticleName = TestDataSource.fullLinkTextCapitalized;

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
        onData(withToCLine(firstSubHeading))
                .inAdapterView(withId(R.id.page_toc_list))
                .check(matches(isDisplayed()));

        //rotate screen
        onView(isRoot()).perform(orientationLandscape());

        //assert list is still visible
        onView(withId(R.id.page_toc_list))
                .check(matches(isDisplayed()));
        onData(withToCLine(firstSubHeading))
                .inAdapterView(withId(R.id.page_toc_list))
                .check(matches(isDisplayed()));
    }
    
}
