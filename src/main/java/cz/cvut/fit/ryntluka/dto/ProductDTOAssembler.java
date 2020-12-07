package cz.cvut.fit.ryntluka.dto;

import cz.cvut.fit.ryntluka.controller.ProductController;
import cz.cvut.fit.ryntluka.entity.Customer;
import cz.cvut.fit.ryntluka.entity.Product;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductDTOAssembler extends RepresentationModelAssemblerSupport<Product, ProductDTO> {

    public ProductDTOAssembler() {
        super(ProductController.class, ProductDTO.class);
    }

    @Override
    public ProductDTO toModel(Product entity) {
        ProductDTO res = new ProductDTO(entity.getId(),
                entity.getPrice(),
                entity.getName(),
                entity.getOrders().stream().map(Customer::getId).collect(Collectors.toList()));
        res.add(
                linkTo(
                        methodOn(ProductController.class).findById(res.getId())
                ).withSelfRel()
        );
        res.add(
                linkTo(
                        methodOn(ProductController.class).findAll()
                ).withRel(IanaLinkRelations.COLLECTION)
        );
        return res;
    }
}
