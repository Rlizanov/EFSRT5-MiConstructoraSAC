package com.edu.pe.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_analista_funcional")
@PrimaryKeyJoinColumn(name = "idanalistafuncional")
public class AnalistaFuncional extends Empleado {

    private String area;
    private String proceso;
    private String herramienta;
}