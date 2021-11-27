package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.entidades.*;

import java.util.List;

public interface AdministradorServicio {

    Administrador registrarAdministrador(Administrador a) throws Exception;

    void actualizarAdministrador(String id,Administrador administrador) throws Exception;

    void eliminarAdministrador(String email,String password) throws Exception;

    Administrador obtenerAdministrador(String id) throws Exception;

    Administrador obtenerAdministradorEmail(String email) throws Exception;

    void actualizar(String id,Administrador a);

    List<Administrador> listarAdministradores();

    List<Administrador> obtenerAdminsRegistrados(String emailAdm);

    Administrador obtenerEmailPassword(String email,String password) throws Exception;

    List<Usuario> obtenerUsuariosRegistrados(String emailAdm);

    List<Libro> obtenerLibrosRegistrados(String emailAdm);
}

