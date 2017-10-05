package com.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 * Created by gorozheyevd on 23.01.2017.
 */
public class SeoRequirementsTests extends BaseClass {

    @Test
    public void seoMetaOnMainPage() {
        openMainPage();
//        проверка descriptions и keywords на гловной странице
        String descriptions = driver.findElement(By.xpath("//meta[@name='description']")).getAttribute("content");
        assertEquals("Avtopoisk.Ru - выгодная купля-продажа авто в России. Покупка автомобиля в России, удобный автопоиск. Каталог автомобилей.", descriptions);
        String keywords = driver.findElement(By.xpath("//meta[@name='keywords']")).getAttribute("content");
        assertEquals("продажа авто в России, купить авто в России, покупка автомобиля в России, авторынок в России", keywords);
        if (driver.findElements(By.xpath("//link[@rel='canonical']")).size() == 0) ;
        else fail("На этой странице не должно быть тега canonical");
    }

    @Test
    public void nofollowOnMainPage() {
        openMainPage();
        for (int i = 1; i <= 4; i++) {
            if (driver.findElement(By.xpath("(//ul[@class='list-items list-reset'])[2]/li[" + i + "]/a")).getAttribute("rel").equals("nofollow"))
                ;
            else fail("В футере в блоке Партнерам нет атрибута nofollow");
        }
        assertEquals("nofollow", driver.findElement(By.xpath("(//a[contains(text(), 'Войти')])[1]")).getAttribute("rel"));
        assertEquals("nofollow", driver.findElement(By.xpath("(//a[contains(text(), '+ разместить объявление')])[1]")).getAttribute("rel"));
        assertEquals("nofollow", driver.findElement(By.cssSelector(".noo-logo")).getAttribute("rel"));
        openMoskvaMainPage();
        for (int i = 1; i <= 4; i++) {
            if (driver.findElement(By.xpath("(//ul[@class='list-items list-reset'])[2]/li[" + i + "]/a")).getAttribute("rel").equals("nofollow"))
                ;
            else fail("В футере в блоке Партнерам нет атрибута nofollow");
        }
        assertEquals("nofollow", driver.findElement(By.xpath("(//a[contains(text(), 'Войти')])[1]")).getAttribute("rel"));
        assertEquals("nofollow", driver.findElement(By.xpath("(//a[contains(text(), '+ разместить объявление')])[1]")).getAttribute("rel"));
        assertEquals("nofollow", driver.findElement(By.cssSelector(".noo-logo")).getAttribute("rel"));
    }

    @Test
    public void seoOnMainSearchPageCar() {
        driver.get("http://www.avtopoisk.ru/car ");
        assertEquals("Купить авто в России. Продажа автомобилей по низкой цене", driver.getTitle());  //проврка тайтла страницы
        assertEquals("canonical", driver.findElement(By.xpath("//link[@href='http://www.avtopoisk.ru/car']")).getAttribute("rel"));//проверка canonical
        assertEquals("Легковые автомобили в России. Выгодная купля-продажа легковых авто в России",
                driver.findElement(By.xpath("//meta[@name='description']")).getAttribute("content")); //проверка description
        assertEquals("", driver.findElement(By.xpath("//meta[@name='keywords']")).getAttribute("content")); //проверка keywords
        assertEquals("Продажа авто в России.", driver.findElement(By.xpath("(//h1)[1]")).getText());//проверка Н1
    }

    @Test
    public void seoOnLocationSearchPageCar() {
        driver.get("http://moskva.avtopoisk.ru/car ");
        assertEquals("Купить авто в Москве. Продажа автомобилей по низкой цене", driver.getTitle());  //проврка тайтла страницы
        assertEquals("http://moskva.avtopoisk.ru/car", driver.findElement(By.xpath("//link[@rel='canonical']")).getAttribute("href"));//проверка canonical
        assertEquals("Легковые автомобили в Москве. Выгодная купля-продажа легковых авто в Москве",
                driver.findElement(By.xpath("//meta[@name='description']")).getAttribute("content")); //проверка description
        assertEquals("", driver.findElement(By.xpath("//meta[@name='keywords']")).getAttribute("content")); //проверка keywords
        assertEquals("Продажа авто в Москве.", driver.findElement(By.xpath("(//h1)[1]")).getText());//проверка Н1
    }

    @Test
    public void seoOnSearchPageOtherCategories() {
        driver.get("http://www.avtopoisk.ru/gruzovye");
        assertEquals("Купить грузовик в России. Продажа грузовых авто по низкой цене", driver.getTitle());
        assertEquals("Грузовые авто в России. Выгодная купля-продажа грузовиков в России ",
                driver.findElement(By.xpath("//meta[@name='description']")).getAttribute("content")); //проверка description
        assertEquals("", driver.findElement(By.xpath("//meta[@name='keywords']")).getAttribute("content")); //проверка keywords
        assertEquals("Продажа грузовиков в России.", driver.findElement(By.xpath("(//h1)[1]")).getText());//проверка Н1
    }

