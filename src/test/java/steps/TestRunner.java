package steps;


import configuration.AutomationConfig;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by Marius Dima on 25.07.2019.
 */

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "json:target/cucumber.json"},
        features = "src/test/resources/features", glue = "steps")
@ContextConfiguration(classes = AutomationConfig.class)
public class TestRunner {
}
