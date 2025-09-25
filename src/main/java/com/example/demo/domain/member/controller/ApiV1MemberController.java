package com.example.demo.domain.member.controller;

import com.example.demo.domain.member.dto.request.MemberRequest;
import com.example.demo.domain.member.dto.response.MemberResponse;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.service.MemberService;
import com.example.demo.global.RsData.RsData;
import com.example.demo.global.jwt.JwtProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/members")
@RequiredArgsConstructor
@Tag(name = "ApiV1MemberController", description = "회원 인증/인가 API")
public class ApiV1MemberController {
    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    @PostMapping("/join")
    public RsData<MemberResponse> join (@Valid @RequestBody MemberRequest memberRequest) {
        Member member = memberService.join(memberRequest.getUsername(), memberRequest.getPassword());

        return RsData.of("200", "회원가입이 완료되었습니다.", new MemberResponse(member));
    }

    @PostMapping("/login")
    public RsData<MemberResponse> login(@Valid@RequestBody MemberRequest memberRequest, HttpServletResponse res) {
        Member member = memberService.getMember(memberRequest.getUsername());

        //accessToken 발급
        String accessToken = jwtProvider.genAccessToken(member);
        Cookie accessTokencookie = new Cookie("accessToken", accessToken);
        accessTokencookie.setHttpOnly(true); //자바에 접근 막기
        accessTokencookie.setSecure(true); //HTTPS에서만 쿠키 보내기
        accessTokencookie.setPath("/"); //전체 사이트에서 사용
        accessTokencookie.setMaxAge(60*60); //쿠키 유효시간 1시간
        res.addCookie(accessTokencookie); //쿠키를 브라우저에 전송

        String refreshToken = member.getRefreshToken();
        Cookie refreshTokencookie = new Cookie("refreshToken", refreshToken);
        refreshTokencookie.setHttpOnly(true); //자바에 접근 막기
        refreshTokencookie.setSecure(true); //HTTPS에서만 쿠키 보내기
        refreshTokencookie.setPath("/"); //전체 사이트에서 사용
        refreshTokencookie.setMaxAge(60*60); //쿠키 유효시간 1시간
        res.addCookie(refreshTokencookie); //쿠키를 브라우저에 전송

        return RsData.of("200", "토큰 발급 성공 : " + accessToken, new MemberResponse(member));
    }

    @GetMapping("/me") //로그인한 사용자의 내 정보 확인
    public RsData<MemberResponse> me(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        String accessToken = "";

        for (Cookie cookie : cookies) {
            if ("accessToken".equals(cookie.getName())) {
                accessToken = cookie.getValue();
            }
        }

        Map<String, Object> claims = jwtProvider.getClaims(accessToken);
        String username = (String) claims.get("username");
        Member member = this.memberService.getMember(username);

        return RsData.of("200", "내 회원정보", new MemberResponse(member));
    }

    @GetMapping("/logout")
    public RsData logout (HttpServletResponse res) {
        Cookie accessTokenCookie = new Cookie("accessToken", null);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(0);
        res.addCookie(accessTokenCookie);

        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0);
        res.addCookie(refreshTokenCookie);

        return RsData.of("200", "로그아웃 성공");
    }
}
