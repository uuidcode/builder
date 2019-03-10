package com.github.uuidcode.builder.selenium;

import java.util.stream.Collectors;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;

import com.github.uuidcode.util.CoreUtil;
import com.github.uuidcode.util.StringStream;

import static java.util.Optional.ofNullable;
import static org.slf4j.LoggerFactory.getLogger;

public class ChromeDriverBuilder {
    protected static Logger logger = getLogger(ChromeDriverBuilder.class);

    private ChromeDriver driver;
    private String host;

    public String getHost() {
        return this.host;
    }

    public ChromeDriverBuilder setHost(String host) {
        this.host = host;
        return this;
    }

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

    public ChromeDriverBuilder click(WebElement webElement) {
        webElement.click();
        return this.sleep();
    }

    public ChromeDriverBuilder loadUrl(String url) {
        if (this.host != null) {
            url = this.host + url;
        }

        this.driver.get(url);
        return this.sleep();
    }

    public ChromeDriverBuilder sleep() {
        return this.sleep(2);
    }

    public ChromeDriverBuilder sleep(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (Throwable t) {
            if (logger.isErrorEnabled()) {
                logger.error(">>> error sleep", t);
            }
        }

        return this;
    }

    public ChromeDriverBuilder clickByClassName(String className) {
        try {
            this.click(this.driver.findElementByClassName(className));
        } catch (Throwable t) {
            if (logger.isErrorEnabled()) {
                logger.error(">>> error clickByClassName", t);
            }
        }

        return this;
    }

    public ChromeDriverBuilder clickById(String id) {
        try {
            WebElement element = this.driver.findElementById(id);
            this.click(element);
        } catch (Throwable t) {
            if (logger.isErrorEnabled()) {
                logger.error(">>> error clickById", t);
            }
        }

        return this;
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

    public ChromeDriverBuilder scrollDownByClassName(String className) {
        String method = "document.getElementsByClassName('selector').scrollTop"
            .replaceAll("selector", className);
        return this.scroll(method);
    }

    public ChromeDriverBuilder scrollDownById(String id) {
        String method = "document.getElementById('selector').scrollTop"
            .replaceAll("selector", id);
        return this.scroll(method);
    }

    public ChromeDriverBuilder scroll(String value) {
        String script = StringStream.of()
            .add("var scrollTop = expression;".replaceAll("expression", value))
            .add("window.scrollTo(0, scrollTop);")
            .joiningWithSpace();

        driver.executeScript(script);
        return this.sleep();
    }

    public ChromeDriverBuilder scroll(int scrollTop) {
        return this.scroll(String.valueOf(scrollTop));
    }

    public ChromeDriverBuilder quit() {
        ofNullable(this.driver).ifPresent(ChromeDriver::quit);
        return this;
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

    public ChromeDriverBuilder setInnerHTMLByClassName(String className, String content) {
        content = CoreUtil.splitListWithNewLine(content)
            .stream()
            .map(line -> line.replaceAll("'", "\'"))
            .collect(Collectors.joining("\\n"));

        WebElement element = driver.findElementByClassName(className);
        String script = "arguments[0].innerHTML= '" + content + "'";
        ((JavascriptExecutor) driver).executeScript(script, element);

        return this.sleep();
    }
}
