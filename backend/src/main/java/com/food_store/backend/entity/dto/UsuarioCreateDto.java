package com.food_store.backend.entity.dto;

import com.food_store.backend.entity.enums.Role;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioCreateDto {
    private String nombre;
    private String email;
    private String password;
    private Role role;
}