package com.example.demo.article.controller;

import com.example.demo.article.dto.ArticleDTO;
import com.example.demo.article.entity.Article;
import com.example.demo.article.request.ArticleCreateRequest;
import com.example.demo.article.request.ArticleModifyRequest;
import com.example.demo.article.response.ArticleResponse;
import com.example.demo.article.response.ArticlesResponse;
import com.example.demo.article.service.ArticleService;
import com.example.demo.global.RsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor //final 사용 가능
public class ApiV1ArticleController {
    private final ArticleService articleService;

//    다건 조회
    @GetMapping("")
    public RsData<ArticlesResponse> list() {
        List<ArticleDTO> articleList = new ArrayList<>();

        Article article1 = new Article("제목1", "내용1");
        articleList.add(new ArticleDTO(article1));

        Article article2 = new Article("제목2", "내용2");
        articleList.add(new ArticleDTO(article2));

        Article article3 = new Article("제목3", "내용3");
        articleList.add(new ArticleDTO(article3));

        return RsData.of("200", "게시글 다건 조회 성공", new ArticlesResponse(articleList)); //ArticlesResponse로 감싸는 이유 프론트엔드 작업자가 확인하기 좋음
    }

//    단건 조회
    @GetMapping("/{id}")
    public RsData<ArticleResponse> getArticle(@PathVariable("id") Long id) {
        Article article = new Article("제목1", "내용1");
        ArticleDTO articleDTO = new ArticleDTO(article);

        return RsData.of("200", "게시글 단건 조회 성공", new ArticleResponse(articleDTO)); //ArticlesResponse로 감싸는 이유 프론트엔드 작업자가 확인하기 좋음
    }

//    등록
    @PostMapping("")
    public String create(@Valid@RequestBody ArticleCreateRequest articleCreateRequest) {
        System.out.println(articleCreateRequest.getSubject());
        System.out.println(articleCreateRequest.getContent());

        return "등록";
    }

//    수정
    @PatchMapping("/{id}")
    public String modify(@PathVariable("id") Long id, @Valid @RequestBody ArticleModifyRequest articleModifyRequest) {
        System.out.println(id);
        System.out.println(articleModifyRequest.getSubject());
        System.out.println(articleModifyRequest.getContent());

        return "수정";
    }

//    삭제
    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") Long id) {
        System.out.println(id);

        return "삭제";
    }
}
