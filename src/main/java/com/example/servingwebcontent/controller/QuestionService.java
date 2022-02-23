package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepo;

    public List<Question> getAllQuestions(){
        List<Question> questions = new ArrayList<>();
        questionRepo.findAll().forEach(questions::add);
        return questions;
    }
    public Question getQuestion(String name){
        return questionRepo.findByName(name);
    }
    public void addQuestion(Question q){
        questionRepo.save(q);
    }
    public void updateQuestion(Integer id , Question q){
        questionRepo.save(q);
    }
    public void deleteQuestion(String name){
        questionRepo.deleteOne(name);
    }
}
