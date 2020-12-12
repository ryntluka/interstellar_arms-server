package cz.cvut.fit.ryntluka.service;

import cz.cvut.fit.ryntluka.dto.CustomerCreateDTO;
import cz.cvut.fit.ryntluka.dto.PlanetDTO;
import cz.cvut.fit.ryntluka.entity.Customer;
import cz.cvut.fit.ryntluka.entity.Planet;
import cz.cvut.fit.ryntluka.exceptions.EntityMissingException;
import cz.cvut.fit.ryntluka.repository.CustomerRepository;
import cz.cvut.fit.ryntluka.repository.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PlanetRepository planetRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, PlanetRepository planetRepository) {
        this.customerRepository = customerRepository;
        this.planetRepository = planetRepository;
    }

    /*================================================================================================================*/

    @Transactional
    public Customer create(CustomerCreateDTO customerCreateDTO) throws EntityMissingException {
        return customerRepository.save(
                new Customer(
                        customerCreateDTO.getFirstName(),
                        customerCreateDTO.getLastName(),
                        customerCreateDTO.getEmail(),
                        planetRepository.findById(customerCreateDTO.getPlanetId())
                                .orElseThrow(EntityMissingException::new))
        );
    }

    /*================================================================================================================*/

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public List<Customer> findAllByLastName(String lastName) {
        return customerRepository.findAllByLastName(lastName);
    }

    public Optional<Customer> findById(int id) {
        return customerRepository.findById(id);
    }

    public List<Customer> findByIds(List<Integer> ids) {
        return customerRepository.findAllById(ids);
    }

    /*================================================================================================================*/

    @Transactional
    public Customer update(int id, CustomerCreateDTO customerCreateDTO) throws EntityMissingException {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty())
            throw new EntityMissingException(id);

        Customer customer = optionalCustomer.get();
        customer.setFirstName(customerCreateDTO.getFirstName());
        customer.setLastName(customerCreateDTO.getLastName());
        customer.setEmail(customerCreateDTO.getEmail());
        customer.setPlanet(planetRepository.findById(customerCreateDTO.getPlanetId())
                .orElseThrow(EntityMissingException::new));
        return customer;
    }

    /*================================================================================================================*/

    @Transactional
    public void delete(int id) throws EntityMissingException {
        if (customerRepository.findById(id).isEmpty())
            throw new EntityMissingException();
        customerRepository.deleteById(id);
    }
}
