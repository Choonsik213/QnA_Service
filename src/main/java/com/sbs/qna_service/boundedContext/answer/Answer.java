   package com.sbs.qna_service.boundedContext.answer;

   import com.sbs.qna_service.boundedContext.question.Question;
   import jakarta.persistence.*;
   import lombok.AllArgsConstructor;
   import lombok.Data;
   import lombok.NoArgsConstructor;
   import lombok.ToString;

   import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    @ToString.Exclude   // ToString 대상에서 제외
    private Question question;  // question_id 생성

}