package com.app.mobile.pageobjects;

import com.app.Action;
import org.openqa.selenium.support.PageFactory;

public class BaseSauceDemoPage extends BaseMobilePage {

    /**
     * Author : @regiewby
     * Parameterised constructor for BaseSauceDemoPage class.
     * Initiates the AppiumDriver and Task objects
     *
     * @param action for initializing Appium Driver
     */
    public BaseSauceDemoPage(Action action) {
        super(action);
        PageFactory.initElements(appiumFieldDecorator, this);
    }
}
