package com.example.blog.dto;

import com.example.blog.domain.Article;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleViewResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createAt;

    public ArticleViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createAt = article.getCreatedAt();
    }
}
