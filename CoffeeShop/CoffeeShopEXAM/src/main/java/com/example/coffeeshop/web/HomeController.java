package com.example.coffeeshop.web;

import com.example.coffeeshop.model.view.OrderViewModel;
import com.example.coffeeshop.service.OrderService;
import com.example.coffeeshop.service.UserService;
import com.example.coffeeshop.util.CurrentUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final CurrentUser currentUser;
    private final OrderService orderService;
    private final UserService userService;

    public HomeController(CurrentUser currentUser, OrderService orderService, UserService userService) {
        this.currentUser = currentUser;
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model) {

        if(currentUser.getId() == null){
            return "index";
        }

        //VISUALIZE THE ADDED ORDERS ON THE HOMEPAGE
        List<OrderViewModel> orders = orderService.findAllOrderByPriceDesc();

        //ORDERS
        model.addAttribute("orders", orders);

        //TOTAL TIME TO COMPLETE THE ORDERS
        model.addAttribute("totalTime",
                orders.stream()
                        .map(orderViewModel -> orderViewModel.getCategory().getNeededTime())
                        .reduce((a,b) -> a+b)
                        .orElse(0)
        );

        //VISUALIZE EMPLOYEES
        model.addAttribute("users", userService.findAllUsersAndCountOfOrdersOrderedByCountDesc());

        return "home";
    }

}
