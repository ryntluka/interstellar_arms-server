package cz.cvut.fit.ryntluka.service;

import cz.cvut.fit.ryntluka.dto.CustomerCreateDTO;
import cz.cvut.fit.ryntluka.dto.CustomerDTO;
import cz.cvut.fit.ryntluka.entity.Customer;
import cz.cvut.fit.ryntluka.exceptions.EntityMissingException;
import cz.cvut.fit.ryntluka.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /*================================================================================================================*/

    @Transactional
    public Customer create(CustomerCreateDTO customerCreateDTO) {
        return customerRepository.save(
                new Customer(
                        customerCreateDTO.getFirstName(),
                        customerCreateDTO.getLastName(),
                        customerCreateDTO.getEmail())
        );
    }

    /*================================================================================================================*/

    public Optional<Customer> findByLastName (String lastName) {
        return customerRepository.findByLastName(lastName);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Page<Customer> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
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
        return customer;
    }

    /*================================================================================================================*/

    @Transactional
    public void delete(int id) {
        customerRepository.deleteById(id);
    }
}
