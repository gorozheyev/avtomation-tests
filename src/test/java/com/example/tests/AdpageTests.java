package com.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by gorozheyevd on 08.08.2017.
 */
public class AdpageTests extends BaseClass{

    @Test
    public void checkTitlesAndCkicksOnBreadCrumbs(){
        openSearchPageCar("moskva", "");
        String partOfUrl = driver.findElement(By.xpath("//div[@class='description']//a")).getAttribute("href");
        driver.get(partOfUrl);
        assertTrue(driver.getCurrentUrl().contains("http://moskva.avtopoisk.ru/search/adpage/"),
                "This is not adpage");
        checkPresenseElementsInHeader();
        assertTrue(driver.findElement(By.xpath("(//li[@itemtype]/a)[1]")).getAttribute("title")
                .equals("Продажа авто в Москве"), "Проверить тайтл в ХК Главная");
        assertTrue(driver.findElement(By.xpath("(//li[@itemtype]/a/span)[1]")).getText()
        .equals("Главная"), "ХК должна называться 'Главная'");
        driver.findElement(By.xpath("(//li[@itemtype]/a)[1]")).click();
        assertTrue(driver.getCurrentUrl().equals("http://moskva.avtopoisk.ru/"));
        driver.navigate().back();
        assertTrue(driver.findElement(By.xpath("(//li[@itemtype]/a)[2]")).getAttribute("title")
                .equals("Продажа легковых авто в Москве"), "Проверить тайтл в ХК 'Легковые авто'");
        assertTrue(driver.findElement(By.xpath("(//li[@itemtype]/a/span)[2]")).getText()
                .equals("Легковые авто"), "ХК должна называться 'Легковые авто'");
        driver.findElement(By.xpath("(//li[@itemtype]/a)[2]")).click();
        assertTrue(driver.getCurrentUrl().equals("http://moskva.avtopoisk.ru/car"));
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
        assertTrue(driver.getCurrentUrl().equals("http://moskva.avtopoisk.ru/car/" + title.toLowerCase()),
                "Проверить переход на страницу марки из дропдауна ХК");
        driver.navigate().back();
        WebElement markName = driver.findElement(By.xpath("(//div[@class='dropdown crumb-dropdown']//span)[1]"));
        String mark =  markName.getText();
        markName.click();
        assertEquals(driver.getCurrentUrl(), "http://moskva.avtopoisk.ru/car/"+mark.toLowerCase());
    }

    @Test
    public void checkTitlesAndCkicksOnBreadCrumbWithModel(){
        openSearchPageCar("moskva", "");
        String partOfUrl = driver.findElement(By.xpath("//div[@class='description']//a")).getAttribute("href");
        driver.get(partOfUrl);
        WebElement breadCrumbs = driver.findElement(By.xpath("(//li[@itemtype]/div/a)[2]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(breadCrumbs).build().perform();
        List<WebElement> models = driver.findElements(By.xpath("(//ul[@class='dropdown-menu'])[2]/li/a"));
        for(int i=0; i<models.size(); i++){
            assertTrue(models.get(i).getAttribute("title").contains(models.get(i).getText() + " в Москве"));
        }
        String mark = driver.findElement(By.xpath("(//li[@itemtype]/div/a)[1]/span")).getText();
        String model = driver.findElement(By.xpath("((//ul[@class='dropdown-menu'])[2]//a)[1]")).getText();
        driver.findElement(By.xpath("((//ul[@class='dropdown-menu'])[2]//a)[1]")).click();
        assertEquals(driver.getCurrentUrl(), "http://moskva.avtopoisk.ru/car/"+mark.toLowerCase()+"/"+model.toLowerCase());
    }

}
