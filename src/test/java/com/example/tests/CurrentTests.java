/**
 * Created by gorozheyevd on 07.12.2016.
 */
package com.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class CurrentTests extends BaseClass{

    @Test
    public void openStoOnMainPage() throws InterruptedException {
        driver.get("http://avtopoisk.ru/");
        WebElement menu = driver.findElement(By.id("btn-nav-categories"));
        menu.click();
        WebElement sto = driver.findElement(By.xpath("//a[@title='СТО в России']"));
        sto.click();
        driver.findElement(By.linkText("Мультибрендовый сервис «Автомоторс КВС»")).click();
        Thread.sleep(2000);
    }

    @Test
    public void checkCountAdverts () throws Exception {
        openMainPage();
        clickOnSearchButton();
        WebElement counter = driver.findElement(By.xpath("//*[@class='subtitle text-gray']"));
        String text = counter.getText();
        String counterWalue = text.substring("Найдено ".length(), text.length() - "проедложений".length());
        counterWalue = counterWalue.replaceAll(" ", "");
        int counters;
        counters = Integer.parseInt(counterWalue);
        if (counters > 390000){
            System.out.println("Объявлений на выдаче достаточно - "+ counters + " штук.");
        }
            else {
            throw new Exception("Мало объявлений на выдаче");
        }
    }
}