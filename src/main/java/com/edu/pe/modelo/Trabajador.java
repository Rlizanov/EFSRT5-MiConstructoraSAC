package com.edu.pe.modelo;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_trabajador")
public class Trabajador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idtrabajador;

    private String trabajo;
    private Double sueldo;
}