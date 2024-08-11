package com.app.mobile.pageobjects;

import com.app.Action;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

/**
 * author : @regiewby
 * BaseMobilePage is an abstract base class for all the Mobile page objects.
 * It holds the instance of the driver and the task
 */
public class BaseMobilePage extends BasePage {
    protected Action action;
    protected AppiumFieldDecorator appiumFieldDecorator;

    /**
     * Parameterised constructor for BaseMobilePage class.
     * Initiates the AppiumDriver and Task objects
     *
     * @param action for initializing Appium Driver
     */
    public BaseMobilePage(Action action) {
        this.action = action;
        this.appiumFieldDecorator = action.getAppiumFieldDecorator();
    }
}
