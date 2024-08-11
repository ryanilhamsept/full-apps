package com.app.mobile.tests;

import com.app.Action;
import com.app.factories.DriverFactory;
import com.app.listeners.ExtentTestManager;
import com.app.utilities.Helper;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;

public abstract class BaseAppiumTest {

    DriverFactory driverFactory;
    Action action;

    public BaseAppiumTest() {
        driverFactory = new DriverFactory();
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(ITestContext context, Method method) {
        Helper getHelp = new Helper();
        getHelp.set_path("src/main/resources/Constants.properties");
        String platformName = getHelp.loadProperties("Platform");
        initializeAppiumDriver(platformName, method.getName());
        action = new Action(driverFactory.getDriver(method.getName()));
        ExtentTestManager.startTest(method.getName());
    }

    protected void initializeAppiumDriver(String platformName, String methodName) {
        driverFactory.initializeAppiumDriver(platformName, methodName);
    }
}
