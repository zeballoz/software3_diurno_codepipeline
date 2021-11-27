package co.edu.uniquindio.proyecto.bean;

import co.edu.uniquindio.proyecto.entidades.Imagen;
import co.edu.uniquindio.proyecto.entidades.Libro;
import co.edu.uniquindio.proyecto.entidades.Persona;
import co.edu.uniquindio.proyecto.entidades.Usuario;
import co.edu.uniquindio.proyecto.servicios.LibroServicio;
import co.edu.uniquindio.proyecto.servicios.UsuarioServicio;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Component
@ViewScoped
public class DetalleLibroBean implements Serializable {

    @Value("#{param['libro']}")
    private  String isbnLibro;

    @Autowired
    private LibroServicio libroServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Getter @Setter
    private Libro libro;

    @Getter @Setter
    private Libro libroAux;

    @Value(value = "#{seguridadBean.persona}")
    private Persona personaLogin;

    @Getter @Setter
    private String icono;

    @Getter @Setter
    private Usuario usuario;

    @Getter @Setter
    private List<String>urlImagenes;

    @PostConstruct
    public void inicializar(){

        if (isbnLibro!=null && !"".equals(isbnLibro)){
            try {
                String id = isbnLibro;

                this.libro = libroServicio.obtenerLibro(id);
                this.urlImagenes = new ArrayList<>();

                List<Imagen>imagenes = libro.getImagenes();

                if(imagenes.size()>0){

                    for(Imagen i:imagenes){

                        urlImagenes.add(i.getUrl());
                    }
                }else{

                    urlImagenes.add("default.png");
                }

                List<Libro>libros=new ArrayList<>();
                libros.add(libro);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void realizarReserva() {

        if(personaLogin!= null){

            try {
                this.libroAux = libro;

                usuario = usuarioServicio.obtenerUsuario(personaLogin.getId());

                usuarioServicio.reservarLibro(libroAux,usuario.getId());


                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "¡Super! has reservado un libro, tienes 7 dias para devolverlo o se te cobrara una multa");
                FacesContext.getCurrentInstance().addMessage("mensajePersonalizado", facesMsg);

            } catch (Exception e) {
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
                FacesContext.getCurrentInstance().addMessage("mensajePersonalizado", facesMsg);
            }
        }
    }

}
