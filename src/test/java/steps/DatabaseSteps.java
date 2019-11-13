package steps;

import database.dao.CustomerDAO;
import database.dao.TestBean;
import database.model.Customer;
import database.repository.CustomerRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DatabaseSteps {

    private static final String SELECT_FROM_CUSTOMER = "SELECT * FROM Customer";

    @Autowired
    private DataSource dataSource;  // pt Java jdbc

    @Autowired
    private CustomerDAO customerDAO;    // pt Spring jdbc

    @Autowired
    private CustomerRepository customerRepository;  // pt Spring Data JBA

    @Autowired
    private TestBean testBean;

    @Given("I select something from the database using Java JDBC")
    public void iSelectSomethingFromTheDatabaseUsingJavaJDBC() {
        System.out.println(getCustomerUsingJavaJdbc());
    }

    private List<Customer> getCustomerUsingJavaJdbc() {
        List<Customer> customers = new ArrayList<>();
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_CUSTOMER);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(2);
                customer.setName("Test");
                customer.setEmail("test@email");
                customer.setStatus(1);
                customers.add(customer);
            }
            return customers;
        }
        catch (SQLException e) {
                throw new RuntimeException("SQL Query has failed " + SELECT_FROM_CUSTOMER, e);
        }
    }

    @And("I select from database using Spring JDBC Template")
    public void iSelectFromDatabaseUsingSpringJDBCTemplate() {
        List<Customer> customers = customerDAO.getCustomers();
        Assert.assertEquals(1, customers.size());
    }

    @When("I select a user with email {string} using Spring JDBC")
    public void iSelectAUserWithEmailUsingSpringJDBC(String email) {
        Customer customer = customerDAO.getCustomerWithEmail(email);
        Assert.assertEquals("Janet", customer.getName());
    }

    @And("I select a user via Spring Data JPA")
    public void iSelectAUserViaSpringDataJPA() {
        Iterable<Customer> all = customerRepository.findAll();
        Assert.assertEquals(1, ((Collection)all).size());
        Assert.assertEquals(1, customerRepository.count());

        Customer customerByEmail = customerRepository.findCustomerByEmail("janet@email.com");
        Assert.assertEquals("Janet", customerByEmail.getName());
    }

    @And("I add a new user with")
    public void iAddANewUserWith(DataTable dataTable) {
    }

    @Then("I check that there are {int} customers in the database")
    public void iCheckThatThereAreCustomersInTheDatabase(int arg0) {
        Assert.assertEquals(1, customerRepository.count());
    }

    @When("I check test bean")
    public void iCheckTestBean() {
        System.out.println("I check the test bean...");
        System.out.println(testBean.getMessage());
    }
}
