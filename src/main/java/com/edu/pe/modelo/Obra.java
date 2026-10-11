package com.edu.pe.modelo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_obra")
public class Obra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idobra;

    private String nombreobra;
    private String ubicacion;
    private Double presupuesto;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = ISO.DATE)
    private Date fechainicio;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = ISO.DATE)
    private Date fechafin;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idempleado")
    private Empleado empleado;
}