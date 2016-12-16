/**
 * Created by gorozheyevd on 07.12.2016.
 */
package com.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.AssertJUnit.fail;

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

//    тeсты для блока "по маркам"
//    ================================================
//    проверка тайтла для марки из блока
    @Test
    public void checkTitleOnMainPage()  {
        openMoskvaMainPage();
        // находим элемент
        WebElement markTitle = driver.findElement(By.xpath("(//a[@class='tile-title'])[2]"));
        // получаем значение из title
        String titleName = markTitle.getAttribute("title");
        if (titleName.equals(""))
            fail("Пропали тайтлы у марок на главной странице");
        driver.findElement(By.xpath("//div[@class='simple-tile simple-tile__sm'][3]")).click();
        driver.navigate().back();
        driver.findElement(By.xpath("(//span[@class='link-more text-uppercase'])[1]")).click();
        driver.findElement(By.xpath("html/body/section/div[2]/div/div[2]/div[2]/div[2]/a")).click();
        String url = driver.getCurrentUrl();
        if (url.equals("http://moskva.avtopoisk.ru/all-brands.html")) {
            driver.navigate().back();
        } else {
            fail("Это не страница всех марок");
        }
    }

//    =========================== тесты для хедера
//    клик по лого и проверка возврата на главную страницу
    @Test
    public void checkCkickOnLogo() throws Exception {
        openMainPage();
        clickOnSearchButton();
        driver.findElement(By.xpath("//a[@class='logo']")).click();
        String mainTitle = driver.getTitle();
        if (mainTitle.equals("Продажа авто в России. Купить автомобиль на «Автопоиск.ру» – авторынок России")){}
        else throw new Exception("Проверить тайтл главной страицы");
        String url = driver.getCurrentUrl();
        if (!url.equals("http://www.avtopoisk.ru/")){
            throw new Exception("Это не главная страница");
        }
    }

//    проверка вывода блоков: марки,объявления,статистика,статьи,услуги на главной
    @Test
    public void presenceElementsOnMainPage() throws Exception {
        openMainPage();
        WebElement seo = driver.findElement(By.xpath("//div[@class='seo text-uppercase']/h1"));
        String seoTextH1 = seo.getText().toLowerCase();
        if (seoTextH1.equals("лучший вариант для поиска авто в россии")){}
        else {
            throw new Exception("Проверить сео текст под хедером на главной");
        }
        presenceFiveBlocksOnMainePage();
        List<WebElement> fiveBlocks =  driver.findElements(By.cssSelector(".content-frame"));
        if (fiveBlocks.size() ==5){}
        else {
            throw new Exception("На главной странице не все блоки");
        }
    }

//      проверка вывода блоков: марки,объявления,статистика,статьи,услуги на главной странице с поддоменом
    @Test
    public void presenceElementsOnMainPageMoskov()throws Exception{
        openMainPage();
        openMoskvaMainPage();
        String mainTitle = driver.getTitle();
        if (mainTitle.equals("Продажа авто в Москве. Купить автомобиль на «Автопоиск.ру» – авторынок Москвы")){}
        else throw new Exception("Проверить тайтл главной страицы Москвы");
        String url = driver.getCurrentUrl();
        if (!url.equals("http://moskva.avtopoisk.ru/")){
            throw new Exception("Это не главная страница");
        }
        WebElement seo = driver.findElement(By.xpath("//div[@class='seo text-uppercase']/h1"));
        String seoTextH1 = seo.getText().toLowerCase();
        if (seoTextH1.equals("лучший вариант для поиска авто в москве")){}
        else {
            throw new Exception("Проверить сео текст под хедером на главной");
        }
        presenceFiveBlocksOnMainePage();
    }

//    ==================== тесты для футера
    @Test
    public void presenceSeoTextInFooter() throws Exception {
        openMainPage();
        String seoText = getFooterSeoText();
        if (!seoText.equals("") & seoText.contains("Наиболее простой и комфортный поиск автомобилей")){}
        else {throw new Exception("Пропали сео тексты в футере");}
        System.out.println(seoText);
    }

//    ======================== тесты для блока другие услуги
//    @Test
//    public void checkTitleAndCklickOnServices(){
//        openMainPage();
//        String titles = driver.findElement(By.xpath("//div[@class='services-container content-panel']")).getText();
//        System.out.println(titles);
//        if (!titles.equals(""));
//        driver.findElement()
//    }
}