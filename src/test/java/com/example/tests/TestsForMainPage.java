/**
 * Created by gorozheyevd on 07.12.2016.
 */
package com.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.fail;


public class TestsForMainPage extends BaseClass{

//   ======================================================================================= тесты для фильтра поиска
    @Test
    public void testSearchFilterWithWalue() throws Exception {
        openMainPage();
        MainPageData car = new MainPageData();
        car.name = "toyota";
        inputValueInSearchField(car);
        clickOnSearchButtonMaiPage();
//     проверяем тайтл открывшейся страницы
        if (driver.getTitle().equals("Купить Toyota в России. Продажа автомобилей Тойота по низкой цене")){}
        else fail("Тайтл страницы не совпадает");
    }

    @Test
    public void testWithEmptySearchFilter() {
        openMainPage();
        inputValueInSearchField(new MainPageData(""));
        clickOnSearchButtonMaiPage();
        if (driver.getTitle().equals("Купить авто в России. Продажа автомобилей по низкой цене")){}
        else fail("Тайтл страницы не совпадает");
    }

    @Test
    public void testSearchFieldWithPressedTabs(){
        openMoskvaMainPage();
        MainPageData car = new MainPageData();
        car.name = "ford";
        for (int i=2; i<=3; i++) {
            inputValueInSearchField(car);
            driver.findElement(By.xpath("//li[@role='presentation'][" + i + "]")).click();
            clickOnSearchButtonMaiPage();
            driver.findElement(By.cssSelector("#searchbar"));
            String url = driver.getCurrentUrl();
            if (url.contains("moskva.avtopoisk.ru/car/ford/new") | url.contains("moskva.avtopoisk.ru/car/ford/old"));
            else fail("Открылась не та страница проверить работу табов в главном фильтре на главной странице Москвы");
            driver.navigate().back();
        }
        driver.findElement(By.xpath("//li[@role='presentation'][4]")).click();
        clickOnSearchButtonMaiPage();
        assertEquals("https://www.avtopoisk.ru/catalog", driver.getCurrentUrl());
        assertEquals("каталог по маркам",driver.findElement(By.xpath("//h2[@class='h3']")).getText().toLowerCase());
    }

