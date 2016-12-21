package com.ohrimenko1988.QA_homework_4;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


/**
 * Created by Igor on 18.12.2016.
 */
public class PageObjects
{
    public PageObjects(WebDriver driver)
    {
        PageFactory.initElements(driver,this);
    }


    @FindBy(id = "scpl1")
    WebElement imagesButton;

    @FindBy(css = "div>img[alt^=Рекомендуемое]")
    List<WebElement> imageList;

    @FindBy(className = "b_searchbox")
    WebElement searchField;

    @FindBy(xpath = "//div[@class='imgres']/div[@class='dg_u'][1]/div/a" )
    WebElement firstImage;

    @FindBy(xpath = "//div[@class='irhcs']")
    WebElement bigerPicture;

    @FindBy(xpath = "//div[@class='irhcs']/span/span")
    WebElement saveImageButton;

    @FindBy(xpath = "//div[@class='irhcs']/span/img[@title='Поиск по изображению']")
    WebElement searchByImageButton;

    @FindBy(xpath = "//div[@class='irhcs']/span/img[@title='Пометить как изображение для взрослых']")
    WebElement violationButton;

    @FindBy(xpath = "//span[@id='copy']")
    WebElement indicatorLoadMainImageSlideshow;

    @FindBy(xpath = "//div[@class='expandButton clickable active']/span")
    WebElement watchAnotherImagesButton;

    @FindBy(xpath = "//div[@class='tab-content']//li")
    List<WebElement> relatedImagesList;

    @FindBy(xpath = "//div[@class='expandButton clickable active']/span")
    By locatorWatchAnotherImagesButton;



    public By locatorImageListElement(int index)
    {
        return By.xpath("//div[@id='recContainer']/div/ul/li[@data-idx='" +(index+35)+ "']");
    }




}
