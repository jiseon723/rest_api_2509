package com.example.demo.article.controller;

import com.example.demo.article.dto.ArticleDTO;
import com.example.demo.article.entity.Article;
import com.example.demo.article.request.ArticleCreateRequest;
import com.example.demo.article.request.ArticleModifyRequest;
import com.example.demo.article.response.ArticleCreateResponse;
import com.example.demo.article.response.ArticleModifyResponse;
import com.example.demo.article.response.ArticleResponse;
import com.example.demo.article.response.ArticlesResponse;
import com.example.demo.article.service.ArticleService;
import com.example.demo.global.RsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor //final 사용 가능
public class ApiV1ArticleController {
    private final ArticleService articleService;

//    다건 조회
    @GetMapping("")
    public RsData<ArticlesResponse> list() {
        List<ArticleDTO> articleList = articleService.getList();

        return RsData.of("200", "게시글 다건 조회 성공", new ArticlesResponse(articleList)); //ArticlesResponse로 감싸는 이유 프론트엔드 작업자가 확인하기 좋음
    }

//    단건 조회
    @GetMapping("/{id}")
    public RsData<ArticleResponse> getArticle(@PathVariable("id") Long id) {
        Article article = articleService.getArticle(id);
        ArticleDTO articleDTO = new ArticleDTO(article);

    return RsData.of("200", "게시글 단건 조회 성공", new ArticleResponse(articleDTO));
    }

//    등록
    @PostMapping("")
    public RsData<ArticleCreateResponse> create(@Valid @RequestBody ArticleCreateRequest articleCreateRequest) {
        Article article = articleService.write(articleCreateRequest.getSubject(), articleCreateRequest.getContent());

        return RsData.of("200", "등록성공", new ArticleCreateResponse(article));
    }

//    수정
    @PatchMapping("/{id}")
    public RsData<ArticleModifyResponse> modify(@PathVariable("id") Long id, @Valid @RequestBody ArticleModifyRequest articleModifyRequest){
        Article article = articleService.getArticle(id);

        if ( article == null )return RsData.of(
                "500",
                "%d번 게시물은 존재하지 않습니다.".formatted(id),
                null
        );

        article = articleService.update(article, articleModifyRequest.getSubject(), articleModifyRequest.getContent());

        return RsData.of("200", "수정성공", new ArticleModifyResponse(article));
    }

//    삭제
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {

        return "삭제";
    }
}
