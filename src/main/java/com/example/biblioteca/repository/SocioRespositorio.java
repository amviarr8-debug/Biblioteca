package com.example.biblioteca.repository;

import com.example.biblioteca.model.Socio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocioRespositorio extends JpaRepository<Socio, Integer> {
}
