package com.sheryians.major.controller;

import com.sheryians.major.global.GlobalData;
import com.sheryians.major.model.Product;
import com.sheryians.major.service.CategoryService;
import com.sheryians.major.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    @Autowired
    public CategoryService categoryService;

    @Autowired
    public ProductService productService;

    @GetMapping({"/", "/home"})
    public String getHome(Model model){

        model.addAttribute("cartCount",GlobalData.cart.size());
        return "index";
    }

    @GetMapping({"shop"})
    public String getShopPage(Model model){

        model.addAttribute("cartCount",GlobalData.cart.size());
        model.addAttribute("categories",categoryService.findAll());
        model.addAttribute("products",productService.findAll());
        return "shop";
    }

    @GetMapping("/shop/category/{id}")
    public String getShopPageByCategory(Model model, @PathVariable int id){
        model.addAttribute("cartCount",GlobalData.cart.size());
        model.addAttribute("categories",categoryService.findAll());
        model.addAttribute("products",productService.findByCategoryId(id));
        return "shop";
    }

    @GetMapping("/shop/viewproduct/{id}")
    public String getViewProduct(Model model, @PathVariable long id){

        model.addAttribute("product",productService.findById(id));
        return "viewProduct";
    }

    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable int id){
        GlobalData.cart.add(productService.findById(id));
        return "redirect:/shop";
    }

    @GetMapping("/cart")
    public String cartView(Model model){
        model.addAttribute("cartCount",GlobalData.cart.size());
        model.addAttribute("total",GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cart",GlobalData.cart);
        return "cart";
    }

    @GetMapping("/cart/removeItem/{index}")
    public String cartItemRemove(@PathVariable int index){
        GlobalData.cart.remove(index);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public  String checkOut(Model model){
        model.addAttribute("total",GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        return "checkout";
    }



}
