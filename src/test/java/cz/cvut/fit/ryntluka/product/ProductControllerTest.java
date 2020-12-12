package cz.cvut.fit.ryntluka.product;

import cz.cvut.fit.ryntluka.entity.Customer;
import cz.cvut.fit.ryntluka.service.ProductService;
import org.aspectj.lang.annotation.Before;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;
import java.util.stream.Collectors;

import static cz.cvut.fit.ryntluka.customer.CustomerObjects.customer1;
import static cz.cvut.fit.ryntluka.product.ProductObjects.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    private final static String ROOT_URL = "/api/products";
    private final static String GET_ONE = "/{id}";
    private final static String CONTENT_TYPE = "application/vnd-products+json";


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void findById() throws Exception {
        given(productService.findById(product1.getId())).willReturn(Optional.of(product1));

        mockMvc.perform(
                MockMvcRequestBuilders.
                        get(ROOT_URL + GET_ONE, product1.getId()).
                        accept(CONTENT_TYPE).
                        contentType(CONTENT_TYPE)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.id", CoreMatchers.is(product1.getId()))).
                andExpect(jsonPath("$.name", CoreMatchers.is(product1.getName()))).
                andExpect(jsonPath("$.price", CoreMatchers.is(product1.getPrice()))).
                andExpect(jsonPath("$.ordersIds", CoreMatchers.is(product1.getOrders().stream().map(Customer::getId).collect(Collectors.toList())))).
                andExpect(jsonPath("$.links[0].href", CoreMatchers.endsWith(ROOT_URL + '/' + product1.getId()))).
                andExpect(jsonPath("$.links[1].href", CoreMatchers.endsWith(ROOT_URL)));

        verify(productService, atLeastOnce()).findById(product1.getId());
    }

    @Test
    void findByName() throws Exception {
        given(productService.findByName(product1.getName())).willReturn(Optional.of(product1));

        mockMvc.perform(
                MockMvcRequestBuilders.
                        get(ROOT_URL + "?name=" + product1.getName()).
                        accept(CONTENT_TYPE).
                        contentType(CONTENT_TYPE)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.id", CoreMatchers.is(product1.getId()))).
                andExpect(jsonPath("$.name", CoreMatchers.is(product1.getName()))).
                andExpect(jsonPath("$.price", CoreMatchers.is(product1.getPrice()))).
                andExpect(jsonPath("$.ordersIds", CoreMatchers.is(product1.getOrders().stream().map(Customer::getId).collect(Collectors.toList())))).
                andExpect(jsonPath("$.links[0].href", CoreMatchers.endsWith(ROOT_URL + '/' + product1.getId()))).
                andExpect(jsonPath("$.links[1].href", CoreMatchers.endsWith(ROOT_URL)));

        verify(productService, atLeastOnce()).findByName(product1.getName());
    }

    @Test
    void findAll() throws Exception {
        given(productService.findAll()).willReturn(all);

        mockMvc.perform(
                MockMvcRequestBuilders.
                        get(ROOT_URL).
                        accept(CONTENT_TYPE).
                        contentType(CONTENT_TYPE)).
                andExpect(status().isOk());

        verify(productService, atLeastOnce()).findAll();
    }

    @Test
    void create() throws Exception {
        given(productService.create(createDTO(product1))).willReturn(product1);
        mockMvc.perform(
                MockMvcRequestBuilders.
                        post(ROOT_URL).
                        contentType(CONTENT_TYPE).
                        accept(CONTENT_TYPE).
                        content(toCreateJSON(product1))).
                andExpect(status().isCreated()).
                andExpect(jsonPath("$.id", CoreMatchers.is(product1.getId()))).
                andExpect(jsonPath("$.name", CoreMatchers.is(product1.getName()))).
                andExpect(jsonPath("$.price", CoreMatchers.is(product1.getPrice()))).
                andExpect(jsonPath("$.ordersIds", CoreMatchers.is(product1
                        .getOrders()
                        .stream()
                        .map(Customer::getId)
                        .collect(Collectors.toList())))).
                andExpect(jsonPath("$.links[0].href", CoreMatchers.endsWith(ROOT_URL + '/' + product1.getId()))).
                andExpect(jsonPath("$.links[1].href", CoreMatchers.endsWith(ROOT_URL)));
        verify(productService, atLeastOnce()).create(createDTO(product1));
    }

    @Test
    void update() throws Exception {

        given(productService.update(product1.getId(), createDTO(product2))).willReturn(product2);

        mockMvc.perform(
                MockMvcRequestBuilders.
                        put(ROOT_URL + GET_ONE, product1.getId()).
                        accept(CONTENT_TYPE).
                        contentType(CONTENT_TYPE).
                        content(toCreateJSON(product2))).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.id", CoreMatchers.is(product1.getId()))).
                andExpect(jsonPath("$.name", CoreMatchers.is(product2.getName()))).
                andExpect(jsonPath("$.price", CoreMatchers.is(product2.getPrice()))).
                andExpect(jsonPath("$.ordersIds", CoreMatchers.is(product2.getOrders().stream().map(Customer::getId).collect(Collectors.toList())))).
                andExpect(jsonPath("$.links[0].href", CoreMatchers.endsWith(ROOT_URL + '/' + product2.getId()))).
                andExpect(jsonPath("$.links[1].href", CoreMatchers.endsWith(ROOT_URL)));

        verify(productService, atLeastOnce()).update(product1.getId(), createDTO(product2));
    }

    @Test
    void delete() throws Exception {
        productService.delete(product1.getId());
        mockMvc.perform(
                MockMvcRequestBuilders.
                        delete(ROOT_URL + GET_ONE, product1.getId()).
                        accept(CONTENT_TYPE).
                        contentType(CONTENT_TYPE)).
                andExpect(status().isOk());
        verify(productService, Mockito.atLeastOnce()).delete(product1.getId());
    }

    @Test
    void order() throws Exception {

        given(productService.order(customer1.getId(), product1.getId())).willReturn(product1_ordered);

        mockMvc.perform(
                MockMvcRequestBuilders.
                        put(ROOT_URL + GET_ONE + "?customerId=" + customer1.getId(), product1.getId()).
                        accept(CONTENT_TYPE).
                        contentType(CONTENT_TYPE)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.id", CoreMatchers.is(product1.getId()))).
                andExpect(jsonPath("$.name", CoreMatchers.is(product1.getName()))).
                andExpect(jsonPath("$.price", CoreMatchers.is(product1.getPrice()))).
                andExpect(jsonPath("$.ordersIds", CoreMatchers.is(product1_ordered.getOrders().stream().map(Customer::getId).collect(Collectors.toList())))).
                andExpect(jsonPath("$.links[0].href", CoreMatchers.endsWith(ROOT_URL + '/' + product1.getId()))).
                andExpect(jsonPath("$.links[1].href", CoreMatchers.endsWith(ROOT_URL)));

        verify(productService, atLeastOnce()).order(customer1.getId(), product1.getId());
    }

    @Test
    void removeOrder() throws Exception {

        given(productService.removeOrder(customer1.getId(), product1_ordered.getId())).willReturn(product1);

        mockMvc.perform(
                MockMvcRequestBuilders.
                        delete(ROOT_URL + GET_ONE + "?customerId=" + customer1.getId(), product1.getId()).
                        accept(CONTENT_TYPE).
                        contentType(CONTENT_TYPE)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.id", CoreMatchers.is(product1.getId()))).
                andExpect(jsonPath("$.name", CoreMatchers.is(product1.getName()))).
                andExpect(jsonPath("$.price", CoreMatchers.is(product1.getPrice()))).
                andExpect(jsonPath("$.ordersIds", CoreMatchers.is(product1.getOrders().stream().map(Customer::getId).collect(Collectors.toList())))).
                andExpect(jsonPath("$.links[0].href", CoreMatchers.endsWith(ROOT_URL + '/' + product1.getId()))).
                andExpect(jsonPath("$.links[1].href", CoreMatchers.endsWith(ROOT_URL)));

        verify(productService, atLeastOnce()).removeOrder(customer1.getId(), product1.getId());
    }
}
