package com.sbs.qna_service.boundedContext.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Question findBySubject(String subject);

    Question findBySubjectAndContent(String subject, String content);

    List<Question> findBySubjectLike(String subject);

    @Modifying  // INSERT, UPDATE, DELETE 같은 데이터가 변경 작업에서만 사용,
                // nativeQuery = true 여야만 MySQL 쿼리 사용이 가능하다.
    @Transactional  // 성공하면 커밋, 실패하면 롤백
    @Query(value = "ALTER TABLE question AUTO_INCREMENT = 1", nativeQuery = true) // 자동 1번 설정
    void clearAutoIncrement();
}
