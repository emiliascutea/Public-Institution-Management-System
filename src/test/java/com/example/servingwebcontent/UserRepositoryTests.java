package com.example.servingwebcontent;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.servingwebcontent.model.User;
import com.example.servingwebcontent.model.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repo;

    // test methods go below

    @Test
    public void testCreateUser() {
        User user = new User();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("alexia");
        user.setPassword(encodedPassword);
        user.setEmail("alexiapop28@yahoo.com");
        user.setFirstName("Alexia");
        user.setLastName("Pop");
        user.setStatus("ADMIN");

        User savedUser = repo.save(user);

        User existUser = entityManager.find(User.class, savedUser.getId());

        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());

    }

    @Test
    public void testCreateUser2() {
        User user = new User();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("emilia");
        user.setPassword(encodedPassword);
        user.setEmail("escutea@yahoo.ro");
        user.setFirstName("Emilia");
        user.setLastName("Scutea");
        user.setStatus("USER");

        User savedUser = repo.save(user);

        User existUser = entityManager.find(User.class, savedUser.getId());

        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());

    }
}

