package org.wikipedia.uiautomator_tests;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;
import android.support.v7.preference.PreferenceManager;

import org.junit.After;
import org.junit.Before;
import org.wikipedia.database.Database;
import org.wikipedia.page.PageFragment;
import org.wikipedia.uiautomator_tests.Utils.TestDataSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

public class BaseTestClass {

    protected UiDevice device;
    public static final int GENERAL_TIMEOUT = 5000;
    private final String WIKIPEDIA_ALPHA_PACKAGE = "org.wikipedia.alpha";

    protected String articleName1 = TestDataSource.articleName1;
    protected String articleName2 = TestDataSource.articleName2;
    protected String articleName3 = TestDataSource.articleName3;

    @Before
    public final void setUpBaseTestClass(){
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        //launch app
        String launcherPackage = device.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());

        //wait for launcher
        device.wait(Until.hasObject(
                By.pkg(launcherPackage).depth(0)), GENERAL_TIMEOUT);

        //launch it
        Context context = InstrumentationRegistry.getContext();
        Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(WIKIPEDIA_ALPHA_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        device.wait(Until.hasObject(
                By.pkg(WIKIPEDIA_ALPHA_PACKAGE).depth(0)), GENERAL_TIMEOUT);
    }

    @After
    public final void terDownBaseTestClass(){
        PreferenceManager.
                getDefaultSharedPreferences(
                        InstrumentationRegistry.getTargetContext().getApplicationContext())
                .edit().clear().commit();

        PageFragment.clearTabs();

        //clear the database
        Database.clearDatabase(InstrumentationRegistry.getTargetContext().getApplicationContext());
    }
}
