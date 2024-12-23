package com.betaplan.anjeza.auth.controllers;

import com.betaplan.anjeza.auth.enums.Role;
import com.betaplan.anjeza.auth.models.User;
import com.betaplan.anjeza.auth.services.UserService;
import com.betaplan.anjeza.auth.validators.UserValidator;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Objects;

@Controller
public class UserController {

    private final UserService service;
    private final UserValidator validator;

    public UserController(UserService service, UserValidator validator) {
        this.service = service;
        this.validator = validator;
    }

    @RequestMapping("/register")
    public String registerForm(@Valid @ModelAttribute("user") User user) {
        return "registration";
    }

    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
        validator.validate(user, result);
        if (result.hasErrors()) {
            return "registration";
        }
        service.addUser(user, Role.ROLE_USER);
        return "redirect:/login";
    }

    @RequestMapping("/login")
    public String login(@RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model) {
        if(Objects.nonNull(error)) {
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
        }
        if(Objects.nonNull(logout)) {
            model.addAttribute("logoutMessage", "Logout Successful!");
        }
        return "login";
    }

    @RequestMapping(value = {"/", "/home"})
    public String home(Principal principal, Model model) {
        // before Spring 3
//        User userPrincipal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String name = user.getUsername();
        // with Spring 3
        String username = principal.getName();
        model.addAttribute("currentUser", service.findByUsername(username));
        return "home";
    }
}