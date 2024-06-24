package com.example.auth.controller;

import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.RegisterRequest;
import com.example.auth.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @Autowired
    MemberService memberService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        boolean validated = memberService.validateMember(loginRequest);
        return validated ? "success" : "fail";
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest registerRequest) {
        memberService.registerMember(registerRequest);
        return "Registered successfully";
    }
}
