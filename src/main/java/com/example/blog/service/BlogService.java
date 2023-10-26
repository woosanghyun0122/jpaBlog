package com.example.blog.service;

import com.example.blog.domain.Article;
import com.example.blog.dto.AddArticleRequest;
import com.example.blog.dto.UpdateArticleRequest;
import com.example.blog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    // findAll , save 둘다 jpa 지원 기본 메서드

    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found: "+id));
    }

    public void delete(Long id) {
        blogRepository.deleteById(id);
    }

    @Transactional // 트랜잭셔널 메서드
    // 매칭한 메서드를 하나의 트랜잭션으로 묶는 역할을 한다. 여러개의 메서드를 사용하게 되었을 때
    public Article update(Long id, UpdateArticleRequest request) {

        Article article = blogRepository.findById(id)
                .orElseThrow((() -> new IllegalArgumentException("not found" + id)));

        article.update(request.getTitle(), request.getContent());

        return article;
    }




}
