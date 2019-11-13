package steps;

import backpack.TestBackPack;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import io.cucumber.java.After;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import pageobjects.ContactSuccess;
import pageobjects.ContactUsForm;
import pageobjects.HomePage;
import testLink.TestLink;
import testlink.api.java.client.TestLinkAPIClient;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Marius Dima on 25.07.2019.
 */
@ActiveProfiles("chrome")
public class AutomationSteps extends TestRunner {

    @Autowired
    private WebDriver driver;

    @Autowired
    private TestBackPack testBackPack;

    @Autowired
    private TestLink testLink;

    @Value("${browser.url}")
    private String url;


    @Given("I setup driver")
    public void iSetupDriver() {

    }

    @And("I go to {string}")
    public void iGoTo(String url) {
        driver.get(this.url);
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

    @io.cucumber.java.en.When("I click on 'Contact us'")
    public void iClickOn() {
        HomePage homePage = new HomePage(driver);
        homePage.clickContactUs();
    }

    @io.cucumber.java.en.And("I select Subject Heading {string}")
    public void iSelectSubjectHeading(String value) {
        ContactUsForm contactPage = new ContactUsForm(driver);
        contactPage.selectSubjectHeading(value);
    }

    @io.cucumber.java.en.And("I enter email address {string}")
    public void iEnterEmailAddress(String email) {
        ContactUsForm contactPage = new ContactUsForm(driver);
        contactPage.enterEmailAddress(email);
    }

    @io.cucumber.java.en.And("I enter message {string}")
    public void iEnterMessage(String text) {
        ContactUsForm contactPage = new ContactUsForm(driver);
        contactPage.enterMessage(text);
        testBackPack.setMessage(text);
    }

    @io.cucumber.java.en.Then("Success message {string} appears")
    public void successMessageAppears(String arg0) {
        ContactSuccess successPage = new ContactSuccess(driver);
        Assert.assertEquals("Success message", arg0, successPage.getSuccessMessage());
    }

    @io.cucumber.java.en.Then("Error message appears {string}")
    public void errorMessageAppears(String arg0) {
        ContactUsForm contactPage = new ContactUsForm(driver);
        Assert.assertEquals("Error message", arg0, contactPage.getErrorMessage());
    }

    @io.cucumber.java.en.And("I click 'Send' expecting success")
    public void iClickSendExpectingSuccess() {
        ContactUsForm contactPage = new ContactUsForm(driver);
        contactPage.clickSendSuccess();
    }

    @io.cucumber.java.en.And("I click 'Send' expecting error")
    public void iClickSendExpectingError() {
        ContactUsForm contactPage = new ContactUsForm(driver);
        contactPage.clickSendError();
    }


    // Contact simple feature


    @io.cucumber.java.en.When("I click on 'Contact us' button")
    public void iClickOnContactUsButton() {
        HomePage homePage = new HomePage(driver);
        homePage.clickContactUs();
    }

    @io.cucumber.java.en.And("I complete the contact details")
    public void iCompleteTheContactDetails() {
        ContactUsForm contactPage = new ContactUsForm(driver);
        contactPage.selectSubjectHeading("Webmaster")
                .enterEmailAddress("test@email.com")
                .enterMessage("mesaj de test");
    }

    @io.cucumber.java.en.Then("I submit contact form")
    public void iSubmitContactForm() {
        ContactUsForm contactPage = new ContactUsForm(driver);
        contactPage.clickSendSuccess();
        System.out.println("in 'I submit'...");
        testLink.ping();
       // testLink.updateTest(TestLink.TEST_CASE_NAME, TestLinkAPIClient.TEST_PASSED);
    }

    @After
    public void quit() {
        System.out.println("in @after...");
        testLink.ping();
        testLink.updateTest(TestLink.TEST_CASE_NAME, TestLinkAPIClient.TEST_PASSED);
        driver.quit();
    }

    @io.cucumber.java.en.Given("I go to url http:\\/\\/automationpractice.com")
    public void iGoToUrlHttpAutomationpracticeCom() {
        driver.get("");
    }

    @io.cucumber.java.en.Given("I go to url")
    public void iGoToUrl() {
        driver.get(url);
    }

}
