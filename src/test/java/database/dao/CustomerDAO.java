package database.dao;

import database.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerDAO {

    List<Customer> getCustomers();

    Customer getCustomerWithEmail(String email);

    void removeCustomer(String name);
}
