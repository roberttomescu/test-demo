package steps;

import backpack.TestBackPack;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.request.LoginUser;
import model.request.RegisterUser;
import model.response.Data;
import model.response.Response;
import model.response.User;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class ApiSteps {

    private Data response;
    private User user;

    @Autowired
    private TestBackPack testBackPack;

    @Given("I get all the users from {string}")
    public void iGetAllTheUsersFrom(String url) {
        response = given().when().get(url).as(Data.class);
        System.out.println(response);
    }

    @And("I save an email from a random user")
    public void iSaveAnEmailFromARandomUser() {
        List<User> users = response.getUsers();
        user = users.get(new Random().nextInt(users.size()));
        testBackPack.setEmail(user.getEmail());
    }

    @When("I register another user using the saved email at {string}")
    public void iRegisterAnotherUserUsingTheSavedEmailAt(String url) {
        RegisterUser registerUser = new RegisterUser(testBackPack.getEmail(), "passw@rd");
        String token = given().contentType("application/json").body(registerUser).post(url).jsonPath().getString("token");
        testBackPack.setRegisterToken(token);
        System.out.println(token);
    }

    @And("I login with the newly created user at {string}")
    public void iLoginWithTheNewlyCreatedUserAt(String url) {
        LoginUser loginUser = new LoginUser();
        loginUser.setEmail(testBackPack.getEmail());
        String password = "passw@rd";
        loginUser.setPassword(password);

        String token = given().contentType("application/json").body(loginUser).post(url).jsonPath().getString("token");

        testBackPack.setLoginToken(token);
    }

    @Then("The register user token and login token should be the same")
    public void theRegisterUserTokenAndLoginTokenShouldBeTheSame() {
        Assert.assertEquals(testBackPack.getRegisterToken(), testBackPack.getLoginToken());
    }

    @Given("I check testBackPack")
    public void iCheckTestBackPack() {
        System.out.println("Checking that the testBackPack maintains email state");
        System.out.println(testBackPack.getEmail());
    }

    @Before("~@backpack")
    public void setup() {
        testBackPack = new TestBackPack();
    }
}
