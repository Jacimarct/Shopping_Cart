/*
 * package com.ecom.service;
 * 
 * import java.util.List;
 * 
 * import com.ecom.model.Product;
 * 
 * public interface ProductService {
 * 
 * public Product saveProduct(Product product);
 * 
 * public List<Product> getAllProducts();
 * 
 * public Boolean deleteProduct(Integer id);
 * 
 * }
 */
package com.ecom.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.Product;

public interface ProductService {
    Product saveProduct(Product product);
    List<Product> getAllProducts();
    Boolean deleteProduct(Integer id);
    Product getProductById(Integer id);
    Product updateProduct(Product product, MultipartFile file);
    List<Product> getAllActiveProducts(String category);
}