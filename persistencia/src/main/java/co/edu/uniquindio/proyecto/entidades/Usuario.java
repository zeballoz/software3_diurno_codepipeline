package co.edu.uniquindio.proyecto.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Usuario extends Persona implements Serializable {

    @Column(name = "numero_tarjeta",length = 100)
    private String numeroTarjeta;

    @Column(name = "fecha_tarjeta",length = 100)
    private String fechatarjeta;

    @Column(name = "codigo_tarjeta",length = 100)
    private String codigoTarjeta;

    //================================= RELACION CON LA ENTIDAD CIUDAD =================================//
    @ManyToOne
    @JsonIgnore
    private Ciudad ciudad;

    //================================= RELACION CON LA ENTIDAD ADMINISTRADOR =================================//
    @ManyToOne
    private Administrador administrador;

    //================================= RELACION CON LA ENTIDAD RESERVA=================================//
    @OneToMany(mappedBy = "usuario")
    @ToString.Exclude
    @JsonIgnore
    private List<Reserva> reservas;

    //================================= RELACION CON LA ENTIDAD MULTA=================================//
    @OneToMany(mappedBy = "usuario")
    @ToString.Exclude
    @JsonIgnore
    private List<Multa> multas;

    //================================= CONSTRUCTOR  =================================//
    public Usuario(String id, String nombre,String telefono, String password, String email) {
        super(id, nombre,telefono, password, email);
        this.multas=new ArrayList<>();
        this.reservas = new ArrayList<>();
    }

}
