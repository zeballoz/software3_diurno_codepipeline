package co.edu.uniquindio.proyecto.bean;

import co.edu.uniquindio.proyecto.entidades.Libro;
import co.edu.uniquindio.proyecto.servicios.LibroServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;


@Component
@ViewScoped
public class BusquedaBean implements Serializable {

    @Value("#{param['busqueda']}")
    private String busquedaParam;
    private String busqueda;

    @Getter @Setter
    private List<Libro> libros;

    @Autowired
    private LibroServicio libroServicio;

    @PostConstruct
    public void inicializar(){
        if (busquedaParam!=null && !busquedaParam.isEmpty()){

            libros=  libroServicio.buscarLugares(busquedaParam);
        }
    }

    public String buscar(){
        if(!busqueda.isEmpty()){
            return "resultadoBusqueda?faces-redirect=true&amp;busqueda="+busqueda;
        }
        return "";
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public String getBusquedaParam() {
        return busquedaParam;
    }

    public void setBusquedaParam(String busquedaParam) {
        this.busquedaParam = busquedaParam;
    }

}
