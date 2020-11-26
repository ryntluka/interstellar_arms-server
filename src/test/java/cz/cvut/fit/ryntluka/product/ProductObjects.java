package cz.cvut.fit.ryntluka.product;

import cz.cvut.fit.ryntluka.dto.ProductCreateDTO;
import cz.cvut.fit.ryntluka.dto.ProductDTO;
import cz.cvut.fit.ryntluka.entity.Customer;
import cz.cvut.fit.ryntluka.entity.Planet;
import cz.cvut.fit.ryntluka.entity.Product;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ProductObjects {
    public static Product product1 = new Product(12,"DL-44 heavy blaster pistol", new ArrayList<>());
    public static Product product2 = new Product(31589,"X-Wing", new ArrayList<>());
    public static Product product3 = new Product(46,"AAT", new ArrayList<>());
    public static Product product4 = new Product(544932187,"Death Start", new ArrayList<>());

    /*================================================================================================================*/

    public static ProductCreateDTO createDTO(Product product) {
        return new ProductCreateDTO(
                product.getPrice(),
                product.getName(),
                product.getOrders().
                        stream().
                        map(Customer::getId).
                        collect(Collectors.toList()));
    }

    public static ProductDTO dto(Product product) {
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
