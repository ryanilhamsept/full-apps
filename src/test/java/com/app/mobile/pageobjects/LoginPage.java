package com.app.mobile.pageobjects;

import com.app.Action;
import com.app.mobile.enums.UserRole;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class LoginPage extends BaseSauceDemoPage {
    public LoginPage(Action action) {
        super(action);
    }

    @AndroidFindBy(accessibility = "")
    @iOSXCUITFindBy(accessibility = "test-Username")
    private WebElement txtUsername;

    @AndroidFindBy(accessibility = "")
    @iOSXCUITFindBy(accessibility = "test-Password")
    private WebElement txtPassword;

    @AndroidFindBy(accessibility = "")
    @iOSXCUITFindBy(accessibility = "test-LOGIN")
    private WebElement btnLogin;

    public HomePage login(UserRole userRole) {
        String username = "";
        String password = switch (userRole) {
            case STANDARD_USER -> {
                username = UserRole.STANDARD_USER.getCredential().get("username");
                yield UserRole.STANDARD_USER.getCredential().get("password");
            }
            case LOCKED_OUT_USER -> {
                username = UserRole.LOCKED_OUT_USER.getCredential().get("username");
                yield UserRole.LOCKED_OUT_USER.getCredential().get("password");
            }
            case PROBLEM_USER -> {
                username = UserRole.PROBLEM_USER.getCredential().get("username");
                yield UserRole.PROBLEM_USER.getCredential().get("password");
            }
        };

        action.enterText(txtUsername, username)
                .enterText(txtPassword, password)
                .click(btnLogin);
        return new HomePage(action);
    }
}
