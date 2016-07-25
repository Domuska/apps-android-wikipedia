package org.wikipedia.espresso_test;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.web.webdriver.Locator;
import android.support.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.wikipedia.MainActivity;
import org.wikipedia.R;
import org.wikipedia.TestingHelpers.SearchIdlingResource;
import org.wikipedia.espresso_test.Utilities.TestDataSource;
import org.wikipedia.espresso_test.Utilities.Utils;
import org.wikipedia.page.PageTitle;
import org.wikipedia.page.Section;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.webClick;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

public class articleTests extends BaseTestClass{

    private String fullLinkText = TestDataSource.fullLinkText;
    private String partialLinkText = TestDataSource.partialLinkText;
    private String newArticleText = TestDataSource.newArticleTitle;
    private String changeLanguageText;

    @Before
    public void setUp(){
        startActivity = myActivityRule.getActivity();
        changeLanguageText = startActivity.getString(R.string.menu_page_other_languages);
    }

    @Test
    public void testTableOfContents_checkSubTitles(){

        String subHeading1 = "Gameplay";
        String subHeading2 = "Plot";
        String subHeading3 = "Development";

        Utils.enterSearchScreenFromStartingFragment();
        Utils.enterSearchTermAndOpenArticle(articleName1, articleToString1, startActivity);
        Utils.openToC();

        //check that certain 3 topmost subheadings in table of contents show up
        onData(withToCLine(subHeading1))
                .inAdapterView(withId(R.id.page_toc_list))
                .check(matches(isDisplayed()));

        onData(withToCLine(subHeading2))
                .inAdapterView(withId(R.id.page_toc_list))
                .check(matches(isDisplayed()));

        onData(withToCLine(subHeading3))
                .inAdapterView(withId(R.id.page_toc_list))
                .check(matches(isDisplayed()));


        //make sure those same three subtitles are visible in the webview
        //this might not be the best way to do it but it will make sure they exist in the webView
        Utils.closeToC();
        onWebView().withElement(findElement(Locator.ID, subHeading1));
        onWebView().withElement(findElement(Locator.ID, subHeading2));
        onWebView().withElement(findElement(Locator.ID, subHeading3));

//                .withElement(findElement(Locator.CLASS_NAME, "section_heading"))
//                .check(webMatches(getText(), containsString(subHeading1)));
    }


    @Test
    public void testClickLink_fullText_assertPreviewShown(){

        //open article
        Utils.enterSearchScreenFromStartingFragment();
        Utils.enterSearchTermAndOpenArticle(articleName1, articleToString1, startActivity);

        //click on link
        onWebView()
                .withElement(findElement(Locator.LINK_TEXT, fullLinkText))
                .perform(webClick());

        //assert a popup showing preview of new article shows
        assertArticlePreviewVisible();

    }

    @Test
    public void testClickLink_partialText_assertPreviewShown(){
        //open article
        Utils.enterSearchScreenFromStartingFragment();
        Utils.enterSearchTermAndOpenArticle(articleName1, articleToString1, startActivity);

        //click on partial link
        onWebView()
                .withElement(findElement(Locator.PARTIAL_LINK_TEXT, partialLinkText))
                .perform(webClick());

        //assert a popup showing preview of new article shows
        assertArticlePreviewVisible();
    }

    @Test
    public void testChangeLanguage(){
        //open article
        
        Utils.enterSearchScreenFromStartingFragment();
        Utils.enterSearchTermAndOpenArticle(articleName3, articleToString3, startActivity);

        //open overflow menu in toolbar
        openActionBarOverflowOrOptionsMenu(myActivityRule.getActivity().getApplicationContext());
        onView(withText(changeLanguageText)).perform(click());

        //change language to finnish
//        onView(withText(finnishLanguage)).perform(click());
        onData(withLanguageName(articleName3_finnish))
                .inAdapterView(withId(R.id.langlinks_list))
                .perform(click());

        //assert article changed to finnish language one
        onView(allOf(withId(R.id.view_article_header_text),
                withText(containsString(articleToString3_finnish))))
                .check(matches(isDisplayed()));
//                .check(matches(withText(containsString(articleName3_finnish))));
    }

    private void assertArticlePreviewVisible() {
        onView(allOf(
                withText(containsString(newArticleText)),
                withId(R.id.link_preview_title)))
                .check(matches(isDisplayed()));
    }


    private static Matcher<Object> withToCLine(final String text){
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

    //String languageCode = item.getSite().languageCode()
    private static Matcher<Object> withLanguageName(final String text){
        return new BoundedMatcher<Object, PageTitle>(PageTitle.class) {
            @Override
            public void describeTo(org.hamcrest.Description description) {
                description.appendText("has text of " + text);
            }
            @Override
            protected boolean matchesSafely(PageTitle item) {
                return item.getText().equals(text);
            }
        };
    }
}
