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
public class AdministradorBean implements Serializable {


    @Autowired
    private AdministradorServicio administradorServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private CiudadServicio ciudadServicio;

    @Getter @Setter
    private Administrador administrador;

    @Getter @Setter
    private Administrador administradorAux;

    @Getter @Setter
    private Administrador administradorB;

    @Getter @Setter
    private Usuario usuario;

    @Getter @Setter
    private Usuario usuarioAux;

    @Value(value = "#{seguridadBean.persona}")
    private Persona personaLogin;

    @Getter @Setter
    private Ciudad ciudad;

    @Getter @Setter
    private List<Ciudad> ciudades;

    @Getter
    @Setter
    private List<Usuario> usuariosRegistrados;

    @Getter
    @Setter
    private List<Administrador> administradoresRegistrados;

    @Getter
    @Setter
    private List<Libro> librosRegistrados;

    @PostConstruct
    public void inicializar() {
        this.administrador = obtenerAdministrador();
        this.ciudades = ciudadServicio.listarCiudades();
        this.usuariosRegistrados = obtenerUsuariosRegistrados();
        this.librosRegistrados = obtenerLibrosRegistrados();
        this.administradoresRegistrados = obtenerAdminsRegistrados();
        this.usuario  = new Usuario();
        this.usuarioAux  = new Usuario();
        this.administradorAux = new Administrador();
        this.administradorB= new Administrador();
    }

    public Administrador obtenerAdministrador(){

        Administrador administradorEncontrado = new Administrador();

        if(personaLogin!=null){

            try{
                administradorEncontrado = administradorServicio.obtenerAdministrador(personaLogin.getId());

            }catch (Exception e){
                e.printStackTrace();
            }

        }

        System.out.println(administradorEncontrado.toString());

        return administradorEncontrado;


    }

    public void registrarAdministrador() {
        try {

            if (personaLogin != null) {

                administradorAux.setAdministrador((Administrador) personaLogin);
                administradorServicio.registrarAdministrador(administradorAux);

                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "¡Super! el administrador se registro correctamente");
                FacesContext.getCurrentInstance().addMessage("mensajePersonalizado", facesMsg);
            }

        } catch (Exception e) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensajePersonalizado", facesMsg);
        }
    }

    public void eliminarAdministrador() {

        try {
            if (personaLogin != null) {

                administradorServicio.eliminarAdministrador(administradorAux.getEmail(), administradorAux.getPassword());
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "¡Super! el administrador ha sido eliminado con exito");
                FacesContext.getCurrentInstance().addMessage("mensajePersonalizado", facesMsg);

            }

        } catch (Exception e) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensajePersonalizado", facesMsg);
        }

    }

    public void actualizarAdministrador(){

        try{

            if(personaLogin!=null){

                administradorServicio.actualizarAdministrador(administradorB.getId(),administradorAux);
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "¡Super! el usuario se actualizo con exito");
                FacesContext.getCurrentInstance().addMessage("mensajePersonalizado", facesMsg);

            }

        }catch(Exception e){
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensajePersonalizado", facesMsg);

        }
    }

    public void registrarUsuario() {
        try {

            if (personaLogin != null) {

                usuario.setAdministrador((Administrador) personaLogin);
                usuarioServicio.registrarUsuario(usuario);
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "¡Super! el usuario se registro correctamente");
                FacesContext.getCurrentInstance().addMessage("mensajePersonalizado", facesMsg);
            }

        } catch (Exception e) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensajePersonalizado", facesMsg);
        }
    }


    public void eliminarUsuario(){

        try {
            if (personaLogin!=null) {

                usuarioServicio.eliminarUsuario(usuario.getEmail(),usuario.getPassword());
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "¡Super! el usuario ha sido eliminado con exito");
                FacesContext.getCurrentInstance().addMessage("mensajePersonalizado", facesMsg);

            }

        }catch (Exception e) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensajePersonalizado", facesMsg);
        }

    }


    public void actualizarUsuario(){

        try{

            if(personaLogin!=null){

                usuarioServicio.actualizarUsuario(usuarioAux.getId(), usuario);
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "¡Super! el usuario se actualizo con exito");
                FacesContext.getCurrentInstance().addMessage("mensajePersonalizado", facesMsg);

            }

        }catch(Exception e){
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mensajePersonalizado", facesMsg);

        }
    }

    public Usuario obtenerUsuario(){

        Usuario usuarioEncontrado = new Usuario();

        if (personaLogin!=null){

            try {
                usuarioEncontrado = usuarioServicio.obtenerUsuarioEmail(usuarioAux.getEmail());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return usuarioEncontrado;
    }

    public List<Usuario> obtenerUsuariosRegistrados(){

        List<Usuario> registrados=null;

        if (personaLogin!=null){

            try{
                registrados= administradorServicio.obtenerUsuariosRegistrados(administrador.getEmail());
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return registrados;
    }



    public List<Administrador> obtenerAdminsRegistrados(){

        List<Administrador> registrados=null;

        if (personaLogin!=null){

            try{
                registrados = administradorServicio.obtenerAdminsRegistrados(personaLogin.getEmail());
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return registrados;
    }


    public List<Libro> obtenerLibrosRegistrados(){

        List<Libro> registrados=null;

        if (personaLogin!=null){

            try{
                registrados= administradorServicio.obtenerLibrosRegistrados(administrador.getEmail());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return registrados;
    }

}
