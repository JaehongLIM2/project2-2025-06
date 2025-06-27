package com.example.prj2.config;

import com.example.prj2.user.Entity.User;
import com.example.prj2.user.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalUserAttribute {

    private final UserService userService;

    public GlobalUserAttribute(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public User user(HttpSession session) {
        String loginId = (String) session.getAttribute("loginId");
        if (loginId != null) {
            return userService.findById(loginId); // loginId 기준으로 DB 조회
        }
        return null;
    }
}
