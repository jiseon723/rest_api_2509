package com.example.demo.domain.article.controller;

import com.example.demo.domain.article.dto.ArticleDTO;
import com.example.demo.domain.article.entity.Article;
import com.example.demo.domain.article.dto.request.ArticleCreateRequest;
import com.example.demo.domain.article.dto.request.ArticleModifyRequest;
import com.example.demo.domain.article.dto.response.ArticleCreateResponse;
import com.example.demo.domain.article.dto.response.ArticleModifyResponse;
import com.example.demo.domain.article.dto.response.ArticleResponse;
import com.example.demo.domain.article.dto.response.ArticlesResponse;
import com.example.demo.domain.article.service.ArticleService;
import com.example.demo.global.RsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/articles", produces = APPLICATION_JSON_VALUE) //JSON 형식으로 응답한다.
@RequiredArgsConstructor //final 사용 가능
@Tag(name = "ApiV1ArticleController", description = "게시글 CRUD API")
public class ApiV1ArticleController {
    private final ArticleService articleService;

//    다건 조회
    @GetMapping("")
    @Operation(summary = "게시글 다건 조회")
    public RsData<ArticlesResponse> list() {
        List<ArticleDTO> articleList = articleService.getList();

        return RsData.of("200", "게시글 다건 조회 성공", new ArticlesResponse(articleList)); //ArticlesResponse로 감싸는 이유 프론트엔드 작업자가 확인하기 좋음
    }

//    단건 조회
    @GetMapping("/{id}")
    @Operation(summary = "게시글 단건 조회")
    public RsData<ArticleResponse> getArticle(@PathVariable("id") Long id) {
        Article article = articleService.getArticle(id);
        ArticleDTO articleDTO = new ArticleDTO(article);

    return RsData.of("200", "게시글 단건 조회 성공", new ArticleResponse(articleDTO));
    }

//    등록
    @PostMapping("")
    @Operation(summary = "게시글 등록")
    public RsData<ArticleCreateResponse> create(@Valid @RequestBody ArticleCreateRequest articleCreateRequest) {
        Article article = articleService.write(articleCreateRequest.getSubject(), articleCreateRequest.getContent());

        return RsData.of("200", "등록성공", new ArticleCreateResponse(article));
    }

//    수정
    @PatchMapping("/{id}")
    @Operation(summary = "게시글 수정")
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
    @Operation(summary = "게시글 삭제")
    public RsData<ArticleResponse> delete(@PathVariable("id") Long id) {
        Article article = articleService.getArticle(id);

        if(article == null)return RsData.of(
                "500",
                "%d번 게시물은 존재하지 않습니다.".formatted(id),
                null
        );

        articleService.delete(article);
        ArticleDTO articleDTO = new ArticleDTO(article);

        return RsData.of("200", "삭제성공", new ArticleResponse(articleDTO));
    }
}
