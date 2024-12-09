package com.ecom.service.impl;
  
	import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.Product;
import com.ecom.repository.ProductRepository;
import com.ecom.service.ProductService;
 
 @Service
 public class ProductServiceImpl implements ProductService {
 
     @Autowired
     private ProductRepository productRepository;
 
     @Override
     public Product saveProduct(Product product) {
         return productRepository.save(product);
     }
 
     @Override
     public List<Product> getAllProducts() {
         return productRepository.findAll();
     }
 
     @Override
     public Boolean deleteProduct(Integer id) {
         if(productRepository.existsById(id)) {
             productRepository.deleteById(id);
             return true;
         }
         return false;
     }
 
     @Override
     public Product getProductById(Integer id) {
         return productRepository.findById(id).orElse(null);
     }
 
     @Override
     public Product updateProduct(Product product, MultipartFile file) {
         // Implementar lógica de atualização com file
         return productRepository.save(product);
     }
 
     @Override
     public List<Product> getAllActiveProducts(String category) {
         if (category.isEmpty()) {
        	 return productRepository.findAllByIsActiveTrue();
         } else {
        	 return productRepository.findByCategory(category);
         }
     }
 }





