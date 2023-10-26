package com.example.blog.dto;

import com.example.blog.domain.Article;
import lombok.Getter;

// 조회용으로 사용하기 때문에 불변성이 중요하다. 그래서 final 을 넣어주고 생성자를 사용하여 불변성을 높여줌
@Getter
public class ArticleResponse {

    private final String title;
    private final String content;

    public ArticleResponse(Article article) {
        this.title = article.getTitle();
        this.content = article.getContent();

    }
}