    @Test
    public void outclicksOnAdvert() {
        String urls[] = new String[6];
        urls[0] = "http://www.avtopoisk.ru/car";
        urls[1] = "http://www.avtopoisk.ru/car/subbody/minivan";
        urls[2] = "http://samara.avtopoisk.ru/car";
        urls[3] = "http://sankt-peterburg.avtopoisk.ru/car/subbody/minivan";
        urls[4] = "http://sankt-peterburg.avtopoisk.ru/car/subbody/vnedorozhnik";
        urls[5] = "http://novosibirsk.avtopoisk.ru/car";
        for (int i = 0; i < urls.length; i++) {
            driver.get(urls[i]);
                for (int j = 5; j < 7; j++) {
                    if (!driver.findElement(By.xpath("(//div[@class='partner']/a)["+ j+ "]")).getText().isEmpty()) {
                        assertEquals("nofollow", driver.findElement(By.xpath("(//div[@class='photos-container upload']/a)[" + j + "]")).getAttribute("rel"),
                                "На странице "+ driver.getCurrentUrl()+ " у объявления №"+ j + " нет атрибута nofollow");
                        assertEquals("nofollow", driver.findElement(By.xpath("(//div[@class='info']/a)[" + j + "]")).getAttribute("rel"),
                                "На странице "+ driver.getCurrentUrl()+ " у объявления №"+ j + " нет атрибута nofollow");
                        assertEquals("nofollow", driver.findElement(By.xpath("(//div[@class='partner']/a)[" + j + "]")).getAttribute("rel"),
                                "На странице "+ driver.getCurrentUrl()+ " у объявления №"+ j + " нет атрибута nofollow");
                        assertEquals("nofollow", driver.findElement(By.xpath("(//div[@class='btn btn-md btn-blue'])[" + j + "]//..")).getAttribute("rel"),
                                "На странице "+ driver.getCurrentUrl()+ " у объявления №"+ j + " нет атрибута nofollow");
                    }
                        WebElement goToAdpage = driver.findElement(By.xpath("//span[@class='link']//.."));
                        if (goToAdpage.getAttribute("href").toString().contains("avtopoisk.ru/search/adpage/")) ;
                        else
                            fail("На странице " + driver.getCurrentUrl() + " ссылка подробнее должна вести на адпейдж");
                        assertEquals("_blank", goToAdpage.getAttribute("target"));
                }
        }
    }

    @Test
    public void seoForCastomLinksInTabs() {
        openSearchPageCar("www", "");
        openCustomCategoryInTabs(); //по типам авто
        List<WebElement> customLinks = driver.findElements(By.xpath("(//div[@id='propositionsByCities'])[3]//a"));
        for (WebElement link : customLinks) {
            if (link.getAttribute("title").equals(""))
                fail("Нет тайтлов в в кастом линках 'по типам авто'");
        }
        for (int i = 1; i <= customLinks.size(); i++) {
            driver.findElement(By.xpath("((//div[@id='propositionsByCities'])[3]/div/ul/li/a)["+ i+ "]")).click();
            assertEquals(driver.getCurrentUrl(), driver.findElement(By.xpath("//link[@rel='canonical']")).getAttribute("href"));
            driver.findElement(By.cssSelector("#searchbar"));
            if (i != customLinks.size()) {
                driver.navigate().back();
                openCustomCategoryInTabs();
            } else break;
        }
    }

