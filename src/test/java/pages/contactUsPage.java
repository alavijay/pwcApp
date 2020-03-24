package pages;

import main.CucumberRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class contactUsPage extends CucumberRunner {

    public String title = "Contact us - Digital Pulse";
    public String contactHeaderPath = "(//*[@class='container content contacts padding']//*[@class='span_3']/h2)";
    public String contactLinkPath = "(//*[@class='container content contacts padding']//*[@class='span_3']//a)";
    public void verifyContactUsPage(){
        explicitWaitForTitle(title);
    }

    public ArrayList verifyContactOptionsHeaders(){
        ArrayList boxes = (ArrayList) driver.findElements(By.xpath(contactHeaderPath));
        ArrayList ContactHeaderList = new ArrayList();
        System.out.println(boxes.size()+" :total no of boxes ");
        String text;
        for (int i = 0; i < boxes.size(); i++) {
            int index = i + 1;
            text = driver.findElement(By.xpath(contactHeaderPath + "[" + index + "]")).getText();
            text = safeChar(text);
            ContactHeaderList.add(text);
            System.out.println(text + "  Priting Web Text &&&&&");

        }
        return ContactHeaderList;
    }
    public void goHome(){
        driver.findElement(By.cssSelector(".logo-pwc")).click();
        explicitWaitForTitle("Digital Pulse - Disruption, Innovation and Industry Change");
    }

    public ArrayList verifyContactOptionslinks(){
        List<WebElement> boxes = driver.findElements(By.xpath(contactLinkPath));
        ArrayList contactLinkList = new ArrayList();
        for (int i = 0; i < boxes.size(); i++) {
            String urlText = boxes.get(i).getAttribute("href");
            contactLinkList.add(urlText);
        }
        return contactLinkList;
    }
}
