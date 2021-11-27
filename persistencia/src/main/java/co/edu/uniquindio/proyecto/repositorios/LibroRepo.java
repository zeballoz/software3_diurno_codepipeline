package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.entidades.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepo extends JpaRepository<Libro,Integer> {

    Libro findByIsbn(String isbn);

    Libro findById(int id);

    @Query("select l from Libro l where l.administrador.email =:emailAdm")
    List<Libro> obtenerLibrosAdmin(String emailAdm);

    @Query("select i from Libro l join l.imagenes i where l.id = :idLibro")
    List<Imagen> obtenerImagenes(int idLibro);

    @Query("select l from Libro l where l.titulo like concat('%', :cadena,'%')")
    List<Libro> busquedaLibrosNombre(String cadena);

}
