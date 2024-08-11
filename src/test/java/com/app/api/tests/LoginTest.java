package com.app.api.tests;

import com.app.actions.ValidatorOperation;
import com.app.baseAPI.Auth;
import com.app.listeners.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;
import java.lang.reflect.Method;


public class LoginTest {

    @Test
    public void validLoginTest(Method method) {

        ExtentTestManager.startTest(method.getName(), "Description: Valid Login Scenario with username and password.");
        Auth response = new Auth();
        response.getLoginToken("admin", "password123");

        try {
            response.assertIt(200);
            ExtentTestManager.getTest().log(LogStatus.INFO, "Asserting response code");

            response.assertIt("token", null, ValidatorOperation.NOT_EMPTY);
            ExtentTestManager.getTest().log(LogStatus.INFO, "Asserting response value not empty case");

            response.assertIt("token", null, ValidatorOperation.NOT_NULL);
            ExtentTestManager.getTest().log(LogStatus.INFO, "Asserting response value not null case");
        } catch (AssertionError e) {
            ExtentTestManager.getTest().log(LogStatus.FAIL, "Assertion Failure: " + e.getMessage());
        }
    }

    @Test
    public void invalidLoginTest(Method method) {
        ExtentTestManager.startTest(method.getName(), "Description: InValid Login Scenario with username and password.");
        Auth response = new Auth();
        response.getLoginToken("dummy", "dummypassword123");

        try {
            response.assertIt(200);
            ExtentTestManager.getTest().log(LogStatus.INFO, "Asserting response code");

            response.assertIt("reason", "Bad credentials", ValidatorOperation.EQUALS);
            ExtentTestManager.getTest().log(LogStatus.INFO, "Asserting response value == Bad credentials");

        } catch (AssertionError e) {
            ExtentTestManager.getTest().log(LogStatus.FAIL, "Assertion Failure: " + e.getMessage());
        }
    }
}
