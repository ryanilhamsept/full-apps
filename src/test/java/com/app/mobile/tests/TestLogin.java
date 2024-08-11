package com.app.mobile.tests;

import com.app.listeners.ExtentTestManager;
import com.app.mobile.enums.UserRole;
import com.app.mobile.pageobjects.LoginPage;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class TestLogin extends BaseAppiumTest {

    @Test
    public void testLogin(Method method) {
        ExtentTestManager.startTest(method.getName(), "Description: login with standard user");
        LoginPage loginPage = new LoginPage(action);
        loginPage.login(UserRole.STANDARD_USER);
    }
}
