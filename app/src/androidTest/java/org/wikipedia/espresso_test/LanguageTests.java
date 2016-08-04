package org.wikipedia.espresso_test;

import android.support.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wikipedia.R;
import org.wikipedia.WikipediaApp;
import org.wikipedia.espresso_test.Utilities.Utils;
import org.wikipedia.page.PageTitle;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.hasToString;

public class LanguageTests extends BaseTestClass{

    private String appLanguageCode;
    private String changeLanguageText;

    @Before
    public void setUp(){
        startActivity = myActivityRule.getActivity();
        appLanguageCode = WikipediaApp.getInstance().getAppOrSystemLanguageCode();
        changeLanguageText = startActivity.getString(R.string.menu_page_other_languages);
    }

    @After
    public void tearDown(){
        //set app's language back to original
        WikipediaApp.getInstance().setAppLanguageCode(appLanguageCode);
    }


    @Test
    public void testChangeLanguageInSearch(){
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

    @Test
    public void testChangeLanguageInArticle(){
        //open article
        Utils.openSearchFromStartScreen();
        Utils.searchAndOpenArticleWithName(articleName3, articleToString3, startActivity);

        //open overflow menu in toolbar
        openActionBarOverflowOrOptionsMenu(myActivityRule.getActivity().getApplicationContext());
        onView(withText(changeLanguageText)).perform(click());

        //change language to finnish
        onData(withLanguageName(articleName3_finnish))
                .inAdapterView(withId(R.id.langlinks_list))
                .perform(click());

        //assert article changed to finnish language one
        Utils.assertArticleTitleContains(articleName3_finnish);
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
