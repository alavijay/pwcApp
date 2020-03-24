package stepdefinition;

import cucumber.api.java.en.Then;
import pages.searchResultsPage;


public class searchResultsStep {

    searchResultsPage searchpage = new searchResultsPage();

        @Then("^I am taken to the search results page$")
            public void viewSearchResultsPage(){
            searchpage.viewSearchResults();
        }

        @Then("^I am presented with at least 1 search result$")
        public void countSearchResultsPage(){
            searchpage.countSearchResults();
            searchpage.goHome();

        }
}
