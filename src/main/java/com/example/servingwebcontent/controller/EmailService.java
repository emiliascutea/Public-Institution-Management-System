package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.User;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;


@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String mail) throws MessagingException{
        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Thank you for making an appointment with us!");
        helper.setText("Your appointment has been confirmed");
        helper.setTo(mail);
        javaMailSender.send(mimeMessage);
    }
}
