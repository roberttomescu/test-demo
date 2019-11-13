package database.dao;

import org.springframework.stereotype.Component;

@Component
public class TestBean {

    private String message = "Test bean autowire works!";

    public String getMessage() {
        return message;
    }
}
