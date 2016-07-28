package org.wikipedia.robotium_test;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.wikipedia.MainActivity;

public class BaseTestClass extends ActivityInstrumentationTestCase2<MainActivity>{

    protected Solo solo;

    public BaseTestClass(){
        super(MainActivity.class);
    }

    final public void setUpBaseTestClass() throws Exception{
        super.setUp();
        solo = new Solo(getInstrumentation());
        getActivity();
    }

    final public void tearDownBaseTestClass() throws Exception{
        solo.finishOpenedActivities();
        super.tearDown();
    }


    public void testStuff(){

    }
}
