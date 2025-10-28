package com.food_store.backend.entity.dto.UsuarioDtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioLoginDto {
    private String email;
    private String password;
}
