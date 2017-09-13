package com.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.AssertJUnit.fail;

/**
 * Created by gorozheyevd on 22.06.2017.
 */

public class ClickOnAllLinksInMainMenuHeader extends BaseClass{

    @Test
    public void dropDownMenuHeaderFirstPart() {
        openMainPage();
        driver.findElement(By.id("btn-nav-categories")).click();
        List<WebElement> categories1 = driver.findElements(By.xpath("//div[@class='col-md-2'][1]/ul/li"));
        for (int i=1; i<=categories1.size(); i++) {
            WebElement page = driver.findElement(By.xpath("(//div[@class='col-md-2'][1]/ul/li/a)[" + i + "]"));
            if (!page.getAttribute("title").equals(""));
            else fail("Пропали тайтлы в выпадающем меню у категорий в хедере");
            page.click();
            driver.findElement(By.cssSelector("#searchbar"));
            driver.findElement(By.id("btn-nav-categories")).click();
        }
    }

    @Test
    public void dropDownMenuHeaderSecondPart() {
        openMainPage();
        driver.findElement(By.id("btn-nav-categories")).click();
        List<WebElement> categories2 = driver.findElements(By.xpath("//div[@class='col-md-2'][2]/ul/li"));
        for (int i=1; i<=categories2.size(); i++) {
            WebElement page = driver.findElement(By.xpath("(//div[@class='col-md-2'][2]/ul/li/a)[" + i + "]"));
            if (!page.getAttribute("title").equals(""));
            else fail("Пропали тайтлы в выпадающем меню у категорий в хедере");
            page.click();
            driver.findElement(By.cssSelector("#searchbar"));
            driver.findElement(By.id("btn-nav-categories")).click();
        }
    }

    @Test
    public void dropDownMenuHeaderThirdPart() {
        openMainPage();
        driver.findElement(By.id("btn-nav-categories")).click();
        List<WebElement> categories = driver.findElements(By.xpath("//div[@class='col-md-2'][3]/ul/li"));
        for (int i=1; i<=categories.size(); i++) {
            WebElement page = driver.findElement(By.xpath("(//div[@class='col-md-2'][3]/ul/li/a)[" + i + "]"));
            if (!page.getAttribute("title").equals(""));
            else fail("Пропали тайтлы в выпадающем меню у категорий в хедере");
            page.click();
            driver.findElement(By.cssSelector("#searchbar"));
            driver.findElement(By.id("btn-nav-categories")).click();
        }
    }

    @Test
    public void dropDownMenuHeaderForthdPart() {
        openMainPage();
        driver.findElement(By.id("btn-nav-categories")).click();
        List<WebElement> categories = driver.findElements(By.xpath("//div[@class='col-md-2'][4]/ul/li"));
        for (int i=1; i<=categories.size(); i++) {
            WebElement page = driver.findElement(By.xpath("(//div[@class='col-md-2'][4]/ul/li/a)[" + i + "]"));
            if (!page.getAttribute("title").equals(""));
            else fail("Пропали тайтлы в выпадающем меню у категорий в хедере");
            page.click();
            driver.findElement(By.cssSelector("#searchbar"));
            driver.findElement(By.id("btn-nav-categories")).click();
        }
    }

    @Test
    public void dropDownMenuHeaderFifthdPart() {
        openMainPage();
        driver.findElement(By.id("btn-nav-categories")).click();
        List<WebElement> categories = driver.findElements(By.xpath("//div[@class='col-md-2'][5]/ul/li"));
        for (int i=1; i<=categories.size(); i++) {
            WebElement page = driver.findElement(By.xpath("(//div[@class='col-md-2'][5]/ul/li/a)[" + i + "]"));
            if (!page.getAttribute("title").equals(""));
            else fail("Пропали тайтлы в выпадающем меню у категорий в хедере");
            page.click();
            driver.findElement(By.cssSelector("#searchbar"));
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector("#btn-nav-categories")))).click();
        }
    }

    @Test
    public void dropDownMenuHeaderSixdPart() {
        openMainPage();
        driver.findElement(By.id("btn-nav-categories")).click();
        List<WebElement> categories = driver.findElements(By.xpath("//div[@class='col-md-2'][6]/ul/li"));
        for (int i=2; i<=categories.size(); i++) {
            if (i == 14) {
            } else {
                WebElement page = driver.findElement(By.xpath("(//div[@class='col-md-2'][6]/ul/li/a)[" + i + "]"));
                if (!page.getAttribute("title").equals("")) ;
                else fail("Пропали тайтлы в выпадающем меню у категорий в хедере");
                page.click();
                driver.navigate().back();
                driver.findElement(By.id("btn-nav-categories")).click();
            }
        }
    }

}
