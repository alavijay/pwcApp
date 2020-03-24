package pages;

import main.CucumberRunner;
import org.openqa.selenium.By;
import org.testng.Assert;

import java.util.ArrayList;

public class searchResultsPage extends CucumberRunner {
        public void viewSearchResults(){
            explicitWaitForTitle("You searched for Single page applications - Digital Pulse");
        }

        public void countSearchResults(){
            ArrayList resultList = (ArrayList) driver.findElements(By.className("type-post"));
            Assert.assertTrue(resultList.size()>=1);
    }

    public void goHome(){
        driver.findElement(By.cssSelector(".logo-pwc")).click();
        explicitWaitForTitle("Digital Pulse - Disruption, Innovation and Industry Change");
    }


}
