/**
 * Created by gorozheyevd on 07.12.2016.
 */
package com.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class TestsForMainPage extends BaseClass{

//   =============================================
//    тесты для фильтра поиска
    @Test
    public void testSearchFilterWithWalue() throws Exception {
        openMainPage();
        MainPageData car = new MainPageData("toyota");
        car.name = "toyota";
        inputValueInSearchField(car);
        clickOnSearchButton();
//     проверяем тайтл открывшейся страницы
        String pageTitle = driver.getTitle();
        if (pageTitle.equals("Купить Toyota б/у в России: цены, фото, характеристики. Продажа Тойота с пробегом"));
        else {
            throw new Exception("Тайтл страницы не совпадает");
        }
    }

    @Test
    public void testWithEmptySearchFilter() throws Exception {
        openMainPage();
        inputValueInSearchField(new MainPageData(""));
        clickOnSearchButton();
    }
//    ================================================
//    тксты для блока "по маркам"
//    проверка тайтла для марки из бблока
    @Test
    public void checkTitleOnMainPage()throws Exception{
        driver.get("http://moskva.avtopoisk.ru/");
        // находим элемент
        WebElement markTitle = driver.findElement(By.xpath("(//a[@title=''])[1]"));
        // получаем значение из title
        String titleName = markTitle.getAttribute("title");
        if (titleName.equals(""))
            throw new Exception("Пропали тайтлы у марок на главной странице");
    }

}
