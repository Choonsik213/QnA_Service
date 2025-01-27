package com.sbs.qna_service;

import com.sbs.qna_service.boundedContext.question.Question;
import com.sbs.qna_service.boundedContext.question.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class QnaServiceApplicationTests {

    @Autowired  // 필드 주입
    private QuestionRepository questionRepository;

    @BeforeEach // 각 테스트 케이스 실행 전에 딱 한 번 먼저 실행 됨
    void beforeEach() {
        // 모든 데이터 삭제
        questionRepository.deleteAll();

        // 흔적 삭제(다음 번 INSERT 때 Id가 1번으로 설정되도록)
        questionRepository.clearAutoIncrement();

        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        questionRepository.save(q1);   // 첫번째 질문 저장

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        questionRepository.save(q2);    // 두번째 질문 저장
    }

    @Test
    @DisplayName("데이터 저장하기")    // 테스트 의도를 사람이 읽기 쉬운형태로 설명
    void t001() {
        Question q = new Question();
        q.setSubject("겨울 제철 음식으로는 무엇을 먹어야 하나요?");
        q.setContent("겨울 제철 음식을 알려주세요.");
        q.setCreateDate(LocalDateTime.now());
        questionRepository.save(q);   // 세번째 질문 저장
        // save() : INSERT 쿼리 실행

        assertEquals("겨울 제철 음식으로는 무엇을 먹어야 하나요?", questionRepository.findById(3).get().getSubject());
    }

    @Test
    @DisplayName("findAll") // [SQL] select * from question;
    void t002() {
        List<Question> all = questionRepository.findAll();
        assertEquals(2, all.size());    // 현재 데이터가 2개냐?

        Question q = all.get(0);    // 그 곳에 0번째 데이터를 질문객체 데이터 q에 넣음
        assertEquals("sbb가 무엇인가요?", q.getSubject());    // 0번째 질문제목이 이거냐?
    }

    @Test
    @DisplayName("findById") // [SQL] select * from question where id = 1;
    void t003() {
        Optional<Question> oq = questionRepository.findById(1);
        if (oq.isPresent()) {
            Question q = oq.get();
            assertEquals("sbb가 무엇인가요?", q.getSubject());
        }
    }

    @Test
    @DisplayName("findBySubject") // [SQL] select * from question where subject = "sbb가 무엇인가요?";
    void t004() {
        Question q = questionRepository.findBySubject("sbb가 무엇인가요?");
        assertEquals(1, q.getId());
    }

    /*
    [SQL]
    SELECT *
    FROM question
    WHERE subject = 'sbb가 무엇인가요?'
    AND content = 'sbb에 대해서 알고 싶습니다.';
    */
    @Test
    @DisplayName("findBySubjectAndContent")
    void t005() {
        Question q = questionRepository.findBySubjectAndContent(
                "sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
        assertEquals(1, q.getId());
    }

    /*
    [SQL]
    SELECT *
    FROM question
    WHERE subject LIKE 'sbb%';
    */
    @Test
    @DisplayName("findBySubjectLike")
    void t006() {
        List<Question> qList = questionRepository.findBySubjectLike("sbb%");
        Question q = qList.get(0);  // sbb로 시작하는 문장들 중 0번째 가져와
        assertEquals("sbb가 무엇인가요?", q.getSubject());
    }

    /*
    [SQL]
    UPDATE question
    SET content = ?,
    create_date = ?,
    subject = ?,
    WHERE id = ?;
    */
    @Test
    @DisplayName("데이터 수정하기")
    void t007() {
        // SELECT * FROM question WHERE id=1;
        Optional<Question> oq = questionRepository.findById(1);
        assertTrue(oq.isPresent());
        Question q = oq.get();
        q.setSubject("수정된 제목");
        questionRepository.save(q);
    }

    /*
    * DELETE
    * FROM question
    * WHERE id = ?
    * */
    @Test
    @DisplayName("데이터 삭제하기")
    void t008() {
        // [ questionRepository.count()의 쿼리 ]
        // SELECT COUNT(*) FROM question;
        assertEquals(2,questionRepository.count());
        Optional<Question> oq = questionRepository.findById(1);
        assertTrue(oq.isPresent());
        Question q = oq.get();
        questionRepository.delete(q);
        assertEquals(1, questionRepository.count());
    }

}
