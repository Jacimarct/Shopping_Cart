/*
 * package com.ecom.controller;
 * 
 * import java.util.List;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.RequestParam;
 * 
 * import com.ecom.model.Category; import com.ecom.model.Product; import
 * com.ecom.service.CategoryService; import com.ecom.service.ProductService;
 * 
 * @Controller public class HomeController {
 * 
 * @Autowired private CategoryService categoryService;
 * 
 * @Autowired private ProductService productService;
 * 
 * @GetMapping("/") public String index() { return "index"; }
 * 
 * @GetMapping("/login") public String login() { return "login"; }
 * 
 * @GetMapping("/register") public String register() { return "register"; }
 * 
 * @GetMapping("/products") public String products(Model m, @RequestParam(value=
 * "category", defaultValue = "") String category) {
 * System.out.println("category= " + category); List<Category> categories =
 * categoryService.getAllActiveCategory(); List<Product> products =
 * productService.getAllActiveProducts(category);
 * m.addAttribute("categories",categories); m.addAttribute("products",
 * products); m.addAttribute("paramValue",category); return "product"; }
 * 
 * @GetMapping("/product") public String viewProduct(Model
 * m, @RequestParam("id") Integer id) { Product product =
 * productService.getProductById(id); m.addAttribute("product", product); return
 * "view_product"; }
 * 
 * 
 * @GetMapping("/product") public String product() { return "view_product"; }
 * 
 * 
 * @GetMapping("/view_product") public String viewProduct(Model m) { Product
 * product = productService.getProductById(1); m.addAttribute("product",
 * product); return "view_product"; } }
 * 
 */

  package com.ecom.controller;
  
  import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecom.model.Category;
import com.ecom.model.Product;
import com.ecom.service.CategoryService;
import com.ecom.service.ProductService;
 
 @Controller
 public class HomeController {
     
     @Autowired
     private CategoryService categoryService;
     
     @Autowired
     private ProductService productService;
     
     @GetMapping("/")
     public String index() {
         return "index";
     }
 
     @GetMapping("/login")
     public String login() {
         return "login";
     }
 
     @GetMapping("/register")
     public String register() {
         return "register";
     }
     
     @GetMapping("/products")
     public String products(Model m, @RequestParam(value= "category", defaultValue = "") String category) {
			// System.out.println("category= " + category); 
         List<Category> categories = categoryService.getAllActiveCategory();
         List<Product> products = productService.getAllActiveProducts(category);
         m.addAttribute("categories",categories);
         m.addAttribute("products", products);
         m.addAttribute("paramValue", category);
         return "product";
     }
 
     @GetMapping("/product")
     public String viewProduct(Model m, @RequestParam("id") Integer id) { 
         Product product = productService.getProductById(id); 
         m.addAttribute("product", product); 
         return "view_product"; 
     }
 }


