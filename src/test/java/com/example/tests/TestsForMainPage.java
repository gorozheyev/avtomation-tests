/**
 * Created by gorozheyevd on 07.12.2016.
 */
package com.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.fail;

public class TestsForMainPage extends BaseClass{

//   ======================================================================================= тесты для фильтра поиска
    @Test
    public void testSearchFilterWithWalue() throws Exception {
        openMainPage();
        MainPageData car = new MainPageData();
        car.name = "toyota";
        inputValueInSearchField(car);
        clickOnSearchButton();
//     проверяем тайтл открывшейся страницы
        if (driver.getTitle().equals("Купить Toyota б/у в России: цены, фото, характеристики. Продажа Тойота с пробегом")){}
        else fail("Тайтл страницы не совпадает");
    }

    @Test
    public void testWithEmptySearchFilter() {
        openMainPage();
        inputValueInSearchField(new MainPageData(""));
        clickOnSearchButton();
        if (driver.getTitle().equals("Купить б/у авто с пробегом в России. Подержанные автомобили и цены в России, недорого")){}
        else fail("Тайтл страницы не совпадает");
    }

//    тeсты для блока "по маркам"
//    ============================================================================= проверка тайтла для марки из блока
    @Test
    public void checkTitleOnMainPage()  {
        openMainPage();
        // находим элемент
        WebElement markTitle = driver.findElement(By.xpath("(//a[@class='tile-title'])[2]"));
        // получаем значение из title
        String titleName = markTitle.getAttribute("title");
        if (titleName.equals(""))
            fail("Пропали тайтлы у марок на главной странице");
    }

