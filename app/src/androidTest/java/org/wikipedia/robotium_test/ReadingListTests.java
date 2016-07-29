package org.wikipedia.robotium_test;

import android.widget.EditText;

import org.wikipedia.R;
import org.wikipedia.robotium_test.Utilities.TestDataSource;
import org.wikipedia.robotium_test.Utilities.Utils;

public class ReadingListTests extends BaseTestClass{

    private String readingListName = TestDataSource.readingListName;
    private String gotItText;
    private String readingListText;

    public void setUp() throws Exception{
        super.setUp();
        gotItText = getActivity().getString(R.string.reading_lists_onboarding_got_it);
        readingListText = getActivity().getString(R.string.nav_item_reading_lists);
    }

    public void tearDown() throws Exception{

        super.tearDown();
    }

    public void testAddArticleToReadingList(){

        //open an article
        Utils.openSearchFromStartScreen(solo);
        Utils.searchAndOpenArticleWithName(solo, articleName1);

        //add it to reading list
        solo.clickOnView(solo.getView(R.id.view_article_menu_bar_bookmark));
        solo.clickOnText(gotItText);
        EditText readingListTitle = (EditText)solo.getView(R.id.reading_list_title);
        solo.clearEditText(readingListTitle);
        solo.typeText(readingListTitle, readingListName);

        solo.clickOnView(solo.getView(android.R.id.button1));

        //open reading lists and assert article is visible in the list
        Utils.openDrawer(solo);
        solo.clickOnText(readingListText);
        solo.clickOnText(readingListName);
        solo.clickOnText(articleName1);
        solo.sleep(Utils.SLEEP_TIME_1500_MS);

        Utils.assertArticleTitleContains(solo, articleName1);
    }
}
