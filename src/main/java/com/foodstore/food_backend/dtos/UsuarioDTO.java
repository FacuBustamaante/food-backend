package com.foodstore.food_backend.dtos;
import com.foodstore.food_backend.enums.Rol;
import lombok.Data;

@Data
public class UsuarioDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String mail;
    private int celular;
    private Rol rol;
}