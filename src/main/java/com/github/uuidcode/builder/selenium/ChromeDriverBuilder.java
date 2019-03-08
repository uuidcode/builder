package com.github.uuidcode.builder.selenium;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;

import com.github.uuidcode.util.StringStream;

import static java.util.Optional.ofNullable;
import static org.slf4j.LoggerFactory.getLogger;

public class ChromeDriverBuilder {
    protected static Logger logger = getLogger(ChromeDriverBuilder.class);

    private ChromeDriver driver;

    public static ChromeDriverBuilder of() {
        return new ChromeDriverBuilder();
    }

    public static ChromeDriverBuilder headless() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        return new ChromeDriverBuilder(options);
    }

    private ChromeDriverBuilder(ChromeOptions options) {
        this.driver = new ChromeDriver(options);
    }

    private ChromeDriverBuilder() {
        this(new ChromeOptions());
    }

    public void click(WebElement webElement) {
        webElement.click();
        this.sleep();
    }

    public void loadUrl(String url) {
        this.driver.get(url);
        this.sleep();
    }

    public void sleep() {
        this.sleep(2);
    }

    public void sleep(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (Throwable t) {
            if (logger.isErrorEnabled()) {
                logger.error(">>> error sleep", t);
            }
        }
    }

    public void clickByClassName(String className) {
        try {
            this.click(this.driver.findElementByClassName(className));
        } catch (Throwable t) {
            if (logger.isErrorEnabled()) {
                logger.error(">>> error clickByClassName", t);
            }
        }
    }

    public void clickById(String id) {
        try {
            WebElement element = this.driver.findElementById(id);
            this.click(element);
        } catch (Throwable t) {
            if (logger.isErrorEnabled()) {
                logger.error(">>> error clickById", t);
            }
        }
    }

    public ChromeDriverBuilder sendKeyByClassName(String className, String value) {
        try {
            WebElement element = this.driver.findElementByClassName(className);
            this.sendKey(element, value);
        } catch (Throwable t) {
            if (logger.isErrorEnabled()) {
                logger.error(">>> error sendKeyByClassName", t);
            }
        }

        return this;
    }

    public ChromeDriverBuilder sendKeyById(String id, String value) {
        try {
            WebElement element = this.driver.findElementById(id);
            this.sendKey(element, value);
        } catch (Throwable t) {
            if (logger.isErrorEnabled()) {
                logger.error(">>> error sendKeyById", t);
            }
        }

        return this;
    }

    public ChromeDriverBuilder sendKey(WebElement webElement, String value) {
        webElement.sendKeys(value);
        return this;
    }

    public void scrollDownByClassName(String className) {
        String method = "getElementsByClassName('" + className + "')";
        this.scroll(method);
    }

    public void scrollDownById(String id) {
        String method = "getElementById('" + id + "')";
        this.scroll(method);
    }

    public void scroll(String method) {
        String script = StringStream.of()
            .add("var element = document." + method + ");")
            .add("var scrollTop = element.scrollTop;")
            .add("window.scrollTo(0, scrollTop);")
            .joiningWithSpace();

        driver.executeScript(script);
        this.sleep();
    }

    public void scroll(int scrollTop) {
        String script = StringStream.of()
            .add("var scrollTop = " + scrollTop + ";")
            .add("window.scrollTo(0, scrollTop);")
            .joiningWithSpace();

        driver.executeScript(script);
        this.sleep();
    }

    public void quit() {
        ofNullable(this.driver).ifPresent(ChromeDriver::quit);
    }

    public boolean containsAtSource(String content) {
        return driver.getPageSource().contains(content);
    }

    public boolean loadUrlAndContainsAtSource(String url, String content) {
        this.loadUrl(url);
        return driver.getPageSource().contains(content);
    }

    public ChromeDriver getDriver() {
        return this.driver;
    }

    public void setInnerHTMLByClassName(String className, String content) {
        WebElement element = driver.findElementByClassName(className);
        String script = "arguments[0].innerHTML='" + content + "'";
        ((JavascriptExecutor) driver).executeScript(script, element);
        this.sleep();
    }
}
