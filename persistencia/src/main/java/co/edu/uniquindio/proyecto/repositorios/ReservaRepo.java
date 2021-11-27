package co.edu.uniquindio.proyecto.repositorios;

import co.edu.uniquindio.proyecto.entidades.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepo extends JpaRepository<Reserva,Integer> {

    @Query("select r from Reserva r where r.id =:idReserva and r.usuario.id =:cedulaUsuario")
    Reserva obtenerReservaUsuario(int idReserva,String cedulaUsuario);

    @Query("select r from Reserva r where r.usuario.id =:cedulaU")
    List<Reserva> obtenerhistorialReservasUsuario(String cedulaU);

    @Query("select r from Reserva r where r.usuario.id =:cedulaU and r.estado = true")
    List<Reserva> obtenerReservasActivasUsuario(String cedulaU);
}
