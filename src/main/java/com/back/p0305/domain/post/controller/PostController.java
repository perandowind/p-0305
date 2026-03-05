package com.back.p0305.domain.post.controller;

import com.back.p0305.domain.post.entity.Post;
import com.back.p0305.domain.post.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts/write-form")
    @ResponseBody
    public String writeForm() {

        return getWriteForm("", "", "");
    }

    @PostMapping("/posts/write")
    @ResponseBody
    public String write(@RequestParam String title, String content) {
        // 유효성 체크
        if (title.isBlank()) {
            return """
                    <div style="color:red">제목을 입력해주세요.</div>
                    %s
                    """.formatted(getWriteForm(title, content, "title"));
        }
        if (content.isBlank()) {
            return """
                    <div style="color:red">내용을 입력해주세요.</div>
                    %s
                    """.formatted(getWriteForm(title, content, "content"));
        }

        // GetMapping 요청이라 중복 경우 처리를 안할 수 있음(다른 개발자가 볼때)
        Post post = postService.write(title, content);

        return "%d번 글이 작성되었습니다.".formatted(post.getId());
    }

    private String getWriteForm(String title, String content, String errorFieldName) {
        return """
                <form method="post" action="/posts/write">
                  <input type="text" name="title" value="%s" autoFocus>
                  <br>
                  <textarea name="content">%s</textarea>
                  <br>
                  <input type="submit" value="작성">
                </form>
                
                <script>
                    const errorFieldName = "%s";
                    
                    if(errorFieldName.length > 0) {
                        const form = document.querySelector("form");
                        form[errorFieldName].focus();
                    }
                </script>
                """.formatted(title, content, errorFieldName);
    }

}