package database.dao;

import database.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerDaoImpl implements CustomerDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Customer> getCustomers() {
        return jdbcTemplate.query("select * from customer", BeanPropertyRowMapper.newInstance(Customer.class));
    }

    @Override
    public Customer getCustomerWithEmail(String email) {
        return jdbcTemplate.queryForObject("select * from customer where email='" + email+"'", BeanPropertyRowMapper.newInstance(Customer.class));
    }

    @Override
    public void removeCustomer(String name) {

    }
}
