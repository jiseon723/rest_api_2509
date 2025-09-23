package com.example.demo.global.initData;

import com.example.demo.domain.article.service.ArticleService;
import com.example.demo.domain.member.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Init {

    @Bean //CommandLineRunner = 초기 데이터 세팅에 사용
    CommandLineRunner initData(ArticleService articleService, MemberService memberService) {
        return args -> {
            memberService.join("admin", "1234");
            memberService.join("admin1", "1234");
            memberService.join("admin2", "1234");
            memberService.join("admin3", "1234");
            memberService.join("admin4", "1234");

            articleService.write("제목1", "내용1");
            articleService.write("제목2", "내용2");
            articleService.write("제목3", "내용3");
            articleService.write("제목4", "내용4");
            articleService.write("제목5", "내용5");
            articleService.write("제목6", "내용6");
        };
    }
}
