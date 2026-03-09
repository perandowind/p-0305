package com.back.p0305.domain.post.controller;

import com.back.p0305.domain.post.entity.Post;
import com.back.p0305.domain.post.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/posts")
@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @AllArgsConstructor
    @Getter
    public static class WriteRequestForm {

        @Size(min=2, max=10, message = "03-title-제목은 2자 이상 10자 이하로 입력해주세요.")
        @NotBlank(message = "01-title-제목은 필수입니다.")
        private String title;
        @NotBlank(message = "02-content-내용은 필수입니다.")
        @Size(min=2, max=100, message = "04-content-내용은 2자 이상 100자 이하로 입력해주세요.")
        private String content;

    }

    @GetMapping("/write")
    public String writeForm(@ModelAttribute("form") WriteRequestForm form) {
        return "write";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute("form") @Valid WriteRequestForm form, BindingResult bindingResult,
                        Model model) {

        if(bindingResult.hasErrors()) {

//            String errorMessages = bindingResult.getFieldErrors()
//                    .stream()
//                    .map((fieldError) -> fieldError.getField() + "-" + fieldError.getDefaultMessage())
//                    .map((message) -> {
//                        String[] bits = message.split("-"); // [field, 1, errorMessage]
//                        return "<!-- %s --> <li data-error-field=\"%s\">%s</li>".formatted(bits[1], bits[0], bits[2]);
//                    })
//                    .sorted()
//                    .collect(Collectors.joining("\n"));
//
//            // 템플릿 응답
//            model.addAttribute("errorMessages", errorMessages);
            return "write";
        }

        Post post = postService.write(form.title, form.content);
        return "redirect:/posts/%d".formatted(post.getId()); // 주소창을 바꿔, GET 요청
    }

    // 상세'조회 -> GET요청'
    @GetMapping("/{id}")
    public String detail(@PathVariable int id, Model model) {
        Post post = postService.findById(id).get();
        model.addAttribute("post", post);

        return "detail";
    }


}