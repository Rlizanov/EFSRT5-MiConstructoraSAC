package com.edu.pe.modelo;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
@Entity
@Table(name="tbl_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idusuario;

    private String nomusuario;
    private String password;
    private String email;
    private Boolean activo;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = ISO.DATE)
    private Date fechareg;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "tbl_usuario_rol",
        joinColumns = @JoinColumn(name="idusuario"),
        inverseJoinColumns = @JoinColumn(name="idrol")
    )
    private Set<Rol> roles;
}
