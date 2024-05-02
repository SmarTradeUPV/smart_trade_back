package com.bluejtitans.smarttradebackend.products.controller;
import java.util.List;

import com.bluejtitans.smarttradebackend.products.model.IProduct;
import com.bluejtitans.smarttradebackend.products.model.Product;
import com.bluejtitans.smarttradebackend.products.model.ProductFactory;
import com.bluejtitans.smarttradebackend.products.service.ProductService;
import com.bluejtitans.smarttradebackend.products.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) { this.productService = productService; }

    @PostMapping("/{productType}")
    public ResponseEntity<String> createProduct(@PathVariable String productType, @RequestBody String productJson){
        try {
            Product product = (Product) ProductFactory.createProductFromJson(productType, productJson);
            productService.saveProduct(product);
        } catch(JsonProcessingException e){
            return ResponseEntity.badRequest().body("Formato del producto no válido");
        } catch(Exception e){
            return ResponseEntity.badRequest().body("Ha ocurrido un error inesperado");
        }
        return ResponseEntity.created(null).body("product.getName()");
    }
}

