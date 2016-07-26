package org.wikipedia;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import static junit.framework.Assert.assertTrue;

public class AppiumTests {

    AndroidDriver driver;

    @Before
    public void testCaseSetup()throws  Exception
    {
        //service.start();
        //reader.readFile();
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
        cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Nexus 5x 1");
        cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "4000");
        cap.setCapability("appPackage", "org.wikipedia.alpha");
        cap.setCapability(MobileCapabilityType.APP, "C:\\Users\\Tomi\\Projects\\wikipedia_3\\apps-android-wikipedia\\app\\build\\outputs\\apk\\app-alpha-debug.apk");
        driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), cap);

    }

    @Test
    public void testcase1() throws Exception {
        driver.findElementById("Example").click();
        assertTrue(driver.findElementById("Example").isDisplayed());
    }

    @After
    public void testCaseTearDown()
    {
        driver.quit();
    }

}
