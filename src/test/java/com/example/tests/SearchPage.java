package com.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.testng.Assert.*;

/**
 * Created by gorozheyevd on 07.02.2017.
 */
public class SearchPage extends BaseClass{

    @Test
    public void countAdvertsOnSearchPage(){
        openSearchPageCar("www","");
        List<WebElement> adverts = driver.findElements(By.xpath("//div[@class='favorite-link liked ']"));
        if (adverts.size() != 30)
            fail("Проверить кол-во объявлений на выдаче, сейчас их-" + adverts.size()+ ", а должно быть 30 штук");
//        проверка наличия элементов хедера
        checkPresenseElementsInHeader();
    }

    @Test
    public void clickOnSubscriptionButton(){
        openSearchPageCar("www","");
        driver.findElement(By.xpath("//a[@class='btn btn-blue btn-md subscribe-btn hidden-xs']")).click();
        driver.findElement(By.xpath("//a[@class='btn btn-lg btn-blue w-fluid buttonSubscribe']")).click();
        driver.findElement(By.id("alert"));
        driver.findElement(By.cssSelector(".btn-close-cross__dk.btn-rnd.btn-md.closeSubscribe")).click();
    }

    @Test
    public void breadCrumbs(){
        openSearchPageCar("www", "");
        assertEquals("Главная Легковые авто", driver.findElement(By.xpath("//ul[@class='breadcrumb breadcrumb_search']")).getText());
        assertEquals("Продажа авто в России", driver.findElement(By.xpath("//ul[@class='breadcrumb breadcrumb_search']/li/a")).getAttribute("title"));
        assertEquals("http://www.avtopoisk.ru/", driver.findElement(By.xpath("//ul[@class='breadcrumb breadcrumb_search']/li/a")).getAttribute("href"));
        driver.findElement(By.xpath("//ul[@class='breadcrumb breadcrumb_search']/li/a")).click();
        assertEquals("http://www.avtopoisk.ru/", driver.getCurrentUrl());
    }

    @Test
    public void breadCrumbsWithMark(){
        openSearchPageCar("www", "opel");
        assertEquals("Главная Легковые авто Opel", driver.findElement(By.xpath("//ul[@class='breadcrumb breadcrumb_search']")).getText());
        assertEquals("Продажа авто в России", driver.findElement(By.xpath("(//ul[@class='breadcrumb breadcrumb_search']/li/a)[1]")).getAttribute("title"));
        assertEquals("http://www.avtopoisk.ru/", driver.findElement(By.xpath("(//ul[@class='breadcrumb breadcrumb_search']/li/a)[1]")).getAttribute("href"));
        assertEquals("Продажа легковых авто в России", driver.findElement(By.xpath("(//ul[@class='breadcrumb breadcrumb_search']/li/a)[2]")).getAttribute("title"));
        assertEquals("http://www.avtopoisk.ru/car", driver.findElement(By.xpath("(//ul[@class='breadcrumb breadcrumb_search']/li/a)[2]")).getAttribute("href"));
        Actions actions = new Actions(driver);
        WebElement element = driver.findElement(By.xpath("//div[@class='dropdown crumb-dropdown']"));
        actions.moveToElement(element).build().perform();
        List<WebElement> marks = driver.findElements(By.xpath("//ul[@class='dropdown-menu']/li/a"));
        if (marks.size()<=10);
        else fail("В хлебных крошках в, выпадающем списке, марок должно быть не более 10");
        for (WebElement mark : marks) {
            assertEquals("Продажа легковых авто " + mark.getText() + " в России", mark.getAttribute("title"));
        }
        driver.findElement(By.xpath("(//ul[@class='dropdown-menu']/li/a)[1]")).click();
        WebElement element2 = driver.findElement(By.xpath("//div[@class='dropdown crumb-dropdown']"));
        actions.moveToElement(element2).build().perform();
        driver.findElement(By.xpath("(//ul[@class='dropdown-menu']/li)[3]/a")).click();
        driver.findElement(By.id("searchbar"));
    }

