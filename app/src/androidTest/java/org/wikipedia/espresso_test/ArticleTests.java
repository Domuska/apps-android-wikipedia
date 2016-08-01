package org.wikipedia.espresso_test;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.web.webdriver.Locator;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wikipedia.R;
import org.wikipedia.espresso_test.Utilities.TestDataSource;
import org.wikipedia.espresso_test.Utilities.Utils;
import org.wikipedia.page.PageTitle;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.getText;
import static android.support.test.espresso.web.webdriver.DriverAtoms.webClick;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsNot.not;
import static org.wikipedia.espresso_test.Utilities.Utils.withToCLine;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ArticleTests extends BaseTestClass{

    private String fullLinkText = TestDataSource.fullLinkText1;
    private String partialLinkText = TestDataSource.partialLinkText;
    private String newArticleText = TestDataSource.newArticleTitle;
    private String changeLanguageText;
    private String refencesJSId = TestDataSource.referencesElementId;
    private String referenceJSclassName = TestDataSource.referencesJSClassName;
    private String article1_referenceSubHeading = TestDataSource.article1_referenceSubHeading;

    String subHeading1 = TestDataSource.article1_subheading1;
    String subHeading2 = TestDataSource.article1_subheading2;
    String subHeading3 = TestDataSource.article1_subheading3;
    private String referenceTextClassName = "mw-reference-text";
    private String article1_firstReference = TestDataSource.article1_firstReference;

    @Before
    public void setUp(){
        startActivity = myActivityRule.getActivity();
        changeLanguageText = startActivity.getString(R.string.menu_page_other_languages);
    }

    @Test
    public void testTableOfContents_checkSubTitles(){

        Utils.openSearchFromStartScreen();
        Utils.searchAndOpenArticleWithName(articleName1, articleToString1, startActivity);
        Utils.openToC();

        //check that certain 3 topmost subheadings in table of contents show up, see Utils for matcher
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
    }

    @Test
    public void testScrollingToC_clickSubHeading(){
        //open article and table of contents
        Utils.openSearchFromStartScreen();
        Utils.searchAndOpenArticleWithName(articleName1, articleToString1, startActivity);
        Utils.openToC();

        //article1_referenceSubHeading
        onData(withToCLine(article1_referenceSubHeading))
                .inAdapterView(withId(R.id.page_toc_list))
                .perform(click());

        //make sure we the title bar is no longer visible (so we at least moved somewhere)
        onView(allOf(withId(R.id.view_article_header_text), withText(containsString(articleName1))))
                .check(matches(not(isDisplayed())));

    }


    @Test
    public void testClickLink_fullText_assertPreviewShown(){

        //open article
        Utils.openSearchFromStartScreen();
        Utils.searchAndOpenArticleWithName(articleName1, articleToString1, startActivity);

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
        Utils.openSearchFromStartScreen();
        Utils.searchAndOpenArticleWithName(articleName1, articleToString1, startActivity);

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
        Utils.openSearchFromStartScreen();
        Utils.searchAndOpenArticleWithName(articleName3, articleToString3, startActivity);

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
    }

    //jostain syystä tämä testi ei toimi, katso alemmas
    @Test
    public void testOpeningAndClosingReferences(){

        //navigate to the article
        Utils.openSearchFromStartScreen();
        Utils.searchAndOpenArticleWithName(articleName1, articleToString1, startActivity);
        Utils.openToC();
        onData(withToCLine(article1_referenceSubHeading))
                .inAdapterView(withId(R.id.page_toc_list))
                .perform(click());

        onWebView()
                //open references
                .withElement(findElement(Locator.CLASS_NAME, referenceJSclassName))
                .perform(webClick())
                //assert reference is visible
                .withElement(findElement(Locator.CLASS_NAME, referenceTextClassName))
                .check(webMatches(getText(), containsString(article1_firstReference)))
                //close references
                .withElement(findElement(Locator.CLASS_NAME, "app_table_collapse_close"))
                .perform(webClick())
                //assert reference is no longer visible
                .withElement(findElement(Locator.CLASS_NAME, referenceTextClassName))
                .check(webMatches(getText(), not(containsString(article1_firstReference))));
    }



    private void assertArticlePreviewVisible() {
        onView(allOf(
                withText(containsString(newArticleText)),
                withId(R.id.link_preview_title)))
                .check(matches(isDisplayed()));
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
