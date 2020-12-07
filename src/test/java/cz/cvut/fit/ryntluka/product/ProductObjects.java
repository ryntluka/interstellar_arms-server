package cz.cvut.fit.ryntluka.product;

import cz.cvut.fit.ryntluka.dto.ProductCreateDTO;
import cz.cvut.fit.ryntluka.dto.ProductDTO;
import cz.cvut.fit.ryntluka.entity.Customer;
import cz.cvut.fit.ryntluka.entity.Planet;
import cz.cvut.fit.ryntluka.entity.Product;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductObjects {
    public static Product product1 = new Product(12,"DL-44 heavy blaster pistol", new ArrayList<>());
    public static Product product2 = new Product(31589,"X-Wing", new ArrayList<>());
    public static Product product3 = new Product(46,"AAT", new ArrayList<>());
    public static Product product4 = new Product(544932187,"Death Star", new ArrayList<>());

    public static java.util.List<Product> all = List.of(
            product1,
            product2,
            product3,
            product4
    );

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

    public static String toCreateJSON(Product product) {
        return "{ \"name\": \"" + product.getName() + "\", "
                + "\"price\": \"" + product.getPrice() + "\", "
                + "\"orders\": [] }";
    }
}
