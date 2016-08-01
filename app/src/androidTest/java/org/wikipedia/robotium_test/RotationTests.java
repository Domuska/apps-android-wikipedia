package org.wikipedia.robotium_test;

import android.view.View;
import android.widget.TextView;

import com.robotium.solo.Solo;

import org.wikipedia.R;
import org.wikipedia.robotium_test.Utilities.TestDataSource;
import org.wikipedia.robotium_test.Utilities.Utils;

import java.util.List;

public class RotationTests extends BaseTestClass{

    private String firstSubHeading = TestDataSource.article1_firstSubHeading;
    private String openInNewTabText;

    public void setUp() throws Exception{
        super.setUp();
        openInNewTabText = getActivity().getString(R.string.menu_long_press_open_in_new_tab);
    }

    public void tearDown() throws Exception{
        super.tearDown();
    }

    public void testOpenToC_rotatePhone(){

        Utils.openSearchFromStartScreen(solo);
        Utils.searchAndOpenArticleWithName(solo, articleName1);
        Utils.openToC(solo);

        //assert list is visible
        assertTrue("Table of Contents is not visible",
                solo.getView(R.id.page_toc_list).isShown());
        assertFirstSubheadingInList();

        //rotate screen
        solo.setActivityOrientation(Solo.LANDSCAPE);

        //assert list is still visible
        assertTrue("Table of Contents is not visible",
                solo.getView(R.id.page_toc_list).isShown());
        assertFirstSubheadingInList();
    }

    public void testOpenTab_rotatePhone(){
        fail("not implemented, clicking links not working");
    }

    public void assertFirstSubheadingInList() {

        boolean subHeadingFound = false;
        //get all views in ToC, check if text is visible
        List<View> views = solo.getViews(solo.getView(R.id.page_toc_list));
        for(View view : views){
           if(view instanceof TextView) {
                if (((TextView) view).getText().toString().equals(firstSubHeading) &&
                        view.isShown()) {
                    subHeadingFound = true;
                }
            }
        }
        assertTrue("Subheading \"" + firstSubHeading + "\" not found in table of contents",
                subHeadingFound);
    }
}
