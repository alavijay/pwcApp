package stepdefinition;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import main.CucumberRunner;
import org.testng.Assert;
import pages.homePage;

import java.util.ArrayList;

public class homePageSteps extends CucumberRunner {

    homePage page = new homePage();
    public ArrayList carouselList1;
    public ArrayList carouselList2;
    public ArrayList carouselList3;

	@Given("^I navigate to the PwC Digital Pulse website$")
		public void navigateToPwc(){
		page.navToHome();
    }
    
    @When("^I am viewing the Home page$")
        public void homepageCheck() { page.verifyHome();}

    @Then("^I am presented with a carousel displaying 3 featured articles$")
        public void verifyCarousel() throws InterruptedException {
        carouselList1 = page.verifyActiveCarouselCards();
    }

    @Then("^Clicking the Next button on the carousel will load the next 3 featured articles$")
        public void clickNextButton(){
	    page.clickNext();
    }

    @Then("^Clicking the Previous button on the carousel will load the previous 3 featured articles$")
        public void verifySecondCarousel() throws InterruptedException {
        carouselList2 = page.verifyActiveCarouselCards();
	    page.clickPrevious();
        carouselList3 = page.verifyActiveCarouselCards();
        Assert.assertNotEquals(carouselList1,carouselList2); //Verify that first carousel items dont match second carousel
        Assert.assertEquals(carouselList1,carouselList3); //verify that first carousel is loaded
    }

    @When("^I select ‘Contact us’ from the hamburger menu$")
        public void selectContactUsFromMenu(){
	        page.clickContactUs();
    }

    @When("^I click on the ‘Magnifying glass’ icon to perform a search$")
        public void launchSearchFromHome(){
            page.launchSearch();
    }

    @When("^I enter the text ‘Single page applications’$")
        public void enterText(){
	    page.enterSearchText();
    }

    @When("^I submit the search$")
        public void clickSearch(){
        page.submitSearchText();
    }

}
