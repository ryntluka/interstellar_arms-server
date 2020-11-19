package cz.cvut.fit.ryntluka.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private int price;

    @NotNull
    private String name;

    @ManyToMany
    @JoinTable(
            name = "product_customer",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private List<Customer> orders;

    public Product() {
    }

    public Product(int price, String name, List<Customer> orders) {
        this.price = price;
        this.name = name;
        this.orders = orders;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Customer> getOrders() {
        return orders;
    }

    public void setOrders(List<Customer> orders) {
        this.orders = orders;
    }
}
