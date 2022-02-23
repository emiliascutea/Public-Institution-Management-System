package com.example.servingwebcontent.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {


    @Query("SELECT a FROM Appointment a WHERE a.name = ?1")
    Appointment findByName(String name);

    @Query("SELECT a FROM Appointment a WHERE a.name = ?1")
    List<Appointment> findByNames(String name);

    @Query("DELETE FROM Appointment a WHERE a.id =?1")
    void deleteOne(Long id);

    @Query("SELECT a FROM Appointment a WHERE a.email = ?1")
    Appointment findByEmail(String email);
}