    @Test
    public void breadCrumbsWithMarkAndModel(){
        openSearchPageCar("moskva", "honda/accord");
        assertEquals("Главная Легковые авто Honda\nAccord", driver.findElement(By.xpath("//ul[@class='breadcrumb breadcrumb_search']")).getText());
        assertEquals("Продажа авто в Москве", driver.findElement(By.xpath("(//ul[@class='breadcrumb breadcrumb_search']/li/a)[1]")).getAttribute("title"));
        assertEquals("http://moskva.avtopoisk.ru/", driver.findElement(By.xpath("(//ul[@class='breadcrumb breadcrumb_search']/li/a)[1]")).getAttribute("href"));
        assertEquals("Продажа легковых авто в Москве", driver.findElement(By.xpath("(//ul[@class='breadcrumb breadcrumb_search']/li/a)[2]")).getAttribute("title"));
        assertEquals("http://moskva.avtopoisk.ru/car", driver.findElement(By.xpath("(//ul[@class='breadcrumb breadcrumb_search']/li/a)[2]")).getAttribute("href"));
        assertEquals("Продажа легковых авто Honda в Москве", driver.findElement(By.xpath("(//div[@class='dropdown crumb-dropdown'])[1]/a")).getAttribute("title"));
        assertEquals("http://moskva.avtopoisk.ru/car/honda", driver.findElement(By.xpath("(//div[@class='dropdown crumb-dropdown'])[1]/a")).getAttribute("href"));
        Actions actions = new Actions(driver);
        WebElement element = driver.findElement(By.xpath("(//div[@class='dropdown crumb-dropdown'])[2]"));
        actions.moveToElement(element).build().perform();
        List<WebElement> models = driver.findElements(By.xpath("(//ul[@class='dropdown-menu'])[2]/li/a"));
        if (models.size()<=10);
        else fail("В хлебных крошках в, выпадающем списке, моделей должно быть не более 10");
        for (WebElement model : models) {
            assertEquals("Продажа легковых авто Honda " + model.getText() + " в Москве", model.getAttribute("title"));
        }
        driver.findElement(By.xpath("(//ul[@class='dropdown-menu'])[2]/li[1]")).click();
        WebElement element2 = driver.findElement(By.xpath("(//div[@class='dropdown crumb-dropdown'])[2]"));
        actions.moveToElement(element2).build().perform();
        driver.findElement(By.xpath("(//ul[@class='dropdown-menu'])[2]/li[4]")).click();
        driver.findElement(By.id("searchbar"));
    }

    @Test
    public void customLinksUnderTabs(){
        driver.get("http://moskva.avtopoisk.ru/usa-cars");
        List<WebElement> marks = driver.findElements(By.cssSelector(".custom.fz-inc"));
        if (marks.size() <= 8);
        else fail("Моделей должно выводиться не больше шести");
        for (WebElement element : marks) {
            assertEquals("Продажа автомобилей " + element.getText() + " в Москве", element.getAttribute("title"));
        }
        driver.findElement(By.xpath("(//a[@class='custom fz-inc'])[1]")).click();
        driver.findElement(By.id("searchbar"));
        driver.navigate().back();
        driver.findElement(By.xpath("(//a[@class='custom fz-inc'])[3]")).click();
        driver.findElement(By.id("searchbar"));
    }

    @Test
    public void sortingBy(){
        openSearchPageCar("volgograd", "");
        driver.findElement(By.cssSelector(".btn-chooser-drop")).click();
        List<WebElement> sorting = driver.findElements(By.xpath("//ul[@class='nav nav-tabs nt__flat nt__md']/li"));
        if (sorting.size() ==4);
        else fail("Сортировок должно быть 4 типа: по цене, пробегу, году выпуска, по релевантности");
        for (int i = 1; i<=sorting.size(); i++){
            driver.findElement(By.xpath("//ul[@class='nav nav-tabs nt__flat nt__md']/li[" + i + "]")).click();
            String url1 = driver.getCurrentUrl();
            driver.findElement(By.cssSelector(".btn-chooser-drop")).click();
            driver.findElement(By.xpath("//ul[@class='nav nav-tabs nt__flat nt__md']/li[" + i + "]")).click();
            String url2 = driver.getCurrentUrl();
            if (url1.equals(url2))
                fail("Проверить работу 'сортировки по:...' на выдаче");
            driver.findElement(By.cssSelector(".btn-chooser-drop")).click();
        }
    }

