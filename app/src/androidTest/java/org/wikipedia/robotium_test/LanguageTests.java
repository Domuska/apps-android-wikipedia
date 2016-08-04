package org.wikipedia.robotium_test;

import android.widget.EditText;
import android.widget.TextView;

import org.wikipedia.R;
import org.wikipedia.WikipediaApp;
import org.wikipedia.robotium_test.Utilities.Utils;

public class LanguageTests extends BaseTestClass{

    private String appLanguageCode;
    private String changeLanguage;

    public void setUp() throws Exception{
        super.setUp();
        appLanguageCode = WikipediaApp.getInstance().getAppOrSystemLanguageCode();
        changeLanguage = getActivity().getString(R.string.menu_page_other_languages);
    }

    public void tearDown() throws Exception{
        //set app's language back to original
        WikipediaApp.getInstance().setAppLanguageCode(appLanguageCode);

        super.tearDown();
    }

    //todo not working - after an article is opened another time the app will just show a white screen
    public void testSearchArticle_changeLanguageInSearch(){
        Utils.openSearchFromStartScreen(solo);
        Utils.searchAndOpenArticleWithName(solo, articleName3);

        //check title is shown in default language
        Utils.assertArticleTitleContains(solo, articleName3);

        //change language in search
        Utils.openSearchFromArticle(solo);
        solo.clickOnView(solo.getView(R.id.search_lang_button));
        solo.typeText((EditText)solo.getView(R.id.preference_languages_filter), finnishLanguage);
        solo.clickOnView(solo.getView(R.id.localized_language_name));

        //open article again, try to make sure we click on the textview under the list
        solo.clickOnView(solo.getText(articleName3_finnish));

//        List<View> views = solo.getViews(solo.getView(R.id.search_results_list));
//        for(View view : views){
//            if(view instanceof TextView) {
//                if (((TextView) view).getText().toString().equals(articleName3_finnish) &&
//                        view.isShown()) {
//                    solo.clickOnView(view);
//                }
//            }
//        }

        //check language changed
//        Utils.assertArticleTitleContains(solo, articleName3_finnish);

        //maybe here we should wait for an activity transition (though it's not really one) or something,
        //if we wait for this text it could pick the text up from the previous search page or somesuch
        if(solo.waitForText(articleName3_finnish, 2, 1500)) {
            String titleText = ((TextView) solo.getView(R.id.view_article_header_text))
                    .getText().toString();
            assertTrue("Title does not contain text: " + articleName3_finnish + ", contains instead " + titleText,
                    titleText.contains(articleName3_finnish));
        }
        else
            fail("no title found");

    }

    public void testChangeLanguageInArticle(){
        //open article
        Utils.openSearchFromStartScreen(solo);
        Utils.searchAndOpenArticleWithName(solo, articleName3);

        //open overflow menu in toolbar
        Utils.openOverflowMenu(solo);
        //change language to finnish
        solo.clickOnText(changeLanguage);
        solo.clickOnText(articleName3_finnish);
        solo.sleep(Utils.PAGE_LOAD_WAIT);

        //assert article changed to finnish
        Utils.assertArticleTitleContains(solo, articleName3_finnish);
    }

}
