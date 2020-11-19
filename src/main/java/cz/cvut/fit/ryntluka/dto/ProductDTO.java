package cz.cvut.fit.ryntluka.dto;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;

public class ProductDTO {

    private final int id;
    private final int price;
    private final String name;
    private final List<Integer> ordersIds;

    public ProductDTO(int id, int price, String name, List<Integer> ordersIds) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.ordersIds = ordersIds;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getOrdersIds() {
        return ordersIds;
    }
}