    @Test
    public void paginationTest(){
        openSearchPageCar("smolensk", "");
        driver.findElement(By.xpath("//ul[@class='pagination']/li/a[contains(text(), '2')]")).click();
        assertTrue(driver.getCurrentUrl().contains("http://smolensk.avtopoisk.ru/car?page=2"));
        WebElement subscription = driver.findElement(By.xpath("//div[@class='form-content']/a"));
        if(subscription.isDisplayed()) {
            subscription.click();
        }
        driver.findElement(By.xpath("//li[@class='next']/a")).click();
        assertTrue(driver.getCurrentUrl().contains("page=3"));
        if(driver.findElement(By.xpath("//div[@class='form-content']/a")).isDisplayed()){
            driver.findElement(By.xpath("//div[@class='form-content']/a")).click();}
        driver.findElement(By.xpath("//li[@class='previous']/a")).click();
        assertTrue(driver.getCurrentUrl().contains("page=2"));
        if(driver.findElement(By.xpath("//div[@class='form-content']/a")).isDisplayed()){
            driver.findElement(By.xpath("//div[@class='form-content']/a")).click();}
        driver.findElement(By.xpath("//li/a[contains(text(), '>>')]")).click();
        driver.findElement(By.xpath("//div[@class='title h4']"));
        if(driver.findElement(By.xpath("//div[@class='form-content']/a")).isDisplayed()){
            driver.findElement(By.xpath("//div[@class='form-content']/a")).click();}
        driver.findElement(By.xpath("//li/a[contains(text(), '<<')]")).click();
        assertTrue(driver.getCurrentUrl().contains("http://smolensk.avtopoisk.ru/car"));
    }

    @Test
    public void favoriteAds() throws InterruptedException {
        openSearchPageCar("novosibirsk", "");
        assertTrue(driver.findElement(By.xpath("(//div[@class='proposition listing-item hover '])[1]/div/div[1]")).getAttribute("data-count").contains("0"));
        assertEquals("nav-item nav-item-favorites text-uppercase favorites hide",
                driver.findElement(By.xpath("//div[@class='nav-quick']/a[1]")).getAttribute("class"));
        driver.findElement(By.xpath("(//div[@class='favorite-link liked '])[1]")).click();
        Thread.sleep(500);
        assertTrue(driver.findElement(By.xpath("(//div[@class='proposition listing-item hover '])[1]/div/div[1]")).getAttribute("data-count").contains("1"));
        assertEquals("nav-item nav-item-favorites text-uppercase favorites",
                driver.findElement(By.xpath("//div[@class='nav-quick']/a[1]")).getAttribute("class"));
            driver.findElement(By.xpath("(//div[@class='proposition listing-item hover '])[1]/div")).sendKeys(Keys.END);
        assertTrue(driver.findElement(By.id("sticky-panel")).isEnabled());
        driver.findElement(By.xpath("(//div[@class='proposition listing-item hover '])[2]/div/div[1]")).click();
        Thread.sleep(700);
        assertThat(driver.findElement(By.xpath("//div[@class='nav-quick']/a/span")).getText(), is("2"));
        driver.findElement(By.xpath("//div[@class='nav-quick']/a[1]")).click();
        assertEquals("http://novosibirsk.avtopoisk.ru/favorites.html", driver.getCurrentUrl());
        assertTrue(driver.findElements(By.xpath("//div[@class='listing-item-flex']")).size() ==2);
        driver.findElement(By.xpath("(//a[contains(text(), 'Проверка штрафов')])[2]")).click();
        assertEquals("http://novosibirsk.avtopoisk.ru/fine", driver.getCurrentUrl());
        driver.findElement(By.xpath("//h1[@class='h4 text-uppercase']"));
        driver.navigate().back();
        driver.findElement(By.xpath("(//a[@class='option-item-del'])[1]")).click();
        assertTrue(driver.findElements(By.xpath("//div[@class='listing-item-flex']")).size() ==1);
        driver.navigate().back();
        driver.findElement(By.xpath("(//div[@class='proposition listing-item hover '])[1]/div")).sendKeys(Keys.END);
        assertThat(driver.findElement(By.xpath("//div[@class='nav-quick']/a[1]/span")).getText(), is("1"));
    }

