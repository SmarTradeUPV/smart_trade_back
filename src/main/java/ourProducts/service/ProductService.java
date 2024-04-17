package ourProducts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import ourProducts.model.*;
import ourProducts.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void createProduct(Product product) {
        productRepository.save(product);
    }
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }
    public void updateProduct(Long id, Product product) {
        Product p = productRepository.findById(id).orElse(null);
        if (p != null) {
            p.setName(product.getName());
            p.setPrice(product.getPrice());
            p.setQuantity(product.getQuantity());
            p.setDescription(product.getDescription());
            p.setDataSheet(product.getDataSheet());
            p.setPhotos(product.getPhotos());
            productRepository.save(p);
        }
    }
}