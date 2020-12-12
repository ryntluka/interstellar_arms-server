package cz.cvut.fit.ryntluka.service;

import cz.cvut.fit.ryntluka.dto.PlanetDTO;
import cz.cvut.fit.ryntluka.dto.ProductCreateDTO;
import cz.cvut.fit.ryntluka.dto.ProductDTO;
import cz.cvut.fit.ryntluka.entity.Customer;
import cz.cvut.fit.ryntluka.entity.Planet;
import cz.cvut.fit.ryntluka.entity.Product;
import cz.cvut.fit.ryntluka.exceptions.EntityContainsElementsException;
import cz.cvut.fit.ryntluka.exceptions.EntityMissingException;
import cz.cvut.fit.ryntluka.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CustomerService customerService;

    @Autowired
    public ProductService(ProductRepository productRepository, CustomerService customerService) {
        this.productRepository = productRepository;
        this.customerService = customerService;
    }

    /*================================================================================================================*/

    @Transactional
    public Product create(ProductCreateDTO productCreateDTO) throws EntityMissingException {

        List<Customer> customers = customerService.findByIds(productCreateDTO.getOrdersIds());
        if (customers.size() != productCreateDTO.getOrdersIds().size())
            throw new EntityMissingException();
        return productRepository.save(
                new Product(productCreateDTO.getPrice(), productCreateDTO.getName(), customers)
        );
    }

    /*================================================================================================================*/

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findByIds(List<Integer> ids) {
        return productRepository.findAllById(ids);
    }

    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    public Optional<Product> findByName (String name) {
        return productRepository.findByName(name);
    }

    /*================================================================================================================*/

    @Transactional
    public Product update(int id, ProductCreateDTO productCreateDTO) throws EntityMissingException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty())
            throw new EntityMissingException(id);

        List<Customer> customers = customerService.findByIds(productCreateDTO.getOrdersIds());
        if (customers.size() != productCreateDTO.getOrdersIds().size())
            throw new EntityMissingException();

        Product product = optionalProduct.get();
        product.setPrice(productCreateDTO.getPrice());
        product.setName(productCreateDTO.getName());
        product.setOrders(customers);
        return product;
    }


    /*================================================================================================================*/

    @Transactional
    public Product order(int customerId, int productId) throws EntityMissingException {
        Product product = productRepository.findById(productId).orElseThrow(EntityMissingException::new);
        product.getOrders().add(customerService.findById(customerId).orElseThrow(EntityMissingException::new));
        return product;
    }

    @Transactional
    public Product removeOrder(int customerId, int productId) throws EntityMissingException {
        Product product = productRepository.findById(productId).orElseThrow(EntityMissingException::new);
        Customer customer = customerService.findById(customerId).orElseThrow(EntityMissingException::new);
        if (!product.getOrders().contains(customer))
            throw new EntityMissingException();

        List<Customer> orders = new ArrayList<>(product.getOrders());
        Iterator<Customer> itr = orders.listIterator();
        while (itr.hasNext()) {
            if (itr.next().getId() == customerId) {
                itr.remove();
                break;
            }
        }
        product.setOrders(orders);
        return product;
    }

    @Transactional
    public void delete(int id) throws EntityMissingException, EntityContainsElementsException {
        if (productRepository.findById(id).isEmpty())
            throw new EntityMissingException();
        if (!productRepository.findById(id).get().getOrders().isEmpty())
            throw new EntityContainsElementsException();
        productRepository.deleteById(id);
    }
}
