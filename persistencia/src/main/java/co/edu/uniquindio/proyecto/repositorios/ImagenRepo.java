package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenRepo extends JpaRepository<Imagen,Integer>{

    //================================= REPOSITORIO DE IMAGEN =================================//

    @Query("select i.url from Imagen i where i.libro.isbn=:isbnLibro")
    String obtenerUrlImagenLibro(String isbnLibro);

}
