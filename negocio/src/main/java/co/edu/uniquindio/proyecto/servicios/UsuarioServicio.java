package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.entidades.*;

import java.util.List;

public interface UsuarioServicio {

    Usuario registrarUsuario(Usuario u) throws Exception;

    void actualizarUsuario(String email, Usuario u) throws Exception;

    void actualizar(String id,Usuario u) throws Exception;

    void eliminarUsuario(String email, String password) throws Exception;

    void eliminarUsuarioId(String id) throws Exception;

    void reservarLibro(Libro libro,String cedula) throws Exception;

    void devolverLibro(int idReserva,int idLibro,String cedula) throws Exception;

    Usuario obtenerUsuario(String id) throws Exception;

    Reserva obtenerReserva(int id) throws Exception;

    void asignarMulta(int idReserva)throws Exception;

    List<Reserva> obtenerHistorialReserva(String cedulaU);

    Multa obtenerMultaUsuario(int idMulta,String cedula) throws Exception;

    void registrarTarjetaUsuario(String idUsuario,String numero,String codigo,String fecha)throws Exception;

    List<Reserva> obtenerReservasActivas(String cedulaU);

    List<Multa> obtenerHistorialMultas(String cedulaU);

    List<Multa> obtenerMultasActivas(String cedulaU);

    void pagarMulta(int idMulta,String cedula,String numeroTarjeta);

    Usuario obtenerUsuarioEmail(String email) throws Exception;

    Usuario obtenerUsuarioEmailPassword(String email, String password) throws Exception;

    List<Usuario> listarUsuarios();

}

