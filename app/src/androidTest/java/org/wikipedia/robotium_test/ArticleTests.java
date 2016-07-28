package org.wikipedia.robotium_test;

import android.view.View;

import com.robotium.solo.Condition;

import org.wikipedia.R;
import org.wikipedia.robotium_test.Utilities.TestDataSource;
import org.wikipedia.robotium_test.Utilities.Utils;

//latuasprogressbari:
//org.wikipedia.alpha:id/main_progressbar

public class ArticleTests extends BaseTestClass{


    String subHeading1 = TestDataSource.article1_subheading1;
    String subHeading2 = TestDataSource.article1_subheading2;
    String subHeading3 = TestDataSource.article1_subheading3;


    public void setUp() throws Exception {
        super.setUp();
        //
    }

    public void tearDown() throws Exception {
        //
        super.tearDown();
    }

    public void testTableOfContents_checkSubTitles(){

        fail("not finished yet");
        Utils.openSearchFromStartScreen(solo);
        Utils.searchAndOpenArticleWithName(solo, articleName1);
        Utils.assertArticleTitleContains(solo, articleName1);
        //wait until progress bar disappeared
        final View progressBar = solo.getView(R.id.main_progressbar);
        solo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                while(progressBar.getVisibility() != View.GONE){

                }
                return true;
            }
        }, TIMEOUT_FIFTEEN_SECONDS_INT);
        Utils.openToC(solo);

        //check that certain 3 topmost subheadings in table of contents show up, see Utils for matcher
        assertTrue("Heading " + subHeading1 + " not visible",
                solo.searchText(subHeading1));
        assertTrue("Heading " + subHeading2 + " not visible",
                solo.searchText(subHeading2));
        assertTrue("Heading " + subHeading3 + " not visible",
                solo.searchText(subHeading3));

        //make sure those same three subtitles are visible in the webview
        Utils.closeToC(solo);
    }

}
