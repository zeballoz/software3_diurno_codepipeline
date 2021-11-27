package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.entidades.*;
import co.edu.uniquindio.proyecto.repositorios.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {

    private final UsuarioRepo usuarioRepo;
    private final LibroRepo libroRepo;
    private final ReservaRepo reservaRepo;
    private final MultaRepo multaRepo;

    public UsuarioServicioImpl(UsuarioRepo usuarioRepo, LibroRepo libroRepo, ReservaRepo reservaRepo, MultaRepo multaRepo) {
        this.usuarioRepo = usuarioRepo;
        this.libroRepo = libroRepo;
        this.reservaRepo = reservaRepo;
        this.multaRepo = multaRepo;
    }


    public boolean estaDisponible(String email){
        Usuario usuarioEmail = usuarioRepo.findByEmail(email);

        if (usuarioEmail!=null){
            return  true;
        }

        return false;
    }


    @Override
    public Usuario registrarUsuario(Usuario u) throws Exception {

        if (u.getId().length()>10){
            throw new Exception("La cedula solo puede tener 10 caracteres ");
        }


        if (u.getTelefono().length()>30){
            throw new Exception("El telefono solo puede tener 30 caracteres ");
        }

        if (u.getEmail().length()>100){
            throw new Exception("El correo solo puede tener 100 caracteres ");
        }

        if (u.getNombre().length()>100){
            throw new Exception("El nombre solo puede tener 100 caracteres compa");
        }

        if (u.getPassword().length()>100){
            throw new Exception("La contraseña solo puede tener 100 caracteres compa");
        }

        if(estaDisponible(u.getEmail())){
            throw new Exception("El usuario ya existe");
        }

        return usuarioRepo.save(u);
    }

    @Override
    public void actualizarUsuario(String cedula,Usuario u) throws Exception {

        Usuario usuarioEncontrado = obtenerUsuario(cedula);

        if(usuarioEncontrado!=null){

            usuarioEncontrado.setNombre(u.getNombre());
            usuarioEncontrado.setPassword(u.getPassword());
            usuarioEncontrado.setCiudad(u.getCiudad());
            usuarioEncontrado.setEmail(u.getEmail());
            usuarioEncontrado.setTelefono(u.getTelefono());

            usuarioRepo.save(usuarioEncontrado);
        }

    }

    @Override
    public void actualizar(String id,Usuario u){

        try {
            Usuario usuarioEncontrado = obtenerUsuario(id);

            if (usuarioEncontrado!=null){

                usuarioEncontrado.setNombre(u.getNombre());
                usuarioEncontrado.setPassword(u.getPassword());
                usuarioEncontrado.setCiudad(u.getCiudad());
                usuarioEncontrado.setTelefono(u.getTelefono());
                usuarioEncontrado.setEmail(u.getEmail());

                usuarioRepo.save(usuarioEncontrado);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void eliminarUsuario(String email,String password) throws Exception {

        Usuario usuarioEncontrado = obtenerUsuarioEmailPassword(email,password) ;

        if (usuarioEncontrado != null){
            usuarioRepo.delete(usuarioEncontrado);
        }else{
            throw new Exception("Usuario no encontrado ");
        }
    }

    @Override
    public void eliminarUsuarioId(String id) throws Exception {

        Usuario usuarioEncontrado = obtenerUsuario(id);

        if (usuarioEncontrado!=null){
            usuarioRepo.delete(usuarioEncontrado);
        }else{
            throw new Exception("Usuario no encontrado ");
        }
    }


    @Override
    public Usuario obtenerUsuario(String id) throws Exception {

        Optional<Usuario> usuario = usuarioRepo.findById(id);

        if(usuario.isEmpty()){
            throw new Exception("No existe un usuario con el id dado");
        }

        return usuario.get();
    }

    @Override
    public Reserva obtenerReserva(int id) throws Exception {

        Optional<Reserva> reserva = reservaRepo.findById(id);

        if (reserva.isEmpty()){

            throw new Exception("No se encontro la reserva");
        }

        return reserva.get();
    }

    @Override
    public Usuario obtenerUsuarioEmail(String email) throws Exception {

        Usuario usuario = usuarioRepo.findByEmail(email);

        if(usuario!=null){
            throw new Exception("No existe un usuario con el id dado");
        }

        return usuario;
    }

    @Override
    public void registrarTarjetaUsuario(String idUsuario,String numero, String codigo, String fecha) throws Exception {

        Usuario usuarioEncontrado= obtenerUsuario(idUsuario);

        if (usuarioEncontrado!=null){

            usuarioEncontrado.setNumeroTarjeta(numero);
            usuarioEncontrado.setCodigoTarjeta(codigo);
            usuarioEncontrado.setFechatarjeta(fecha);
            usuarioRepo.save(usuarioEncontrado);
        }else{
            throw new Exception("El usuario no existe");
        }
    }

    @Override
    public Usuario obtenerUsuarioEmailPassword(String email,String password) throws Exception {

        Usuario usuario = usuarioRepo.findByEmailAndPassword(email,password);

        if(usuario==null){
            throw new Exception("No existe un usuario con el correo y contraseña ingresado");
        }

        return usuario;
    }

    @Override
    public void reservarLibro(Libro libro,String cedula) throws Exception{

        Usuario usuario = usuarioRepo.obtenerUsuarioCedula(cedula);
        Date prestamo = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(prestamo);
        c.add(Calendar.DATE,7);
        Date finPrestmo = c.getTime();

        Reserva reserva = new Reserva();

        if (usuario!=null && libro!=null && libro.getEstado()){

            reserva.setUsuario(usuario);
            reserva.setLibro(libro);
            reserva.setEstado(true);
            reserva.setFechaPrestamo(prestamo);
            reserva.setFechaFinPrestamo(finPrestmo);
            reservaRepo.save(reserva);
            libro.setEstado(false);
            libroRepo.save(libro);
            usuario.getReservas().add(reserva);
            usuarioRepo.save(usuario);

        }else{
            throw new Exception("El libro no se encuentra disponible");
        }
    }

    @Override
    public void devolverLibro(int idReserva,int idLibro,String cedula) throws Exception{

        Usuario usuario = usuarioRepo.obtenerUsuarioCedula(cedula);
        Libro libro = libroRepo.findById(idLibro);
        Date devolucion = new Date();

        Reserva reserva = reservaRepo.obtenerReservaUsuario(idReserva,usuario.getId());

        if (reserva !=null && reserva.getEstado()){

            reserva.setEstado(false);
            reserva.setFechaDevolucion(devolucion);
            reservaRepo.save(reserva);
            libro.setEstado(true);
            libroRepo.save(libro);
            usuario.getReservas().remove(reserva);
            usuarioRepo.save(usuario);

        }else{
            throw new Exception("No tienes reservas activas");
        }
    }

    @Override
    public List<Reserva> obtenerHistorialReserva(String cedulaU){

        List<Reserva> historialReserva = reservaRepo.obtenerhistorialReservasUsuario(cedulaU);

        return historialReserva;
    }

    @Override
    public List<Reserva> obtenerReservasActivas(String cedulaU)  {

        List<Reserva> reservasActivas = reservaRepo.obtenerReservasActivasUsuario(cedulaU);

        return reservasActivas;
    }


    @Override
    public void asignarMulta(int idReserva)throws Exception{

        Reserva reservaEncontrada = obtenerReserva(idReserva);
        Usuario usuarioEncontrado = reservaEncontrada.getUsuario();

        Date inicioPrestamo = reservaEncontrada.getFechaPrestamo();
        Date devolucion = reservaEncontrada.getFechaDevolucion();

        long diff = devolucion.getTime() - inicioPrestamo.getTime();

        TimeUnit time = TimeUnit.DAYS;
        long diffrence = time.convert(diff, TimeUnit.MILLISECONDS);

        Multa multa = new Multa();

        if (diffrence>7 && usuarioEncontrado !=null && reservaEncontrada !=null){

            multa.setFechaMulta(new Date());
            multa.setPrecioMulta(20000);
            multa.setDescripcion("¡Ups! Se te cobrara una multa por pasarte del tiempo limite del prestamo");
            multa.setEstado(true);
            multa.setUsuario(usuarioEncontrado);
            multaRepo.save(multa);
            usuarioEncontrado.getMultas().add(multa);
            usuarioRepo.save(usuarioEncontrado);
        }
    }


    @Override
    public List<Multa> obtenerHistorialMultas(String cedulaU){

        List<Multa> historialMultas = multaRepo.obtenerHistorioMultasUsuario(cedulaU);

        return historialMultas;
    }

    @Override
    public Multa obtenerMultaUsuario(int idMulta,String cedula) throws Exception{

        Multa multa = multaRepo.obtenerMultaUsuario(idMulta,cedula);

        if (multa== null){

            throw new Exception("No existe tal multa");
        }

        return multa;
    }

    @Override
    public List<Multa> obtenerMultasActivas(String cedulaU) {

        List<Multa> multasActivas = multaRepo.obtenerMultasActivasUsuario(cedulaU);

        return multasActivas;
    }

    @Override
    public void pagarMulta(int idMulta,String cedula,String numeroTarjeta){

        Usuario usuarioEncontrado = usuarioRepo.obtenerUsuarioPago(cedula,numeroTarjeta);
        Multa multaEncontrada = multaRepo.obtenerMultaUsuario(idMulta,usuarioEncontrado.getId());

        if (multaEncontrada!=null && usuarioEncontrado!=null){

            multaEncontrada.setEstado(false);
            multaEncontrada.setFechaPago(new Date());
            multaRepo.save(multaEncontrada);
        }
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepo.findAll();
    }


}
