package cz.cvut.fit.ryntluka.customer;

import cz.cvut.fit.ryntluka.service.CustomerService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static cz.cvut.fit.ryntluka.customer.CustomerObjects.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    private final static String ROOT_URL = "/api/customers";
    private final static String GET_ONE = "/{id}";
    private final static String CONTENT_TYPE = "application/vnd-customers+json";


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
                andExpect(jsonPath("$.planetId", CoreMatchers.is(customer1.getPlanet().getId()))).
                andExpect(jsonPath("$.links[0].href", CoreMatchers.endsWith(ROOT_URL + '/' + customer1.getId()))).
                andExpect(jsonPath("$.links[1].href", CoreMatchers.endsWith(ROOT_URL)));

        verify(customerService, atLeastOnce()).findById(customer1.getId());
    }

    @Test
    void findByName() throws Exception {
        given(customerService.findAllByLastName(customer1.getLastName())).willReturn(List.of(customer1));

        mockMvc.perform(
                MockMvcRequestBuilders.
                        get(ROOT_URL + "?name=" + customer1.getLastName()).
                        accept(CONTENT_TYPE).
                        contentType(CONTENT_TYPE)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.[0].id", CoreMatchers.is(customer1.getId()))).
                andExpect(jsonPath("$.[0].firstName", CoreMatchers.is(customer1.getFirstName()))).
                andExpect(jsonPath("$.[0].lastName", CoreMatchers.is(customer1.getLastName()))).
                andExpect(jsonPath("$.[0].email", CoreMatchers.is(customer1.getEmail()))).
                andExpect(jsonPath("$.[0].planetId", CoreMatchers.is(customer1.getPlanet().getId()))).
                andExpect(jsonPath("$.[0].links[0].href", CoreMatchers.endsWith(ROOT_URL + '/' + customer1.getId()))).
                andExpect(jsonPath("$.[0].links[1].href", CoreMatchers.endsWith(ROOT_URL)));

        verify(customerService, atLeastOnce()).findAllByLastName(customer1.getLastName());
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
        given(customerService.create(createDTO(customer1))).willReturn(customer1);
        mockMvc.perform(
                MockMvcRequestBuilders.
                        post(ROOT_URL).
                        contentType(CONTENT_TYPE).
                        accept(CONTENT_TYPE).
                        content(toCreateJSON(customer1))).
                andExpect(status().isCreated()).
                andExpect(jsonPath("$.id", CoreMatchers.is(customer1.getId()))).
                andExpect(jsonPath("$.firstName", CoreMatchers.is(customer1.getFirstName()))).
                andExpect(jsonPath("$.lastName", CoreMatchers.is(customer1.getLastName()))).
                andExpect(jsonPath("$.email", CoreMatchers.is(customer1.getEmail()))).
                andExpect(jsonPath("$.planetId", CoreMatchers.is(customer1.getPlanet().getId()))).
                andExpect(jsonPath("$.links[0].href", CoreMatchers.endsWith(ROOT_URL + '/' + customer1.getId()))).
                andExpect(jsonPath("$.links[1].href", CoreMatchers.endsWith(ROOT_URL)));
        verify(customerService, atLeastOnce()).create(createDTO(customer1));
    }

    @Test
    void update() throws Exception {

        given(customerService.update(customer1.getId(), createDTO(customer2))).willReturn(customer2);

        mockMvc.perform(
                MockMvcRequestBuilders.
                        put(ROOT_URL + GET_ONE, customer1.getId()).
                        accept(CONTENT_TYPE).
                        contentType(CONTENT_TYPE).
                        content(toCreateJSON(customer2))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.id", CoreMatchers.is(customer1.getId()))).
                andExpect(jsonPath("$.firstName", CoreMatchers.is(customer2.getFirstName()))).
                andExpect(jsonPath("$.lastName", CoreMatchers.is(customer2.getLastName()))).
                andExpect(jsonPath("$.email", CoreMatchers.is(customer2.getEmail()))).
                andExpect(jsonPath("$.planetId", CoreMatchers.is(customer1.getPlanet().getId()))).
                andExpect(jsonPath("$.links[0].href", CoreMatchers.endsWith(ROOT_URL + '/' + customer1.getId()))).
                andExpect(jsonPath("$.links[1].href", CoreMatchers.endsWith(ROOT_URL)));

        verify(customerService, atLeastOnce()).update(customer1.getId(), createDTO(customer2));
    }

    @Test
    void delete() throws Exception {
        customerService.delete(customer1.getId());
        mockMvc.perform(
                MockMvcRequestBuilders.
                        delete(ROOT_URL + GET_ONE, customer1.getId()).
                        accept(CONTENT_TYPE).
                        contentType(CONTENT_TYPE)).
                andExpect(status().isOk());
        verify(customerService, Mockito.atLeastOnce()).delete(customer1.getId());
    }
}
