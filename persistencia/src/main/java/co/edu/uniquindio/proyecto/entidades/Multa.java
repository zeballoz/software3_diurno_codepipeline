package co.edu.uniquindio.proyecto.entidades;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@ToString
public class Multa implements Serializable {

    //================================= ATRIBUTOS CON SU RESPECTIVA PARAMETRIZACION =================================//

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    @EqualsAndHashCode.Include
    private int id;

    @Column(name = "precio_multa")
    private double precioMulta;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "descripcion")
    private String descripcion;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_multa")
    private Date fechaMulta;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_pago")
    private Date fechaPago;

    //================================= RELACION CON LA ENTIDAD USUARIO =================================//
    @ManyToOne
    @ToString.Exclude
    private Usuario usuario;


    public Multa(double precioMulta, Boolean estado, String descripcion, Date fechaMulta,Usuario usuario,Date fechaPago) {
        this.precioMulta = precioMulta;
        this.estado = estado;
        this.descripcion = descripcion;
        this.fechaMulta = fechaMulta;
        this.fechaPago = fechaPago;
        this.usuario = usuario;
    }
}
