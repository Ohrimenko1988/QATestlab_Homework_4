package com.ohrimenko1988.QA_homework_4;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

/**
 * Created by Igor on 17.12.2016.
 */

public class Main
{


    EventFiringWebDriver driver;
    PageObjects page;
    JavascriptExecutor jse;
    WebDriverWait explicitWait;
    Actions action;


    @BeforeClass
    public void initialize()
    {
        log("--->> initialize all class fields");

        String property = System.getProperty("user.dir")+"/driver/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver" , property);
        driver = new EventFiringWebDriver(new ChromeDriver());
        driver.register(new AutoLogListener());
        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30,TimeUnit.SECONDS);
        page = new PageObjects(driver);
        jse = ((JavascriptExecutor)driver);
        explicitWait = new WebDriverWait(driver,20);
        action = new Actions(driver);
        Reporter.setEscapeHtml(false);

        log("--->> initialize is completed");

    }

    @Test
    public void uploadPage()
    {
        log("--->> go to URL");
        driver.get("https://www.bing.com/");
        log("--->> find Images button");
        WebElement imagesButton = page.imagesButton;
        imagesButton.click();
        log("--->> check the page title");
        Assert.assertTrue(explicitWait.until(titleIs("Лента изображений Bing")));
        log("--->> the page title was checked");
    }

    @Test(dependsOnMethods = {"uploadPage"})
    public void scrollPage()
    {
        log(">-->> found and save count of elements");
        int prevListSize = page.imageList.size();
        log(">-->> count was saved");

        log(">-->> --------------  try scroll page 4 times  --------------");
        for(int i = 0 ; i <4; i++)
        {
            log(">-->> scroll down" );
            jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            log(">-->> scroll is completed");
            log(">-->> wait visibility of the last list element");
            explicitWait.until(visibilityOf(page.imageList.get(prevListSize-1)));
            log(">-->> element is visibility");
            log(">-->> wait until the new images will be present in the DOM ");
            explicitWait.until(presenceOfElementLocated(page.locatorImageListElement(prevListSize)));
            log(">-->> wait is completed");
            log(">-->> compare previously and after scroll images count");
            int afterScrollListSize =  page.imageList.size();
            boolean check = afterScrollListSize > prevListSize;

            log(">-->> check that new images was download after scroll page");
            Assert.assertTrue( check );
            log(">-->> check is complete");

            prevListSize=afterScrollListSize;



        }

        log(">-->> ----------------  page scrolled 4 times  --------------------");

        log(">-->> scroll window to top");
        jse.executeScript("window.scrollTo(0,0)");
        log(">-->> scroll is completed");
    }



    @Test(dataProvider = "fromTextFileToDataProvider",dependsOnMethods = "scrollPage")
    public void searchByKeywords(String inputString)
    {
        log(">-->> wait until search field will be visible");
        explicitWait.until(visibilityOf(page.searchField));
        WebElement searchField = page.searchField;
        log(">-->> clear search field");
        searchField.clear();
        log(">-->> input search string");
        searchField.sendKeys(inputString);
        log(">-->> wait until search expression will be located in the search field");
        explicitWait.until(attributeContains(page.searchField,"value",inputString));
        log(">-->> press Enter button");
        searchField.sendKeys(Keys.ENTER);
        log(">-->> wait until the page title will be with search expression");
        Assert.assertTrue(explicitWait.until(titleIs(inputString + " - Bing images")));
        log(">-->> wait until the first image on the page will be visible ");
        explicitWait.until(visibilityOf(page.firstImage));


    }

    @Test(dependsOnMethods = "searchByKeywords" )
    public void validateImageAndItsButtons()
    {
        log(">-->> hover the mouse to the first image");
        action.moveToElement(page.firstImage).build().perform();
        log(">-->> wait resize image");
        explicitWait.until(visibilityOf(page.bigerPicture));
        log(">-->> check resize image");
        Assert.assertTrue(page.bigerPicture.isDisplayed());
        log(">-->> wait then save-button will be clickable");
        explicitWait.until(elementToBeClickable(page.saveImageButton));
        log(">-->> check that element is available");
        Assert.assertTrue(page.saveImageButton.isDisplayed());
        log(">-->> wait until search-by-image button will be clickable");
        explicitWait.until(elementToBeClickable(page.searchByImageButton));
        log(">-->> check that element is available");
        Assert.assertTrue(page.searchByImageButton.isDisplayed());
        log(">-->> wait until violation button will be clickable");
        explicitWait.until(elementToBeClickable(page.violationButton));
        log(">-->> check that element is available");
        Assert.assertTrue(page.violationButton.isDisplayed());

    }

    @Test(dependsOnMethods = "validateImageAndItsButtons")
    public void clickSearchByImageButton()
    {
        log(">-->> click on search-by-image button");
        action.click(page.searchByImageButton).build().perform();
        log(">-->> wait until slide show will be loaded");
        explicitWait.until(visibilityOf( page.indicatorLoadMainImageSlideshow));
        log(">-->> check that element is available");
        Assert.assertEquals(page.indicatorLoadMainImageSlideshow.isDisplayed(),true);
    }

    @Test(dependsOnMethods = "clickSearchByImageButton")
    @Parameters({"imagesMinCount"})
    public void watchAnotherImages(int imagesMinCount)
    {
        log(">-->> wait until element will be clickable");
        explicitWait.until(elementToBeClickable(page.watchAnotherImagesButton));
        log(">-->> wait until element will be visibility ");
        explicitWait.until(visibilityOf(page.watchAnotherImagesButton));
        log(">-->> click on it");
        action.click(page.watchAnotherImagesButton).build().perform();
        log(">-->> get last index of the related images list");
        WebElement lastImage = page.relatedImagesList.get(page.relatedImagesList.size()-1);
        log(">-->> wait until last element will be not hiden");
        explicitWait.until(ExpectedConditions.attributeContains(lastImage, "class" ,""));
        log(">-->> check that size of related images in the acceptable limits");
        Assert.assertTrue(page.relatedImagesList.size() >= imagesMinCount);


    }

    @DataProvider
    public Object[][] fromTextFileToDataProvider()throws FileNotFoundException, IOException
    {
        ArrayList<String> arrayString = new ArrayList<String>();

            try
            {
                BufferedReader reader = new BufferedReader(new FileReader("searchWords.txt"));
                String buferString = "";


                while (true)
                {
                    buferString = reader.readLine();

                    if(buferString == null)
                    {
                        break;
                    }
                    arrayString.add(buferString);
                }

            }
            catch(FileNotFoundException ex)
            {
                log("text file not found");
            }
            catch(IOException ex)
            {
                log("some input / output error");
            }



        int arraySize = arrayString.size();
        Object[][] rezult = new Object[arraySize][1];

        for(int i = 0; i < arraySize; i++)
        {
            rezult[i][0] = arrayString.get(i);
        }

        return rezult;

    }







    @AfterClass
    public void tearDown()
    {
        driver.quit();
    }

    private void log (String message)
    {
        Reporter.log(message+"<br>");
    }

}
