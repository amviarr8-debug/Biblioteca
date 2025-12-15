package com.example.biblioteca.services;

import com.example.biblioteca.model.Socio;
import com.example.biblioteca.repository.SocioRespositorio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SocioServiceImpl implements SocioService {

    private final SocioRespositorio socioRespositorio;


    @Override
    public Socio save(Socio socio) {
        return  socioRespositorio.save(socio);
    }

    @Override
    public Optional<Socio> findById(int id) {
        return socioRespositorio.findById(id);
    }

    @Override
    public List<Socio> findAll() {
        return socioRespositorio.findAll();
    }

    @Override
    public void deleteById(int id) {
        socioRespositorio.deleteById(id);
    }
}
