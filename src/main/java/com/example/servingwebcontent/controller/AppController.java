package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.mail.MessagingException;
import java.util.List;

@Controller
public class AppController {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private QuestionRepository questionRepo;
    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping("")
    public String viewHomePg() {
        return "home";
    }

    @GetMapping("/homepage")
    public String viewHomePage(Model model) {
        model.addAttribute("user", new User());
        return "/homepage";
    }


    @GetMapping("/registerQuestion")
    public String showRegisterQuestionForm(Model model){
        model.addAttribute("question",new Question());
        return "question_form";
    }

    @GetMapping("/registerAppointment")
    public String showRegisterAppointmentForm(Model model){
        model.addAttribute("appointment",new Appointment());
        return "appointment_form";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepo.save(user);

        return "register_success";
    }

    @PostMapping("/process_register_question")
    public String processRegisterQuestion(Question q,Model model) {
        questionRepo.save(q);
        List<Question> listQuestions = questionRepo.findQuestions();
        model.addAttribute("listQuestions", listQuestions);
        return "questions";
    }

    @PostMapping("/process_register_appointment")
    public String processRegisterAppointment(Appointment a,Model model) {
        CustomUserDetails u = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        a.setName(u.getFullName());
        a.setEmail(u.getUsername());
        appointmentRepo.save(a);
        List<Appointment> listAppointments = appointmentRepo.findByNames(u.getFullName());
        model.addAttribute("listyourAppointments", listAppointments);
        return "appointments";
    }

    @PostMapping("/edit_question")
    public String editQuestion(Question q,Model model) {
        questionRepo.updateQuestion(q.getAnswer(), q.getId());
        List<Question> listQuestions = questionRepo.findAll();
        model.addAttribute("listQuestions", listQuestions);
        model.addAttribute("question",new Question());
        return "questionsadmin";
    }

    @PostMapping("/delete_question")
    public String deleteQuestion(Question q,Model model) {
        questionRepo.deleteById(q.getId());
        List<Question> listQuestions = questionRepo.findAll();
        model.addAttribute("listQuestions", listQuestions);
        model.addAttribute("question",new Question());
        return "questionsadmin";
    }

    @PostMapping("/cancel_appointment")
    public String cancelAppointment(Appointment a,Model model) {
        appointmentRepo.deleteById(a.getId());
        CustomUserDetails u = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Appointment> listAppointments = appointmentRepo.findByNames(u.getFullName());
        model.addAttribute("listyourAppointments", listAppointments);
        return "appointments";
    }

    @RequestMapping(value="/edit_appointment",method = RequestMethod.POST,params ="action=delete")
    public String deleteAppointment(Appointment a,Model model) {
        appointmentRepo.deleteById(a.getId());
        List<Appointment> listAppointments = appointmentRepo.findAll();
        model.addAttribute("listAppointments", listAppointments);

        return "appointmentsadmin";
    }

    @RequestMapping(value="/edit_appointment",method = RequestMethod.POST,params ="action=sendMail")
    public String sendMail(Appointment a,Model model) throws MessagingException {
        EmailService emailService=new EmailService(javaMailSender);
        Appointment app = appointmentRepo.getById(a.getId());
        emailService.sendMail(app.getEmail());
        List<Appointment> listAppointments = appointmentRepo.findAll();
        model.addAttribute("listAppointments", listAppointments);

        return "appointmentsadmin";
    }

    @GetMapping("/home")
    public String viewHome() {

        return "home";
    }
    @GetMapping("/contact")
    public String viewContact() {

        return "contact";
    }
    @GetMapping("/about")
    public String viewAbout() {

        return "about";
    }
    @GetMapping("/news")
    public String viewNews() {

        return "news";
    }
    @GetMapping("/appointments")
    public String showAppointmentsForm(Model model) {
        CustomUserDetails u = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Appointment> listAppointments = appointmentRepo.findByNames(u.getFullName());
        model.addAttribute("listyourAppointments", listAppointments);
        model.addAttribute(new Appointment());
        return "appointments";
    }

    @GetMapping("/questions")
    public String showQuestionForm(Model model) {
        List<Question> listQuestions = questionRepo.findQuestions();
        model.addAttribute("listQuestions", listQuestions);
        return "questions";
    }
    @GetMapping("/documents")
    public String viewDocuments(){
        return "documents";
    }

    @GetMapping("/registrationNumber")
    public String viewRegistrationNumber(){
        return "registrationNumber";
    }

    @GetMapping("/drivingLicense")
    public String viewDrivingLicense(){
        return "drivingLicense";
    }

    @GetMapping("/drivingTest")
    public String viewDrivingTest(){
        return "drivingTest";
    }

    @GetMapping("/taxes")
    public String viewTaxes(){
        return "taxes";
    }

    @GetMapping("/passports")
    public String viewPassports(){return "passports";}

    @GetMapping("/homeadmin")
    public String viewHomeAdmin() {

        return "homeadmin";
    }
    @GetMapping("/questionsadmin")
    public String viewChatAdmin(Model model) {
        List<Question> listQuestions = questionRepo.findAll();
        model.addAttribute("listQuestions", listQuestions);
        model.addAttribute("question",new Question());
        return "questionsadmin";
    }

    @GetMapping("/usersadmin")
    public String viewUsersAdmin(Model model) {
        List<User> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);
        return "usersadmin";
    }

    @GetMapping("/appointmentsadmin")
    public String viewAppointmentsAdmin(Model model) {
        List<Appointment> listAppointments = appointmentRepo.findAll();
        model.addAttribute("listAppointments", listAppointments);
        model.addAttribute("appointment",new Appointment());
        return "appointmentsadmin";
    }

}