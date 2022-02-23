package com.example.servingwebcontent.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {

    @Query("SELECT q FROM Question q WHERE q.username = ?1")
    Question findByName(String name);

    @Query("DELETE FROM Question q WHERE q.username =?1")
    void deleteOne(String name);

    @Query("SELECT q FROM Question q WHERE q.answer IS NOT NULL")
    List<Question>findQuestions();

    @Transactional
    @Modifying
    @Query("UPDATE Question q SET q.answer=?1 WHERE q.id =?2")
    void updateQuestion(String answer,Long id);
}
