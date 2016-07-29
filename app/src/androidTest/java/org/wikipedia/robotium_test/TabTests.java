package org.wikipedia.robotium_test;

import android.view.View;
import android.widget.ImageButton;

import com.robotium.solo.By;

import org.wikipedia.R;
import org.wikipedia.robotium_test.Utilities.TestDataSource;
import org.wikipedia.robotium_test.Utilities.Utils;

import java.util.List;

public class TabTests extends BaseTestClass{

    private String newTabContentDesc;
    private String openInNewTabText;
    private String newTabDefaultText = TestDataSource.newTabDefaultText;

    public void setUp() throws Exception{
        super.setUp();
        newTabContentDesc = getActivity().getString(R.string.menu_new_tab);
        openInNewTabText = getActivity().getString(R.string.menu_long_press_open_in_new_tab);
    }

    public void tearDown()throws Exception{

        super.tearDown();
    }

    //dont work. Stuff's broken. Page goes blank.
    public void testOpenMultipleTabs(){

        //open one article
        Utils.openSearchFromStartScreen(solo);
        Utils.searchAndOpenArticleWithName(solo, articleName1);

        //open a new tab
        solo.clickOnView(solo.getView(R.id.menu_page_show_tabs));
        solo.sleep(1500);
        //only have content description for the view, gotta do this
        List<View> views = solo.getCurrentViews(View.class);
        for(View view : views){
            if(view.getContentDescription() != null &&
                    view.getContentDescription().toString().equals(newTabContentDesc)){
                solo.clickOnView(view);
            }
        }
        solo.clickOnText(newTabDefaultText);


        //navigate to another article
//        Utils.openSearchFromArticle(solo);
//        Utils.searchAndOpenArticleWithName(solo, articleName2);
//        Utils.assertArticleTitleContains(solo, articleName2);

        //assert you can change between them
        solo.clickOnView(solo.getView(R.id.menu_page_show_tabs));
        solo.clickOnText(articleName1);
        solo.sleep(3000);
        Utils.assertArticleTitleContains(solo, articleName1);

    }

    public void testOpenArticleInNewTab(){
        fail("clicking on links dun work.");
        solo.clickOnWebElement(By.xpath("//*[@id=\"content_block_0\"]/span[2]/p/a[1]"));
        ////*[@id="content_block_0"]/span[2]/p/a[1]
    }
}
