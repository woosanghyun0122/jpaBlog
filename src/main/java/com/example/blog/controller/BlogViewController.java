package com.example.blog.controller;

import com.example.blog.domain.Article;
import com.example.blog.dto.ArticleListViewResponse;
import com.example.blog.dto.ArticleViewResponse;
import com.example.blog.repository.BlogRepository;
import com.example.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class BlogViewController {

    private final BlogService blogSerivce;

    @GetMapping("/articles")
    public String getArticles(Model model) {

        List<ArticleListViewResponse> articles = blogSerivce.findAll().stream()
                .map(ArticleListViewResponse::new)
                .collect(Collectors.toList());

        model.addAttribute("articles", articles);

        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {

        Article article = blogSerivce.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article));

        return "article";
    }

    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long id, Model model) {

        if (id == null) {
            model.addAttribute("article", new ArticleViewResponse());
        }
        else{
            Article article = blogSerivce.findById(id);
            model.addAttribute("article",new ArticleViewResponse(article));
        }
        return "newArticle";
    }
}
