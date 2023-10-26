package com.example.blog.controller;

import com.example.blog.domain.Article;
import com.example.blog.dto.AddArticleRequest;
import com.example.blog.repository.BlogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // 테스트용 애플리케이션 컨텍스트
@AutoConfigureMockMvc // MockMvc 생성 및 자동 구성

 /*
 * @SpringBootTest :  전체 빈을 메모리에 올리는 테스트 -> controller, service, repository 전부 다
 * @AutoConfigureMockMvc:   MockMvc를 주입 받아서 톰캣 서버를 띄우지 않을 상태로 API 요청 부분을 mocking하여 사용
 *
 * */

class BlogApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper; // 직렬화, 역직렬화를 위한 클래스

    // ObjectMapper : 자바 객체를 JSON 데이터로 변환하는 직렬화 혹은 JSON 데이터를 자바 객체로 변환하는 역직렬화를 위해 사용

    @Autowired
    private WebApplicationContext context;
    //  dispatcherServlet 클래스에 의해서 만들어짐 / context : spring이 관리하는 bean이 담겨있는 컨테이너
    @Autowired
    BlogRepository blogRepository;

    @BeforeEach
    public void setMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        blogRepository.deleteAll();
    }

    @DisplayName("추가")
    @Test
    void addArticle() throws Exception {
        //given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final AddArticleRequest userRequest = new AddArticleRequest(title, content);

        final String requestBody = objectMapper.writeValueAsString(userRequest);
        // 객체를 json형태로 직렬화

        //when
        // 설정한 내용을 바탕으로 요청 전송
        // ResultActions : mockMvc 객체를 테스트할 때 기대하고 있는 결과 혹은 동작을 적용할 수 있음
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then
        //andExpect(): 응답 검증( 상태코드를 이용하여)
        result.andExpect(status().isCreated());

        List<Article> articles = blogRepository.findAll();

        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getTitle()).isEqualTo("title");
        assertThat(articles.get(0).getContent()).isEqualTo("content");
    }

    @DisplayName("전체조회")
    @Test
    void findAllArticles() throws Exception {

        //given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";

        // 조회를 하기 위해서는 실제로 db 안에 데이터가 필요하기 때문에 save함수를 실행시킴
        blogRepository.save(Article.builder()
                .title(title)
                .content(content)
                .build());
        //when
        final ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("content"))
                .andExpect(jsonPath("$[0].title").value(title));
    }


}