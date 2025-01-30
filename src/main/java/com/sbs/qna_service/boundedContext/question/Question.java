package com.sbs.qna_service.boundedContext.question;

import com.sbs.qna_service.boundedContext.answer.Answer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
@Entity // 스프링부트가 Questiondmf Entity로 본다
public class Question {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)   // VARCHAR(200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    // 질문에서 답변을 참조할 수 없을까?
    // 만들면, 해당 객체(질문 객체)에서 관련된 답변을 찾을 때 편하다.
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) // fetch = FetchType.EAGER 테스트는 나중에 해봐
    private List<Answer> answerList = new ArrayList<>();

    // 외부에서 answerList필드에 접근하는 것을 차단 -> '캡슐화'라고 함
    public void addAnswer(Answer a) {
        a.setQuestion(this);
        answerList.add(a);
    }

    private LocalDateTime createDate;

    public Question() {

    }
}