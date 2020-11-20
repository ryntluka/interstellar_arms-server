package cz.cvut.fit.ryntluka.controller;

import cz.cvut.fit.ryntluka.dto.CustomerCreateDTO;
import cz.cvut.fit.ryntluka.dto.CustomerDTO;
import cz.cvut.fit.ryntluka.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerDTO> readAll() {
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public CustomerDTO readById(@PathVariable int id) {
        return customerService.findByIdAsDTO(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(params = {"name"}) // requires title query parameter
    public CustomerDTO readByName(@RequestParam String name) {
        return customerService.findByLastName(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public CustomerDTO create(@RequestBody CustomerCreateDTO customer) throws Exception {
        return customerService.create(customer);
    }

    @PutMapping("/{id}")
    public CustomerDTO update(@PathVariable int id, @RequestBody CustomerCreateDTO customer) throws Exception {
        return customerService.update(id, customer);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        customerService.delete(id);
    }
}

