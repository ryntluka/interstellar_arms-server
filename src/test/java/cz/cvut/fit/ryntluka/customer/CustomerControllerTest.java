package cz.cvut.fit.ryntluka.customer;

import cz.cvut.fit.ryntluka.controller.CustomerController;
import cz.cvut.fit.ryntluka.entity.Customer;
import cz.cvut.fit.ryntluka.service.CustomerService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {
    private CustomerController customerController;

//    na semestrálku mockovat repository a testovat service
//    testovat vše - tedy konkrétní třídy, ne interface, ne gettery a settery
//    @Autowired
//    public CustomerControllerTest(CustomerController customerController) {
//        this.customerController = customerController;
//    }

    // takhle lepší pro rest api
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

//    @Test
//    void readOne() throws Exception {
//        Customer customer = new Customer("Lukas", "Rynt", "ryntluka@fit.cvut.cz");
//        BDDMockito.given(customerService.findById(customer.getId())).willReturn(Optional.of(customer));
////        Assertions.assertEquals(customer, customerController.byId(customer.getId()));
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.
//                        get("/customer/{id}", customer.getFirstName()).
//                        accept("json").
//                        contentType("json")
//        ).andExpect(MockMvcResultMatchers.status().isOk()).
//                andExpect(MockMvcResultMatchers.jsonPath(".$firstName", CoreMatchers.is(customer.getFirstName())));
//
//        Mockito.verify(customerService, Mockito.atLeastOnce()).findById(customer.getId());
//    }

//    @Test
//    void postNew() {
//        Customer customer = new Customer("Lukas", "Rynt", "ryntluka@fit.cvut.cz");
//        // willThrow je negativní
//        BDDMockito.given(customerService.create(customer));
//        mockMvc.perform(
//                MockMvcRequestBuilders.
//                        post("/").
//                        contentType("").
//                        accept("").
//                        content("{firstName: \"Lukas\", lastName: \"Rynt\"} ")
//        ).andExpect(MockMvcResultMatchers.status().isCreated())
//        .andExpect(MockMvcResultMatchers.header().exists("Location"));
//    }
}
