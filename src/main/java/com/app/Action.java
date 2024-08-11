package com.app;

import com.app.listeners.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.clipboard.HasClipboard;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.SupportsContextSwitching;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Task class handles all the UI related actions happening on screen.
 */
public class Action {

    /**
     * Context Enum.
     */
    public enum Context {
        NATIVE,
        WEBVIEW
    }

    /**
     * Swipe Direction Enum.
     */
    public enum Direction {
        DOWN,
        UP
    }

    /**
     * Android Driver Setting Enum.
     */
    public enum AndroidDriverSetting {
        ESPRESSO,
        COMPOSE
    }

    private final AppiumDriver driver;

    /**
     * Parameterized Constructor for Task class.
     *
     * @param driver to be used for executing the tasks
     */
    public Action(WebDriver driver) {
        this.driver = (AppiumDriver) driver;
    }

    /**
     * Switch to compose/espresso.
     * @param subDriver either compose or espresso
     * @return same instance of Action
     */
    public Action switchSubDriver(AndroidDriverSetting subDriver) {
        driver.setSetting("driver", subDriver.name().toLowerCase());
        return this;
    }

    /**
     * Get current sub driver setting.
     * @return enum of android driver setting
     */
    public AndroidDriverSetting getSubDriver() {
        return AndroidDriverSetting
                .valueOf(driver.getSettings()
                        .get("driver").toString().toUpperCase());
    }

    /**
     * Get WebElement.
     *
     * @param by locator to find element
     * @return WebElement
     */
    public WebElement getElement(By by) {
        return driver.findElement(by);
    }

    /**
     * Get list of WebElements.
     * @param by By
     * @return List
     */
    public List<WebElement> getElements(By by) {
        return driver.findElements(by);
    }

