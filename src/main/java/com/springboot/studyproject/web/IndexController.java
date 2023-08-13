package com.springboot.studyproject.web;

import com.springboot.studyproject.config.auth.LoginUser;
import com.springboot.studyproject.config.auth.UserService;
import com.springboot.studyproject.config.auth.dto.SessionUser;
import com.springboot.studyproject.config.auth.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class IndexController {
    final private UserService userService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        if(user!=null){
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @PostMapping("/signup")
    public String signup(UserSaveRequestDto userSaveRequestDto) {
        userService.saveUser(userSaveRequestDto);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/signup")
    public String showSignupForm(){
        return "signup";
    }
}