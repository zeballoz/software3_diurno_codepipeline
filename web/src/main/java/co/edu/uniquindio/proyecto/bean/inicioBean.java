package co.edu.uniquindio.proyecto.bean;

import co.edu.uniquindio.proyecto.entidades.Libro;
import co.edu.uniquindio.proyecto.servicios.LibroServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;


@Component
@ViewScoped
public class inicioBean implements Serializable {


    @Autowired
    private LibroServicio libroServicio;

    @Getter
    @Setter
    private List<Libro> libros;

    @PostConstruct
    public void inicializar(){

        this.libros = libroServicio.listarLibro();

    }

    public String irADetalle(String isbn){
        return  "/detalleLibro?faces-redirect=true&amp;libro="+isbn;
    }

}
