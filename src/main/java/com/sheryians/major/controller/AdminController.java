package com.sheryians.major.controller;

import com.sheryians.major.dto.ProductDTO;
import com.sheryians.major.model.Category;
import com.sheryians.major.model.Product;
import com.sheryians.major.service.CategoryService;
import com.sheryians.major.service.CategoryServiceImpl;
import com.sheryians.major.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Controller
public class AdminController {

    public String uploadDir=System.getProperty("user.dir")+"/src/main/resources/static/productImages";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/admin")
    public String Admin(){
        return "adminHome";
    }

    @GetMapping("/admin/categories")
    public String getCategories(Model theModel){
        List<Category> categoryList=categoryService.findAll();
        theModel.addAttribute("categories",categoryList);
        return "categories";
    }

    @GetMapping("/admin/categories/add")
    public String getCatAdd(Model theModel){
        theModel.addAttribute("category",new Category());
        return "categoriesAdd";
    }

    @PostMapping("/admin/categories/add")
    public String getCatAdd(@ModelAttribute ("category")Category theCategory ){
        categoryService.save(theCategory);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String updateCat(@PathVariable int id, Model theModel){
        Category thecategory=categoryService.findById(id);
        theModel.addAttribute("category",thecategory);
        return "categoriesAdd";
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCat(@PathVariable int id) {
        categoryService.deleteById(id);
        return "redirect:/admin/categories";
    }

    //product endpoints
    @GetMapping("/admin/products")
    public String getProducts(Model model){
        List<Product> theProducts=productService.findAll();
        model.addAttribute("products",theProducts);
        return "products";
    }

    @GetMapping("/admin/products/add")
    public String getProductAdd(Model model){
        model.addAttribute("productDTO",new ProductDTO());
        model.addAttribute("categories",categoryService.findAll());
        return "productsAdd";
    }

    @PostMapping("/admin/products/add")
    public String getCatAdd(@ModelAttribute ("productDTO")ProductDTO theProductDTO,
                            @RequestParam("productImage")MultipartFile file,
                            @RequestParam("imgName")String imageName) throws IOException {

        //convert productDTO into product
        Product product=new Product();
        product.setId(theProductDTO.getId());
        product.setName(theProductDTO.getName());
        product.setCategory(categoryService.findById(theProductDTO.getCategoryId()));
        product.setPrice(theProductDTO.getPrice());
        product.setWeight(theProductDTO.getWeight());
        product.setDescription(theProductDTO.getDescription());
        String imageUUID;
        if (!file.isEmpty()){
            imageUUID=file.getOriginalFilename();
            Path fileNameAndPath= Paths.get(uploadDir,imageUUID);
            Files.write(fileNameAndPath,file.getBytes());
        }
        else{
            imageUUID=imageName;
        }
        product.setImageName(imageUUID);
        productService.save(product);

        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable long id) {
        productService.deleteById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/update/{id}")
    public String updateProduct(@PathVariable long id, Model theModel){
        Product theProduct=productService.findById(id);
        ProductDTO productDTO=new ProductDTO();
        productDTO.setId(theProduct.getId());
        productDTO.setName(theProduct.getName());
        productDTO.setCategoryId(theProduct.getCategory().getId());
        productDTO.setPrice(theProduct.getPrice());
        productDTO.setWeight(theProduct.getWeight());
        productDTO.setDescription(theProduct.getDescription());
        productDTO.setImageName(theProduct.getImageName());
        theModel.addAttribute("productDTO",productDTO);
        theModel.addAttribute("categories",categoryService.findAll());
        return "productsAdd";
    }


}
