package com.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * Created by gorozheyevd on 07.02.2017.
 */
public class SearchPage extends BaseClass{

    @Test
    public void countAdvertsOnSearchPage(){
        openSearchPageCar("www","");
        if (driver.findElements(By.xpath("//img[@class='photo lazy-load']")).size() +
                driver.findElements(By.xpath("//div[@class='photo-frame brazzerCarusel brazzers-daddy']")).size() != 30)
            fail("Проверить кол-во объявлений на выдаче, их долно быть 30 штук");
//        проверка наличия элементов хедера
        driver.findElement(By.cssSelector(".logo"));
        driver.findElement(By.cssSelector("#btn-nav-categories")).click();
        if (driver.findElement(By.id("nav-categories")).isDisplayed());
        else fail("Главное меню в хедере не отображается");
        driver.findElement(By.cssSelector("#btn-nav-cities>span")).click();
        if (driver.findElement(By.id("btn-nav-cities")).isDisplayed());
        else fail("Меню выбора городов не отображается");
        driver.findElement(By.xpath("//a[contains(text(), 'Войти')]"));
        driver.findElement(By.xpath("//a[contains(text(), '+ разместить объявление')]"));
        driver.findElement(By.id("searchbar"));
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
    public void presenceTabsAndClickThem(){
        openSearchPageCar("www", "");
        List<WebElement> tabs = driver.findElements(By.xpath("//div[@class='tabs-menu-i clearfix']/a"));
        String nameTabs[] = new String[5];
        nameTabs[0] = "Продажа авто";
        nameTabs[1] = "Быстрые фильтры";
        nameTabs[2] = "Отзывы";
        nameTabs[3] = "Статистика";
        nameTabs[4] = "Новости";
        for (int i =1; i<=tabs.size(); i++) {
            driver.findElement(By.xpath("//div[@class='tabs-menu-i clearfix']/a[" + i + "]")).click();
            String tab = driver.findElement(By.xpath("//div[@class='tabs-menu-i clearfix']/a[" + i + "]")).getText();
            assertEquals(nameTabs[i-1], tab);
        }
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
        openSearchPageCar("moskva", "");
        driver.findElement(By.linkText("Новые авто  ")).click();
        assertEquals("http://moskva.avtopoisk.ru/new-cars", driver.getCurrentUrl());
        assertEquals("Продажа новых автомобилей в Москве.", driver.findElement(By.cssSelector(".h4.inline-block")).getText());
        driver.get("http://moskva.avtopoisk.ru/usa-cars");
        driver.findElement(By.linkText("Новые авто  "));
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
        openSearchPageCar("orel", "");
        driver.findElement(By.cssSelector(".btn-chooser-drop")).click();
        List<WebElement> sorting = driver.findElements(By.xpath("//ul[@class='nav nav-tabs nt__flat nt__md']/li"));
        if (sorting.size() ==4);
        else fail("Сортироводолжно быть 4 типа: по цене, пробегу, году выпуска, по релевантности");
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

}


