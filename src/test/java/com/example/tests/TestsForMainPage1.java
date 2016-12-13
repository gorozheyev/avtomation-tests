/**
 * Created by gorozheyevd on 07.12.2016.
 */
package com.example.tests;

import org.testng.annotations.Test;

public class TestsForMainPage1 extends BaseClass{

    @Test
    public void testSearchFilterWithWalue() throws Exception {
        openMainPage();
        MainPageData car = new MainPageData("toyota");
        car.name = "toyota";
        inputValueInSearchField(car);
        clickOnSearchButton();
    }

    @Test
    public void testWithEmptySearchFilter() throws Exception {
        openMainPage();
        inputValueInSearchField(new MainPageData(""));
        clickOnSearchButton();
    }
}