    /**
     * Validates whether element is enabled and visible before.
     *
     * @param element to be checked for
     * @throws RuntimeException if element is disabled or not visible
     */
    public void validateElementIsVisibleAndEnabled(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOf(element));
            if (!element.isDisplayed() || !element.isEnabled()) {
                throw new ElementNotInteractableException(element.toString());
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("failed to validate element");
        }
    }

    /**
     * Wait for presence of element.
     *
     * @param by to wait for
     * @param timeout in secs
     * @return same instance of Action
     */
    public Action waitForPresenceOfElement(By by, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
        return this;
    }

    /**
     * Get text from clipboard.
     * @return clipboard text
     */
    public String getClipboardText() {
        return ((HasClipboard) driver).getClipboardText();
    }

    /**
     * Wait for element to be visible.
     *
     * @param element to wait for
     * @param timeout in secs
     * @return same instance of Action
     */
    public Action waitForElementToBeDisplayed(WebElement element, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException timeoutException) {
            throw new TimeoutException("element is not visible for "+ timeout + "seconds");
        }
        return this;
    }

    /**
     * Wait for text to be present in element.
     *
     * @param element to wait for
     * @param text to be present in element
     * @return boolean
     */
    public boolean waitForTextToBePresentInElement(WebElement element, String text) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
        } catch (TimeoutException e) {
            throw new ElementNotInteractableException(element.toString());
        }
    }

    /**
     * Wait for element to become stale.
     *
     * @param element to wait for
     * @param timeout in secs
     * @return same instance of Action
     */
    public Action waitForStalenessOfElement(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.stalenessOf(element));
        return this;
    }

    /**
     * Wait for element to become invisible.
     *
     * @param element to wait for
     * @param timeout in secs
     * @return same instance of Action
     */
    public Action waitForInvisibilityOfElement(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.invisibilityOf(element));
        return this;
    }

    /**
     * Wait for element to become clickable
     * @param element to wait for
     * @param timeout in secs
     * @return  same instance of action
     */
    public Action waitForElementClickable(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        return this;
    }

    /**
     * Clicks the element.
     *
     * @param element to be clicked
     * @return same instance of Action
     */
    public Action click(WebElement element) {
        validateElementIsVisibleAndEnabled(element);
        element.click();
        ExtentTestManager.getTest().log(LogStatus.PASS, "click ===> "+ element);
        return this;
    }

    /**
     * Clears the text.
     *
     * @param element to be clear
     * @return same instance of Action
     */
    public Action clear(WebElement element) {
        validateElementIsVisibleAndEnabled(element);
        element.clear();
        return this;
    }

    /**
     * Enters the required text into an element of type edit box.
     *
     * @param element in which the text needs to be entered
     * @param text    to be entered
     * @return same instance of Action
     */
    public Action enterText(WebElement element, String text) {
        validateElementIsVisibleAndEnabled(element);
        element.clear();
        element.sendKeys(text);
        ExtentTestManager.getTest().log(LogStatus.PASS, "enter text "+ '"'+text+'"' + " ===> " + element);
        return this;
    }

    /**
     * Selects an option from the dropdown list.
     *
     * @param element containing the list of tag <select>
     * @param text    option to be selected
     * @return same instance of Action
     */
    public Action selectOptionFromList(WebElement element, String text) {
        validateElementIsVisibleAndEnabled(element);
        Select select = new Select(element);
        select.selectByVisibleText(text);
        return this;
    }

    /**
     * Checks or Un-checks the checkbox.
     *
     * @param element which needs to be checked
     * @param check   boolean to specify if box needs to be checked or unchecked
     * @return same instance of Action
     */
    public Action checkCheckBox(WebElement element, boolean check) {
        validateElementIsVisibleAndEnabled(element);
        if ((check && !element.isSelected()) || (!check && element.isSelected())) {
            element.click();
        }
        return this;
    }

    /**
     * Rotates the device screen.
     *
     * @param orientation can either be LANDSCAPE or PORTRAIT
     * @return same instance of Action
     */
    public Action rotate(ScreenOrientation orientation) {
        if (driver instanceof AndroidDriver) {
            ((AndroidDriver) driver).rotate(orientation);
        } else {
            ((IOSDriver) driver).rotate(orientation);
        }
        return this;
    }

    /**
     * Waits for a specific Android activity to load.
     *
     * @param expectedActivity is the name of expected activity
     * @param secs             is the timeout in secs
     * @return same instance of Action
     * @throws RuntimeException is activity doesn't load in specified time
     */
    public Action waitForAndroidActivity(String expectedActivity, int secs) throws InterruptedException {
        int i = 0;
        String actualActivity = "";

        //Loop for activity
        while (i < secs) {
            actualActivity = ((AndroidDriver) driver).currentActivity();
            if (actualActivity.equals(expectedActivity)) {
                return this;
            }

            Thread.sleep(1000);
            i++;
        }

        throw (new TimeoutException(
                "Timeout occurred while waiting for Android Activity " + expectedActivity + " Current Activity is " +
                        actualActivity));
    }

    public Action waitForSeconds(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.getMessage();
        }
        return this;
    }


    /**
     * Switches the context to a WEBVIEW or NATIVE view.
     *
     * @param context can be a WEBVIEW or NATIVE
     * @return same instance of Action
     */
    public Action switchContext(Context context) {
            Set<String> handles = ((SupportsContextSwitching) driver).getContextHandles();
            for (String handle : handles) {
                if (handle.contains(context.name())) {
                    ((SupportsContextSwitching) driver).context(handle);
                }
            }
        return this;
    }

    /**
     * Gets the text from the element.
     *
     * @param element in which the text needs to be entered
     * @return String : text of the element
     */
    public String getText(WebElement element) {

        return element.getText();
    }

    /**
     * Get Appium field decorator.
     *
     * @return AppiumFieldDecorator
     */
    public AppiumFieldDecorator getAppiumFieldDecorator() {
        return new AppiumFieldDecorator(driver, Duration.ofSeconds(10));
    }

    /**
     * Navigate To.
     *
     * @param url of deeplink or universal link
     * @return return existing instance of action
     */
    public Action navigateTo(String url) {
        driver.get(url);
        return this;
    }

    /**
     * Type using webdriver actions.
     *
     * @param text to type
     * @return return existing instance of action
     */
    public Action type(String text) {
        Actions actions = new Actions(driver);
        actions.sendKeys(text).build().perform();
        return this;
    }

    /**
     * Press Key.
     *
     * @param key to press
     * @return same instance of Action
     */
    public Action pressAndroidKey(AndroidKey key) {
        ((AndroidDriver) driver).pressKey(new KeyEvent(key));
        return this;
    }

    /**
     * Executes a native mobile command such as scrollTo, shell, swipe etc.
     * @param args Map
     * @param command String
     * @return same instance of Action
     */
    public Action executeMobileCommand(String command, Map<String, String> args) {
        driver.executeScript("mobile: " + command, args);
        return this;
    }

    /**
     * Creates a swipe action from point x to y.
     * @param source Point
     * @param target Point
     */
    private void swipe(Point source, Point target) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), source.getX(), source.getY()));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(700),
                PointerInput.Origin.viewport(), target.getX(), target.getY()));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singleton(swipe));
    }

    /**
     * Performs swipe/drag&drop from one element to another using w3c actions.
     * @param sourceElement WebElement
     * @param targetElement WebElement
     * @return same instance of Action
     */
    public Action dragAndDrop(WebElement sourceElement, WebElement targetElement) {
        Point source = sourceElement.getLocation();
        Point target = targetElement.getLocation();
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        swipe(source, target);
        return this;
    }

    /**
     * Performs swipe in the given direction using w3c actions.
     * @param direction Direction enum
     * @return same instance of Action
     */
    public Action swipe(Direction direction) {

        int height;
        int width;

        height = driver.manage().window().getSize().getHeight();
        width = driver.manage().window().getSize().getWidth();

        int startX = width / 2, startY = 0, endX = width / 2, endY = 0;

        if (direction.equals(Direction.UP)) {
            startY = (int) (height * 0.2);
            endY = (int) (height * 0.8);
        } else if (direction.equals(Direction.DOWN)) {
            startY = (int) (height * 0.8);
            endY = (int) (height * 0.2);
        }

        Point start = new Point(startX, startY);
        Point end = new Point(endX, endY);

        swipe(end, start);

        return this;
    }

    /**
     * Returns boolean depending on element is present or not.
     * @param by locator
     * @return boolean
     */
    public boolean isElementPresent(By by) {
        try {
            getElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Returns boolean depending on element is present or not.
     * @param webElement
     * @return boolean
     */
    public boolean isElementPresent(WebElement webElement) {
        try {
            return webElement.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     *
     * @param actualValue       string actual value
     * @param expectedValue     string expected values
     * @return                  same instance of Action
     */
    public Action shouldBeEqual(String actualValue, String expectedValue) {
        Assert.assertEquals(actualValue, expectedValue);
        return this;
    }

    /**
     *
     * @param actualValue       int actual value
     * @param expectedValue     int expected value
     * @return                  same instance of Action
     */
    public Action shouldBeEqual(int actualValue, int expectedValue) {
        Assert.assertEquals(actualValue, expectedValue);
        return this;
    }

    /**
     *
     * @param actualValue       int actual value
     * @param expectedValue     int expected value
     * @return                  same instance of Action
     */
    public Action shouldBeEqual(Object actualValue, Object expectedValue) {
        Assert.assertEquals(actualValue, expectedValue);
        return this;
    }

    /**
     *
     * @param actual value
     * @param expected value
     * @return same instance of Action
     */
    public Action shouldNotBeEqual(String actual, String expected) {
        Assert.assertNotEquals(actual, expected);
        return this;
    }

    /**
     *
     * @param actual value
     * @param expected value
     * @return same instance of Action
     */
    public Action shouldNotBeEqual(int actual, int expected) {
        Assert.assertNotEquals(actual, expected);
        return this;
    }

    /**
     *
     * @param actual value
     * @param expected value
     * @return same instance of Action
     */
    public Action shouldNotBeEqual(Object actual, Object expected) {
        Assert.assertNotEquals(actual, expected);
        return this;
    }

    /**
     *
     * @param element to check
     * @param value of expected
     * @return same instance of Action
     */
    public Action elementShouldContain(WebElement element, String value) {
        Assert.assertTrue(element.getText().contains(value));
        return this;
    }
}
