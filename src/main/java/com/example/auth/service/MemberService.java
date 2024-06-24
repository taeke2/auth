package com.example.auth.service;

import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.RegisterRequest;
import com.example.auth.entity.Member;
import com.example.auth.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired MemberRepository memberRepository;
    @Autowired PasswordEncoder passwordEncoder;

    public boolean validateMember(LoginRequest loginRequest) {
        Member member = memberRepository.findByMemberId(loginRequest.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + loginRequest.getId()));
        return passwordEncoder.matches(loginRequest.getPw(), member.getPassword());
    }

    public void registerMember(RegisterRequest registerRequest) {
        Member member = new Member();
        member.toEntity(registerRequest.getRegisterId(), passwordEncoder.encode(registerRequest.getRegisterPw()));
        memberRepository.save(member);
    }
}
