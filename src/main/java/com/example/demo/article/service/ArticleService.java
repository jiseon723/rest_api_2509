package com.example.demo.article.service;

import com.example.demo.article.dto.ArticleDTO;
import com.example.demo.article.entity.Article;
import com.example.demo.article.repository.ArticleRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public List<ArticleDTO> getList() {
        List<Article> articleList = articleRepository.findAll();

        List<ArticleDTO> articleDTOList = articleList.stream()
                .map(article -> new ArticleDTO(article))
                .collect(Collectors.toList());

        return articleDTOList;
    }

    public Article getArticle(Long id) {
        Optional<Article> optionalArticle = articleRepository.findById(id);

        return  optionalArticle.orElse(null); //있으면 DTO 없으면 null
    }

    public Article write(@NotBlank String subject, @NotBlank String content) {
        Article article = Article.builder()
                .subject(subject)
                .content(content)
                .build();

        articleRepository.save(article);

        return article;
    }

    public Article update(Article article, String subject, String content) {
        article.setSubject(subject);
        article.setContent(content);

        articleRepository.save(article);

        return article;
    }
}
