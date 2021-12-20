package myspringboot.reactive.r2dbc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    private int id;
    private String name;
    private String email;
    private List<String> phoneNumbers;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }
}