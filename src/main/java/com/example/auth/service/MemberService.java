package com.example.auth.service;

import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.LoginResponse;
import com.example.auth.dto.RegisterRequest;
import com.example.auth.entity.Member;
import com.example.auth.repository.MemberRepository;
import com.example.auth.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired MemberRepository memberRepository;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired JwtUtil jwtUtil;

    public LoginResponse validateMember(LoginRequest loginRequest) {
        Member member = memberRepository.findByMemberId(loginRequest.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + loginRequest.getId()));
        if (passwordEncoder.matches(loginRequest.getPw(), member.getPassword())) {
            String accessToken = jwtUtil.generateAccessToken(member.getMemberId());
            String refreshToken = jwtUtil.generateRefreshToken(member.getMemberId());
            return new LoginResponse(accessToken, refreshToken);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    public void registerMember(RegisterRequest registerRequest) {
        Member member = new Member();
        member.toEntity(registerRequest.getRegisterId(), passwordEncoder.encode(registerRequest.getRegisterPw()));
        memberRepository.save(member);
    }
}
