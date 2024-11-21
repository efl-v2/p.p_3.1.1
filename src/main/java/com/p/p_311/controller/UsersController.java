package com.p.p_311.controller;

import com.p.p_311.model.User;
import com.p.p_311.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "pages/users";
    }

    @GetMapping("/add_user")
    public String formForAdd(Model model) {
        model.addAttribute("user", new User());
        return "pages/addUser";
    }

    @PostMapping("/add_user")
    public String addUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pages/addUser";
        } else {
            userService.addUser(user);
            return "redirect:/users";
        }
    }

    @GetMapping("/edit_user")
    public String formForEdit(@RequestParam(name = "id") Integer id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "pages/editUser";
    }

    @PostMapping("/edit_user")
    public String editUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pages/editUser";
        } else {
            User existingUser = userService.getUserById(user.getId());
            existingUser.setName(user.getName());
            existingUser.setPassword(user.getPassword());
            userService.updateUser(existingUser);
            return "redirect:/users";
        }
    }

    @PostMapping("/delete_user")
    public String deleteUser(@RequestParam(name = "id") Integer id) {
        userService.removeUserById(id);
        return "redirect:/users";
    }
}
