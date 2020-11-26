package cz.cvut.fit.ryntluka.controller;

import cz.cvut.fit.ryntluka.dto.ProductCreateDTO;
import cz.cvut.fit.ryntluka.dto.ProductDTO;
import cz.cvut.fit.ryntluka.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDTO> readAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ProductDTO readById(@PathVariable int id) {
        return productService.findByIdAsDTO(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(params = {"name"})
    public ProductDTO readByName(@RequestParam String name) {
        return productService.findByName(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ProductDTO create(@RequestBody ProductCreateDTO product) throws Exception {
        return productService.create(product);
    }

    @PutMapping("/{id}")
    public ProductDTO update(@PathVariable int id, @RequestBody ProductCreateDTO product) throws Exception {
        return productService.update(id, product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        productService.delete(id);
    }
}

