package com.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;
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
        assertEquals("Купить б/у авто с пробегом в России. Подержанные автомобили и цены в России, недорого", driver.getTitle());  //проврка тайтла страницы
        assertEquals("canonical", driver.findElement(By.xpath("//link[@href='http://www.avtopoisk.ru/car']")).getAttribute("rel"));//проверка canonical
        assertEquals("Продажа б/у авто с пробегом в России. Выгодные цены на автомобили – купить подержанный, б/у автомобиль в России на сайте Avtopoisk.Ru.",
                driver.findElement(By.xpath("//meta[@name='description']")).getAttribute("content")); //проверка description
        assertEquals("купить авто с пробегом в России, подержанные авто в России, автомобили и цены в России, продажа б/у авто в России",
                driver.findElement(By.xpath("//meta[@name='keywords']")).getAttribute("content")); //проверка keywords
        assertEquals("Купить б/у авто с пробегом в России.", driver.findElement(By.xpath("(//h1)[1]")).getText());//проверка Н1
    }

    @Test
    public void seoOnLocationSearchPageCar() {
        driver.get("http://moskva.avtopoisk.ru/car ");
        assertEquals("Купить б/у авто с пробегом в Москве. Подержанные автомобили и цены в Москве, недорого", driver.getTitle());  //проврка тайтла страницы
        assertEquals("http://moskva.avtopoisk.ru/car", driver.findElement(By.xpath("//link[@rel='canonical']")).getAttribute("href"));//проверка canonical
        assertEquals("Продажа б/у авто с пробегом в Москве. Выгодные цены на автомобили – купить подержанный, б/у автомобиль в Москве на сайте Avtopoisk.Ru.",
                driver.findElement(By.xpath("//meta[@name='description']")).getAttribute("content")); //проверка description
        assertEquals("купить авто с пробегом в Москве, подержанные авто в Москве, автомобили и цены в Москве, продажа б/у авто в Москве",
                driver.findElement(By.xpath("//meta[@name='keywords']")).getAttribute("content")); //проверка keywords
        assertEquals("Купить б/у авто с пробегом в Москве.", driver.findElement(By.xpath("(//h1)[1]")).getText());//проверка Н1
    }

    @Test
    public void seoOnSearchPageOtherCategories() {
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.get("http://www.avtopoisk.ru/gruzovye");
        assertEquals("Грузовые автомобили с пробегом в России. Купля-продажа грузовых автомобилей б/у: цены, фото", driver.getTitle());
        assertEquals("Возможность купить грузовик с пробегом в России на сайте Avtopoisk.Ru. Продажа подержанных грузовых машин в России: цены, описания и фото смотрите на сайте.",
                driver.findElement(By.xpath("//meta[@name='description']")).getAttribute("content")); //проверка description
        assertEquals("грузовые автомобили в России, купить грузовик в России, продажа грузовых автомобилей в России",
                driver.findElement(By.xpath("//meta[@name='keywords']")).getAttribute("content")); //проверка keywords
        assertEquals("Продажа грузовых автомобилей в России.", driver.findElement(By.xpath("(//h1)[1]")).getText());//проверка Н1
    }

    @Test
    public void outclicksOnAdvert() {
        String urls[] = new String[6];
        urls[0] = "http://www.avtopoisk.ru/car";
        urls[1] = "http://www.avtopoisk.ru/car/subbody/minivan";
        urls[2] = "http://samara.avtopoisk.ru/car";
        urls[3] = "http://sankt-peterburg.avtopoisk.ru/car/subbody/minivan";
        urls[4] = "http://sankt-peterburg.avtopoisk.ru/car/subbody/vnedorozhnik";
        urls[5] = "http://krasnojarsk.avtopoisk.ru/car";
        for (int i = 0; i <= urls.length - 1; i++) {
            driver.get(urls[i]);
            if (driver.findElements(By.xpath("//div[@class='mark-status new ']")).isEmpty()) {
                for (int j = 5; j < 7; j++) {
                    if (!driver.findElement(By.xpath("(//div[@class='partner'])[5]/a")).getText().isEmpty()) {
                        assertEquals("nofollow", driver.findElement(By.xpath("(//div[@class='photos-container upload'])["+j+"]/a")).getAttribute("rel"));
                        assertEquals("nofollow", driver.findElement(By.xpath("(//div[@class='info'])["+j+"]/a")).getAttribute("rel"));
                        assertEquals("nofollow", driver.findElement(By.xpath("(//div[@class='partner'])["+j+"]/a")).getAttribute("rel"));
                        assertEquals("nofollow", driver.findElement(By.xpath("(//div[@class='btn btn-md btn-blue'])["+j+"]//..")).getAttribute("rel"));
                    } else {
                        j++;
                    }
                }
                    WebElement goToAdpage = driver.findElement(By.xpath("//span[@class='link']//.."));
                    if (goToAdpage.getAttribute("href").toString().contains("avtopoisk.ru/search/adpage/")) ;
                    else fail("На странице " + driver.getCurrentUrl() + " ссылка подробнее должна вести на адпейдж");
                    assertEquals("_blank", goToAdpage.getAttribute("target"));
                }
        }
    }

    @Test
    public void seoForCastomLinksInTabs() {
        openSearchPageCar("www", "");
        openCustomCategoryInTabs(); //по типам авто
        List<WebElement> customLinks = driver.findElements(By.xpath("(//div[@id='propositionsByCities'])[3]/div/ul/li/a"));
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
        assertEquals("Купить Chevrolet б/у в России: цены, фото, характеристики. Продажа Шевроле с пробегом", driver.getTitle());
        assertEquals("Подержанные автомобили Chevrolet (Шевроле) с пробегом в России. Выгодная купля-продажа б/у автомобиля Chevrolet в России на сайте Avtopoisk.Ru.",
                driver.findElement(By.xpath("//meta[@name='description']")).getAttribute("content")); //проверка description
        assertEquals("купить Chevrolet (Шевроле) в России, продажа Chevrolet в России, б/у автомобиль Chevrolet в России",
                driver.findElement(By.xpath("//meta[@name='keywords']")).getAttribute("content")); //проверка keywords
        assertEquals("Продажа Chevrolet (Шевроле) в России.", driver.findElement(By.xpath("(//h1)[1]")).getText());
    }

    @Test
    public void markPlusAuxiliaryFiltersPageSeo() {
        driver.get("http://www.avtopoisk.ru/car/dodge/subbody/vnedorozhnik/transmission/auto");
        assertEquals(driver.getCurrentUrl(), driver.findElement(By.xpath("//link[@rel='canonical']")).getAttribute("href"));
        assertEquals("Купить Dodge б/у в России: цены, фото, характеристики. Продажа Додж с пробегом", driver.getTitle());
        assertEquals("Подержанные автомобили Dodge (Додж) с пробегом в России. Выгодная купля-продажа б/у автомобиля Dodge в России на сайте Avtopoisk.Ru.",
                driver.findElement(By.xpath("//meta[@name='description']")).getAttribute("content")); //проверка description
        assertEquals("купить Dodge (Додж) в России, продажа Dodge в России, б/у автомобиль Dodge в России",
                driver.findElement(By.xpath("//meta[@name='keywords']")).getAttribute("content")); //проверка keywords
        assertEquals("Продажа Dodge (Додж) в России.", driver.findElement(By.xpath("(//h1)[1]")).getText());
    }

    @Test
    public void markPlusModelPageSeo() {
        driver.get("http://jekaterinburg.avtopoisk.ru/car/ford/focus");
        assertEquals(driver.getCurrentUrl(), driver.findElement(By.xpath("//link[@rel='canonical']")).getAttribute("href"));
        assertEquals("Купить Ford Focus б/у в Екатеринбурге: цены, фото, характеристики. Продажа Форд фокус с пробегом", driver.getTitle());
        assertEquals("Подержанные автомобили Ford Focus (Форд фокус) с пробегом в Екатеринбурге. Выгодная купля-продажа б/у автомобиля Ford Focus в Екатеринбурге на сайте Avtopoisk.Ru.",
                driver.findElement(By.xpath("//meta[@name='description']")).getAttribute("content")); //проверка description
        assertEquals("купить Ford Focus (Форд фокус) в Екатеринбурге, продажа Ford Focus в Екатеринбурге, б/у автомобиль Ford Focus в Екатеринбурге",
                driver.findElement(By.xpath("//meta[@name='keywords']")).getAttribute("content")); //проверка keywords
        assertEquals("Продажа Ford Focus (Форд фокус) в Екатеринбурге.", driver.findElement(By.xpath("(//h1)[1]")).getText());
    }

    @Test
    public void markPlusModePlusAuxiliaryFilterslPageSeo() {
        driver.get("http://samara.avtopoisk.ru/car/toyota/corolla/transmission/mech");
        assertEquals(driver.getCurrentUrl(), driver.findElement(By.xpath("//link[@rel='canonical']")).getAttribute("href"));
        assertEquals("Купить Toyota Corolla с механической коробкой передач в Самаре. Продажа б/у Тойота Королла с пробегом с механикой: цены, фото", driver.getTitle());
        assertEquals("Купить автомобиль Toyota Corolla (Тойота Королла) с механикой в Самаре с пробегом. Продажа новых Toyota Corolla с механической коробкой передач в Самаре на сайте Avtopoisk.Ru.",
                driver.findElement(By.xpath("//meta[@name='description']")).getAttribute("content")); //проверка description
        assertEquals("Toyota Corolla с механической коробкой передач в Самаре, Toyota Corolla с механикой в Самаре, Toyota Corolla с механикой с пробегом в Самаре",
                driver.findElement(By.xpath("//meta[@name='keywords']")).getAttribute("content")); //проверка keywords
        assertEquals("Купить Toyota Corolla с механической коробкой передач в Самаре.", driver.findElement(By.xpath("(//h1)[1]")).getText());
    }

    @Test
    public void adpageSeo(){
        driver.get("http://rostov-na-donu.avtopoisk.ru/car/audi/a4/mileagefrom/1/mileageto/2?priceCurrency=RUR&state=all&sort=d&sortd=d");
        driver.findElement(By.cssSelector(".form-modal.form-modal-subscribe"));
        driver.findElement(By.xpath("//div[@class=\"form-content\"]/a")).click();
        assertEquals("noindex, follow", driver.findElement(By.xpath("//meta[@name='robots'][1]")).getAttribute("content"));
        assertEquals("Продажа Audi A4 (Ауди а4) в Ростове-на-Дону.", driver.findElement(By.xpath("(//h1)[1]")).getText());
        List<WebElement> adverts = driver.findElements(By.xpath("//div[@class='proposition listing-item hover ']"));
        if (adverts.size()!=0 && adverts.size()<=15);
        else fail("На странице 'ничего не найдено' нет блока 'объявления близкие к указанному запросу' либо в нем больше 15 объявлений");
    }

}
