package com.ingeniawork.ticketflow.domain.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


@Table(name="usuarios")
@Entity(name="Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Informacion Personal
    private String nombre;
    private String apellido;
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaDeNacimiento;
    @Column(name = "cedula")
    private String cedula;
    private String genero;
    @Column(name = "pais_residencia")
    private String PaisDeResidencia;
    private String telefono;
    //Informacion de cuenta y Acceso
    private String email;
    private String contrasena;
    @Enumerated(EnumType.STRING)
    private Perfil perfil;
    //Informacion digital
    private boolean activo;
    private boolean emailActivo;


    public Usuario(DatosRegistroUsuario datosRegistroUsuario, String hashContrasena) {
        //Informacion Personal
        this.nombre = datosRegistroUsuario.nombre();
        this.apellido = datosRegistroUsuario.apellido();
        this.fechaDeNacimiento = datosRegistroUsuario.fechaDeNacimiento();
        this.cedula = datosRegistroUsuario.cedula();
        this.genero = datosRegistroUsuario.genero();
        this.PaisDeResidencia = datosRegistroUsuario.paisDeResidencia();
        this.telefono = datosRegistroUsuario.telefono();
        //Informacion de cuenta y Acceso
        this.email = datosRegistroUsuario.email();
        this.contrasena = hashContrasena;
        this.perfil = datosRegistroUsuario.perfil();
        //Informacion digital
        this.activo = true;
        this.emailActivo = false;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public @Nullable String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
