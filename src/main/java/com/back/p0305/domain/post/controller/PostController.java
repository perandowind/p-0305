package com.back.p0305.domain.post.controller;

import com.back.p0305.domain.post.entity.Post;
import com.back.p0305.domain.post.service.PostService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Validated
public class PostController {

    private final PostService postService;

    @GetMapping("/posts/write-form")
    @ResponseBody
    public String writeForm() {

        return getWriteForm("", "", "", "");
    }

    @PostMapping("/posts/write")
    @ResponseBody
    public String write(
            @RequestParam
            @NotBlank
            @Size(min=2, max=10)
            String title,

            @NotBlank
            @Size(min=2, max=100)
            String content) {

        // GetMapping 요청이라 중복 경우 처리를 안할 수 있음(다른 개발자가 볼때)
        Post post = postService.write(title, content);

        return "%d번 글이 작성되었습니다.".formatted(post.getId());
    }



    private String getWriteForm(String errorMessage, String title, String content, String errorFieldName) {
        return """
                <div style="color:red">%s</div>
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
                """.formatted(errorMessage, title, content, errorFieldName);
    }

}