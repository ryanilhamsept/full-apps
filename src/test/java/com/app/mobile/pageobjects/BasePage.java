package com.app.mobile.pageobjects;

import com.app.Action;

public abstract class BasePage {

    /**
     * Generics based method to get an instance of new page object class at runtime.
     *
     * @param <T> type for which instance needs to be created
     * @param clazz  Class of object T
     * @param action Action needed to instantiate new class
     * @return new instance of class T
     * @throws RuntimeException in case class cannot be initialized successfully
     */
    protected <T extends BasePage> T getNewInstanceOfClass(Class<T> clazz, Action action) {
        if (this.getClass().equals(clazz)) {
            return (T) this;
        }

        try {
            return (T) clazz.getDeclaredConstructors()[0].newInstance(action);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
