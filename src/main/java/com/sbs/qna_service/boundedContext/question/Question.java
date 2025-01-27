package com.sbs.qna_service.boundedContext.question;

import com.sbs.qna_service.boundedContext.answer.Answer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
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
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    private LocalDateTime createDate;

    public Question() {

    }
}