    @Test
    public void seoMetaOnCustomLinkPages() {
//        для кастом линок 'каталог автомобилей по странам'
        driver.get("http://www.avtopoisk.ru/usa-cars");
        assertEquals(driver.getCurrentUrl(), driver.findElement(By.xpath("//link[@rel='canonical']")).getAttribute("href"));
        assertEquals("Американские автомобили в России. Купить авто из Америки, американские марки автомобилей: цены, фото", driver.getTitle());
        assertEquals("Продажа американских автомобилей в России. Выгодно купить авто из Америки в России можно на сайте Avtopoisk.Ru – каталог объявлений американских автомобилей.",
                driver.findElement(By.xpath("//meta[@name='description']")).getAttribute("content")); //проверка description
        assertEquals("купить авто из Америки в России, американские автомобили в России, продажа американских авто в России",
                driver.findElement(By.xpath("//meta[@name='keywords']")).getAttribute("content")); //проверка keywords
        assertEquals("Американские автомобили в России.", driver.findElement(By.xpath("(//h1)[1]")).getText());
//        для кастом линок 'каталог машин по типам'
        driver.get("http://www.avtopoisk.ru/sport-cars");
        assertEquals(driver.getCurrentUrl(), driver.findElement(By.xpath("//link[@rel='canonical']")).getAttribute("href"));
        assertEquals("Спортивные автомобили в России. Продажа спортивных б/у автомобилей в России: фото, цены", driver.getTitle());
        assertEquals("Выгодно купить спортивный б/у автомобиль в России можно на сайте «Автопоиск». Продажа спортивных авто в России по доступным ценам: описание и фото смотрите на сайте.",
                driver.findElement(By.xpath("//meta[@name='description']")).getAttribute("content")); //проверка description
        assertEquals("продажа спортивных автомобилей бу в России, спортивные модели автомобилей в России, марки спортивных автомобилей в России",
                driver.findElement(By.xpath("//meta[@name='keywords']")).getAttribute("content")); //проверка keywords
        assertEquals("Продажа спортивных автомобилей в России.", driver.findElement(By.xpath("(//h1)[1]")).getText());
//        для кастом линок 'каталог машин по цене'
        driver.get("http://www.avtopoisk.ru/cheap-cars");
        assertEquals(driver.getCurrentUrl(), driver.findElement(By.xpath("//link[@rel='canonical']")).getAttribute("href"));
        assertEquals("Самые дешевые автомобили в России. Купить автомобиль дешево в России: цены, фото", driver.getTitle());
        assertEquals("Продажа дешевых автомобилей в России. Дешевые авто с пробегом в России смотрите на сайте Avtopoisk.Ru.",
                driver.findElement(By.xpath("//meta[@name='description']")).getAttribute("content")); //проверка description
        assertEquals("дешевые автомобили в России, купить автомобиль дешево в России, продажа дешевых авто в России",
                driver.findElement(By.xpath("//meta[@name='keywords']")).getAttribute("content")); //проверка keywords
        assertEquals("Продажа дешевых автомобилей в России.", driver.findElement(By.xpath("(//h1)[1]")).getText());
    }

    @Test
    public void markPageSeo() {
        driver.get("http://www.avtopoisk.ru/car/chevrolet");
        assertEquals(driver.getCurrentUrl(), driver.findElement(By.xpath("//link[@rel='canonical']")).getAttribute("href"));
        assertEquals("Купить Chevrolet в России. Продажа автомобилей Шевроле по низкой цене", driver.getTitle());
        assertEquals("Автомобили Chevrolet в России. Выгодная купля-продажа Шевроле в России",
                driver.findElement(By.xpath("//meta[@name='description']")).getAttribute("content")); //проверка description
        assertEquals("", driver.findElement(By.xpath("//meta[@name='keywords']")).getAttribute("content")); //проверка keywords
        assertEquals("Продажа Chevrolet (Шевроле) в России.", driver.findElement(By.xpath("(//h1)[1]")).getText());
    }

    @Test
    public void markPlusAuxiliaryFiltersPageSeo() {
        driver.get("http://www.avtopoisk.ru/car/dodge/subbody/vnedorozhnik/transmission/auto");
        assertEquals(driver.getCurrentUrl(), driver.findElement(By.xpath("//link[@rel='canonical']")).getAttribute("href"));
        assertEquals("Купить Dodge в кузове Внедорожник с автоматической коробкой передач в России", driver.getTitle());
        assertEquals("Dodge (Додж) в кузове Внедорожник с автоматической коробкой передач в России. " +
                        "Выгодная купля-продажа Dodge (Додж) в кузове Внедорожник с автоматической коробкой передач в России",
                driver.findElement(By.xpath("//meta[@name='description']")).getAttribute("content")); //проверка description
        assertEquals("", driver.findElement(By.xpath("//meta[@name='keywords']")).getAttribute("content")); //проверка keywords
        assertEquals("Продажа Dodge в кузове Внедорожник с автоматической коробкой передач в России.",
                driver.findElement(By.xpath("(//h1)[1]")).getText());
    }

    @Test
    public void markPlusModelPageSeo() {
        driver.get("http://jekaterinburg.avtopoisk.ru/car/ford/focus");
        assertEquals(driver.getCurrentUrl(), driver.findElement(By.xpath("//link[@rel='canonical']")).getAttribute("href"));
        assertEquals("Купить Ford Focus в Екатеринбурге. Продажа автомобилей Ford Focus по низкой цене", driver.getTitle());
        assertEquals("Автомобили Ford Focus в Екатеринбурге. Выгодная купля-продажа Ford Focus в Екатеринбурге",
                driver.findElement(By.xpath("//meta[@name='description']")).getAttribute("content")); //проверка description
        assertEquals("", driver.findElement(By.xpath("//meta[@name='keywords']")).getAttribute("content")); //проверка keywords
        assertEquals("Продажа Ford Focus в Екатеринбурге.", driver.findElement(By.xpath("(//h1)[1]")).getText());
    }

