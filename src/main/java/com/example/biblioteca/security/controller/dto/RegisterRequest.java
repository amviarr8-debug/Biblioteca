package com.example.biblioteca.security.controller.dto;


import lombok.*;

@Data // --> obtienes getter, setter, to string etc
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
private String rol;
}
