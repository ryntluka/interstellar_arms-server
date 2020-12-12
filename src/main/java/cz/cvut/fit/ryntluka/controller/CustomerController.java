package cz.cvut.fit.ryntluka.controller;

import cz.cvut.fit.ryntluka.dto.CustomerCreateDTO;
import cz.cvut.fit.ryntluka.dto.CustomerDTO;
import cz.cvut.fit.ryntluka.dto.CustomerDTOAssembler;
import cz.cvut.fit.ryntluka.exceptions.EntityMissingException;
import cz.cvut.fit.ryntluka.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/customers", produces = "application/vnd-customers+json", consumes = "application/vnd-customers+json")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerDTOAssembler customerDTOAssembler;

    @Autowired
    public CustomerController(CustomerService customerService, CustomerDTOAssembler customerDTOAssembler) {
        this.customerService = customerService;
        this.customerDTOAssembler = customerDTOAssembler;
    }

    @GetMapping
    public List<CustomerDTO> findAll() {
        return customerService.findAll().stream().map(customerDTOAssembler::toModel).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CustomerDTO findById(@PathVariable int id) {
        return customerDTOAssembler.toModel(
                customerService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
        );
    }

    @GetMapping(params = {"name"})
    public List<CustomerDTO> findByName(@RequestParam String name) {
        return customerService.findAllByLastName(name).stream().map(customerDTOAssembler::toModel).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> create(@RequestBody CustomerCreateDTO customer) {
        CustomerDTO inserted = null;
        try {
            inserted = customerDTOAssembler.toModel(
                    customerService.create(customer)
            );
        } catch (EntityMissingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity
                .created(Link.of("http://localhost:8080/api/customers/" + inserted.getId()).toUri())
                .body(inserted);

    }

    @PutMapping("/{id}")
    public CustomerDTO update(@PathVariable int id, @RequestBody CustomerCreateDTO customer) {
        try {
            return customerDTOAssembler.toModel(
                    customerService.update(id, customer)
            );
        }
        catch (EntityMissingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        try {
            customerService.delete(id);
        } catch (EntityMissingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}

