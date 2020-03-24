package pages;

import main.CucumberRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import java.util.ArrayList;

public class homePage extends CucumberRunner {

    public String pageTitle = "Digital Pulse - Disruption, Innovation and Industry Change";
    public WebElement homeCarousel = driver.findElement(By.className("flexslider"));
    public static ArrayList listOne;
    public static ArrayList listTwo;
    public WebElement contactUsLink =  driver.findElement(By.id("menu-item-65"));
    public WebElement hambugerMenu = driver.findElement(By.xpath("//*[@class='btr bt-bars trigger']"));
    public String selector = "(//*[@class='flex flex-active-slide']//*[@class='inner'])";
    public WebElement searchIcon = driver.findElement(By.className("bt-search"));
    public WebElement searchBar = driver.findElement(By.className("search-field"));

    public void navToHome(){
        explicitWaitForTitle(pageTitle);
    }

    public void verifyHome(){
        explicitWait(homeCarousel);
        Assert.assertTrue(homeCarousel.isDisplayed());
    }

    public ArrayList verifyActiveCarouselCards() throws InterruptedException {
        loadCarousel();
        ArrayList items = (ArrayList) driver.findElements(By.xpath(selector));
        ArrayList List = new ArrayList();
        String name;
        for (int i = 0; i < items.size(); i++) {
            int index = i + 1;
            //This will get the text of each item in carousel
            name = driver.findElement(By.xpath(selector + "[" + index + "]")).getText();
            List.add(name);

        }
        return List;
    }

    public void clickNext(){
        driver.findElement(By.cssSelector(".flex-next")).click();
    }

    public void clickPrevious(){
        driver.findElement(By.cssSelector(".flex-prev")).click();
    }

    public void compareBothCarousels(){
        Assert.assertNotEquals(listOne,listTwo);
    }

    public void clickContactUs(){
        explicitWait(hambugerMenu);
        hambugerMenu.click();
        explicitWait(contactUsLink);
        contactUsLink.click();
    }

    public void launchSearch(){
        explicitWait(searchIcon);
        searchIcon.click();
    }

    public void enterSearchText(){
        explicitWait(searchBar);
        searchBar.sendKeys("Single page applications");
    }

    public void submitSearchText(){
        explicitWait(searchBar);
        searchBar.sendKeys(Keys.ENTER);
    }
}