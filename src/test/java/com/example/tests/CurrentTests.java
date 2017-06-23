/**
 * Created by gorozheyevd on 07.12.2016.
 */
package com.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;


public class CurrentTests extends BaseClass{

    @Test
    public void openStoOnMainPage() throws InterruptedException {
        openMainPage();
        driver.findElement(By.id("btn-nav-categories")).click();
        driver.findElement(By.xpath("//a[@title='СТО в России']")).click();
        driver.findElement(By.linkText("Мультибрендовый сервис «Автомоторс КВС»")).click();
        Thread.sleep(2000);
    }

    @Test
    //    проверка каунтеров на выдаче
    public void checkCountAdverts() throws Exception {
        openMainPage();
        clickOnSearchButton();
        int counters = getCountAdvertsOnSearchPage();
        if (counters > 360000){
            System.out.println("Объявлений на выдаче достаточно - "+ counters + " штук.");
        }
            else {
            throw new Exception("Мало объявлений на выдаче - " + counters + " штук");
        }
    }

    @Test
//    проверка попапа-подписки на выдаче и при клике на пагинацию
    public void initPopapOnFirstAndSecondSearchPages() {
        openMoskvaMainPage();
        clickOnSearchButton();
        WebElement inputField = driver.findElement(By.xpath("//input[@placeholder='E-mail']"));
        inputField.sendKeys("kljkljkljl");
        driver.findElement(By.cssSelector(".btn.btn-lg.btn-blue.w-fluid.buttonSubscribe")).click();
        driver.findElement(By.cssSelector(".btn-close-cross__dk.btn-rnd.btn-md.closeSubscribe")).click();
        assertEquals("Купить б/у авто с пробегом в Москве. Подержанные автомобили и цены в Москве, недорого", driver.getTitle());
//        открытие стр.2 и проверка попапа-подписки
        driver.findElement(By.xpath("html/body/section/div[2]/div/div[3]/div[1]/nav/ul/li[2]/a")).click();
        driver.findElement(By.cssSelector(".btn.btn-lg.btn-blue.w-fluid.buttonSubscribe")).click();
    }

}
