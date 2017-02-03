package com.example.tests;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * Created by gorozheyevd on 23.01.2017.
 */
public class SeoRequirementsTests extends BaseClass {

    @Test
    public void seoMetaOnMainPage(){
        openMainPage();
//        проверка descriptions и keywords на гловной странице
        String descriptions = driver.findElement(By.xpath("//meta[@name='description']")).getAttribute("content");
        assertEquals("Avtopoisk.Ru - выгодная купля-продажа авто в России. Покупка автомобиля в России, удобный автопоиск. Каталог автомобилей.", descriptions);
        String keywords = driver.findElement(By.xpath("//meta[@name='keywords']")).getAttribute("content");
        assertEquals("продажа авто в России, купить авто в России, покупка автомобиля в России, авторынок в России", keywords);
        if(driver.findElements(By.xpath("//link[@rel='canonical']")).size() == 0);
            else fail("На этой странице не должно быть тега canonical");
}

    @Test
    public void nofollowOnMainPage() {
        openMainPage();
        for (int i = 1; i <= 4; i++) {
            if (driver.findElement(By.xpath("(//ul[@class='list-items list-reset'])[2]/li[" + i + "]/a")).getAttribute("rel").equals("nofollow"));
            else fail("В футере в блоке Партнерам нет атрибута nofollow");
        }
        assertEquals("nofollow", driver.findElement(By.xpath("(//a[contains(text(), 'Войти')])[1]")).getAttribute("rel"));
        assertEquals("nofollow", driver.findElement(By.xpath("(//a[contains(text(), '+ разместить объявление')])[1]")).getAttribute("rel"));
        assertEquals("nofollow", driver.findElement(By.cssSelector(".noo-logo")).getAttribute("rel"));
        openMoskvaMainPage();
        for (int i = 1; i <= 4; i++) {
            if (driver.findElement(By.xpath("(//ul[@class='list-items list-reset'])[2]/li[" + i + "]/a")).getAttribute("rel").equals("nofollow"));
            else fail("В футере в блоке Партнерам нет атрибута nofollow");
        }
        assertEquals("nofollow", driver.findElement(By.xpath("(//a[contains(text(), 'Войти')])[1]")).getAttribute("rel"));
        assertEquals("nofollow", driver.findElement(By.xpath("(//a[contains(text(), '+ разместить объявление')])[1]")).getAttribute("rel"));
        assertEquals("nofollow", driver.findElement(By.cssSelector(".noo-logo")).getAttribute("rel"));
    }

    @Test
    public void seoOnMainSearchPageCar(){
        driver.get("http://www.avtopoisk.ru/car ");
        assertEquals("Купить б/у авто с пробегом в России. Подержанные автомобили и цены в России, недорого", driver.getTitle());  //проврка тайтла страницы
        assertEquals("canonical", driver.findElement(By.xpath("//link[@href='http://www.avtopoisk.ru/car']")).getAttribute("rel"));//проверка canonical
        assertEquals("Продажа б/у авто с пробегом в России. Выгодные цены на автомобили – купить подержанный, б/у автомобиль в России на сайте Avtopoisk.Ru.",
                driver.findElement(By.xpath("//meta[@name='description']")).getAttribute("content") ); //проверка description
        assertEquals("купить авто с пробегом в России, подержанные авто в России, автомобили и цены в России, продажа б/у авто в России",
                driver.findElement(By.xpath("//meta[@name='keywords']")).getAttribute("content")); //проверка keywords
        assertEquals("Купить б/у авто с пробегом в России.", driver.findElement(By.xpath("(//h1)[1]")).getText());//проверка Н1
    }

    @Test
    public void seoOnLocationSearchPageCar(){
        driver.get("http://moskva.avtopoisk.ru/car ");
        assertEquals("Купить б/у авто с пробегом в Москве. Подержанные автомобили и цены в Москве, недорого", driver.getTitle());  //проврка тайтла страницы
        assertEquals("http://moskva.avtopoisk.ru/car", driver.findElement(By.xpath("//link[@rel='canonical']")).getAttribute("href"));//проверка canonical
        assertEquals("Продажа б/у авто с пробегом в Москве. Выгодные цены на автомобили – купить подержанный, б/у автомобиль в Москве на сайте Avtopoisk.Ru.",
                driver.findElement(By.xpath("//meta[@name='description']")).getAttribute("content") ); //проверка description
        assertEquals("купить авто с пробегом в Москве, подержанные авто в Москве, автомобили и цены в Москве, продажа б/у авто в Москве",
                driver.findElement(By.xpath("//meta[@name='keywords']")).getAttribute("content")); //проверка keywords
        assertEquals("Купить б/у авто с пробегом в Москве.", driver.findElement(By.xpath("(//h1)[1]")).getText());//проверка Н1
    }

    @Test
    public void seoOnSearchPageOtherCategories(){
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.get("http://www.avtopoisk.ru/gruzovye");
        assertEquals("Грузовые автомобили с пробегом в России. Купля-продажа грузовых автомобилей б/у: цены, фото", driver.getTitle());
        assertEquals("Возможность купить грузовик с пробегом в России на сайте Avtopoisk.Ru. Продажа подержанных грузовых машин в России: цены, описания и фото смотрите на сайте.",
                driver.findElement(By.xpath("//meta[@name='description']")).getAttribute("content") ); //проверка description
        assertEquals("грузовые автомобили в России, купить грузовик в России, продажа грузовых автомобилей в России",
                driver.findElement(By.xpath("//meta[@name='keywords']")).getAttribute("content")); //проверка keywords
        assertEquals("Продажа грузовых автомобилей в России.", driver.findElement(By.xpath("(//h1)[1]")).getText());//проверка Н1
    }

    @Test
    public void outclicksOnAdvert(){
        String urls[] = new String [6];
        urls[0]= "http://www.avtopoisk.ru/car";
        urls[1]= "http://www.avtopoisk.ru/car/subbody/minivan";
        urls[2]= "http://samara.avtopoisk.ru/car";
        urls[3]= "http://sankt-peterburg.avtopoisk.ru/car/subbody/minivan";
        urls[4]= "http://sankt-peterburg.avtopoisk.ru/car/subbody/vnedorozhnik";
        urls[5]= "http://krasnojarsk.avtopoisk.ru/car";
        for (int i=0; i<=urls.length-1; i++) {
            driver.get(urls[i]);
            assertEquals("nofollow", driver.findElement(By.xpath("(//div[@class='photos-container upload'])[2]/a")).getAttribute("rel"));
            assertEquals("nofollow", driver.findElement(By.xpath("(//div[@class='info'])[2]/a")).getAttribute("rel"));
            assertEquals("nofollow", driver.findElement(By.xpath("(//div[@class='partner'])[2]/a")).getAttribute("rel"));
            assertEquals("nofollow", driver.findElement(By.xpath("(//div[@class='btn btn-md btn-blue'])[2]//..")).getAttribute("rel"));
            String more = (driver.findElement(By.xpath("(//div[@class='description'])[2]")).getText());
            if (more.contains("подробнее")) {
                assertEquals("nofollow", driver.findElement(By.xpath("(//div[@class='description'])[2]/div/a")).getAttribute("rel"));
            }
        }
    }

}
