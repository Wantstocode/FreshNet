package com.kiran.major.controller;


import com.kiran.major.dto.OrderRequest;
import com.kiran.major.model.Cart;
import com.kiran.major.model.Order;
import com.kiran.major.model.Product;
import com.kiran.major.model.User;
import com.kiran.major.repository.CartRepository;
import com.kiran.major.repository.UserRepository;
import com.kiran.major.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    private HttpSession httpSession;

    @Autowired
    private OrderService orderService;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    public CategoryService categoryService;


    @Autowired
    private CartService cartService;


    @Autowired
    public ProductService productService;


    @GetMapping({"/", "/home"})
    public String getHome(Model model, Principal principal){

        String email = null;
        if (principal instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oAuth2Token = (OAuth2AuthenticationToken) principal;
            Map<String, Object> attributes = oAuth2Token.getPrincipal().getAttributes();
            email = (String) attributes.get("email");
            System.out.println("OAuth2 email: " + email);
        } else {
            email = principal.getName();
            System.out.println("Principal name: " + email);
        }

        User user=userRepository.findUserByEmail(email);
        if(user!=null) {
            httpSession.setAttribute("userId",user.getId());
            String name = user.getFirstName();
            int userId=user.getId();
            int itemCount = cartRepository.countItemsByUserId(userId);
            model.addAttribute("cartCount", itemCount);
            model.addAttribute("userName", name);
        }
        return "index";
    }





    @GetMapping("/shop/category/{id}")
    public String getShopPageByCategory(Model model, @PathVariable int id){
        int userId=(Integer)httpSession.getAttribute("userId");
        int itemCount = cartRepository.countItemsByUserId(userId);
        model.addAttribute("cartCount", itemCount);
        model.addAttribute("categories",categoryService.findAll());
        model.addAttribute("products",productService.findByCategoryId(id));

        return "shop";
    }

    @GetMapping("/shop/viewproduct/{id}")
    public String getViewProduct(Model model, @PathVariable long id){

        int userId=(Integer)httpSession.getAttribute("userId");
        int itemCount = cartRepository.countItemsByUserId(userId);
        model.addAttribute("cartCount", itemCount);
        model.addAttribute("product",productService.findById(id));
        return "viewProduct";
    }

    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable long id,Model model){

        int userId=(Integer)httpSession.getAttribute("userId");
        User user=userService.findById(userId);
        if(user!=null) {
            Cart cart = cartService.saveCart(id, userId);
        }
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String cartView(Model model){

        int userId=(Integer)httpSession.getAttribute("userId");
        User user=userService.findById(userId);
        if(user!=null) {

            List<Cart> cart = cartService.getByUserId(userId);
            if (cart != null) {
                double totalPrice=0.0;
                for (Cart cart1 : cart) {
                    totalPrice +=cart1.getTotalPrice();
                }
                int itemCount = cartRepository.countItemsByUserId(userId);
                model.addAttribute("cartCount", itemCount);
                model.addAttribute("total", totalPrice);
                model.addAttribute("cart", cartService.getByUserId(userId));
            }
        }
        return "cart";
    }


    @GetMapping("/cart/removeItem/{productId}")
    public String cartItemRemove(@PathVariable long productId){
        int userId=(Integer)httpSession.getAttribute("userId");
        Cart cart=cartRepository.findByProductIdAndUserId(userId,productId);
        if (cart != null && cart.getProduct() != null) {
            cartService.delete(cart);
        }
        return "redirect:/cart";
    }



    //for listing all products with or without sorting
    @GetMapping("/shop/{pageNumber}")
    public String getShopPage(Model model,
                              @Param("sortDir") String sortField,
                              @Param("sortDir") String sortDir,
                              @PathVariable("pageNumber") int currentPage){

        Page<Product> productsWithPaging;
        if((sortDir == null || sortDir.isEmpty()) || (sortField == null || sortField.isEmpty())){
            productsWithPaging=productService.findAll(currentPage);
        }
        else{
            productsWithPaging=productService.findAll(currentPage,sortDir,sortField);
        }

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

        int userId=(Integer)httpSession.getAttribute("userId");
        int itemCount = cartRepository.countItemsByUserId(userId);
        model.addAttribute("cartCount", itemCount);
        model.addAttribute("categories",categoryService.findAll());
        model.addAttribute("products",listProducts);
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("sortField",sortField);

        return "shop";
    }



    //for finding searched products with or without sorting
    @GetMapping("/shop/search/{pageNumber}")
    public String sorting(Model model,
                          @Param("query")String query,
                          @Param("sortDir") String sortField,
                          @Param("sortDir") String sortDir,
                          @PathVariable("pageNumber") int currentPage
                          ){

        Page<Product> productsWithPagingAndSorting;
        if((sortDir == null || sortDir.isEmpty()) || (sortField == null || sortField.isEmpty())) {
            productsWithPagingAndSorting = productService.findWithPagination(query, currentPage);
        }else{
            productsWithPagingAndSorting=productService.findWithPaginationAndSorting(query,currentPage,sortDir,sortField);
        }
        long totalItems=productsWithPagingAndSorting.getTotalElements();
        int totalPages=productsWithPagingAndSorting.getTotalPages();

        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = 1; i <= totalPages; i++) {
            pageNumbers.add(i);
        }

        List<Product> listProducts=productsWithPagingAndSorting.getContent();


        int userId=(Integer)httpSession.getAttribute("userId");
        int itemCount = cartRepository.countItemsByUserId(userId);
        model.addAttribute("cartCount", itemCount);
        model.addAttribute("categories",categoryService.findAll());
        model.addAttribute("products",listProducts);
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("keyword",query);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("sortField",sortField);

        return "shop";
    }

    @GetMapping("/checkout")
    public  String checkOut(Model model){
        int userId=(Integer)httpSession.getAttribute("userId");
        List<Cart> cart=cartRepository.findByUserId(userId);
        if(!cart.isEmpty()) {
            double totalPrice = 0.0;
            for (Cart cart1 : cart) {
                totalPrice += cart1.getTotalPrice();
            }
            model.addAttribute("total", totalPrice);
            return "checkout";
        }
        model.addAttribute("message","Your cart is empty !!!"+".<br/>make sure to add items to cart");
        return "cart";
    }


    @PostMapping("/order")
    public String order(@ModelAttribute OrderRequest orderRequest){
        System.out.println(orderRequest);
        int userId=(Integer)httpSession.getAttribute("userId");
        orderService.saveOrder(orderRequest,userId);
        List<Cart> cartList=cartService.getByUserId(userId);
        if(cartList!=null) {
            for (Cart cart : cartList) {
                cartService.delete(cart);
            }
        }
        return "order_succes";
    }

    @GetMapping("/my-orders")
    public String myOrders(Model model){
        int userId=(int)httpSession.getAttribute("userId");
        User user=userService.findById(userId);
        List<Order> order= orderService.findOrderByUser(user);
        model.addAttribute("orders",order);
        return "My_orders";
    }

    @GetMapping("/order/cancel/{id}")
    public String cancelOrder(@PathVariable("id")int orderId,Model model){

        boolean updateStatus=orderService.cancelOrder(orderId);
        if(updateStatus){
            model.addAttribute("successMessage","Status Updated");
        }
        else{
            model.addAttribute("errorMessage","something went wrong on server");
        }

        int userId=(int)httpSession.getAttribute("userId");
        User user=userService.findById(userId);
        List<Order> order= orderService.findOrderByUser(user);
        model.addAttribute("orders",order);
        return "My_orders";
    }

}
