package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.entidades.Multa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MultaRepo extends JpaRepository<Multa,Integer>{

    @Query("select m from Multa m where m.id =:idMulta and m.usuario.id =:cedulaUsuario")
    Multa obtenerMultaUsuario(int idMulta,String cedulaUsuario);

    @Query("select m from Multa m where m.usuario.id =:cedulaU and m.estado = true")
    List<Multa> obtenerMultasActivasUsuario(String cedulaU);

    @Query("select m from Multa m where m.usuario.id =:cedulaU")
    List<Multa> obtenerHistorioMultasUsuario(String cedulaU);
}
