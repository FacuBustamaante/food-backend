package com.food_store.backend.entity.dto.UsuarioDtos;

import com.food_store.backend.entity.enums.Role;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDto {
    private Long id;
    private String nombre;
    private String email;
    private Role role;
    private List<Long> pedidosIds;
}
