package com.example.tests;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;
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

    protected void clickOnSearchButton() {
        driver.findElement(By.xpath("//input[@value='Найти']")).click();
    }

    protected void inputValueInSearchField(MainPageData data) {
        driver.findElement(By.xpath("(//input[@type='text'])[2]")).clear();
        driver.findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys(data.getName());
    }

    protected void openMainPage() {
        driver.get(baseUrl + "/");
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
        driver.navigate().back();
        driver.findElement(By.xpath("//div[contains(text(), 'Проверка штрафов')]"));
    }

    protected void checkTitleInBlockOtherServices() {
        String title = driver.findElement(By.cssSelector(".simple-tile.service-img.service-img__sell")).getAttribute("title");
        if (!title.equals("")){}
            else {
            fail("Пропали тайтлы в блоке Другие услуги");}
    }

    protected void clickOnSeeMoreTestDrivesButton() {
        driver.findElement(By.xpath("(//a[@id='tile-more-testdrives'])[1]")).click();
    }

    protected void clickOnThirdTestDriveAndGoBack() {
        driver.findElement(By.xpath("//div[@id='panel-testdrives']/a[3]")).click();
        driver.findElement(By.cssSelector(".div-panel.indent__on.toe.div-panel_content"));
        driver.navigate().back();
    }

    protected void clickOnSecondTestDriveAndGoBack() {
        driver.findElement(By.xpath("//div[@id='panel-testdrives']/a[2]")).click();
        driver.findElement(By.cssSelector(".div-panel.indent__on.toe.div-panel_content"));
        driver.navigate().back();
    }

    protected void clickOnFirstTestDriveAndGoBack() {
        driver.findElement(By.xpath("//div[@id='panel-testdrives']/a[1]")).click();
        driver.findElement(By.cssSelector(".div-panel.indent__on.toe.div-panel_content"));
        driver.navigate().back();
    }

    protected void clickSeeMoreCatalogButton() {
        driver.findElement(By.xpath("((//div[@id='panel-testdrives'])[2])/a[4]")).click();
    }

    protected void clickOnThirdCatalogModelAndGoBack() {
        driver.findElement(By.xpath("((//div[@id='panel-testdrives'])[2])/a[3]")).click();
        driver.findElement(By.cssSelector(".content-frame.margin-b15"));
        driver.navigate().back();
    }

    protected void clickOnSecondCatalogModelAndGoBack() {
        driver.findElement(By.xpath("((//div[@id='panel-testdrives'])[2])/a[2]")).click();
        driver.findElement(By.cssSelector(".content-frame.margin-b15"));
        driver.navigate().back();
    }

    protected void clickOnFirstCatalogModelAndGoBack() {
        driver.findElement(By.xpath("((//div[@id='panel-testdrives'])[2])/a[1]")).click();
        driver.findElement(By.cssSelector(".content-frame.margin-b15"));
        driver.navigate().back();
    }

    protected void checkFineUrlAndTabFine() {
        assertEquals("http://www.avtopoisk.ru/fine.html", driver.getCurrentUrl());
        driver.findElement(By.cssSelector(".table-container.table-container__columned"));
    }

    protected void openCustomCategoryInTabs() {
        driver.findElement(By.xpath("//a[contains(text(), 'Быстрые фильтры')]")).click();
        driver.findElement(By.xpath("//a[@class='btn-chooser-dd']")).click();
        driver.findElement(By.xpath("//a[contains(text(), 'Каталог машин по типам')]")).click();
    }

    protected void openSearchPageCar(String s, String s1) {
        driver.get("http://"+s+".avtopoisk.ru/car/" + s1);
    }
}
