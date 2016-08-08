package org.wikipedia.appium;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.wikipedia.appium.Utilities.TestDataSource;

import java.net.URL;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import static junit.framework.Assert.assertTrue;


// credit to http://qaautomated.blogspot.fi/2016/01/setting-up-appium-with-android-studio.html
// for skeleton of this file and instructions on how to setup Appium with Android Studio
public class BaseTestClass {

    AndroidDriver<MobileElement> driver;
    WebDriverWait stareAtPixies;

    public static int TIMEOUT_FIFTEEN_SECONDS = 15;

    protected String articleName1 = TestDataSource.articleName1;
    protected String articleName2 = TestDataSource.articleName2;
    protected String articleName3 = TestDataSource.articleName3;
    protected String articleName3_finnish = TestDataSource.articleName3_finnish;

    protected String finnishLanguage = TestDataSource.finnishLanguage;

    @Before
    final public void testCaseSetup() throws Exception {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
        cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Nexus 5x 1");
        cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "4000");
        cap.setCapability("appPackage", "org.wikipedia.alpha");
        cap.setCapability("fullReset", false);
        cap.setCapability(MobileCapabilityType.NO_RESET, false);
        cap.setCapability(MobileCapabilityType.APP, "C:\\Users\\Tomi\\Projects\\wikipedia_3\\apps-android-wikipedia\\app\\build\\outputs\\apk\\app-alpha-debug.apk");
//        cap.setCapability("chromedriverExecutable", "C:\\Users\\Tomi\\Downloads\\chromedriver_win32\\chromedriver.exe");

        driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), cap);

        stareAtPixies = new WebDriverWait(driver, TIMEOUT_FIFTEEN_SECONDS);
    }

    @After
    final public void testCaseTearDown() {
        driver.quit();
    }

}
