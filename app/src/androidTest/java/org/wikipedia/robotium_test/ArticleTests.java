package org.wikipedia.robotium_test;

import android.view.View;
import android.widget.ImageButton;

import com.robotium.solo.Condition;
import com.robotium.solo.Solo;

import org.wikipedia.R;
import org.wikipedia.robotium_test.Utilities.TestDataSource;
import org.wikipedia.robotium_test.Utilities.Utils;

//latuasprogressbari:
//org.wikipedia.alpha:id/main_progressbar

public class ArticleTests extends BaseTestClass{


    String subHeading1 = TestDataSource.article1_subheading1;
    String subHeading2 = TestDataSource.article1_subheading2;
    String subHeading3 = TestDataSource.article1_subheading3;
    String article1_references = TestDataSource.article1_referenceSubHeading;


    public void setUp() throws Exception {
        super.setUp();
        //
    }

    public void tearDown() throws Exception {
        //
        super.tearDown();
    }

    public void testTableOfContents_checkSubTitles(){

        Utils.openSearchFromStartScreen(solo);
        Utils.searchAndOpenArticleWithName(solo, articleName1);
        Utils.openToC(solo);

        //check that certain 3 topmost subheadings in table of contents show up, see Utils for matcher
        assertTrue("Heading " + subHeading1 + " not visible in ToC",
                Utils.isElementFoundInToC(solo, subHeading1));
        assertTrue("Heading " + subHeading2 + " not visible in ToC",
                Utils.isElementFoundInToC(solo, subHeading2));
        assertTrue("Heading " + subHeading3 + " not visible in ToC",
                Utils.isElementFoundInToC(solo, subHeading3));
        Utils.isElementFoundInToC(solo, "References");

        //make sure those same three subtitles are visible in the webview
        Utils.closeToC(solo);


        //execution time of this is veeeeeerryyyyy slow. Excruciatingly so. Is webview the fault?
        // Or the amount of text?
        assertTrue("Heading " + subHeading1 + " not visible in webview",
                solo.searchText(subHeading1));
        assertTrue("Heading " + subHeading2 + " not visible in webview",
                solo.searchText(subHeading2));
        assertTrue("Heading " + subHeading3 + " not visible in webview",
                solo.searchText(subHeading3));
    }

    public void testScrollingToC_clickSubHeading(){

        //open article and table of contents
        Utils.openSearchFromStartScreen(solo);
        Utils.searchAndOpenArticleWithName(solo, articleName1);
        Utils.openToC(solo);

        //click references subheading
        if(Utils.isElementFoundInToC(solo, article1_references))
            solo.clickOnText(article1_references);
        else
            fail("Text " + article1_references + " not found in ToC");

        //make sure references is visible
        assertTrue("Text " + article1_references + " not visible in webView",
                solo.searchText(article1_references, true));
    }
}
