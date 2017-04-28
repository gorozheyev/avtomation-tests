package com.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;


/**
 * Created by gorozheyevd on 06.04.2017.
 */
public class SearchFilterTests extends BaseClass{

    @Test
    public void choiceModelWithoutMark(){
        openSearchPageCar("krasnojarsk", "");
        driver.findElement(By.xpath("//span[(contains(text(), 'Все модели'))]")).click();
        driver.findElement(By.xpath("//div[@class='tooltip-inner']")).isDisplayed();
        clickOnFilterButtons("//div[@class='btn-Radio'][1]");
        assertTrue(driver.getCurrentUrl().contains("new"));
        assertThat(driver.findElement(By.xpath("//label[@for='new']")).getCssValue("background-color"), is("rgba(64, 132, 245, 1)"));
        clickOnFilterButtons("//div[@class='btn-Radio'][2]");
        assertTrue(driver.getCurrentUrl().contains("old"));
        assertThat(driver.findElement(By.xpath("//label[@for='old']")).getCssValue("background-color"), is("rgba(64, 132, 245, 1)"));
        clickOnFilterButtons("//div[@class='btn-Radio'][3]");
        assertTrue(driver.getCurrentUrl().contains("dealer"));
        assertThat(driver.findElement(By.xpath("//label[@for='dealer']")).getCssValue("background-color"), is("rgba(64, 132, 245, 1)"));
    }

    @Test
    public void choiceMarkAndModel(){
        openSearchPageCar("www","");
        driver.findElement(By.xpath("(//button)[1]")).click();
        driver.findElement(By.linkText("Toyota")).click();
        assertTrue(driver.findElement(By.id("filterFloatSubmit")).isDisplayed());
        driver.findElement(By.linkText("Показать объявления")).click();
        assertTrue(driver.findElement(By.xpath("//h1[@class='h4 inline-block']")).getText().contains("Toyota"));
        driver.findElement(By.xpath("(//span[@class='filter-option pull-left'])[2]")).click();
        driver.findElement(By.linkText("Corolla")).click();
        assertTrue(driver.findElement(By.id("filterFloatSubmit")).isDisplayed());
        driver.findElement(By.xpath("//button[contains(text(), 'найти')]")).click();
        assertTrue(driver.findElement(By.xpath("//h1[@class='h4 inline-block']")).getText().contains("Toyota Corolla"));
    }

    @Test
    public void priceFrom(){
        openSearchPageCar("moskva", "");
        driver.findElement(By.id("priceFrom")).sendKeys("500000");
        driver.findElement(By.xpath("//button[contains(text(), 'найти')]")).click();
        assertTrue(driver.findElement(By.xpath("//div[@class='selected-item fade-in']")).isDisplayed());
        List<WebElement> list = driver.findElements(By.xpath("//div[@class='price']"));
        for(WebElement element : list){
            if(element.getText().length()!=0) {
                String s = element.getText().substring(0, element.getText().length() - " руб.".length());
                int a = Integer.parseInt(s.replaceAll(" ", ""));
                if(a<495000)
                fail("Цены на выдаче - "+a+" , а должна быть больше 500 000 руб.");
            }
        }
    }

    @Test
    public void priceTo(){
        openSearchPageCar("www","");
        driver.findElement(By.id("priceTo")).sendKeys("400000");
        driver.findElement(By.xpath("//button[contains(text(), 'найти')]")).click();
        assertTrue(driver.findElement(By.xpath("//div[@class='selected-item fade-in']")).isDisplayed());
        List<WebElement> list = driver.findElements(By.xpath("//div[@class='price']"));
        for(WebElement element : list){
            if(element.getText().length()!=0) {
                String s = element.getText().substring(0, element.getText().length() - " руб.".length());
                int a = Integer.parseInt(s.replaceAll(" ", ""));
                if(a>415000)
                    fail("Цена на выдаче "+a+" .А она не должна быть больше 400 000 руб.");
            }
        }
    }

    @Test
    public void priceFromTo(){
        openSearchPageCar("www","");
        driver.findElement(By.id("priceFrom")).sendKeys("300000");
        driver.findElement(By.id("priceTo")).sendKeys("600000");
        driver.findElement(By.xpath("//button[contains(text(), 'найти')]")).click();
        assertTrue(driver.findElement(By.xpath("//div[@class='selected-item fade-in']")).isDisplayed());
        List<WebElement> list = driver.findElements(By.xpath("//div[@class='price']"));
        for(WebElement element : list){
            if(element.getText().length()!=0) {
                String s = element.getText().substring(0, element.getText().length() - " руб.".length());
                int a = Integer.parseInt(s.replaceAll(" ", ""));
                if(a<300000 && a>600000)
                    fail("Цены на выдаче " + a + ". Цена должна быть в диапазоне от 300 000 до 600 000 руб.");
            }
        }
    }

