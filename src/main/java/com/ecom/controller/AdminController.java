package com.ecom.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.Category;
import com.ecom.model.Product;
import com.ecom.service.CategoryService;
import com.ecom.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	/*                                            */
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	/*                                                                             */

	@Autowired
	private CategoryService categoryService;

	/*
	 * @Autowired private CommonServiceImpl commonServiceImpl;
	 */

	@Autowired
	private ProductService productService;

	@GetMapping("/")
	public String index() {
		return "admin/index";
	}

	@GetMapping("/loadAddProduct")
	public String loadAddProduct(Model m) {
		List<Category> categories = categoryService.getAllCategory();
		m.addAttribute("categories", categories);
		return "admin/add_product";
	}

	@GetMapping("/category")
	public String category(Model m) {
		m.addAttribute("categorys", categoryService.getAllCategory());
		return "admin/category";
	}
	
	@PostMapping("/saveCategory")
	public String saveCategory(@ModelAttribute Category category, 
	                           @RequestParam("file") MultipartFile file, 
	                           HttpSession session) throws IOException {

	    logger.info("Iniciando o método saveCategory");

	    String imageName = (file != null && !file.isEmpty()) ? file.getOriginalFilename() : "default.jpg";
	    category.setImageName(imageName);

	    logger.info("Nome da categoria: {}", category.getName());
	    logger.info("Nome da imagem: {}", imageName);

	    Boolean existCategory = categoryService.existCategory(category.getName());
	    if (existCategory) {
	        session.setAttribute("errorMsg", "O nome da categoria já existe!");
	        logger.warn("A categoria já existe: {}", category.getName());
	    } else {
	        Category saveCategory = categoryService.saveCategory(category);
	        if (!ObjectUtils.isEmpty(saveCategory)) {
	            if (!file.isEmpty()) {
	                File saveFile = new ClassPathResource("static/img").getFile();
	                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "category_img" + File.separator + file.getOriginalFilename());
	                logger.info("Salvando a imagem em: {}", path);
	                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
	            }
	            session.setAttribute("succMsg", "Salvo com sucesso!");
	            logger.info("Categoria salva com sucesso!");
	        } else {
	            session.setAttribute("errorMsg", "Não salvou! Erro interno do servidor");
	            logger.error("Erro ao salvar a categoria: {}", category.getName());
	        }
	    }

	    return "redirect:/admin/category";
	}
	

	@GetMapping("/deleteCategory/{id}")
	public String deleteCategory(@PathVariable int id, HttpSession session) {
		Boolean deleteCategory = categoryService.deleteCategory(id);
		if (deleteCategory) {
			session.setAttribute("succMsg", "Categoria excluida com sucesso!");
		} else {
			session.setAttribute("errorMsg", "Não excluiu! Erro interno do servidor");
		}
		return "redirect:/admin/category";
	}

	@GetMapping("/loadEditCategory/{id}")
	public String loadEditCategory(@PathVariable int id, Model m) {
		m.addAttribute("category", categoryService.getCategoryById(id));
		return "admin/edit_category";
	}

	@PostMapping("/updateCategory")
	public String updateCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file,
			HttpSession session) throws IOException {
		Category oldCategory = categoryService.getCategoryById(category.getId());

		String ImageName = file.isEmpty() ? oldCategory.getImageName() : file.getOriginalFilename();

		if (!ObjectUtils.isEmpty(category)) {

			oldCategory.setName(category.getName());
			oldCategory.setIsActive(category.getIsActive());
			oldCategory.setImageName(ImageName);
		}

		Category updateCategory = categoryService.saveCategory(oldCategory);

		if (!ObjectUtils.isEmpty(updateCategory)) {
			if (!file.isEmpty()) {
				File saveFile = new ClassPathResource("static/img").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "category_img" + File.separator
						+ file.getOriginalFilename());

				System.out.println(path);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			}
			session.setAttribute("succMsg", "Categoria Atualizada com Sucesso!");
		} else {
			session.setAttribute("errorMsg", "Não excluiu! Erro interno do servidor");
		}
		return "redirect:/admin/loadEditCategory/" + category.getId();
	}

	@PostMapping("/saveProduct")
	public String saveProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile image,
			HttpSession session) throws IOException {

		String imageName = image.isEmpty() ? "default.jpg" : image.getOriginalFilename();

		product.setImage(imageName);
		product.setDiscount(0);
		product.setDiscountPrice(product.getPrice());
		Product saveProduct = productService.saveProduct(product);

		if (!ObjectUtils.isEmpty(saveProduct)) {

			File saveFile = new ClassPathResource("static/img").getFile();

			Path path = Paths
					.get(saveFile.getAbsolutePath() + File.separator + "product_img" + image.getOriginalFilename());

			// System.out.println(path);
			Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			session.setAttribute("succMsg", "Produto salvo com sucesso!");
		} else {
			session.setAttribute("errorMsg", "Não salvou! Erro interno do servidor");
		}

		return "redirect:/admin/loadAddProduct";
	}

	@GetMapping("/products")
	public String loadViewProduct(Model m) {
		m.addAttribute("products", productService.getAllProducts());
		return "admin/products";
	}

	@GetMapping("/deleteProduct/{id}")
	public String deleteproduct(@PathVariable int id, HttpSession session) {
		Boolean deleteProduct = productService.deleteProduct(id);
		if (deleteProduct) {
			session.setAttribute("succMsg", "Produto excluido com sucesso!");
		} else {
			session.setAttribute("errorMsg", "Não salvou! Erro interno do servidor");
		}
		return "redirect:/admin/products";
	}
	
	@GetMapping("/editProduct/{id}")
	public String editProduct(@PathVariable int id, Model m) {
		m.addAttribute("product", productService.getProductById(id));
		m.addAttribute("categories",categoryService.getAllCategory());
		return "admin/edit_product";
	}
		
	@PostMapping("/updateProduct")
	public String updateProduct(@ModelAttribute Product product,@RequestParam("file") MultipartFile image, 
			HttpSession session,  Model m) {
		
		
		if(product.getDiscount()<0 || product.getDiscount()>100)
		{
			session.setAttribute("errorMsg", "Desconto Inválido!");
		} else {
		Product updateProduct = productService.updateProduct(product, image);
		if(!ObjectUtils.isEmpty(updateProduct))
		{
			session.setAttribute("succMsg", "Produto Atualizado com sucesso!");
		} else {
			session.setAttribute("errorMsg", "Não salvou! Erro interno do servidor");
		}
	}	
		return "redirect:/admin/editProduct/" + product.getId();
	}

}