    @Test
//    клик по марке из блока марок и проверка тайтла у марки на странице 'всех марок'
    public void goToMarkPageAndToAllBrandsPage() {
        openMoskvaMainPage();
        driver.findElement(By.xpath("//div[@class='simple-tile simple-tile__sm'][3]")).click();
        driver.navigate().back();
        driver.findElement(By.xpath("(//span[@class='link-more text-uppercase'])[1]")).click();
        driver.findElement(By.linkText("Все марки")).click();
        String url = driver.getCurrentUrl();
        if (url.equals("http://moskva.avtopoisk.ru/all-brands.html")) {
        } else {fail("Это не страница всех марок");}
        String markTitle = driver.findElement(By.xpath("//div[@class='simple-tile simple-tile__sm'][12]")).getAttribute("title");
        if (markTitle.equals("")){
            fail("Не отображаются тайтлы марок на странице Всех марок");
        }
    }

//    ==================================================================================== тесты для хедера
//    клик по лого и проверка возврата на главную страницу
    @Test
    public void checkCkickOnLogo() throws Exception {
        openMoskvaMainPage();
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

//    ====================================================================================== тесты для футера
    @Test
    public void presenceSeoTextInFooter() throws Exception {
        openMainPage();
        String seoText = getFooterSeoText();
        if (!seoText.equals("") & seoText.contains("Наиболее простой и комфортный поиск автомобилей")){}
        else {throw new Exception("Пропали сео тексты в футере");}
        System.out.println(seoText);
    }

//    ================================================================================ тесты для блока другие услуги
    @Test
//    проверка тайтлов + клик по разделам
    public void checkTitleAndCklickOnServices(){
        openMainPage();
        checkTitleInBlockOtherServices();
        clickOnElementsFromOtherServices();
        openMoskvaMainPage();
        checkTitleInBlockOtherServices();
        clickOnElementsFromOtherServices();
    }

//    ================================================================================== тесты для блока топ категорий
    @Test
//    проверка тайтлов + клик по категориям
    public void checkTitlesTopCategory(){
        openMainPage();
        assertEquals("Продажа легковых авто в России", driver.findElement(By.xpath("(//a[@class='title-item'])[1]")).getAttribute("title"));
        driver.findElement(By.xpath("(//a[@class='title-item'])[1]")).click();
        driver.findElement(By.cssSelector(".listing-item-flex"));
        driver.navigate().back();
        String titleTopCategory = driver.findElement(By.xpath("(//a[@class='title-item'])[2]")).getAttribute("title");
        if (titleTopCategory.contains("в России")){}
        else {fail("Проверить тайтлы в блоке топ категорий на главной Москвы");}
        driver.findElement(By.xpath("(//a[@class='title-item'])[2]")).click();
        driver.findElement(By.cssSelector(".listing-item-flex"));
    }

    @Test
    public void checkTitlesTopCategoryMoscow(){
        openMoskvaMainPage();
        assertEquals("Продажа легковых авто в Москве", driver.findElement(By.xpath("(//a[@class='title-item'])[1]")).getAttribute("title"));
        driver.findElement(By.xpath("(//a[@class='title-item'])[1]")).click();
        driver.findElement(By.cssSelector(".listing-item-flex"));
        driver.navigate().back();
//        assertEquals("Продажа спецтехники в Москве", driver.findElement(By.xpath("(//a[@class='title-item'])[2]")).getAttribute("title"));
        String titleTopCategory = driver.findElement(By.xpath("(//a[@class='title-item'])[2]")).getAttribute("title");
        if (titleTopCategory.contains("в Москве")){}
            else {fail("Проверить тайтлы в блоке топ категорий на главной Москвы");}
        driver.findElement(By.xpath("(//a[@class='title-item'])[2]")).click();
        driver.findElement(By.cssSelector(".listing-item-flex"));
    }

//    ===========================================================================Тесты для блока Похожие объявления
    @Test
    public void checkClickAndTitleOnLastAdverts(){
        openMainPage();
        String advertTitle = driver.findElement(By.xpath("//a[@class='link-tile'][1]")).getAttribute("title");
//        проверяем тайтлы у похожих объявлений
        if (advertTitle.equals(""))
            fail("Пропали тайтлы в блоке последних объявлений на лавной странице");
        String advertAlt = driver.findElement(By.xpath("html/body/section/div[2]/div/div[4]/div[2]/div/div/div/a[1]/div[1]/div/img[1]")).getAttribute("alt");
//        проверяем alt у картинок
        if (advertAlt.equals(""))
            fail("В похожих объявлениях у картинок пропали ALTы");
        driver.findElement(By.xpath("//a[@class='link-tile'][1]")).click();
        String adpageUrl = driver.getCurrentUrl();
//        проверяем что при клике мы пореходим на страницу адпейдж
        if (adpageUrl.contains("avtopoisk.ru/search/adpage/"));
        else fail("Это не страница адпейдж, проверить клик по последним объявлениям");
        driver.navigate().back();
        List<WebElement> listOfTitles = driver.findElements(By.xpath("//div[@class='links-tiles-container']/div/a"));
        if (listOfTitles.size() <= 15);
            else fail("Похожих объявлений должно быть не больше 15");
    }

    @Test
    public void checkTitlesAndGoToAllCityesPage(){
        openMainPage();
        driver.findElement(By.cssSelector(".caret-dd")).click();
        String cityTitle = driver.findElement(By.xpath("//div[@class='cities-list margin-t05']/ul/li[3]/a")).getAttribute("title");
        if (cityTitle.contains("Продажа автомобилей в"));
        else fail("Пропали тайтлы в выпадающем списке локаций на главной");
        assertEquals("Продажа автомобилей по городам ", driver.findElement(By.linkText("Все города")).getAttribute("title"));
        driver.findElement(By.linkText("Все города")).click();
        assertEquals("http://www.avtopoisk.ru/all-cities.html", driver.getCurrentUrl());
        assertEquals("ПРОДАЖА АВТОМОБИЛЕЙ В ГОРОДАХ", driver.findElement(By.xpath("//h2[@class='h3']")).getText());
    }

//    ============================================================================ тесты для блока 'тест-драйвы'
    @Test
    public void checkClickAndTitlesInBlockTestDrivesOnMainPage(){
        openMainPage();
        assertEquals("Тест-драйвы", driver.findElement(By.xpath("//div[@id='panel-testdrives']/a[1]")).getAttribute("title"));
        clickOnFirstTestDriveAndGoBack();
        clickOnSecondTestDriveAndGoBack();
        clickOnThirdTestDriveAndGoBack();
        clickOnSeeMoreTestDrivesButton();
        assertEquals("http://www.avtopoisk.ru/testdrive.html", driver.getCurrentUrl());
        assertEquals("Тест-драйвы автомобилей на Avtopoisk.ru - каталог тест-драйвов авто.", driver.getTitle());
    }

    @Test
    public void checkClickAndTitlesInBlockCatalogOnMainPage(){
        openMainPage();
        assertEquals("каталог", driver.findElement(By.xpath("((//div[@id='panel-testdrives'])[2])/a[1]")).getAttribute("title"));
        clickOnFirstCatalogModelAndGoBack();
        clickOnSecondCatalogModelAndGoBack();
        clickOnThirdCatalogModelAndGoBack();
        clickSeeMoreCatalogButton();
        assertEquals("http://www.avtopoisk.ru/catalog.html", driver.getCurrentUrl());
        assertEquals("Каталог автомобилей по маркам на Avtopoisk.Ru в России.", driver.getTitle());
    }

}
