package org.wikipedia.robotium_test;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.wikipedia.MainActivity;
import org.wikipedia.R;
import org.wikipedia.robotium_test.Utilities.TestDataSource;

public class BaseTestClass extends ActivityInstrumentationTestCase2<MainActivity>{

    protected Solo solo;
    public static long TIMEOUT_FIFTEEN_SECONDS = 15000;

    protected String articleName1 = TestDataSource.articleName1;

    public BaseTestClass(){
        super(MainActivity.class);
    }

    //since for some reason JUnit4 won't play with us, we have to make sure
    //these are called in the right order in classes that extend this one
    public void setUp() throws Exception{
        super.setUp();
        solo = new Solo(getInstrumentation());
        getActivity();
    }

    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
        super.tearDown();
    }

}
