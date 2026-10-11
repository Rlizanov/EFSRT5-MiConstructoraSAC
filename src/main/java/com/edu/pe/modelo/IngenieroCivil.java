package com.edu.pe.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_ingeniero_civil")
@PrimaryKeyJoinColumn(name = "idingenierocivil")
public class IngenieroCivil extends Empleado {

    private String especialidad;
    private String colegiatura;
    private String universidad;
}