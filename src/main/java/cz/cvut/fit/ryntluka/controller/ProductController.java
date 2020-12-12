package cz.cvut.fit.ryntluka.controller;

import cz.cvut.fit.ryntluka.dto.ProductCreateDTO;
import cz.cvut.fit.ryntluka.dto.ProductDTO;
import cz.cvut.fit.ryntluka.dto.ProductDTOAssembler;
import cz.cvut.fit.ryntluka.exceptions.EntityContainsElementsException;
import cz.cvut.fit.ryntluka.exceptions.EntityMissingException;
import cz.cvut.fit.ryntluka.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/products", produces = "application/vnd-products+json", consumes = "application/vnd-products+json")
public class ProductController {

    private final ProductService productService;
    private final ProductDTOAssembler productDTOAssembler;

    @Autowired
    public ProductController(ProductService productService, ProductDTOAssembler productDTOAssembler) {
        this.productService = productService;
        this.productDTOAssembler = productDTOAssembler;
    }

    @GetMapping
    public List<ProductDTO> findAll() {
        return productService.findAll().stream().map(productDTOAssembler::toModel).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductDTO findById(@PathVariable int id) {
        return productDTOAssembler.toModel(
                productService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
        );
    }

    @GetMapping(params = {"name"})
    public List<ProductDTO> findByName(@RequestParam String name) {
        return productService.findAllByName(name).stream().map(productDTOAssembler::toModel).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody ProductCreateDTO product) {
        ProductDTO inserted;
        try{
            inserted = productDTOAssembler.toModel(
                    productService.create(product)
            );
        }
        catch (EntityMissingException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity
                .created(Link.of("http://localhost:8080/api/products/" + inserted.getId()).toUri())
                .body(inserted);

    }

    @PutMapping("/{id}")
    public ProductDTO update(@PathVariable int id, @RequestBody ProductCreateDTO product) {
        try {
            return productDTOAssembler.toModel(
                    productService.update(id, product)
            );
        }
        catch (EntityMissingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        try {
            productService.delete(id);
        } catch (EntityMissingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (EntityContainsElementsException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value = {"/{id}"}, params = {"customerId"})
    public ProductDTO order(@PathVariable int id, @RequestParam int customerId) {
        try {
            return productDTOAssembler.toModel(
                    productService.order(customerId, id)
            );
        }
        catch (EntityMissingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = {"/{id}"}, params = {"customerId"})
    public ProductDTO removeOrder(@PathVariable int id, @RequestParam int customerId) {
        try {
            return productDTOAssembler.toModel(
                    productService.removeOrder(customerId, id)
            );
        }
        catch (EntityMissingException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}

