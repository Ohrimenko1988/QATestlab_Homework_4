package com.ohrimenko1988.QA_homework_4;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Igor on 13.12.2016.
 */
public abstract class Waits
{
    //-----------------------------------------------------------------------

    public static void waitLoadPage(WebDriver driver)
    {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>()
        {
            public Boolean apply(WebDriver driver)
            {
                return ((JavascriptExecutor)driver).executeScript("return document.readyState")
                        .toString().equals("complete");
            }
        };

        WebDriverWait expWait = new WebDriverWait(driver,30);
        expWait.until(expectation);

    }

    //-----------------------------------------------------------------------

    public static void waitForSlideShowNext (WebDriver driver , final WebElement element, final int startCoordinate )
    {

        WebDriverWait expWait = new WebDriverWait(driver,10);

        final int elementWidth = element.getSize().width;


        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>()
        {
            public Boolean apply(WebDriver driver)
            {
                return (element.getLocation().x <= startCoordinate - elementWidth);

            }
        };

        expWait.until(expectation);
    }

    //---------------------------------------------------------------------------


}
