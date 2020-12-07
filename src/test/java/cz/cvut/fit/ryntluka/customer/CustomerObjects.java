package cz.cvut.fit.ryntluka.customer;

import cz.cvut.fit.ryntluka.dto.CustomerCreateDTO;
import cz.cvut.fit.ryntluka.dto.CustomerDTO;
import cz.cvut.fit.ryntluka.entity.Customer;

import java.util.List;

public class CustomerObjects {
    public static Customer customer1 = new Customer("Lukáš", "Rynt", "ryntluka@fit.cvut.cz");
    public static Customer customer2 = new Customer("Anakin", "Skywalker", "ani.sky@sw.com");
    public static Customer customer3 = new Customer("Ahsoka", "Tano", "ahso.tano@sw.com");
    public static Customer customer4 = new Customer("Darth", "Maul", "d.maul@sw.com");
    public static Customer customer5 = new Customer("Din", "Djarin", "din.mandalorian@sw.com");
    public static List<Customer> all = List.of(
            customer1,
            customer2,
            customer3,
            customer4,
            customer5
    );

    /*================================================================================================================*/

    public static CustomerCreateDTO createDTO(Customer customer) {
        return new CustomerCreateDTO(customer.getFirstName(), customer.getLastName(), customer.getEmail());
    }

    public static String toCreateJSON(Customer customer) {
        return "{ \"firstName\": \"" + customer.getFirstName() + "\", "
                + "\"lastName\": \"" + customer.getLastName() + "\", "
                + "\"email\": \"" + customer.getEmail() + "\" }";
    }

}
