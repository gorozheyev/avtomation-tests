package com.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.AssertJUnit.fail;

public class MainMenuHeaderOldAndNew extends BaseClass{

    @Test
    public void dropDownMenuHeaderFirstPart() {
        openMainPage();
        driver.findElement(By.id("btn-nav-categories")).click();
        List<WebElement> categories1 = driver.findElements(By.xpath("//div[@class='list-item'][1]//li"));
        for (int i=1; i<=categories1.size(); i++) {
            WebElement page = driver.findElement(By.xpath("(//div[@class='list-item'][1]//li/a)[" + i + "]"));
            if (!page.getAttribute("title").equals(""));
            else fail("Пропали тайтлы в выпадающем меню у категорий в хедере");
            page.click();
            driver.findElement(By.cssSelector("#searchbar"));
            driver.navigate().back();
            driver.findElement(By.id("btn-nav-categories")).click();
        }
    }

    @Test
    public void dropDownMenuHeaderSecondPart() {
        openMainPage();
        driver.findElement(By.id("btn-nav-categories")).click();
        List<WebElement> categories2 = driver.findElements(By.xpath("//div[@class='list-item'][2]//li"));
        for (int i=1; i<=categories2.size(); i++) {
            WebElement page = driver.findElement(By.xpath("(//div[@class='list-item'][2]//li/a)[" + i + "]"));
            if (!page.getAttribute("title").equals(""));
            else fail("Пропали тайтлы в выпадающем меню у категорий в хедере");
            page.click();
            driver.findElement(By.cssSelector("#searchbar"));
            driver.navigate().back();
            driver.findElement(By.id("btn-nav-categories")).click();
        }
    }

    @Test
    public void dropDownMenuHeaderThirdPart() {
        openMainPage();
        driver.findElement(By.id("btn-nav-categories")).click();
        List<WebElement> categories = driver.findElements(By.xpath("//div[@class='list-item'][3]//li"));
        for (int i=2; i<=categories.size(); i++) {
            WebElement page = driver.findElement(By.xpath("(//div[@class='list-item'][3]//li/a)[" + i + "]"));
            if (!page.getAttribute("title").equals(""));
            else fail("Пропали тайтлы в выпадающем меню у категорий в хедере");
            page.click();
            if (driver.getCurrentUrl().equals("http://blog.avtopoisk.ru/")){
                driver.navigate().back();
                driver.findElement(By.id("btn-nav-categories")).click();
            }
            else{
                driver.findElement(By.cssSelector("#searchbar"));
                driver.navigate().back();
                driver.findElement(By.id("btn-nav-categories")).click();
            }
        }
    }

    @Test
    public void dropDownMenuHeaderForthdPart() {
        openMainPage();
        driver.findElement(By.id("btn-nav-categories")).click();
        List<WebElement> categories = driver.findElements(By.xpath("//div[@class='list-item'][4]//li"));
        for (int i=1; i<=categories.size(); i++) {
            WebElement page = driver.findElement(By.xpath("(//div[@class='list-item'][4]//li/a)[" + i + "]"));
            if (!page.getAttribute("title").equals(""));
            else fail("Пропали тайтлы в выпадающем меню у категорий в хедере");
            page.click();
            driver.findElement(By.cssSelector("#searchbar"));
            driver.navigate().back();
            driver.findElement(By.id("btn-nav-categories")).click();
        }
    }

    @Test
    public void dropDownMenuHeaderFifthdPart() {
        openMainPage();
        driver.findElement(By.id("btn-nav-categories")).click();
        List<WebElement> categories = driver.findElements(By.xpath("//div[@class='list-item'][5]//li"));
        for (int i=2; i<=categories.size(); i++) {
            WebElement page = driver.findElement(By.xpath("(//div[@class='list-item'][5]//li/a)[" + i + "]"));
            if (!page.getAttribute("title").equals(""));
            else fail("Пропали тайтлы в выпадающем меню у категорий в хедере");
            page.click();
            driver.findElement(By.cssSelector("#searchbar"));
//            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector("#btn-nav-categories")))).click();
            driver.navigate().back();
            driver.findElement(By.id("btn-nav-categories")).click();
        }
    }

    @Test
    public void newMenuHeaderFirstPart() {
        openSearchPageCar("www", "");
        driver.findElement(By.cssSelector(".header-nav__nav-box")).click();
        List<WebElement> categories1 = driver.findElements(By.xpath("//div[@id='collapseOne']//li"));
        for (int i = 1; i <= categories1.size(); i++) {
            WebElement page = driver.findElement(By.xpath("(//div[@id='collapseOne']//a)[" + i + "]"));
            if (!page.getAttribute("title").equals("")) ;
            else fail("Пропали тайтлы в выпадающем меню у категорий в хедере");
            page.click();
            driver.findElement(By.cssSelector("#searchbar"));
            driver.findElement(By.cssSelector(".header-nav__nav-box")).click();
        }
    }

    @Test
    public void newMenuHeaderSecondPart() throws InterruptedException {
        openSearchPageCar("www", "");
        driver.findElement(By.cssSelector(".header-nav__nav-box")).click();
        Actions actions = new Actions(driver);
        WebElement element = driver.findElement(By.xpath("//*[@id='headingTwo']/span"));
        actions.moveToElement(element).build().perform();
        List<WebElement> categories1 = driver.findElements(By.xpath("//div[@id='collapseTwo']//li"));
        for (int i = 1; i <=  categories1.size(); i++) {
            WebElement page = driver.findElement(By.xpath("(//div[@id='collapseTwo']//a)[" + i + "]"));
            if (!page.getAttribute("title").equals("")) ;
            else fail("Пропали тайтлы в выпадающем меню у категорий в хедере");
            page.click();
            if(i==1) {
                driver.navigate().back();
            }
            driver.findElement(By.cssSelector("#searchbar"));
            driver.findElement(By.cssSelector(".header-nav__nav-box")).click();
            WebElement element2 = driver.findElement(By.xpath("//*[@id='headingTwo']/span"));
            actions.moveToElement(element2).build().perform();
            Thread.sleep(300);
        }
    }

    @Test
    public void newMenuHeaderThirdPart() throws InterruptedException {
        openSearchPageCar("www", "");
        driver.findElement(By.cssSelector(".header-nav__nav-box")).click();
        Actions actions = new Actions(driver);
        WebElement element = driver.findElement(By.xpath("//*[@id='headingThree']/span"));
        actions.moveToElement(element).build().perform();
        List<WebElement> categories1 = driver.findElements(By.xpath("//div[@id='collapseThree']//li"));
        for (int i = 1; i <= categories1.size(); i++) {
            WebElement page = driver.findElement(By.xpath("(//div[@id='collapseThree']//a)[" + i + "]"));
            if (!page.getAttribute("title").equals("")) ;
            else fail("Пропали тайтлы в выпадающем меню у категорий в хедере");
            page.click();
            driver.findElement(By.cssSelector("#searchbar"));
            driver.findElement(By.cssSelector(".header-nav__nav-box")).click();
            WebElement element2 = driver.findElement(By.xpath("//*[@id='headingThree']/span"));
            actions.moveToElement(element2).build().perform();
            Thread.sleep(300);

        }
    }

}
