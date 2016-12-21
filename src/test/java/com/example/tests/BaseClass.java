package com.example.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

/**
 * Created by gorozheyevd on 13.12.2016.
 */
public class BaseClass {
    protected static WebDriver driver;
    private static String baseUrl;
    private static boolean acceptNextAlert = true;
    private static StringBuffer verificationErrors = new StringBuffer();

    @BeforeTest
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        baseUrl = "http://www.avtopoisk.ru/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterTest
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    protected void clickOnSearchButton() {
        driver.findElement(By.xpath("//input[@value='Найти']")).click();
    }

    protected void inputValueInSearchField(MainPageData data) {
        driver.findElement(By.xpath("(//input[@type='text'])[2]")).click();
        driver.findElement(By.xpath("(//input[@type='text'])[2]")).clear();
        driver.findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys(data.getName());
    }

    protected void openMainPage() {
        driver.get(baseUrl + "/");
    }


    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }

    protected void presenceFiveBlocksOnMainePage() {
        driver.findElement(By.xpath("(//div[@class='content-frame margin-b20'])[1]")).isDisplayed();
        driver.findElement(By.xpath("(//div[@class='content-frame margin-b20'])[2]")).isDisplayed();
        driver.findElement(By.xpath("(//div[@class='content-frame margin-b20'])[3]")).isDisplayed();
        driver.findElement(By.xpath("(//div[@class='content-frame margin-b20'])[4]")).isDisplayed();
        driver.findElement(By.id("tabsJQ")).isDisplayed();
    }

    protected void openMoskvaMainPage() {
        driver.get("http://www.avtopoisk.ru/");
        driver.findElement(By.cssSelector(".caret-dd")).click();
        driver.findElement(By.linkText("Москва")).click();
    }

    protected String getFooterSeoText() {
        return driver.findElement(By.cssSelector(".seo-text.text")).getText();
    }

    protected void clickOnElementsFromOtherServices() {
        driver.findElement(By.xpath("//div[contains(text(), 'Продажа авто')]")).click();
        driver.findElement(By.xpath("//div/h3[contains(text(), 'Размещение объявления')]"));
        driver.navigate().back();
        driver.findElement(By.xpath("//div[contains(text(), 'Автосалоны')]")).click();
        driver.navigate().back();
        driver.findElement(By.xpath("//div[contains(text(), 'СТО')]")).click();
        driver.navigate().back();
        driver.findElement(By.xpath("//div[contains(text(), 'Автовыкуп')]")).click();
        driver.navigate().back();
        driver.findElement(By.xpath("//div[contains(text(), 'Кредиты')]")).click();
        driver.navigate().back();
        driver.findElement(By.xpath("//div[contains(text(), 'Страхование')]")).click();
    }

    protected void checkTitleInBlockOtherServices() {
        String title = driver.findElement(By.cssSelector(".simple-tile.service-img.service-img__sell")).getAttribute("title");
        if (!title.equals("")){}
            else {
            AssertJUnit.fail("Пропали тайтлы в блоке Другие услуги");}
    }
}
