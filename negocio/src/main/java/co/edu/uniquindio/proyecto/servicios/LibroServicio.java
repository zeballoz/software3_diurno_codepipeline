package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.entidades.Libro;

import java.util.List;

public interface LibroServicio {

    Libro registrarLibro(Libro libro) throws Exception;

    void actualizarLibro(String isbn,Libro libro) throws Exception;

    void eliminarLibro(int id) throws Exception;

    Libro obtenerLibroId(int id) throws Exception;

    Libro obtenerLibro(String isbn) throws Exception;

    List<Libro> buscarLugares(String cadena);

    List<Libro> listarLibro();
}
