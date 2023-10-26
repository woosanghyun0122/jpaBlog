package com.example.blog.dto;

import com.example.blog.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 *  DTO(data transfer object) : 계층끼리 데이터를 교환하기 위해 사용하는 객체 , 단순한  전달자 역할
 *  * */

@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Getter
public class AddArticleRequest {

    private String title;
    private String content;

    public Article toEntity() { // 생성자를 사용해서 객체를 생성한다.
        return Article.builder()
                .title(title)
                .content(content)
                .build();

    }
}
