package com.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.fail;

/**
 * Created by gorozheyevd on 08.08.2017.
 */
public class AdpageTests extends BaseClass{

    @Test
    public void checkTitlesAndCkicksOnBreadCrumbs() throws InterruptedException {
        openAdpage("moskva");
        assertTrue(driver.getCurrentUrl().contains("https://moskva.avtopoisk.ru/search/adpage/"),
                "This is not adpage");
        checkPresenseElementsInHeader();
        assertTrue(driver.findElement(By.xpath("(//li[@itemtype]/a)[1]")).getAttribute("title")
                .equals("Продажа авто в Москве"), "Проверить тайтл в ХК Главная");
        assertTrue(driver.findElement(By.xpath("(//li[@itemtype]/a/span)[1]")).getText()
        .equals("Главная"), "ХК должна называться 'Главная'");
        driver.findElement(By.xpath("(//li[@itemtype]/a)[1]")).click();
        assertTrue(driver.getCurrentUrl().equals("https://moskva.avtopoisk.ru/"));
        driver.navigate().back();
        assertTrue(driver.findElement(By.xpath("(//li[@itemtype]/a)[2]")).getAttribute("title")
                .equals("Продажа легковых авто в Москве"), "Проверить тайтл в ХК 'Легковые авто'");
        assertTrue(driver.findElement(By.xpath("(//li[@itemtype]/a/span)[2]")).getText()
                .equals("Легковые авто"), "ХК должна называться 'Легковые авто'");
        driver.findElement(By.xpath("(//li[@itemtype]/a)[2]")).click();
        assertTrue(driver.getCurrentUrl().equals("https://moskva.avtopoisk.ru/car"));
        driver.navigate().back();
        WebElement breadCrumbs = driver.findElement(By.xpath("(//li[@itemtype]/div/a)[1]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(breadCrumbs).build().perform();
        List<WebElement> marks = driver.findElements(By.xpath("(//ul[@class='dropdown-menu'])[1]/li/a"));
        assertTrue(marks.size() <=10, "Dropdown must be 10 or less elements");
        for (WebElement mark : marks) {
            assertEquals("Продажа легковых авто " + mark.getText() + " в Москве", mark.getAttribute("title"));
        }
        WebElement markInList = driver.findElement(By.xpath("(//ul[@class='dropdown-menu'])[1]/li[1]"));
        String title = markInList.getText();
        markInList.click();
        Thread.sleep(1000);
        assertTrue(driver.getCurrentUrl().equals("https://moskva.avtopoisk.ru/car/" + title.toLowerCase()),
                "Проверить переход на страницу марки из дропдауна ХК");
        driver.navigate().back();
        WebElement markName = driver.findElement(By.xpath("(//div[@class='dropdown crumb-dropdown']//span)[1]"));
        String mark =  markName.getText();
        markName.click();
        assertEquals(driver.getCurrentUrl(), "https://moskva.avtopoisk.ru/car/"+mark.toLowerCase());
    }

    @Test
    public void checkTitlesAndCkicksOnBreadCrumbWithModel(){
        openAdpage("moskva");
        List<WebElement> breadCrumbs = driver.findElements(By.xpath("(//li[@itemtype]/div/a)[2]"));
        if(breadCrumbs.size() !=0) {
            WebElement modelButton = driver.findElement(By.xpath("(//button[@class='dropdown-toggle disabled'])[2]"));
            Actions actions = new Actions(driver);
            actions.moveToElement(modelButton).build().perform();
            List<WebElement> models = driver.findElements(By.xpath("(//ul[@class='dropdown-menu'])[2]/li/a"));
            for (int i = 0; i < models.size(); i++) {
                assertTrue(models.get(i).getAttribute("title").contains(models.get(i).getText() + " в Москве"));
            }
            String mark = driver.findElement(By.xpath("(//li[@itemtype]/div/a)[1]/span")).getText();
            String model = driver.findElement(By.xpath("(//ul[@class='breadcrumb breadcrumb_search'])/li[4]//a")).getText();
            driver.findElement(By.xpath("(//ul[@class='breadcrumb breadcrumb_search'])/li[4]//a")).click();
            assertEquals(driver.getCurrentUrl(), "https://moskva.avtopoisk.ru/car/" + mark.toLowerCase() + "/" + model.replaceAll(" ", "_").toLowerCase());
        } else {
            fail("На странице адпейдж в ХК нет модели");
        }
    }

    @Test
    public void checkPresensePrices(){
        openAdpage("moskva");
        assertTrue(driver.findElement(By.xpath("//h3[@class='h3 title price']")).getText().contains("RUB"),
                "Тип валют у основной цены должен быть RUB");
        assertTrue(driver.findElement(By.xpath("//div[@class='sub-price'][1]")).getText().contains("$"),
                "Вторая цена должна быть в долларах");
        assertTrue(driver.findElement(By.xpath("//div[@class='sub-price'][2]")).getText().contains("€"),
                "Вторая цена должна быть в евро");
        assertTrue(!driver.findElement(By.xpath("//h3[@class='h3 title']")).getText().isEmpty(),
                "На адпейдж не отображается Марка/модель");
        assertTrue(driver.findElement(By.cssSelector(".region.text-gray")).getText().contains("Москва"),
                "Не выводится регион в верхней части адпейдж");
        assertTrue(driver.findElement(By.cssSelector(".date-item.text-gray.margin-t02")).getText().contains("Дата размещения:"),
                "Нет текста Дата размещения:");
        assertTrue(!driver.findElement(By.cssSelector(".date-item.text-gray.margin-t02>span")).getText().isEmpty(),
                "Не отображается дата размещения объявления");
        assertTrue(driver.findElement(By.cssSelector(".label-share.text-gray")).isDisplayed(),
                "Нет блока поделиться в соцсетях");
        assertTrue(driver.findElement(By.cssSelector(".social-unit.social-unit__flat a:nth-of-type(1)")).isDisplayed(),
                "не отображается иконка для поделиться в FB");
        assertTrue(driver.findElement(By.cssSelector(".social-unit.social-unit__flat a:nth-of-type(2)")).isDisplayed(),
                "не отображается иконка для поделиться в Google");
    }

    @Test
    public void presenseAndClickOnFavorite() throws InterruptedException {
        openAdpage("krasnojarsk");
        assertThat(driver.findElement(By.cssSelector(".options-unit>a")).getAttribute("data-count"), is("0"));
        assertTrue(driver.findElement(By.cssSelector(".options-unit>a")).getAttribute("title").equals("Добавить в избранное"),
                "Тайтл должен быть Добавить в избраное");
        driver.findElement(By.cssSelector("input[placeholder='Марка, модель']")).click();
        driver.findElement(By.cssSelector(".btn-option.btn-option-like.liked")).click();
        Thread.sleep(1000);
        assertTrue(driver.findElement(By.cssSelector(".options-unit>a")).getAttribute("title").equals("Убрать из избранного"),
                "Тайтл должен быть Убрать из избранного");
        assertThat(driver.findElement(By.cssSelector(".options-unit>a")).getAttribute("data-count"), is("1"));
        String markModelYear = driver.findElement(By.cssSelector(".col-sm-8.col-xs-12>h3")).getText();
        assertTrue(driver.getTitle().contains("Купить "+markModelYear+ " с пробегом за"));
        assertTrue(driver.findElement(By.xpath("//meta[@name='robots']")).getAttribute("content").contains("noindex, follow"),
        "Страницы адпейдж должны быть закрыты в noindex, follow");
     }

}
