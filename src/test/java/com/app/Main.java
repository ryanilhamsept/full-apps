package com.app;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AutomationName;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class Main {

    public static void main(String[] args) {
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
            IOSDriver iosDriver = new IOSDriver(new URL("http://localhost:8100"), capabilities);
        } catch (MalformedURLException malformedURLException) {
            throw new RuntimeException("failed");
        }
    }
}
