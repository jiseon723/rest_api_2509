package com.example.demo.global.initData;

import com.example.demo.domain.article.service.ArticleService;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Init {

    @Bean //CommandLineRunner = 초기 데이터 세팅에 사용
    CommandLineRunner initData(ArticleService articleService, MemberService memberService) {
        return args -> {
            Member admin = memberService.join("admin", "1234");
            Member user1 = memberService.join("user1", "1234");
            Member user2 = memberService.join("user2", "1234");
            Member user3 = memberService.join("user3", "1234");
            Member user4 = memberService.join("user4", "1234");

            articleService.write("제목 1", "내용 1", admin);
            articleService.write("제목 2", "내용 2", user1);
            articleService.write("제목 3", "내용 3", user2);
            articleService.write("제목 4", "내용 4", user3);
            articleService.write("제목 5", "내용 5", user4);
            articleService.write("제목 6", "내용 6", user4);
        };
    }
}
