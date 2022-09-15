package com.example.coffeeshop.web;

import com.example.coffeeshop.model.binding.UserLoginBindingModel;
import com.example.coffeeshop.model.binding.UserRegisterBindingModel;
import com.example.coffeeshop.model.service.UserServiceModel;
import com.example.coffeeshop.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    //--------------------------------------REGISTER---------------------------------------------------------------------
    @ModelAttribute
    public UserRegisterBindingModel userRegisterBindingModel(){
        return new UserRegisterBindingModel();
    }

    @GetMapping("/register")
    private String register(){
        return "register";
    }

    @PostMapping("/register")
    private String registerConfirm(@Valid UserRegisterBindingModel userRegisterBindingModel,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes){

//CHECK IF THE UNIQUE VALUES(username + email) ALREADY EXIST IN THE DB AND REDIRECT IF THEY DO
        boolean usernameOrEmailExists = userService.existingUsernameOrEmail(userRegisterBindingModel.getUsername(), userRegisterBindingModel.getEmail());

//CHECK IF ALL INPUT IS CORRECT
        if(bindingResult.hasErrors() ||
                !userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())
                || usernameOrEmailExists){

            redirectAttributes
                    .addFlashAttribute("userRegisterBindingModel",userRegisterBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel",bindingResult);

            return "redirect:register";
        }
//SAVE NEW USER IN THE DB(first inject UserService(create userService + userRepository) + ModelMapper)

        userService.registerUser(modelMapper
                .map(userRegisterBindingModel, UserServiceModel.class));

        return "redirect:login";
    }

//-----------------------------------------LOGIN---------------------------------------------------

    @ModelAttribute
    public UserLoginBindingModel userLoginBindingModel(){
        return new UserLoginBindingModel();
    }

    @GetMapping("/login")
    public String login(Model model) {
        if(!model.containsAttribute("isFound")){
            model.addAttribute("isFound",true);
        }

        return "login";
    }

    @PostMapping("/login")
    public String loginConfirm(@Valid UserLoginBindingModel userLoginBindingModel,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes){


//CHECK IF ALL INPUT IS CORRECT
        if (bindingResult.hasErrors()){

            redirectAttributes
                    .addFlashAttribute("userLoginBindingModel", userLoginBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel",bindingResult);

            return "redirect:login";
        }

//CHECK IN THE DB IF THE USER AND PASSWORD EXIST

        UserServiceModel userServiceModel = userService.findByUsernameAndPassword(userLoginBindingModel.getUsername(),
                                                                                  userLoginBindingModel.getPassword());

        if(userServiceModel == null){
            redirectAttributes
                    .addFlashAttribute("userLoginBindingModel", userLoginBindingModel)
                    .addFlashAttribute("isFound", false);

            return "redirect:login";
        }

//IF THE ABOVE VALIDATION PASSES --> LOGIN

        userService.loginUser(userServiceModel.getId(), userLoginBindingModel.getUsername());
//ADD MODEL TO LOGIN @GETMAPPING

        return "redirect:/";
    }

//LOGOUT
    @GetMapping("/logout")
    public String logout(HttpSession httpSession){
        httpSession.invalidate();

        return "redirect:/";
    }


}
