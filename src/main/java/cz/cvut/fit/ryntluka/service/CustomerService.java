package cz.cvut.fit.ryntluka.service;

import cz.cvut.fit.ryntluka.dto.CustomerCreateDTO;
import cz.cvut.fit.ryntluka.dto.CustomerDTO;
import cz.cvut.fit.ryntluka.entity.Customer;
import cz.cvut.fit.ryntluka.exceptions.EntityMissingException;
import cz.cvut.fit.ryntluka.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public CustomerDTO create(CustomerCreateDTO customerCreateDTO) {
        return toDTO(
                customerRepository.save(
                        new Customer(
                                customerCreateDTO.getFirstName(),
                                customerCreateDTO.getLastName(),
                                customerCreateDTO.getEmail())
                )
        );
    }

    /*================================================================================================================*/

    public Optional<CustomerDTO> findByLastName (String lastName) {
        return toDTO(customerRepository.findByLastName(lastName));
    }

    public List<CustomerDTO> findAll() {
        return customerRepository
                .findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<Customer> findById(int id) {
        return customerRepository.findById(id);
    }

    public Optional<CustomerDTO> findByIdAsDTO(int id) {
        return toDTO(customerRepository.findById(id));
    }

    public List<Customer> findByIds(List<Integer> ids) {
        return customerRepository.findAllById(ids);
    }

    /*================================================================================================================*/

    @Transactional
    public CustomerDTO update(int id, CustomerCreateDTO customerCreateDTO) throws EntityMissingException {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty())
            throw new EntityMissingException(id);

        Customer customer = optionalCustomer.get();
        customer.setFirstName(customerCreateDTO.getFirstName());
        customer.setLastName(customerCreateDTO.getLastName());
        customer.setEmail(customerCreateDTO.getEmail());
        return toDTO(customer);
    }

    /*================================================================================================================*/

    @Transactional
    public void delete(int id) {
        customerRepository.deleteById(id);
    }

    /*================================================================================================================*/

    private CustomerDTO toDTO(Customer customer) {
        return new CustomerDTO(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmail());
    }

    private Optional<CustomerDTO> toDTO(Optional<Customer> customer) {
        if (customer.isEmpty())
            return Optional.empty();
        return Optional.of(toDTO(customer.get()));
    }
}
