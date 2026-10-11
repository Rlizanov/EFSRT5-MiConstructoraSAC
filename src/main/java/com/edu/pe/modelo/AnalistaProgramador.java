package com.edu.pe.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_analista_programador")
@PrimaryKeyJoinColumn(name = "idanalistaprog")
public class AnalistaProgramador extends Empleado {

	private String lenguajeprincipal;
	private String nivel;
	private Integer experienciaanios;
}