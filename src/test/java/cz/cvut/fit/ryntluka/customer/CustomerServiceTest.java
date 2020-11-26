package cz.cvut.fit.ryntluka.customer;

import cz.cvut.fit.ryntluka.dto.CustomerCreateDTO;
import cz.cvut.fit.ryntluka.dto.CustomerDTO;
import cz.cvut.fit.ryntluka.entity.Customer;
import cz.cvut.fit.ryntluka.exceptions.EntityMissingException;
import cz.cvut.fit.ryntluka.repository.CustomerRepository;
import cz.cvut.fit.ryntluka.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static cz.cvut.fit.ryntluka.customer.CustomerObjects.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CustomerServiceTest {
    private final CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceTest(CustomerService customerService) {
        this.customerService = customerService;
    }

    /*================================================================================================================*/

    @Test
    void create() {
        given(customerRepository.save(customer1)).willReturn(customer1);
        assertEquals(customer1, customerService.create(createDTO(customer1)));
        verify(customerRepository, atLeastOnce()).save(customer1);
    }

    /*================================================================================================================*/

    @Test
    void findById() {
        given(customerRepository.findById(customer1.getId())).willReturn(Optional.of(customer1));
        assertEquals(Optional.of(customer1), customerService.findById(customer1.getId()));
        verify(customerRepository, atLeastOnce()).findById(customer1.getId());
    }

    @Test
    void findByName() throws EntityMissingException {
        given(customerRepository.findByLastName(customer1.getLastName())).willReturn(Optional.of(customer1));
        Customer res = customerService.findByLastName(customer1.getLastName()).orElseThrow(EntityMissingException::new);
        assertEquals(customer1, res);
        verify(customerRepository, Mockito.atLeastOnce()).findByLastName(customer1.getLastName());
    }

    @Test
    void findAll() throws EntityMissingException {
        given(customerRepository.findAll()).willReturn(all);
        List<Customer> answer = customerService.findAll();

        if (answer.size() != all.size())
            throw new EntityMissingException();
        for (int i = 0; i < answer.size(); ++i)
            assertEquals(all.get(i), answer.get(i));

        verify(customerRepository, Mockito.atLeastOnce()).findAll();
    }

    @Test
    void findByIds() throws EntityMissingException {
        List<Integer> ids = all.stream().map(Customer::getId).collect(Collectors.toList());
        given(customerRepository.findAllById(ids)).willReturn(all);
        List<Customer> answer = customerService.findByIds(ids);

        if (answer.size() != all.size())
            throw new EntityMissingException();
        for (int i = 0; i < answer.size(); ++i)
            assertEquals(all.get(i), answer.get(i));

        verify(customerRepository, Mockito.atLeastOnce()).findAllById(ids);
    }

    /*================================================================================================================*/

    @Test
    void update() throws EntityMissingException {
        CustomerCreateDTO newData = createDTO(customer2);
        given(customerRepository.findById(customer1.getId())).willReturn(Optional.of(customer1));
        Customer updated = customerService.update(customer1.getId(), newData);

        assertEquals(updated.getEmail(), newData.getEmail());
        assertEquals(updated.getFirstName(), newData.getFirstName());
        assertEquals(updated.getLastName(), newData.getLastName());
        assertEquals(updated.getId(), customer1.getId());

        verify(customerRepository, Mockito.atLeastOnce()).findById(customer1.getId());
    }

    /*================================================================================================================*/

    @Test
    void delete() {
        customerRepository.deleteById(customer1.getId());
        customerService.delete(customer1.getId());
        assertEquals(Optional.empty(), customerService.findById(customer1.getId()));
        verify(customerRepository, Mockito.atLeastOnce()).deleteById(customer1.getId());

    }
}
