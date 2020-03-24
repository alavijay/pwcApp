package stepdefinition;

import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import main.CucumberRunner;
import org.testng.Assert;
import pages.contactUsPage;

import java.util.ArrayList;
import java.util.List;

public class contactStep extends CucumberRunner {

    contactUsPage page = new contactUsPage();

    @Then("^I am taken to the ‘Contact us’ page$")
    public void navigateToContactUs(){
        page.verifyContactUsPage();
    }

    @Then("^I am presented with the below options for contacts$")
    public void verifyContactUs(DataTable table){
        ArrayList ContactHeaderList = page.verifyContactOptionsHeaders();
        ArrayList contactLinkList = page.verifyContactOptionslinks();
        ArrayList ExpectedHeaderList = getColoumFromDataTable(table, 0);
        ArrayList ExpectedcontactList = getColoumFromDataTable(table, 1);
        Assert.assertEquals(ContactHeaderList,ExpectedHeaderList);
        Assert.assertEquals(contactLinkList,ExpectedcontactList);
        page.goHome();

    }

}