    @Test
    public void subscriptionOnStyckyPanel() throws InterruptedException {
        openSearchPageCar("voronezh","");
        if(!driver.findElements(By.xpath("//a[@id='btn-nav-user']/span")).isEmpty()){
            driver.findElement(By.xpath("//a[@id='btn-nav-user']")).click();
            driver.findElement(By.xpath("//a[@class='nav-user-item'][2]")).click();
            driver.findElement(By.xpath("//div[@class='btn-panel']")).click();
        } else {
            driver.findElement(By.xpath("//div[@class='form-content']/a")).click();
        }
        driver.findElement(By.id("searchbar")).sendKeys(Keys.END);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='nav-quick']/a[2]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='form-content']/a"))).click();
        Thread.sleep(1500);
        driver.findElement(By.xpath("//div[@class='nav-quick']/a[3]")).click();
        assertTrue(driver.getCurrentUrl().equals("http://voronezh.avtopoisk.ru/auth.html"));
        driver.findElement(By.xpath("//a[contains(text(), 'Регистрация')]")).click();
        assertTrue(driver.findElement(By.xpath("(//div[@class='form-modal-caption text-center'])[2]")).getText().equals("Регистрация пользователя"));
        driver.findElement(By.xpath("//a[contains(text(), 'Вход')]")).click();
        authorizationOnTheSite();
        assertTrue(driver.getCurrentUrl().equals("http://voronezh.avtopoisk.ru/user/EditProfile"));
    }

    @Test
    public void favoriteWithLoggedUser() {
        openSearchPageCar("volgograd", "");
        assertTrue(driver.findElement(By.xpath("(//div[@class='favorite-link liked '])[1] ")).getAttribute("title").equals("Добавить в избранное"));
        driver.findElement(By.xpath("//a[@class='btn-link-header hidden-xs']")).click();
        authorizationOnTheSite();
        assertTrue(driver.getCurrentUrl().equals("http://volgograd.avtopoisk.ru/user/EditProfile"));
        driver.findElement(By.xpath("//a[contains(text(), 'Избранное')]")).click();
        List<WebElement> favorite = driver.findElements(By.xpath("//div[@class='item-liked']/a[1]"));
        if(!favorite.isEmpty()){
            for(int i=0; i< favorite.size(); i++){
                driver.findElement(By.xpath("//div[@class='item-liked']/a[1]")).click();
            }
        }
        driver.findElement(By.xpath("//div[@class='btn-panel']")).click();
        driver.findElement(By.cssSelector("div[data-count]:nth-of-type(1)")).click();
        driver.findElement(By.id("searchbar")).sendKeys(Keys.END);
        driver.findElement(By.xpath("//div[@class='nav-quick']/a[1]")).click();
        driver.findElement(By.xpath("//a[contains(text(), 'Избранное')]")).click();
        assertTrue(driver.findElements(By.xpath("//div[@class='item-liked']")).size()==1,
                "Не совпадает кол-во добавленных объявлений в избранное со счетчиком на липкой панельке");
        driver.findElement(By.xpath("(//a[@class='option-item-del'])[1]")).click();
        driver.findElement(By.xpath("//div[@class='btn-panel']")).click();
    }

}


