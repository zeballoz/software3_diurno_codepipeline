package co.edu.uniquindio.proyecto.entidades;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Reserva implements Serializable {


    //================================= ATRIBUTOS CON SU RESPECTIVA PARAMETRIZACION =================================//
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private int id;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_prestamo", nullable = false)
    Date fechaPrestamo;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_fin_prestamo", nullable = false)
    Date fechaFinPrestamo;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_devolucion")
    Date fechaDevolucion;


    @Column(name = "estado", nullable = false)
    Boolean estado;

    //================================= RELACION CON LA ENTIDAD LIBRO =================================//
    @ManyToOne
    @ToString.Exclude
    private Libro libro;

    //================================= RELACION CON LA ENTIDAD USUARIO =================================//
    @ManyToOne
    @ToString.Exclude
    private Usuario usuario;

    public Reserva(Date fechaPrestamo, Date fechaFinPrestamo, Date fechaDevolucion, Boolean estado, Libro libro, Usuario usuario) {
        this.fechaPrestamo = fechaPrestamo;
        this.fechaFinPrestamo = fechaFinPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.estado = estado;
        this.libro = libro;
        this.usuario = usuario;
    }
}

