package cz.cvut.fit.ryntluka.dto;

import com.sun.istack.NotNull;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class CustomerDTO {

    private final int id;
    private final String firstName;
    private final String lastName;
    private final String email;

    public CustomerDTO(int id, String firstName, String lastName, String email)

    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
