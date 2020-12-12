package cz.cvut.fit.ryntluka.dto;

import cz.cvut.fit.ryntluka.controller.CustomerController;
import cz.cvut.fit.ryntluka.entity.Customer;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerDTOAssembler extends RepresentationModelAssemblerSupport<Customer, CustomerDTO> {

    public CustomerDTOAssembler() {
        super(CustomerController.class, CustomerDTO.class);
    }

    @Override
    public CustomerDTO toModel(Customer entity) {
        CustomerDTO res = new CustomerDTO(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.getPlanet().getId());
        res.add(
                linkTo(
                        methodOn(CustomerController.class).findById(res.getId())
                ).withSelfRel()
        );
        res.add(
                linkTo(
                        methodOn(CustomerController.class).findAll()
                ).withRel(IanaLinkRelations.COLLECTION)
        );
        return res;
    }
}
