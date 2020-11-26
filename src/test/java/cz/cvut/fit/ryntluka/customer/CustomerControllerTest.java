package cz.cvut.fit.ryntluka.customer;

import cz.cvut.fit.ryntluka.dto.CustomerCreateDTO;
import cz.cvut.fit.ryntluka.entity.Customer;
import cz.cvut.fit.ryntluka.service.CustomerService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static cz.cvut.fit.ryntluka.customer.CustomerObjects.*;
import static net.minidev.json.JSONValue.toJSONString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.data.domain.Pageable;


@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    private final static String ROOT_URL = "/api/customers";
    private final static String GET_ONE = "/{id}";
    private final static String CONTENT_TYPE = "application/vnd-characters+json";


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;


    @Test
    void findById() throws Exception {
        given(customerService.findById(customer1.getId())).willReturn(Optional.of(customer1));

        mockMvc.perform(
                MockMvcRequestBuilders.
                        get(ROOT_URL + GET_ONE, customer1.getId()).
                        accept(CONTENT_TYPE).
                        contentType(CONTENT_TYPE)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.id", CoreMatchers.is(customer1.getId()))).
                andExpect(jsonPath("$.firstName", CoreMatchers.is(customer1.getFirstName()))).
                andExpect(jsonPath("$.lastName", CoreMatchers.is(customer1.getLastName()))).
                andExpect(jsonPath("$.email", CoreMatchers.is(customer1.getEmail()))).
                andExpect(jsonPath("$.links[0].href", CoreMatchers.endsWith(ROOT_URL + '/' + customer1.getId()))).
                andExpect(jsonPath("$.links[1].href", CoreMatchers.endsWith(ROOT_URL)));

        verify(customerService, atLeastOnce()).findById(customer1.getId());
    }

    @Test
    void findAll() throws Exception {
        given(customerService.findAll()).willReturn(all);

        mockMvc.perform(
                MockMvcRequestBuilders.
                        get(ROOT_URL).
                        accept(CONTENT_TYPE).
                        contentType(CONTENT_TYPE)).
                andExpect(status().isOk());

        verify(customerService, atLeastOnce()).findAll();
    }

    @Test
    void create() throws Exception {
        Customer customer = new Customer("Lukáš", "Rynt", "ryntluka@fit.cvut.cz");
        given(customerService.create(createDTO(customer))).willReturn(customer);
        mockMvc.perform(
                MockMvcRequestBuilders.
                        post(ROOT_URL).
                        contentType(CONTENT_TYPE).
                        accept(CONTENT_TYPE).
                        content(" { \"firstName\": \"Lukáš\", \"lastName\": \"Rynt\", \"email\": \"ryntluka@fit.cvut.cz\" } ")).
                andExpect(status().isCreated());
        verify(customerService, atLeastOnce()).create(createDTO(customer));
    }
}
