package com.example.demo.domain.member.service;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.global.RsData.RsData;
import com.example.demo.global.jwt.JwtProvider;
import com.example.demo.global.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public Member join(String username, String password) {
        Member checkedMember = memberRepository.findByUsername(username);

        if (checkedMember != null) {
            throw new RuntimeException("이미 가입된 사용자입니다.");
        }

        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();

        memberRepository.save(member);

        return member;
    }

    public Member getMember(String username) {
        return memberRepository.findByUsername(username);
    }

    public boolean validateToken(String accessToken) {
        return jwtProvider.verify(accessToken);
    }

    public RsData<String> refreshAccessToken(String refreshToken) {
        Member member = memberRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new RuntimeException("존재하지 않는 리프레쉬 토큰입니다."));
        String accessToken = jwtProvider.genAccessToken(member);
        return RsData.of("200", "토큰 갱신 성공", accessToken);
    }

    public SecurityUser getUserFromAccessToken(String accessToken) {
        Map<String, Object> payloadBody = jwtProvider.getClaims(accessToken);

        long id = (int) payloadBody.get("id");
        String username = (String) payloadBody.get("username");
        List<GrantedAuthority> authorities = new ArrayList<>(); //규격만 정한 것

        return new SecurityUser(id, username, "", authorities);
    }
}
