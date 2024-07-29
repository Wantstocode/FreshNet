package com.kiran.major.controller;

import com.kiran.major.dto.OrderStatus;
import com.kiran.major.dto.ProductDTO;
import com.kiran.major.model.Category;
import com.kiran.major.model.Order;
import com.kiran.major.model.Product;
import com.kiran.major.model.User;
import com.kiran.major.repository.UserRepository;
import com.kiran.major.service.CategoryService;
import com.kiran.major.service.OrderService;
import com.kiran.major.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Controller
public class AdminController {


    public String uploadDir=System.getProperty("user.dir")+"/src/main/resources/static/productImages";

    @Autowired
    UserRepository userRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/admin")
    public String Admin(Principal principal,Model model){
        String email=principal.getName();
        User user=userRepository.findUserByEmail(email);
        if(user!=null) {
            String name = user.getFirstName();
            System.out.println(name);
            model.addAttribute("userName", name);
        }
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
    public String addCat(@ModelAttribute ("category")Category theCategory ){
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



    @GetMapping("/admin/products/{pageNumber}")
    public String getProducts(Model model,
                              @PathVariable("pageNumber") int currentPage){

        Page<Product> productsWithPaging=productService.findAll(currentPage);

        long totalItems=productsWithPaging.getTotalElements();
        System.out.println(totalItems);
        int totalPages=productsWithPaging.getTotalPages();
        System.out.println(totalPages);

        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = 1; i <= totalPages; i++) {
            pageNumbers.add(i);
        }
        System.out.println(pageNumbers);

        List<Product> listProducts=productsWithPaging.getContent();
        System.out.println(listProducts.size());

        model.addAttribute("products",listProducts);
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("pageNumbers", pageNumbers);

        return "products";
    }



//    @GetMapping("/admin/products")
//    public String getProducts(Model model){
//        List<Product> theProducts=productService.findAll();
//        model.addAttribute("products",theProducts);
//        return "products";
//    }


    @GetMapping("/admin/products/add")
    public String getProductAdd(Model model){
        model.addAttribute("productDTO",new ProductDTO());
        model.addAttribute("categories",categoryService.findAll());
        return "productsAdd";
    }

    @PostMapping("/admin/products/add")
    public String addProducts(@ModelAttribute ("productDTO")ProductDTO theProductDTO,
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

        return "redirect:/admin/products/1";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable long id) {
        productService.deleteById(id);
        return "redirect:/admin/products/1";
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


    @GetMapping("/admin/orders")
    public String myOrders(Model model){
        List<Order> order=orderService.findAllOrders();
        model.addAttribute("orders",order);
        return "All_orders";
    }

    @PostMapping("/admin/update-status")
    public String updateOrderStatus(@RequestParam Integer order_id, @RequestParam Integer status_id, Model model){

        if(status_id==0){
            List<Order> order=orderService.findAllOrders();
            model.addAttribute("statusErrorMessage","Select the status before updating");
            model.addAttribute("orders",order);
            return "All_orders";
        }
        OrderStatus[] values=OrderStatus.values();
        String status=null;

        for(OrderStatus orderStatus:values){
            if(orderStatus.getId().equals(status_id))
                status=orderStatus.getName();
        }
        boolean updateStatus=orderService.updateOrderStatus(order_id,status);
        if(updateStatus){
            model.addAttribute("successMessage","Status Updated");
        }
        else{
            model.addAttribute("errorMessage","something went wrong on server");
        }
        List<Order> order=orderService.findAllOrders();
        model.addAttribute("orders",order);
        return "All_orders";
    }


    @GetMapping("/admin/order/search")
    public String getOrder(@RequestParam String order_reference_id,Model model){
        Order order=orderService.findOrderByOrderReferenceId(order_reference_id);
        model.addAttribute("orders",order);
        return "All_orders";
    }


}
