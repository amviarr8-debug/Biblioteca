package com.example.biblioteca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


// @Data --> Esta anotacion de lombok incluye Getter, Setter, ToString, EqualsAndHashCode y RequiredArgsConstructor. Luego puedes añadir excepciones de los attributos
@Getter
@Setter
@NoArgsConstructor // contrusctor vacio
@AllArgsConstructor// contructor con todos los atributos de clase
@Entity
public class PruebaLombok {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE) // esto define que el atributo ID no tenga setter ya que es autoincremental y crearia conflicto con la base de datos
    private int id;
    private String nombre;


}
