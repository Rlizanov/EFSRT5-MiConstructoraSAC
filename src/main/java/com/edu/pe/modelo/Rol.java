package com.edu.pe.modelo;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="tbl_rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Integer idrol;
    private String nomrol;
    private String descripcion;
}