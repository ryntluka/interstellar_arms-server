package cz.cvut.fit.ryntluka.product;

import cz.cvut.fit.ryntluka.dto.ProductCreateDTO;
import cz.cvut.fit.ryntluka.dto.ProductDTO;
import cz.cvut.fit.ryntluka.entity.Customer;
import cz.cvut.fit.ryntluka.entity.Product;
import cz.cvut.fit.ryntluka.exceptions.EntityContainsElementsException;
import cz.cvut.fit.ryntluka.exceptions.EntityMissingException;
import cz.cvut.fit.ryntluka.repository.CustomerRepository;
import cz.cvut.fit.ryntluka.repository.ProductRepository;
import cz.cvut.fit.ryntluka.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static cz.cvut.fit.ryntluka.customer.CustomerObjects.customer1;
import static cz.cvut.fit.ryntluka.product.ProductObjects.all;
import static cz.cvut.fit.ryntluka.product.ProductObjects.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ProductServiceTest {
    private final ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    public ProductServiceTest(ProductService productService) {
        this.productService = productService;
    }

    /*================================================================================================================*/

    @Test
    void create() throws EntityMissingException {
        given(productRepository.save(product1)).willReturn(product1);
        assertEquals(product1, productService.create(createDTO(product1)));
    }

    /*================================================================================================================*/

    @Test
    void findById() {
        given(productRepository.findById(product1.getId())).willReturn(Optional.of(product1));
        assertEquals(Optional.of(product1), productService.findById(product1.getId()));
        verify(productRepository, atLeastOnce()).findById(product1.getId());
    }

    @Test
    void findByName() throws EntityMissingException {
        given(productRepository.findByName(product1.getName())).willReturn(Optional.of(product1));
        Product res = productService.findByName(product1.getName()).orElseThrow(EntityMissingException::new);
        assertEquals(product1, res);
        verify(productRepository, atLeastOnce()).findByName(product1.getName());
    }

    @Test
    void findAll() throws EntityMissingException {
        given(productRepository.findAll()).willReturn(all);
        List<Product> answer = productService.findAll();

        if (answer.size() != all.size())
            throw new EntityMissingException();
        for (int i = 0; i < answer.size(); ++i)
            assertEquals(all.get(i), answer.get(i));

        verify(productRepository, atLeastOnce()).findAll();
    }

    @Test
    void findByIds() throws EntityMissingException {
        List<Integer> ids = all.stream().map(Product::getId).collect(Collectors.toList());
        given(productRepository.findAllById(ids)).willReturn(all);
        List<Product> answer = productService.findByIds(ids);

        if (answer.size() != all.size())
            throw new EntityMissingException();
        for (int i = 0; i < answer.size(); ++i)
            assertEquals(all.get(i), answer.get(i));

        verify(productRepository, atLeastOnce()).findAllById(ids);
    }

    /*================================================================================================================*/

    @Test
    void update() throws EntityMissingException {
        ProductCreateDTO newData = createDTO(product2);
        given(productRepository.findById(product1.getId())).willReturn(Optional.of(product1));
        Product updated = productService.update(product1.getId(), newData);

        assertEquals(updated.getName(), newData.getName());
        assertEquals(updated.getPrice(), newData.getPrice());
        assertEquals(updated.getOrders().stream().map(Customer::getId).collect(Collectors.toList()), newData.getOrdersIds());
        assertEquals(updated.getId(), product1.getId());

        verify(productRepository, atLeastOnce()).findById(product1.getId());
    }

    /*================================================================================================================*/

    @Test
    void delete() throws EntityMissingException, EntityContainsElementsException {
        productRepository.deleteById(product1.getId());
        productService.delete(product1.getId());
        assertEquals(Optional.empty(), productService.findById(product1.getId()));
        verify(productRepository, atLeastOnce()).deleteById(product1.getId());
    }

    /*================================================================================================================*/

    @Test
    void order() throws EntityMissingException {
        given(productRepository.findById(product1.getId())).willReturn(Optional.of(product1));
        given(customerRepository.findById(customer1.getId())).willReturn(Optional.of(customer1));

        List<Customer> expectedList = product1.getOrders();
        expectedList.add(customer1);
        Product updated = productService.order(customer1.getId(), product1.getId());
        assertEquals(product1.getId(), updated.getId());
        assertEquals(product1.getName(), updated.getName());
        assertEquals(expectedList, updated.getOrders());

        verify(productRepository, atLeastOnce()).findById(product1.getId());
        verify(customerRepository, atLeastOnce()).findById(customer1.getId());
    }

    @Test
    void removeOrder() throws EntityMissingException {
        given(productRepository.findById(product1_ordered.getId())).willReturn(Optional.of(product1_ordered));
        given(customerRepository.findById(customer1.getId())).willReturn(Optional.of(customer1));

        List<Customer> expectedList = product1.getOrders();
        Product updated = productService.removeOrder(customer1.getId(), product1_ordered.getId());
        assertEquals(product1.getId(), updated.getId());
        assertEquals(product1.getName(), updated.getName());
        assertEquals(expectedList, updated.getOrders());

        verify(productRepository, atLeastOnce()).findById(product1_ordered.getId());
        verify(customerRepository, atLeastOnce()).findById(customer1.getId());
    }
}