    @Test
    public void markPlusModePlusAuxiliaryFilterslPageSeo() {
        driver.get("http://samara.avtopoisk.ru/car/toyota/corolla/transmission/mech");
        assertEquals(driver.getCurrentUrl(), driver.findElement(By.xpath("//link[@rel='canonical']")).getAttribute("href"));
        assertEquals("Купить Toyota Corolla с механической коробкой передач в Самаре", driver.getTitle());
        assertEquals("Toyota Corolla (Тойота Королла) с механической коробкой передач в Самаре. " +
                        "Выгодная купля-продажа Toyota Corolla (Тойота Королла) с механической коробкой передач в Самаре",
                driver.findElement(By.xpath("//meta[@name='description']")).getAttribute("content")); //проверка description
        assertEquals("", driver.findElement(By.xpath("//meta[@name='keywords']")).getAttribute("content")); //проверка keywords
        assertEquals("Продажа Toyota Corolla с механической коробкой передач в Самаре.", driver.findElement(By.xpath("(//h1)[1]")).getText());
    }

    @Test
    public void adpageSeo(){
        driver.get("http://rostov-na-donu.avtopoisk.ru/car/audi/a4/mileagefrom/1/mileageto/2?priceCurrency=RUR&state=all&sort=d&sortd=d");
        driver.findElement(By.cssSelector(".form-modal.form-modal-subscribe"));
        driver.findElement(By.xpath("//div[@class=\"form-content\"]/a")).click();
        assertEquals("noindex, follow", driver.findElement(By.xpath("//meta[@name='robots'][1]")).getAttribute("content"));
        assertEquals("Продажа Audi A4 в Ростове-на-Дону.", driver.findElement(By.xpath("(//h1)[1]")).getText());
        List<WebElement> adverts = driver.findElements(By.xpath("//div[@class='proposition listing-item hover ']"));
        if (adverts.size()!=0 && adverts.size()<=15);
        else fail("На странице 'ничего не найдено' нет блока 'объявления близкие к указанному запросу' либо в нем больше 15 объявлений");
    }

    @Test
    public void noindexOnPages(){
        driver.get("http://www.avtopoisk.ru/car/hasfoto/on");
        assertTrue(driver.findElement(By.xpath("//meta[@name='robots']"))
                .getAttribute("content").contains("noindex, follow"));
        driver.get("http://www.avtopoisk.ru/car/hasfoto/off");
        assertTrue(driver.findElement(By.xpath("//meta[@name='robots']"))
                .getAttribute("content").contains("noindex, follow"));
        driver.get("http://www.avtopoisk.ru/car");
        assertTrue(driver.findElements(By.xpath("//meta[@name='robots']")).isEmpty());
        driver.get("http://moskva.avtopoisk.ru/car/enginevolume/2000");
        assertTrue(driver.findElement(By.xpath("//meta[@name='robots']"))
                .getAttribute("content").contains("noindex, follow"));
        driver.get("http://www.avtopoisk.ru/car/enginevolume/6600");
        assertTrue(driver.findElement(By.xpath("//meta[@name='robots']"))
                .getAttribute("content").contains("noindex, follow"));
        driver.get("http://www.avtopoisk.ru/car/bmw/transmission/auto/year/2013");
        assertTrue(driver.findElements(By.xpath("//meta[@name='robots']")).isEmpty(),
                "Страница должна быть открыта от индексации");
        driver.get("http://samara.avtopoisk.ru/car/toyota/enginetype/benzin/year/2014");
        assertTrue(driver.findElements(By.xpath("//meta[@name='robots']")).isEmpty(),
                "Страница должна быть открыта от индексации");
        openAdpage("samara");
        assertTrue(driver.findElement(By.xpath("//meta[@name='robots']"))
                .getAttribute("content").contains("noindex, follow"),
                "Страница адпейдж должна быть закрыта от индексации");
    }

    @Test
    public void noindexOnPagesWhereCountLessThenFive(){
        openSearchPageCar("www", "");
        driver.findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys("dfghj");
        clickOnSearchButton();
        assertTrue(driver.findElement(By.xpath("//meta[@name='robots']"))
                .getAttribute("content").contains("noindex, follow"),
                "Страница 'Ничего не найдено' должна быть закрыть от индексации");
        driver.navigate().back();
        driver.findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys("Alfa Romeo Mito");
        clickOnSearchButton();
        if(getCountAdvertsOnSearchPage() >0 && getCountAdvertsOnSearchPage() <5){
            assertTrue(driver.findElement(By.xpath("//meta[@name='robots']"))
                    .getAttribute("content").contains("noindex, follow"));
        } else {fail("Подобрать значения так чтобы на выдаче было >0 и <5 объявлений");
        }

    }

}
