package configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.DriverManager;

@Configuration
@PropertySource("classpath:automation.properties")
@ComponentScan(basePackages = {"backpack","testLink", "database", "steps"})
@EnableAutoConfiguration
@EntityScan(basePackages="database/model")
@EnableJpaRepositories(basePackages="database/repository")
public class AutomationConfig {

    @Value("${browser.url}")
    private String browserUrl;

    @Bean
    @Lazy
    @Profile("chrome")
    public WebDriver getChrome() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/driver/chromedriver.exe");
        //ChromeOptions chromeOptions = new ChromeOptions();
        //chromeOptions.addArguments("headless");
        ChromeDriver chromeDriver = new ChromeDriver();
        chromeDriver.manage().window().maximize();
        return chromeDriver;
    }

    @Bean
    @Profile("firefox")
    public WebDriver getFirefox() {
        System.setProperty("webdriver.gecko.driver", "src/test/resources/driver/geckodriver.exe");
        FirefoxDriver firefoxDriver = new FirefoxDriver();
        firefoxDriver.manage().window().maximize();
        return firefoxDriver;
    }

    @Bean
    @Profile("IE")
    public WebDriver getIE() {
        System.setProperty("webdriver.ie.driver", "src/test/resources/driver/IEDriverServer.exe");
        InternetExplorerOptions IEOptions = new InternetExplorerOptions().withInitialBrowserUrl(browserUrl);
        InternetExplorerDriver IEDriver = new InternetExplorerDriver(IEOptions);
        IEDriver.manage().window().maximize();
        return IEDriver;
    }


    @Bean
    public DataSource getDataSource() {
        String url = "jdbc:mysql://localhost:3306/test";
        String username = "root";
        String password = "";
        return new DriverManagerDataSource(url, username, password);
    }
    @Bean
    public JdbcTemplate getTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
