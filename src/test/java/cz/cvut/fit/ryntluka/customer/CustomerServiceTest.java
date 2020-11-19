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
import java.util.List;
import java.util.Optional;

import static cz.cvut.fit.ryntluka.customer.CustomerObjects.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        equalDTO(dto(customer1), customerService.create(createDTO(customer1)));
    }

    /*================================================================================================================*/

    @Test
    void findById() {
        Customer customer = new Customer("Lukas", "Rynt", "ryntluka@fit.cvut.cz");
        given(customerRepository.findById(customer.getId())).willReturn(Optional.of(customer));
        assertEquals(Optional.of(customer), customerService.findById(customer.getId()));
        verify(customerRepository, Mockito.atLeastOnce()).findById(customer.getId());
    }

    @Test
    void findByName() throws EntityMissingException {
        given(customerRepository.findByLastName(customer1.getLastName())).willReturn(Optional.of(customer1));
        CustomerDTO customerDTO = dto(customer1);
        CustomerDTO res = customerService.findByLastName(customer1.getLastName()).orElseThrow(EntityMissingException::new);
        equalDTO(customerDTO, res);
        verify(customerRepository, Mockito.atLeastOnce()).findByLastName(customer1.getLastName());
    }

    @Test
    void findByIdAsDTO() throws EntityMissingException {
        given(customerRepository.findById(customer1.getId())).willReturn(Optional.of(customer1));
        CustomerDTO res = customerService.findByIdAsDTO(customer1.getId()).orElseThrow(EntityMissingException::new);
        equalDTO(res, dto(customer1));
        verify(customerRepository, Mockito.atLeastOnce()).findById(customer1.getId());
    }

    @Test
    void findAll() throws EntityMissingException {
        List<Customer> expectedList = new ArrayList<>() {{
            add(customer1);
            add(customer2);
            add(customer3);
            add(customer4);
        }};
        given(customerRepository.findAll()).willReturn(expectedList);
        List<CustomerDTO> answer = customerService.findAll();

        if (answer.size() != expectedList.size())
            throw new EntityMissingException();
        for (int i = 0; i < answer.size(); ++i)
            equalDTO(dto(expectedList.get(i)), answer.get(i));

        verify(customerRepository, Mockito.atLeastOnce()).findAll();
    }

    @Test
    void findByIds() throws EntityMissingException {
        List<Customer> expectedList = new ArrayList<>() {{
            add(customer1);
            add(customer2);
        }};
        List<Integer> ids = new ArrayList<>() {{
            add(customer1.getId());
            add(customer2.getId());
        }};
        given(customerRepository.findAllById(ids)).willReturn(expectedList);
        List<Customer> answer = customerService.findByIds(ids);

        if (answer.size() != expectedList.size())
            throw new EntityMissingException();
        for (int i = 0; i < answer.size(); ++i)
            equal(expectedList.get(i), answer.get(i));

        verify(customerRepository, Mockito.atLeastOnce()).findAllById(ids);
    }

    /*================================================================================================================*/

    @Test
    void update() throws EntityMissingException {
        CustomerCreateDTO newData = createDTO(customer2);
        given(customerRepository.findById(customer1.getId())).willReturn(Optional.of(customer1));
        CustomerDTO updated = customerService.update(customer1.getId(), newData);

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

    /*================================================================================================================*/

    private CustomerCreateDTO createDTO(Customer customer) {
        return new CustomerCreateDTO(customer.getFirstName(), customer.getLastName(), customer.getEmail());
    }

    private CustomerDTO dto(Customer customer) {
        return new CustomerDTO(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmail());
    }

    private void equalDTO(CustomerDTO c1, CustomerDTO c2) {
        assertEquals(c1.getId(), c2.getId());
        assertEquals(c1.getFirstName(), c2.getFirstName());
        assertEquals(c1.getLastName(), c2.getLastName());
        assertEquals(c1.getEmail(), c2.getEmail());
    }

    private void equal(Customer c1, Customer c2) {
        assertEquals(c1.getId(), c2.getId());
        assertEquals(c1.getFirstName(), c2.getFirstName());
        assertEquals(c1.getLastName(), c2.getLastName());
        assertEquals(c1.getEmail(), c2.getEmail());
    }
}
