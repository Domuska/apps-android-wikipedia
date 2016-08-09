package org.wikipedia.robotium_test;

import android.support.test.InstrumentationRegistry;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.robotium.solo.By;

import org.wikipedia.R;
import org.wikipedia.robotium_test.Utilities.TestDataSource;
import org.wikipedia.robotium_test.Utilities.Utils;

import java.util.List;

//latuasprogressbari:
//org.wikipedia.alpha:id/main_progressbar

public class ArticleTests extends BaseTestClass{

    private String subHeading1 = TestDataSource.article1_subheading1;
    private String subHeading2 = TestDataSource.article1_subheading2;
    private String subHeading3 = TestDataSource.article1_subheading3;
    private String article1_references = TestDataSource.article1_referenceSubHeading;
    private String article1_title = "Final Fantasy XII\n" + "Video game";

    private String fullLinkText = TestDataSource.fullLinkText1;
    private String partialLinkText = TestDataSource.partialLinkText;
    private String newArticleTitle = TestDataSource.newArticleTitle;



    private String referencesJSId = TestDataSource.referencesElementId;
    private String referencesJSClassName = TestDataSource.referencesJSClassName;
    private String article1_firstReference = TestDataSource.article1_firstReference;


    public void setUp() throws Exception {
        super.setUp();

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
                solo.searchText(subHeading1));
        assertTrue("Heading " + subHeading2 + " not visible in ToC",
                solo.searchText(subHeading2));
        assertTrue("Heading " + subHeading3 + " not visible in ToC",
                solo.searchText(subHeading3));

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
        solo.clickOnText(article1_references);

        //make sure we the title bar is no longer visible (so we at least moved somewhere)
        assertFalse("title view should no longer be visible",
                solo.searchText(article1_title, true));

    }


    //this test does not work - the link is not clicked properly by Robotium. It only touches the link.
    public void testClickLink_fullText_assertPreviewShown(){

        //open article
        Utils.openSearchFromStartScreen(solo);
        Utils.searchAndOpenArticleWithName(solo, articleName1);

        //click on link
        solo.clickLongOnText(fullLinkText);
//        solo.clickOnWebElement(By.textContent(fullLinkText));
        solo.sleep(300);

        //assert a popup with article preview is shown
//        solo.waitForView(R.id.link_preview_title);

        String linkTitleText = ((TextView)solo.getView(R.id.link_preview_title))
                .getText().toString();

        assertTrue("Link preview title(" + linkTitleText + ") does not match expected text: " + newArticleTitle,
                linkTitleText.equals(newArticleTitle));
    }


    //not implemented yet, since clicking on an URL does not work
    public void testClickLink_partialText_assertPreviewShown(){
        fail("not implemented");

    }



    public void testOpeningAndClosingReferences(){
        //navigate to the article
        Utils.openSearchFromStartScreen(solo);
        Utils.searchAndOpenArticleWithName(solo, articleName1);
        Utils.openToC(solo);
//        Utils.isElementFoundInToC(solo, article1_references);
        solo.clickOnText(article1_references);

        //expand the references
        solo.clickOnWebElement(By.className(referencesJSClassName));

        //assert first reference visible
        assertTrue("Reference " + article1_firstReference + " be visible" ,
                solo.searchText(article1_firstReference, true));

        //close references
        solo.clickOnWebElement(By.className(referencesJSClassName));

        //assert first reference no longer visible
        assertFalse("Reference " + article1_firstReference + " should no longer be visible" ,
                solo.searchText(article1_firstReference, true));
    }




    //with help of http://executeautomation.com/blog/hybridapplication-robotium/
    private void enableWebViewJS() {
        final WebView webView = (WebView)solo.getView(R.id.page_web_view);

        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setDomStorageEnabled(true);
            }
        });

    }
}
