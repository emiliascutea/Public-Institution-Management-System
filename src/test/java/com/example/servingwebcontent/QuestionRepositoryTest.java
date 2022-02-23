package com.example.servingwebcontent;

import com.example.servingwebcontent.model.Question;
import com.example.servingwebcontent.model.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class QuestionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private QuestionRepository repo;

    // test methods go below

    @Test
    public void testCreateUser() {
       Question q =new Question();
       q.setQuest("WHAT");
       q.setUsername("EU");
       Question savedQ = repo.save(q);

        Question existq = entityManager.find(Question.class, savedQ.getId());


        assertThat(q.getQuest()).isEqualTo(existq.getQuest());

    }
}

