package steps;

import backpack.TestBackPack;
import io.cucumber.java.en.And;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

public class TestBackPackSteps {

    @Autowired
    private TestBackPack testBackPack;

    @And("I check the testbackpack for message to be equal to {string}")
    public void iCheckTheTestbackpackForMessageToBeEqualTo(String message) {
        String backPackMessage = testBackPack.getMessage();
        Assert.assertEquals("Error message", message, backPackMessage);
    }
}
