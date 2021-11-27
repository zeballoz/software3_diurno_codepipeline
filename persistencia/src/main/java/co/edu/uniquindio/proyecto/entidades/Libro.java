package co.edu.uniquindio.proyecto.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@ToString
public class Libro implements Serializable {

    //================================= ATRIBUTOS CON SU RESPECTIVA PARAMETRIZACION =================================//

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    @EqualsAndHashCode.Include
    private int id;

    @Column(name = "isbn",nullable = false)
    private String isbn;

    @Column(name = "titulo",length = 100,nullable = false)
    private String titulo;

    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @Column(name = "autor",length = 100,nullable = false)
    private String autor;

    @Column(name = "fecha_publicacion")
    private String fechaPublicacion;

    @Column(name = "descripcion")
    private String descripcion;

    //================================= RELACION CON LA ENTIDAD ADMINISTRADOR =================================//
    @ManyToOne
    private Administrador administrador;

    //================================= RELACION CON LA ENTIDAD IMAGEN =================================//
    @OneToMany(mappedBy = "libro",fetch=FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    private List<Imagen> imagenes;

    public Libro(String isbn, String titulo, Boolean estado, String autor, String fechaPublicacion,String descripcion) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.estado = estado;
        this.autor = autor;
        this.fechaPublicacion = fechaPublicacion;
        this.descripcion= descripcion;
        this.imagenes = new ArrayList<>();
    }

    public String getImagenPrincipal(){

        if(imagenes!=null && !imagenes.isEmpty()){

            return imagenes.get(0).getUrl();
        }

        return "default.png";
    }
}
