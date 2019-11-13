package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class ContactUsForm {

    private WebDriver driver;

    @FindBy(id="id_contact")
    private WebElement subjectHeading;

    @FindBy(id = "email")
    private WebElement email;

    @FindBy(id = "message")
    private WebElement message;

    @FindBy(id = "submitMessage")
    private WebElement submitMessage;

    @FindBy(xpath = "//div[@id=\'center_column\']/div/ol/li")
    private WebElement errorMessage;

    public ContactUsForm(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public ContactUsForm selectSubjectHeading(String value) {
        Select subjectHeadingSelect = new Select(subjectHeading);
        subjectHeadingSelect.selectByVisibleText(value);
        return this;
    }

    public ContactUsForm enterEmailAddress(String text) {
        email.sendKeys(text);
        return this;
    }

    public ContactUsForm enterMessage(String text) {
        message.sendKeys(text);
        return this;
    }

    public ContactSuccess clickSendSuccess() {
        submitMessage.click();
        return new ContactSuccess(driver);
    }

    public ContactUsForm clickSendError() {
        submitMessage.click();
        return this;
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }
}
