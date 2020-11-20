package cz.cvut.fit.ryntluka.product;

import cz.cvut.fit.ryntluka.dto.ProductCreateDTO;
import cz.cvut.fit.ryntluka.dto.ProductDTO;
import cz.cvut.fit.ryntluka.entity.Customer;
import cz.cvut.fit.ryntluka.entity.Product;
import cz.cvut.fit.ryntluka.exceptions.EntityMissingException;
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

import static cz.cvut.fit.ryntluka.product.ProductObjects.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ProductServiceTest {
    private final ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    public ProductServiceTest(ProductService productService) {
        this.productService = productService;
    }

    /*================================================================================================================*/

    @Test
    void create() throws EntityMissingException {
        given(productRepository.save(product1)).willReturn(product1);
        assertEquals(dto(product1), productService.create(createDTO(product1)));
    }

    /*================================================================================================================*/

    @Test
    void findById() {
        given(productRepository.findById(product1.getId())).willReturn(Optional.of(product1));
        assertEquals(Optional.of(product1), productService.findById(product1.getId()));
        verify(productRepository, Mockito.atLeastOnce()).findById(product1.getId());
    }

    @Test
    void findByName() throws EntityMissingException {
        given(productRepository.findByName(product1.getName())).willReturn(Optional.of(product1));
        ProductDTO productDTO = dto(product1);
        ProductDTO res = productService.findByName(product1.getName()).orElseThrow(EntityMissingException::new);
        assertEquals(productDTO, res);
        verify(productRepository, Mockito.atLeastOnce()).findByName(product1.getName());
    }

    @Test
    void findByIdAsDTO() throws EntityMissingException {
        given(productRepository.findById(product1.getId())).willReturn(Optional.of(product1));
        ProductDTO res = productService.findByIdAsDTO(product1.getId()).orElseThrow(EntityMissingException::new);
        assertEquals(res, dto(product1));
        verify(productRepository, Mockito.atLeastOnce()).findById(product1.getId());
    }

    @Test
    void findAll() throws EntityMissingException {
        List<Product> expectedList = new ArrayList<>() {{
            add(product1);
            add(product2);
            add(product3);
            add(product4);
        }};
        given(productRepository.findAll()).willReturn(expectedList);
        List<ProductDTO> answer = productService.findAll();

        if (answer.size() != expectedList.size())
            throw new EntityMissingException();
        for (int i = 0; i < answer.size(); ++i)
            assertEquals(dto(expectedList.get(i)), answer.get(i));

        verify(productRepository, Mockito.atLeastOnce()).findAll();
    }

    @Test
    void findByIds() throws EntityMissingException {
        List<Product> expectedList = new ArrayList<>() {{
            add(product1);
            add(product2);
        }};
        List<Integer> ids = new ArrayList<>() {{
            add(product1.getId());
            add(product2.getId());
        }};
        given(productRepository.findAllById(ids)).willReturn(expectedList);
        List<Product> answer = productService.findByIds(ids);

        if (answer.size() != expectedList.size())
            throw new EntityMissingException();
        for (int i = 0; i < answer.size(); ++i)
            assertEquals(expectedList.get(i), answer.get(i));

        verify(productRepository, Mockito.atLeastOnce()).findAllById(ids);
    }

    /*================================================================================================================*/

    @Test
    void update() throws EntityMissingException {
        ProductCreateDTO newData = createDTO(product2);
        given(productRepository.findById(product1.getId())).willReturn(Optional.of(product1));
        ProductDTO updated = productService.update(product1.getId(), newData);

        assertEquals(updated.getName(), newData.getName());
        assertEquals(updated.getPrice(), newData.getPrice());
        assertEquals(updated.getOrdersIds(), newData.getOrdersIds());
        assertEquals(updated.getId(), product1.getId());

        verify(productRepository, Mockito.atLeastOnce()).findById(product1.getId());
    }

    /*================================================================================================================*/

    @Test
    void delete() {
        productRepository.deleteById(product1.getId());
        productService.delete(product1.getId());
        assertEquals(Optional.empty(), productService.findById(product1.getId()));
        verify(productRepository, Mockito.atLeastOnce()).deleteById(product1.getId());

    }

    /*================================================================================================================*/

    private ProductCreateDTO createDTO(Product product) {
        return new ProductCreateDTO(
                product.getPrice(),
                product.getName(),
                product.getOrders().
                        stream().
                        map(Customer::getId).
                        collect(Collectors.toList()));
    }

    private ProductDTO dto(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getPrice(),
                product.getName(),
                product.getOrders().
                        stream().
                        map(Customer::getId).
                        collect(Collectors.toList()));
    }
}
