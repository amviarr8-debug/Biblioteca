package com.example.biblioteca.services;

import com.example.biblioteca.model.Socio;

import java.util.List;
import java.util.Optional;

public interface SocioService {

    // Create / Update
    Socio save(Socio socio);

    // Read
    Optional<Socio> findById(int id);
    List<Socio> findAll();

    // Delete
    void deleteById(int id);


}