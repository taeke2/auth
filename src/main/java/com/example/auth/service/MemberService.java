package com.example.auth.service;

import com.example.auth.dto.LoginRequest;
import com.example.auth.entity.Member;
import com.example.auth.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired MemberRepository memberRepository;

    public boolean validateMember(LoginRequest loginRequest) {
        Member member = memberRepository.findByUserId(loginRequest.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + loginRequest.getId()));
        return member.getPassword().equals(loginRequest.getPw());
    }
}
