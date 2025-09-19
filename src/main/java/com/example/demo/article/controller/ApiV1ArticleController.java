package com.example.demo.article.controller;

import com.example.demo.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor //final 사용 가능
public class ApiV1ArticleController {
    private final ArticleService articleService;

    @GetMapping("")
    public String list () {
        return "목록";
    }

    @GetMapping("/{id}")
    public String getArticle () {
        return "단건";
    }

    @PostMapping("")
    public String create () {
        return "등록";
    }

    @PatchMapping("/{id}")
    public String modify () {
        return "수정";
    }

    @DeleteMapping("/{id}")
    public String delete () {
        return "삭제";
    }
}
