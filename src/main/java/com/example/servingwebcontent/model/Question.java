package com.example.servingwebcontent.model;
import javax.persistence.*;

@Entity
@Table(name= "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, length = 64)
    private String username;

    @Column(name = "quest",nullable = false, length = 64)
    private String quest;

    @Column(name= "answer" ,columnDefinition = "",length = 64)
    private String answer;


    public Question(){
        this.answer=null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getQuest() {
        return quest;
    }

    public void setQuest(String question) {
        this.quest = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
