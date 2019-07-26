package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Marius Dima on 25.07.2019.
 */
public class AutomationSteps extends TestRunner {

    public WebDriver driver;


    @Given("I setup driver")
    public void iSetupDriver() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/driver/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
    }

    @And("I go to {string}")
    public void iGoTo(String url) {
        driver.get(url);
    }


    @When("I search for {string}")
    public void iSearchFor(String item) {
        WebElement searchBox = driver.findElement(By.id("search_query_top"));
        searchBox.clear();
        searchBox.sendKeys(item);
        WebElement submitSearch = driver.findElement(By.xpath("//button[@type='submit']"));
        submitSearch.click();
    }

    @Then("I check that search results have {string}")
    public void iCheckThatSearchResultsHave(String searchedItem) {
        List<WebElement> searchResults = driver.findElements(By.xpath("//div[@class='product-container']// a[@class='product-name']"));
        List<String> productNames = searchResults.stream().map(WebElement::getText).collect(Collectors.toList());
        System.out.println(productNames);
        Assert.assertTrue(productNames.stream().allMatch(name -> name.toLowerCase().contains(searchedItem)));
    }

    @And("I quit the driver")
    public void iQuitTheDriver() {
        driver.quit();
    }
}
