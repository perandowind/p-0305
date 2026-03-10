package com.back.p0305;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/order")
    public String order() {
        return "order";
    }
}
