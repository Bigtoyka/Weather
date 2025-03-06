package com.devora.weather.controller;

import com.devora.weather.dto.RegistrationDto;
import com.devora.weather.model.User;
import com.devora.weather.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login-with-errors")
    public String loginWithErrors() {
        return "login-with-errors";
    }

    @GetMapping("/sign-up")
    public String signUp(Model model) {
        model.addAttribute("registrationDto", new RegistrationDto());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(@Valid RegistrationDto registrationDto,
                         BindingResult bindingResult,
                         Model model) {
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.registrationDto", "Passwords don't match.");
        }
        if (userRepository.findByLogin(registrationDto.getUsername().toLowerCase()).isPresent()) {
            bindingResult.rejectValue("username", "error.registrationDto", "Account with this username already exists.");
        }
        if (bindingResult.hasErrors()) {
            return "sign-up";
        }

        userRepository.save(new User(null, registrationDto.getUsername().toLowerCase(), passwordEncoder.encode(registrationDto.getPassword()), "user"));
        return "redirect:/login";
    }
}
