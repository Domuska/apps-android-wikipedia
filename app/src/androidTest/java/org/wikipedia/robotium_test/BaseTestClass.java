package org.wikipedia.robotium_test;

import android.support.v7.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.wikipedia.MainActivity;
import org.wikipedia.R;
import org.wikipedia.database.Database;
import org.wikipedia.page.PageFragment;
import org.wikipedia.robotium_test.Utilities.TestDataSource;

public class BaseTestClass extends ActivityInstrumentationTestCase2<MainActivity>{

    protected Solo solo;
    public static long TIMEOUT_FIFTEEN_SECONDS = 15000;

    protected String articleName1 = TestDataSource.articleName1;
    protected String articleName2 = TestDataSource.articleName2;
    protected String articleName3 = TestDataSource.articleName3;

    protected String finnishLanguage = TestDataSource.finnishLanguage;
    protected String articleName3_finnish = TestDataSource.articleName3_finnish;

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

        PreferenceManager.
                getDefaultSharedPreferences(
                        getActivity().getApplicationContext())
                .edit().clear().commit();

        PageFragment.clearTabs();

        //clear the database
        Database.clearDatabase(getActivity().getApplicationContext());

        solo.finishOpenedActivities();
        super.tearDown();
    }

}
