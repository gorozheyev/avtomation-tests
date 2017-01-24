package com.example.tests;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

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
            else fail("На этой странице не должно быть canonical");
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

}
