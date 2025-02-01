package com.sbs.qna_service.boundedContext.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/home/main")
    @ResponseBody
    public String showHome() {
        return "안녕";
    }

    @GetMapping("/")
    public String root() {
        // redirect : 302
        // 302에러는 '이 분을 찾아가보세요."라고 응답
        return "redirect:/question/list";
    }
}
