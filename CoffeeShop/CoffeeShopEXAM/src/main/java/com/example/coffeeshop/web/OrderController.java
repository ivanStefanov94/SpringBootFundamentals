package com.example.coffeeshop.web;

import com.example.coffeeshop.model.binding.OrderAddBindingModel;
import com.example.coffeeshop.model.service.OrderServiceModel;
import com.example.coffeeshop.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final ModelMapper modelMapper;

    public OrderController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute
    public OrderAddBindingModel orderAddBindingModel(){
        return new OrderAddBindingModel();
    }

    @GetMapping("/add")
    public String add(){
        return "order-add";
    }

    @PostMapping("/add")
    public String addConfirm(@Valid OrderAddBindingModel orderAddBindingModel, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){

//CHECK IF INPUT IS CORRECT
        if(bindingResult.hasErrors()){

            redirectAttributes
                    .addFlashAttribute("orderAddBindingModel", orderAddBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.orderAddBindingModel",bindingResult);

            return "redirect:add";
        }
//SAVE TO DB

        orderService.addOrder(modelMapper.map(orderAddBindingModel, OrderServiceModel.class));

            return "redirect:/";
    }

//DELETING ORDER WHEN CLICKING READY
    @GetMapping("/ready/{id}")
    public String ready(@PathVariable Long id){
        orderService.readyOrder(id);

        return "redirect:/";
    }




}
