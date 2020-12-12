package cz.cvut.fit.ryntluka.customer;

import cz.cvut.fit.ryntluka.dto.CustomerCreateDTO;
import cz.cvut.fit.ryntluka.entity.Customer;

import java.util.List;

import static cz.cvut.fit.ryntluka.planet.PlanetObjects.planet1;

public class CustomerObjects {
    public static Customer customer1 = new Customer("Lukáš", "Rynt", "ryntluka@fit.cvut.cz", planet1);
    public static Customer customer2 = new Customer("Anakin", "Skywalker", "ani.sky@sw.com", planet1);
    public static Customer customer3 = new Customer("Ahsoka", "Tano", "ahso.tano@sw.com", planet1);
    public static Customer customer4 = new Customer("Darth", "Maul", "d.maul@sw.com", planet1);
    public static Customer customer5 = new Customer("Din", "Djarin", "din.mandalorian@sw.com", planet1);
    public static List<Customer> all = List.of(
            customer1,
            customer2,
            customer3,
            customer4,
            customer5
    );

    /*================================================================================================================*/

    public static CustomerCreateDTO createDTO(Customer customer) {
        return new CustomerCreateDTO(customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPlanet().getId());
    }

    public static String toCreateJSON(Customer customer) {
        return "{ \"firstName\": \"" + customer.getFirstName() + "\", "
                + "\"lastName\": \"" + customer.getLastName() + "\", "
                + "\"email\": \"" + customer.getEmail() + "\", "
                + "\"planet\": \"" + customer.getPlanet().getId() + "\" }";
    }

}
