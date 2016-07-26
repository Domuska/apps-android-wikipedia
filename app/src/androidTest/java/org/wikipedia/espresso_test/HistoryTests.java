package org.wikipedia.espresso_test;

import org.junit.Before;
import org.junit.Test;
import org.wikipedia.espresso_test.Utilities.Utils;

public class HistoryTests extends BaseTestClass{

    @Before
    public void setUp(){
        startActivity = myActivityRule.getActivity();
    }

    @Test
    public void testHistory(){

        //open couple of articles
        Utils.enterSearchScreenFromStartingFragment();
        Utils.searchAndOpenArticleWithName(articleName1, articleToString1, startActivity);



        //go to history to see that they are visible
    }
}
