package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.Appointment;
import com.example.servingwebcontent.model.AppointmentRepository;
import com.example.servingwebcontent.model.Question;
import com.example.servingwebcontent.model.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepo;

    public List<Appointment> getAllQuestions(){
        List<Appointment> appointments = new ArrayList<>();
        appointmentRepo.findAll().forEach(appointments::add);
        return appointments;
    }
    public Appointment getAppointment(String name){
        return appointmentRepo.findByName(name);
    }
    public void addAppointment(Appointment a){
        appointmentRepo.save(a);
    }
    public void updateAppointment(Integer id , Appointment a){
        appointmentRepo.save(a);
    }

}
