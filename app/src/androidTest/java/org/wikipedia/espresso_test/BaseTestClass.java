package org.wikipedia.espresso_test;

import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.wikipedia.MainActivity;
import org.wikipedia.TestingHelpers.SearchIdlingResource;
import org.wikipedia.espresso_test.Utilities.TestDataSource;

public class BaseTestClass {

    protected Activity startActivity;

    protected String articleName1 = TestDataSource.articleName1;
    protected String articleName2 = TestDataSource.articleName2;
    protected String articleName3 = TestDataSource.articleName3;
    protected String articleToString1 = "Final_Fantasy_XII";
    protected String articleToString2 = "Google";
    protected String articleToString3 = "Scotland";
    protected String articleName3_finnish = TestDataSource.articleName3_finnish;
    protected String articleToString3_finnish = "Skotlanti";

    protected String finnishLanguage = TestDataSource.finnishLanguage;

    @Rule
    public ActivityTestRule<MainActivity> myActivityRule =
            new ActivityTestRule<MainActivity>(MainActivity.class) {
            };

    @Before
    public final void setUpBaseTestClass(){
        Espresso.registerIdlingResources(SearchIdlingResource.getIdlingResource());
//        startActivity = myActivityRule.getActivity();
//        recentSearchesText = startActivity.getString(R.string.search_recent_header);
//        mainFragmentSearchHint = startActivity.getString(R.string.search_hint);
    }

    @After
    public final void tearDownBaseTestClass(){
        Espresso.unregisterIdlingResources(SearchIdlingResource.getIdlingResource());

//        PreferenceManager.
//                getDefaultSharedPreferences(
//                        myActivityRule.getActivity().getApplicationContext())
//                .edit().clear().commit();
//
//        Database.clearDatabase(myActivityRule.getActivity().getApplicationContext());
    }
}
