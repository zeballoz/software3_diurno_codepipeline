package co.edu.uniquindio.proyecto.bean;

import co.edu.uniquindio.proyecto.entidades.*;
import co.edu.uniquindio.proyecto.servicios.ImagenServicio;
import co.edu.uniquindio.proyecto.servicios.LibroServicio;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@ViewScoped
public class LibroBean implements Serializable {

    private final LibroServicio libroServicio;
    private final ImagenServicio imagenServicio;

    @Getter
    @Setter
    private Libro libro;

    @Getter
    @Setter
    private List<Ciudad> ciudades;

    @Value(value = "#{seguridadBean.persona}")
    private Persona personaLogin;

    @Value("${upload.url}")
    private String urlImagenes;
    private ArrayList<Imagen> imagenes;

    @Getter @Setter
    private Date fechaLibro;


    public LibroBean(LibroServicio libroServicio, ImagenServicio imagenServicio) {
        this.libroServicio = libroServicio;
        this.imagenServicio = imagenServicio;
    }

    @PostConstruct
    public void inicializar() {
        this.libro= new Libro();
        this.imagenes = new ArrayList<>();
    }


    public void registrarLibro(Date fechaLibro) {
        try {
            if (personaLogin != null) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                String fecha = sdf.format(fechaLibro);


                libro.setAdministrador((Administrador) personaLogin);
                libro.setFechaPublicacion(fecha);

                Libro libroCreado = libroServicio.registrarLibro(this.libro);

                for (Imagen i : imagenes) {
                    i.setLibro(libroCreado);
                    imagenServicio.registrarImagen(i);
                }

                libroCreado.setImagenes(imagenes);

                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "¡Super! el libro se registro correctamente");
                FacesContext.getCurrentInstance().addMessage("mensajePersonalizado", facesMsg);

            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }


    public void actualizarLibro(Date fechaLibro) {

        if (personaLogin != null) {

            try {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                String fecha = sdf.format(fechaLibro);

                libro.setFechaPublicacion(fecha);
                libroServicio.actualizarLibro(libro.getIsbn(),libro);
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "¡Super! el libro se actualizo correctamente");
                FacesContext.getCurrentInstance().addMessage("mensajePersonalizado", facesMsg);

            } catch (Exception e) {

                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", "No pudimos actualizar el libro");
                FacesContext.getCurrentInstance().addMessage(null, msg);

            }
        }
    }


    public void eliminarLibro() {

        if (personaLogin != null) {
            try {
                List<Imagen> imagenes = imagenServicio.obtenerImagenesLibro(libro.getId());

                for (Imagen i : imagenes) {
                    imagenServicio.eliminarImagen(i.getId());
                }

                libroServicio.eliminarLibro(libro.getId());
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", "¡Super! el libro se elimino correctamente");
                FacesContext.getCurrentInstance().addMessage("mensajePersonalizado", facesMsg);

            } catch (Exception e) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Alerta", "No pudimos eliminar el libro");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }


    public void subirImagenes(FileUploadEvent event) {

        UploadedFile imagen = event.getFile();
        String nombreImagen = subirImagen(imagen);

        if (nombreImagen != null) {

            Imagen foto = new Imagen(nombreImagen);

            imagenes.add(foto);
        }
    }

    public String subirImagen(UploadedFile file) {

        try {
            InputStream input = file.getInputStream();
            String fileName = FilenameUtils.getName(file.getFileName());
            String baseName = FilenameUtils.getBaseName(fileName) + "_";
            String extension = "." + FilenameUtils.getExtension(fileName);
            File fileDest = File.createTempFile(baseName, extension, new File(urlImagenes));
            FileOutputStream output = new FileOutputStream(fileDest);
            IOUtils.copy(input, output);

            return fileDest.getName();
        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

}
