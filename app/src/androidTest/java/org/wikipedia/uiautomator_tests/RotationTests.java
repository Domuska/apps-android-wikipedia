package org.wikipedia.uiautomator_tests;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;

import org.junit.Before;
import org.junit.Test;
import org.wikipedia.R;
import org.wikipedia.uiautomator_tests.Utils.TestDataSource;
import org.wikipedia.uiautomator_tests.Utils.Utils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

public class RotationTests extends BaseTestClass{

    private String firstSubHeading = TestDataSource.article1_firstSubHeading;
    private String openInNewTabText;
    private String link1 = TestDataSource.fullLinkText1;
    private String link1ArticleName = TestDataSource.fullLinkTextCapitalized;

    @Before
    public void setUp(){
        openInNewTabText =
                InstrumentationRegistry.getTargetContext().getString(R.string.menu_long_press_open_in_new_tab);
    }

    @Test
    public void testOpenToC_rotatePhone() throws Exception{

        Utils.openSearchFromStartScreen(device);
        Utils.searchAndOpenArticleWithName(device, articleName1);
        Utils.openToc(device);

        //assert list and first element are visible
        assertThat(device.findObject(By.res("org.wikipedia.alpha:id/page_toc_list")),
                notNullValue());
        assertThat(device.findObject(By.text(firstSubHeading)),
                notNullValue());

        //rotate screen
        device.setOrientationLeft();

        //assert list and first element are still visible
        assertThat(device.findObject(By.res("org.wikipedia.alpha:id/page_toc_list")),
                notNullValue());
        assertThat(device.findObject(By.text(firstSubHeading)),
                notNullValue());

    }
}
