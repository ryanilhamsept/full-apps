package com.app.factories;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AutomationName;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class DriverFactory {
    protected HashMap<String, AppiumDriver> mobileDriverStore = new HashMap<>();

    /**
     * Author: @regiewby
     * used for initialize android driver
     *
     * @return AndroidDriver
     */
    private AndroidDriver initializeAndroidDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
        capabilities.setCapability("appium:platformVersion", "");
        capabilities.setCapability("appium:app", "");
        capabilities.setCapability("appium:noReset", false);
        capabilities.setCapability("appium:autoGrantPermission", true);
        try {
            return new AndroidDriver(new URL("http://0.0.0.0:4723"), capabilities);
        } catch (RuntimeException | MalformedURLException runtimeException) {
            throw new RuntimeException("failed to connect to appium");
        }
    }

    /**
     * Author: @regiewby
     * used for initialize iOS driver
     *
     * @return IOSDriver
     */
    private IOSDriver initializeIOSDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium:automationName", AutomationName.IOS_XCUI_TEST);
        capabilities.setCapability("platformName", Platform.IOS);
        capabilities.setCapability("appium:platformVersion", "16.2");
        capabilities.setCapability("appium:udid", "ED91FC95-0414-43CA-9EB6-2F8BBA0A1689");
        capabilities.setCapability("appium:app", "/Users/regie.wibhiyanto/git.server/personal/super-framework-master/apps/sauce-demo-app-2.7.1.app");
        capabilities.setCapability("appium:noReset", false);
        capabilities.setCapability("appium:autoAcceptAlerts", true);
        capabilities.setCapability("appium:forceSimulatorSoftwareKeyboardPresence", true);
        try {
            return new IOSDriver(new URL("http://0.0.0.0:4723"), capabilities);
        } catch (RuntimeException | MalformedURLException runtimeException) {
            throw new RuntimeException("failed to connect to appium");
        }
    }

    /**
     * Author: @regiewby
     * @param platformName platform name to be executed
     * @param methodName name of method
     */
    public void initializeAppiumDriver(String platformName, String methodName) {
        AppiumDriver driver;
        if (platformName.equalsIgnoreCase("iOS")) {
            driver = initializeIOSDriver();
        } else {
            driver = initializeAndroidDriver();
        }
        mobileDriverStore.put(methodName, driver);
    }

    /**
     * Author: @regiewby
     * @param methodName name of method
     * @return list of drivers
     */
    public WebDriver getDriver(String methodName) {
        return mobileDriverStore.get(methodName);
    }
}
