package co.edu.uniquindio.proyecto.bean;

import co.edu.uniquindio.proyecto.entidades.*;
import co.edu.uniquindio.proyecto.servicios.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@Component
@RequestScope
public class UsuarioBean implements Serializable {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    @Getter @Setter
    private CiudadServicio ciudadServicio;

    @Autowired
    @Getter @Setter
    private LibroServicio libroServicio;

    @Getter
    @Setter
    private List<Reserva> reservasActivas;

    @Getter
    @Setter
    private List<Reserva> historialReservas;

    @Getter
    @Setter
    private List<Multa> multas;


    @Value(value = "#{seguridadBean.persona}")
    private Persona personaLogin;

    @Getter @Setter
    private Ciudad ciudad;

    @Getter @Setter
    private Usuario usuario;

    @Getter @Setter
    private Usuario usuarioAux;

    @Getter @Setter
    private Multa multa;

    @Getter @Setter
    private List<Ciudad> ciudades;


    @PostConstruct
    public void inicializar() {

        this.usuario = obtenerUsuario();
        this.multa = new Multa();
        this.usuarioAux= new Usuario();
        this.ciudades = ciudadServicio.listarCiudades();
        this.historialReservas = obtenerHistorialReservas();
        this.reservasActivas = obtenerReservasActivas();
        this.multas = obtenerMultasActivas();
    }

    public Usuario obtenerUsuario(){

        Usuario usuarioEncontrado = new Usuario();

        if(personaLogin!=null){

            try{
                usuarioEncontrado = usuarioServicio.obtenerUsuario(personaLogin.getId());

            }catch (Exception e){
                e.printStackTrace();
            }

        }

        return usuarioEncontrado;
    }

    public void registrarTarjetaUsuario(){

        try {

            usuarioServicio.registrarTarjetaUsuario(personaLogin.getId(),usuario.getNumeroTarjeta(),usuario.getCodigoTarjeta(),usuario.getFechatarjeta());
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "¡Super! tu tarjeta se ha registrado con exito");
            FacesContext.getCurrentInstance().addMessage("mensajePersonalizado", facesMsg);

        }catch (Exception e){

            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensajePersonalizado", facesMsg);
        }
    }



    public List<Reserva> obtenerHistorialReservas(){

        List<Reserva> registrados=null;

        if (personaLogin!=null){

            try{
                registrados= usuarioServicio.obtenerHistorialReserva(personaLogin.getId());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return registrados;
    }

    public List<Reserva> obtenerReservasActivas(){

        List<Reserva> registrados=null;

        if (personaLogin!=null){

            try{
                registrados= usuarioServicio.obtenerReservasActivas(personaLogin.getId());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return registrados;
    }

    public List<Multa> obtenerMultasActivas(){

        List<Multa> registrados=null;

        if (personaLogin!=null){

            try{
                registrados= usuarioServicio.obtenerMultasActivas(personaLogin.getId());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return registrados;
    }


    public void devolverLibro(int idLibro,int idReserva) {

        Libro libro;
        Reserva reservaAux;

        if(personaLogin!= null){

            try {
                libro = libroServicio.obtenerLibroId(idLibro);
                reservaAux= usuarioServicio.obtenerReserva(idReserva);

                usuario = usuarioServicio.obtenerUsuario(personaLogin.getId());

                usuarioServicio.devolverLibro(reservaAux.getId(),libro.getId(),usuario.getId());
                this.reservasActivas = obtenerReservasActivas();
                this.historialReservas = obtenerHistorialReservas();

            } catch (Exception e) {
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
                FacesContext.getCurrentInstance().addMessage("mensajePersonalizado", facesMsg);
            }
        }
    }


    public void asignarMulta(int idReserva) {

        Reserva reservaAux;

        if (personaLogin != null) {

            try {
                reservaAux = usuarioServicio.obtenerReserva(idReserva);

                usuario = usuarioServicio.obtenerUsuario(personaLogin.getId());

                usuarioServicio.asignarMulta(reservaAux.getId());


            } catch (Exception e) {
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
                FacesContext.getCurrentInstance().addMessage("mensajePersonalizado", facesMsg);
            }
        }
    }


    public void pagarMulta(){

        Multa multaAux;

        if(personaLogin!= null){
            try {

                usuario = usuarioServicio.obtenerUsuario(personaLogin.getId());
                multaAux = usuarioServicio.obtenerMultaUsuario(multa.getId(),usuario.getId());

                usuarioServicio.pagarMulta(multaAux.getId(),usuarioAux.getId(),usuario.getNumeroTarjeta());
                this.multas = obtenerMultasActivas();

                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "¡Super! pagaste la multa");
                FacesContext.getCurrentInstance().addMessage("mensajePersonalizado", facesMsg);

            }catch (Exception e){
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
                FacesContext.getCurrentInstance().addMessage("mensajePersonalizado", facesMsg);
            }
        }
    }


    public String metodos(int idReserva, int idLibro){

        devolverLibro(idLibro,idReserva);
        asignarMulta(idReserva);

        return "/usuario/perfilUsuario?faces-redirect=true";
    }


    public String metod(){

        pagarMulta();
        return "/usuario/perfilUsuario?faces-redirect=true";
    }


}