    @Test
    public void searchFieldWithPressedTabsCatalog() throws InterruptedException {
        openMainPage();
        MainPageData car = new MainPageData();
        car.name = "bentley";
        driver.findElement(By.xpath("//li[@role='presentation'][4]")).click();
        inputValueInSearchField(car);
        Thread.sleep(1500);
        driver.findElement(By.xpath("//ul[@class='list-reset']/li[3]")).click();
        clickOnSearchButtonMaiPage();
        driver.findElement(By.xpath("//h2[contains(text(), 'каталог моделей   Bentley')]"));
    }

//    ============================================================================= тeсты для блока "по маркам"
//  проверка тайтла для марки из блока
    @Test
    public void checkTitleOnMainPage()  {
        openMainPage();
        // находим элемент
        WebElement markTitle = driver.findElement(By.xpath("(//a[@class='tile-title'])[2]"));
        // получаем значение из title
        String titleName = markTitle.getAttribute("title");
        if (titleName.equals(""))
            fail("Пропали тайтлы у марок на главной странице");
        assertTrue(!driver.findElement(By.cssSelector(".simple-tile.simple-tile__sm:nth-of-type(4) img")).getAttribute("alt").equals(""),
                "There is no Alt for the picture");
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
        if (url.equals("https://moskva.avtopoisk.ru/all-brands.html")) {
        } else {fail("Это не страница всех марок");}
        String markTitle = driver.findElement(By.xpath("//div[@class='simple-tile simple-tile__sm'][12]/a")).getAttribute("title");
        if (markTitle.equals("")){
            fail("Не отображаются тайтлы марок на странице Всех марок");
        }
        assertTrue(!driver.findElement(By.cssSelector(".simple-tile.simple-tile__sm:nth-of-type(5) img")).getAttribute("alt").equals(""),
                "There is no Alt for the picture");
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
        List<WebElement> blocks =  driver.findElements(By.cssSelector(".content-frame"));
        if (blocks.size() ==6);
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
        if (!url.equals("https://moskva.avtopoisk.ru/")){
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
        if (!seoText.equals("") & seoText.contains("Наиболее простой и комфортный поиск автомобилей"));
        else throw new Exception("Пропали сео тексты в футере");
    }

    @Test
    public void blockPartners(){
        openMainPage();
        driver.findElement(By.cssSelector("div.col-md-5"));
        assertEquals("Условия пользования", driver.findElement(By.xpath("(//ul[@class='list-items list-reset'])[2]/li[1]/a")).getAttribute("title"));
        driver.findElement(By.xpath("(//ul[@class='list-items list-reset'])[2]/li[1]")).click();
        assertEquals("https://www.avtopoisk.ru/page/terms.html", driver.getCurrentUrl());
        driver.findElement(By.cssSelector(".content-cell.table-cell"));
        driver.navigate().back();
        driver.findElement(By.xpath("(//ul[@class='list-items list-reset'])[2]/li[2]")).click();
        assertEquals("https://www.avtopoisk.ru/page/policy.html", driver.getCurrentUrl());
        driver.findElement(By.cssSelector(".content-cell.table-cell"));
        driver.navigate().back();
        driver.findElement(By.xpath("(//a[contains(text(), 'Обратная связь')])[2]")).click();
        assertEquals("https://www.avtopoisk.ru/feedback.html", driver.getCurrentUrl());
        driver.findElement(By.cssSelector(".content-cell.table-cell"));
        driver.navigate().back();
        driver.findElement(By.xpath("(//a[contains(text(), 'Для партнеров')])")).click();
        assertEquals("https://www.avtopoisk.ru/partner/login.html", driver.getCurrentUrl());
        driver.findElement(By.cssSelector(".content-cell.table-cell"));
    }

    @Test
    public void presenceIconsOfSocialNetworks() throws InterruptedException {
        openMainPage();
        driver.findElement(By.cssSelector(".col-md-3.col-social"));
        driver.findElement(By.xpath("(//a[@class='soc-link soc-link-vk'])[2]")).click();
        Thread.sleep(3000);
        if (driver.getCurrentUrl().contains("vk.com/"));
            else fail("Это не страница Avtopoisk в VK - "+ driver.getCurrentUrl());
        driver.navigate().back();
        driver.findElement(By.xpath("(//a[@class='soc-link soc-link-ok'])[2]")).click();
        Thread.sleep(2500);
        if (driver.getCurrentUrl().contains("odnoklassniki.ru"));
        else fail("Это не страница Avtopoisk в ОК");
        driver.navigate().back();
        driver.findElement(By.xpath("(//a[@class='soc-link soc-link-fb'])[2]")).click();
        if (driver.getCurrentUrl().contains("https://www.facebook.com/AvtoPoisk.ru.Auto"));
        else fail("Это не страница Avtopoisk в FB");
        driver.navigate().back();
        driver.findElement(By.xpath("(//a[@class='soc-link soc-link-gp'])[2]")).click();
        Thread.sleep(3500);
        if (driver.getCurrentUrl().contains("https://plus.google.com/+AvtopoiskRu"));
        else fail("Это не страница Avtopoisk в Гугл");
    }

    @Test
    public void checkTopCategoryInFooter(){
        openMainPage();
        driver.findElement(By.xpath("(//div[@class='col-md-4'])[1]"));
        assertEquals("Продажа легковых авто в России", driver.findElement(By.xpath("//ul[@class='list-items list-reset']/li[1]/a")).getAttribute("title"));
        String namesOfCategories = driver.findElement(By.xpath("(//ul[@class='list-items list-reset'])[1]")).getText();
//        сравниваем есть ли такие категории последовательно 'нужно подумать как сделать сравнение если категории меняются местами (из-за каунтеров)'
        if (namesOfCategories.contains("Легковые авто\nГрузовые авто\nСпецтехника\nАвтобусы\nПрицепы\n") ||
                namesOfCategories.contains("Легковые авто\nСпецтехника\nГрузовые авто\nАвтобусы\nПрицепы\n") ||
                namesOfCategories.contains("Легковые авто\nГрузовые авто\nСпецтехника\nАвтобусы\nМото\n") ||
                namesOfCategories.contains("Легковые авто\nГрузовые авто\nМото\nАвтобусы\nСпецтехника\n")||
                namesOfCategories.contains("Легковые авто\nГрузовые авто\nАвтобусы\nМото\nСпецтехника\n")||
                namesOfCategories.contains("Легковые авто\nГрузовые авто\nМото\nСпецтехника\nАвтобусы\n")||
                namesOfCategories.contains("Легковые авто\nГрузовые авто\nАвтобусы\nСпецтехника\nПрицепы\n")||
                namesOfCategories.contains("Легковые авто\nГрузовые авто\nСпецтехника\nПрицепы\nАвтобусы\n"));
        else fail("Проверить категории авто в футере");
        List<WebElement> categories = driver.findElements(By.xpath("(//ul[@class='list-items list-reset'])[1]/li"));
        for (int i = 1; i<=categories.size(); i++){
            driver.findElement(By.xpath("((//ul[@class='list-items list-reset'])[1]/li/a)[" + i + "]")).click();
            driver.findElement(By.cssSelector("#searchbar"));
            if(i < categories.size()) {
                driver.navigate().back();
            } else break;
        }
    }

    @Test
    public void clickOnTextLogo(){
        openMainPage();
        assertEquals("javascript:void(0)", driver.findElement(By.cssSelector(".logo-footer.link.text-uppercase")).getAttribute("href"));
        openMoskvaMainPage();
        assertEquals("javascript:void(0)", driver.findElement(By.cssSelector(".logo-footer.link.text-uppercase")).getAttribute("href"));
        driver.findElement(By.xpath("//a[@class='logo']")).click();
        assertEquals("https://www.avtopoisk.ru/", driver.getCurrentUrl());
        assertEquals("javascript:void(0);", driver.findElement(By.xpath("//a[@class='logo']")).getAttribute("href"));
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

    @Test
    public void checkUpFines(){
        openMainPage();
        driver.findElement(By.xpath("//div[contains(text(), 'Проверка штрафов')]")).click();
        checkFineUrlAndTabFine();
        driver.navigate().back();
        driver.findElement(By.id("btn-nav-categories")).click();
        driver.findElement(By.xpath("(//a[contains(text(), 'Проверка штрафов')])[1]")).click();
        checkFineUrlAndTabFine();
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
        assertEquals("https://www.avtopoisk.ru/all-cities", driver.getCurrentUrl());
        assertEquals("ПРОДАЖА АВТОМОБИЛЕЙ В ГОРОДАХ", driver.findElement(By.xpath("//h2[@class='h3']")).getText());
    }

//    ============================================================================ тесты для блока 'тест-драйвы'
    @Test
    public void checkClickAndTitlesInBlockTestDrivesOnMainPage(){
        openMainPage();
        assertTrue(!driver.findElement(By.xpath("//div[@id='panel-testdrives']/a[1]")).getAttribute("title").equals(""),
                "Проверить тайтлы в блоке Тест-драйвы");
        clickOnFirstTestDriveAndGoBack();
        clickOnSecondTestDriveAndGoBack();
        clickOnThirdTestDriveAndGoBack();
        clickOnSeeMoreTestDrivesButton();
        assertEquals("https://www.avtopoisk.ru/testdrive", driver.getCurrentUrl());
        assertEquals("Тест-драйвы автомобилей на Avtopoisk.ru - каталог тест-драйвов авто.", driver.getTitle());
    }

    @Test
    public void checkClickAndTitlesInBlockCatalogOnMainPage(){
        openMainPage();
        assertTrue(!driver.findElement(By.xpath("((//div[@id='panel-testdrives'])[2])/a[1]")).getAttribute("title").equals(""),
                "Проверить тайтлы в блоке Каталог");
        clickOnFirstCatalogModelAndGoBack();
        clickOnSecondCatalogModelAndGoBack();
        clickOnThirdCatalogModelAndGoBack();
        clickSeeMoreCatalogButton();
        assertEquals("https://www.avtopoisk.ru/catalog", driver.getCurrentUrl());
        assertEquals("Каталог автомобилей по маркам на Avtopoisk.Ru", driver.getTitle());
    }

//    =================================================================================== тесты для блока новостей
    @Test
    public void checkTitleAndClickOnArticles(){
        openMainPage();
        driver.findElement(By.xpath("//div[@class='wide-tiles slider-view']/a[1]")).click();
        if (driver.getCurrentUrl().contains("https://www.avtopoisk.ru/news/article/id/"));
        else fail("Это не страница новости");
        driver.findElement(By.cssSelector(".content-frame.clearfix"));
        driver.navigate().back();
        String articleTitle = driver.findElement(By.xpath("//div[@class='wide-tiles slider-view']/a[2]")).getAttribute("title");
        if (articleTitle.equals(""))
        fail("Не отображаются тайтлы для новостей из блока 'Статьи' на главной");
        driver.findElement(By.xpath("//span[contains(text(), 'еще статьи')]")).click();
        assertEquals("https://www.avtopoisk.ru/news", driver.getCurrentUrl());
    }

//    ================================================================================= тесты для блока статистики
    @Test
    public void choiceDifferentGrafics() throws InterruptedException {
        openMainPage();
        for (int i=0; i<=3; i++) {
            driver.findElement(By.xpath("//div[@class='arrows_container']/a[1]")).click();
            Thread.sleep(1500);
        }
            for (int n=0; n<=2; n++){
                driver.findElement(By.xpath("//div[@class='arrows_container']/a[2]")).click();
                Thread.sleep(1500);
            }
            driver.findElement(By.cssSelector(".btn-chooser-dd.icon.icon-list-flat")).click();
        String graficNameInList = driver.findElement(By.xpath("//ul[@class='nav nav-tabs nt__inline nt__flat nt__lg tabs__caption']/li[4]")).getText();
        driver.findElement(By.xpath("//ul[@class='nav nav-tabs nt__inline nt__flat nt__lg tabs__caption']/li[4]")).click();
        String graficName = driver.findElement(By.xpath("(//h2[@class='h4 text-bold'])[4]")).getText();
        assertEquals(graficNameInList, graficName);
        driver.findElement(By.cssSelector(".btn-chooser-dd.icon.icon-list-flat")).click();
        String otherGraficNameInList = driver.findElement(By.xpath("//ul[@class='nav nav-tabs nt__inline nt__flat nt__lg tabs__caption']/li[5]")).getText();
        driver.findElement(By.xpath("//ul[@class='nav nav-tabs nt__inline nt__flat nt__lg tabs__caption']/li[5]")).click();
        String otherGraficName = driver.findElement(By.xpath("(//h2[@class='h4 text-bold'])[5]")).getText();
        assertEquals(otherGraficNameInList, otherGraficName);
    }

    //    ==================================================================================== тесты для хедера
//    клик по лого и проверка возврата на главную страницу
    @Test
    public void checkCkickOnLogo() throws Exception {
        openMoskvaMainPage();
        clickOnSearchButtonMaiPage();
        driver.findElement(By.xpath("//a[@class='header-nav__logo']")).click();
        String mainTitle = driver.getTitle();
        if (mainTitle.equals("Продажа авто в России. Купить автомобиль на «Автопоиск.ру» – авторынок России")){}
        else throw new Exception("Проверить тайтл главной страицы");
        String url = driver.getCurrentUrl();
        if (!url.equals("https://www.avtopoisk.ru/")){
            throw new Exception("Это не главная страница");
        }
    }

    @Test
    public void ckickOnButtonAddAdvert(){
        openMainPage();
        assertEquals("Разместить объявление на Avtopoisk", driver.findElement(By.xpath("(//a[contains(text(), '+ разместить объявление')])[1]")).getAttribute("title"));
        driver.findElement(By.xpath("(//a[contains(text(), '+ разместить объявление')])[1]")).click();
        assertEquals("https://www.avtopoisk.ru/proposition.html", driver.getCurrentUrl());
        assertEquals("Подать объявление о продаже авто в России, разместить объявление бесплатно на Avtopoisk.ru", driver.getTitle());
        driver.findElement(By.cssSelector(".content-frame-caption.mode-left>h2"));
    }

    @Test
    public void chooseLocationInHeder(){
        openMainPage();
//        выбор локации из списка
        driver.findElement(By.cssSelector(".caret-dd")).click();
        WebElement samara = driver.findElement(By.linkText("Самара"));
        assertEquals("Продажа автомобилей в Самаре", samara.getAttribute("title"));
        samara.click();
        assertEquals("https://samara.avtopoisk.ru/", driver.getCurrentUrl());
//        выбор локации путем ввода первых трех символов
        driver.findElement(By.cssSelector(".caret-dd")).click();
        driver.findElement(By.xpath("//input[@placeholder='Введите город']")).sendKeys("нов");
        driver.findElement(By.xpath("//div[@class='dropdown-menu']/ul/li[1]")).click();
        assertEquals("https://novosibirsk.avtopoisk.ru/", driver.getCurrentUrl());
    }

}
