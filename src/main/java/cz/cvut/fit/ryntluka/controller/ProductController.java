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
public class ProductController {

//    private final ProductService productService;
//
//    @Autowired
//    public ProductController(ProductService productService) {
//        this.productService = productService;
//    }

//    @GetMapping("/product/all")
//    public List<ProductDTO> all() {
//        return productService.findAll();
//    }
//
//    @GetMapping("/product/{id}")
//    public ProductDTO byId(@PathVariable int id) {
//        return productService.findByIdAsDTO(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//    }
//
//    @PostMapping("/product")
//    public ProductDTO save(@RequestBody ProductCreateDTO product) throws Exception {
//        return productService.create(product);
//    }
//
//    @PutMapping("/product/{id}")
//    public ProductDTO save(@PathVariable int id, @RequestBody ProductCreateDTO product) throws Exception {
//        return productService.update(id, product);
//    }
}
