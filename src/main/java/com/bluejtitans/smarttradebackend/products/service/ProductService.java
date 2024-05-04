package com.bluejtitans.smarttradebackend.products.service;

import com.bluejtitans.smarttradebackend.exception.UserNotRegisteredException;
import com.bluejtitans.smarttradebackend.products.model.Product;
import com.bluejtitans.smarttradebackend.products.model.ProductAvailability;
import com.bluejtitans.smarttradebackend.products.repository.ProductAvailabilityRepository;
import com.bluejtitans.smarttradebackend.products.repository.ProductRepository;
import com.bluejtitans.smarttradebackend.users.model.Seller;
import com.bluejtitans.smarttradebackend.users.model.User;
import com.bluejtitans.smarttradebackend.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductAvailabilityRepository productAvailabilityRepository;
    @Autowired
    public ProductService(ProductRepository productRepository, UserRepository userRepository, ProductAvailabilityRepository productAvailabilityRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.productAvailabilityRepository = productAvailabilityRepository;
    }
    public Product getProduct(String name) {
        return productRepository.findById(name).orElse(null);
    }

    public void saveProduct(Product product, ProductAvailability productAvailability, String sellerEmail) throws UserNotRegisteredException, RuntimeException {
        try {
            Optional<Product> p = productRepository.findById(product.getName());
            Seller seller = userRepository.findSellerById(sellerEmail);
            if (seller == null) throw new UserNotRegisteredException("Seller with email " + sellerEmail + " not found");
            // If product is already published by another seller, add the new availability information to the existing product
            if (p.isPresent()) {
                productAvailability.setProduct(p.get());
                productAvailability.setSeller(seller);
            }
            // If product is not published yet, create a new product and availability
            else {
                productAvailability.setProduct(product);
                productAvailability.setSeller(seller);
                productRepository.save(product);
                productAvailabilityRepository.save(productAvailability);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unknown error ocurred while saving product");
        }
    }
    public void deleteProduct(String name) {
        productRepository.deleteById(name);
    }

    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }
}