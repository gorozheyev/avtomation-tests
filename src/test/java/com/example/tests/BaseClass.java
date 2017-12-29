package com.example.tests;

import org.junit.rules.ExpectedException;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.ExpectedExceptions;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * Created by gorozheyevd on 13.12.2016.
 */
public class BaseClass {
    protected  WebDriver driver;
//    protected FirefoxOptions options;
    private  String baseUrl;
    private  boolean acceptNextAlert = true;
    private  StringBuffer verificationErrors = new StringBuffer();
    protected  WebDriverWait wait;


    @BeforeClass
    public void setUp() throws Exception {
        driver = new FirefoxDriver();

//        для запуска firefox 55+ with geckodriver
//        System.setProperty("webdriver.gecko.driver", "C:\\Program Files\\Mozilla Firefox_geckodriver\\geckodriver.exe");
//        driver = new FirefoxDriver();

//        для запуска firefox 52esr с selenium 3.3.1
//        options = new FirefoxOptions().setLegacy(true);
//        driver = new FirefoxDriver(options);

//        запуск firefox в headless режиме, версия FF-56+,geckodriver
//        FirefoxBinary firefoxBinary = new FirefoxBinary();
//        firefoxBinary.addCommandLineOptions("--headless");
//        System.setProperty("webdriver.gecko.driver", "C:\\Program Files\\Mozilla Firefox_geckodriver\\geckodriver.exe");
//        FirefoxOptions firefoxOptions = new FirefoxOptions();
//        firefoxOptions.setBinary(firefoxBinary);
//        FirefoxDriver driver = new FirefoxDriver(firefoxOptions);

//        для запуска тестов в хроме
//        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\chromedriver.exe");
//        driver = new ChromeDriver();

//        запуск хрома в headless режиме
//        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\chromedriver.exe");
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
//        driver = new ChromeDriver(options);

//        System.setProperty("webdriver.opera.driver", "C:\\operadriver_win64\\operadriver.exe");
//        driver = new OperaDriver();
        driver.manage().window().maximize();
        baseUrl = "https://www.avtopoisk.ru/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
    }

    //    раскоментировать если нужно получить названия тестов
//    @AfterMethod
//    public void nameAfter(Method method)
//    {
//        System.out.println("Test name: " + method.getName());
//    }

    @AfterClass
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
        driver.findElement(By.xpath("//input[@value='Поиск']")).click();
    }

    protected void clickOnSearchButtonMaiPage() {
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
        driver.get("https://www.avtopoisk.ru/");
        driver.findElement(By.cssSelector(".caret-dd")).click();
        driver.findElement(By.linkText("Москва")).click();
    }

    protected String getFooterSeoText() {
        return driver.findElement(By.cssSelector(".seo-text.text")).getText();
    }

    protected void clickOnElementsFromOtherServices() {
        driver.findElement(By.xpath("//div[contains(text(), 'Продажа авто')]")).click();
        if(driver.getCurrentUrl().contains("avtopoisk.ru/proposition"));
        else fail("Ссылка 'Продать авто' должна вести на страницу добавления объявления");
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
        assertEquals("https://www.avtopoisk.ru/fine.html", driver.getCurrentUrl());
        driver.findElement(By.cssSelector(".table-container.table-container__columned"));
    }

    protected void openCustomCategoryInTabs() {
        driver.findElement(By.xpath("//a[@class='btn-chooser-dd']")).click();
        driver.findElement(By.xpath("//a[contains(text(), 'Каталог машин по типам')]")).click();
    }

    protected void openSearchPageCar(String s, String s1) {
        driver.get("https://"+s+".avtopoisk.ru/car/" + s1);
    }

    public void clickOnFilterButtons(String s) {
        if(driver.findElement(By.xpath("//div[@class='modal-dialog ']")).isDisplayed()) {
            driver.findElement(By.cssSelector(".btn-close-cross__dk.btn-rnd.btn-md.closeSubscribe")).click();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.findElement(By.xpath(s)).click();
            driver.findElement(By.xpath("//button[contains(text(), 'найти')]")).click();
        } else{
            driver.findElement(By.xpath(s)).click();
            driver.findElement(By.xpath("//button[contains(text(), 'найти')]")).click();}
    }

    public int getCountAdvertsOnSearchPage() {
        WebElement counter = driver.findElement(By.xpath("//*[@class='subtitle text-gray']"));
        String text = counter.getText();
        String counterWalue = text.substring("Найдено ".length(), text.length() - "проедложений".length());
        counterWalue = counterWalue.replaceAll(" ", "");
        return Integer.parseInt(counterWalue);
    }

    public void authorizationOnTheSite() {
        WebElement email = driver.findElement(By.id("FrontendLoginForm_email"));
        email.clear();
        email.sendKeys("test@yopmail.com");
        WebElement password = driver.findElement(By.id("FrontendLoginForm_password"));
        password.clear();
        password.sendKeys("1234");
        driver.findElement(By.xpath("//button[contains(text(), 'войти')]")).click();
    }

    public void checkPresenseElementsInHeader() {
        driver.findElement(By.cssSelector(".header-nav__logo"));
        driver.findElement(By.cssSelector(".header-nav__nav-box>a")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-categories")));
        if (driver.findElement(By.id("nav-categories")).isDisplayed());
        else fail("Выпадающий список главного меню в хедере не отображается");
        driver.findElement(By.cssSelector(".header__search__link")).click();
        if (driver.findElement(By.cssSelector(".dropdown-menu.dropdown-menu--city")).isDisplayed());
        else fail("Меню выбора городов не отображается");
        driver.findElement(By.cssSelector(".header-nav__auth>a:nth-of-type(2)"));
        driver.findElement(By.xpath("//a[contains(text(), '+ разместить объявление')]"));
        driver.findElement(By.id("searchbar"));
    }

    public void openAdpage(String region) {
        openSearchPageCar(region, "");
        String partOfUrl = driver.findElement(By.xpath("(//div[@class='description']//a)[3]")).getAttribute("href");
        driver.get(partOfUrl);
    }
}
