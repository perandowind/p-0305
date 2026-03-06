package com.back.p0305.domain.post.controller;

import com.back.p0305.domain.post.entity.Post;
import com.back.p0305.domain.post.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts/write-form")
//    @ResponseBody
    public String writeForm() {

//        return getWriteForm("", "", "");
        return "write";
    }

    @AllArgsConstructor
    public static class WriteRequestForm {
        @NotBlank(message = "1-제목은 필수입니다.")
        @Size(min=2, max=10, message = "3-제목은 2자 이상 10자 이하로 입력해주세요.")
        private String title;

        @NotBlank(message = "2-내용은 필수입니다.")
        @Size(min=2, max=100, message = "4-내용은 2자 이상 100자 이하로 입력해주세요.")
        private String content;
    }

    // GetMapping 요청이면 -> 중복경우처리를 안할 수 있음(다른 개발자가 볼때)
    @PostMapping("/posts/write")
    public String write(@Valid @ModelAttribute WriteRequestForm form, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {


            String errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map((fieldError) -> fieldError.getField() + "-" + fieldError.getDefaultMessage())
                    .map((message) -> { // field-1-errorMessage
                        String[] bits = message.split("-");// [field, 1, errorMessage]
                        String li = "<!-- %s --><li data-error-field=\"%s\">%s</li>".formatted(bits[1], bits[0], bits[2]);
                        return li;
                    })
                    .sorted()
                    .collect(Collectors.joining("\n"));

            // 템플릿 응답
            return "write";
        }

        Post post = postService.write(form.title, form.content);

        // resposeBody 응답 (통일해야함)
        return "%d번 글이 작성되었습니다.".formatted(post.getId());
    }



    private String getWriteForm(String errorMessage, String title, String content) {
        return """
                <ul style="color:red">%s</ul>
                <form method="post" action="/posts/write">
                  <input type="text" name="title" value="%s" autoFocus>
                  <br>
                  <textarea name="content">%s</textarea>
                  <br>
                  <input type="submit" value="작성">
                </form>
                
                <script>
                    const li = document.querySelector("ul li");
                    const errorFieldName = li.dataset.errorField;
                    
                    if(errorFieldName.length > 0) {
                        const form = document.querySelector("form");
                        form[errorFieldName].focus();
                    }
                </script>
                """.formatted(errorMessage, title, content);
    }

}