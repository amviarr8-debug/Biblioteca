package com.example.biblioteca.services;

import com.example.biblioteca.model.Libro;
import com.example.biblioteca.repository.LibroRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibroServiceImpl implements LibroService{

    private final LibroRepositorio libroRepositorio;

    @Override
    public Libro save(Libro libro) {
        return libroRepositorio.save(libro);
    }

    @Override
    public Optional<Libro> findById(int id) {
        return libroRepositorio.findById(id);
    }

    @Override
    public List<Libro> findAll() {
        return libroRepositorio.findAll();
    }

    @Override
    public void deleteById(int id) {
        libroRepositorio.deleteById(id);
    }
}