    @Test
    public void checkTypeOfCurrency(){
        openSearchPageCar("www","");
        assertTrue(driver.findElement(By.xpath("(//span[@class='filter-option pull-left'])[3]")).getText().contains("руб."));
        assertTrue(driver.findElement(By.xpath("(//div[@class='price'])[1]")).getText().contains("руб."));
        driver.findElement(By.xpath("(//span[@class='filter-option pull-left'])[3]")).click();
        driver.findElement(By.xpath("//span[contains(text(), 'USD')]")).click();
        driver.findElement(By.xpath("//button[contains(text(), 'найти')]")).click();
        assertTrue(driver.findElement(By.xpath("(//span[@class='filter-option pull-left'])[3]")).getText().contains("USD"));
        assertTrue(driver.findElement(By.xpath("(//div[@class='price'])[1]")).getText().contains("USD"));
        driver.findElement(By.xpath("(//span[@class='filter-option pull-left'])[3]")).click();
        driver.findElement(By.xpath("//span[contains(text(), 'EUR')]")).click();
        driver.findElement(By.xpath("//button[contains(text(), 'найти')]")).click();
        assertTrue(driver.findElement(By.xpath("(//span[@class='filter-option pull-left'])[3]")).getText().contains("EUR"));
        assertTrue(driver.findElement(By.xpath("(//div[@class='price'])[1]")).getText().contains("EUR"));
    }

    @Test
    public void checkboxHasPhoto() throws InterruptedException {
        openSearchPageCar("www", "");
        driver.findElement(By.id("cbWithPhoto")).click();
        assertTrue(driver.findElement(By.id("cbWithPhoto")).isSelected());
        int a = getCountAdvertsOnSearchPage();
        driver.findElement(By.xpath("//button[contains(text(), 'найти')]")).click();
        int b = getCountAdvertsOnSearchPage();
        if(a>b);
        else fail("Кол-во объявлений с фото "+b+" должно быть меньше чем кол-во объявлений всего "+a);
    }

    @Test
    public void yearFrom(){
        openSearchPageCar("www", "");
        driver.findElement(By.xpath("(//span[@class='filter-option pull-left'])[4]")).click();
        driver.findElement(By.xpath("(//li/a/span[contains(text(), '2016')])[1]")).click();
        driver.findElement(By.xpath("//button[contains(text(), 'найти')]")).click();
        assertTrue(driver.getCurrentUrl().contains("yearfrom/2016"));
        List<WebElement> year = driver.findElements(By.xpath("//div[@class='title']/span"));
        for(WebElement element : year) {
            if (element.getText().length() != 0) {
                int a = Integer.parseInt(element.getText());
                if (a >= 2016) ;
                else fail("На выдаче авто " + a + " года, а должны быть с годом выпуска больше либо равно 2016");
            }
        }
    }

    @Test
    public void yearTo(){
        openSearchPageCar("moskva", "");
        driver.findElement(By.xpath("(//span[@class='filter-option pull-left'])[5]")).click();
        driver.findElement(By.xpath("(//li/a/span[contains(text(), '2000')])[2]")).click();
        driver.findElement(By.xpath("//button[contains(text(), 'найти')]")).click();
        assertTrue(driver.getCurrentUrl().contains("yearto/2000"));
        List<WebElement> year = driver.findElements(By.xpath("//div[@class='title']/span"));
        for(WebElement element : year) {
            if (element.getText().length() != 0) {
                int a = Integer.parseInt(element.getText());
                if (a <= 2000) ;
                else fail("На выдаче авто " + a + " года, а должны быть с годом выпуска больше либо равно 2016");
            }
        }
    }

    @Test
    public void yearFromYearTo(){
        openSearchPageCar("www", "");
        driver.findElement(By.xpath("(//span[@class='filter-option pull-left'])[4]")).click();
        driver.findElement(By.xpath("(//li/a/span[contains(text(), '2010')])[1]")).click();
        driver.findElement(By.xpath("(//span[@class='filter-option pull-left'])[5]")).click();
        driver.findElement(By.xpath("(//li/a/span[contains(text(), '2016')])[2]")).click();
        driver.findElement(By.xpath("//button[contains(text(), 'найти')]")).click();
        assertTrue(driver.getCurrentUrl().contains("yearfrom/2010/yearto/2016"));
        List<WebElement> year = driver.findElements(By.xpath("//div[@class='title']/span"));
        for(WebElement element : year) {
            if (element.getText().length() != 0) {
                int a = Integer.parseInt(element.getText());
                if (a >= 2010 && a <=2016);
                else fail("На выдаче авто " + a + " года, а должны быть с годом выпуска больше либо равно 2016");
            }
        }
    }

}
