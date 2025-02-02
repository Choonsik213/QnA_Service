package com.sbs.qna_service.boundedContext.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor    //
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/question/list")
    public String list(Model model) {
        List<Question> questionList = questionService.findAll();

        model.addAttribute("questionList", questionList);   // questionList라는 이름으로 화면에 뿌려준다.
        return "question_list";
    }

    @GetMapping(value = "/question/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        // 1. 질문에 대한 데이터를 가져오려고 해, 내가 id값 줄게.
        Question question = questionService.getQuestion(id);

        model.addAttribute("question", question);

        return "question_detail";
    }
}
