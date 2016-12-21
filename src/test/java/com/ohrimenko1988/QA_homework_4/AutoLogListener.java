package com.ohrimenko1988.QA_homework_4;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.testng.Reporter;

/**
 * Created by Igor on 20.12.2016.
 */
public class AutoLogListener implements WebDriverEventListener
{

    public void beforeNavigateTo(String url, WebDriver driver) {
        log("--->> try load page "+url);
    }

    public void afterNavigateTo(String url, WebDriver driver) {
        log("--->> page " + url + " is loaded");
    }

    public void beforeNavigateBack(WebDriver driver) {

    }

    public void afterNavigateBack(WebDriver driver) {

    }

    public void beforeNavigateForward(WebDriver driver) {

    }

    public void afterNavigateForward(WebDriver driver) {

    }

    public void beforeNavigateRefresh(WebDriver driver) {

    }

    public void afterNavigateRefresh(WebDriver driver) {

    }

    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        log("--->> try find element --- " + by );
    }

    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        log("--->> element --- " + by + " --- was founded");
    }

    public void beforeClickOn(WebElement element, WebDriver driver) {
        System.out.println("--->> try click on " + element);
    }

    public void afterClickOn(WebElement element, WebDriver driver) {
        log("--->> element "+ element + " was clicked");
    }

    public void beforeChangeValueOf(WebElement element, WebDriver driver) {
        log("--->> try change value of element --- " + element);
    }

    public void afterChangeValueOf(WebElement element, WebDriver driver) {
        log("--->> value was changed in element --- " + element);
    }

    public void beforeScript(String script, WebDriver driver) {
        log("--->> try execute JavaScript --- " + script);
    }

    public void afterScript(String script, WebDriver driver) {
        log("--->> JavaScript was executed --- " + script);
    }

    public void onException(Throwable throwable, WebDriver driver) {

    }

    private void log (String message)
    {
        Reporter.log(message+"<br>");
    }
}
