package ar.uba.fi.pruebalistasypersistencia.prendas;

/**
 * Created by Alfredo on 3/26/2017.
 */

public class Prenda {

    public long getId() {
        return id;
    }

    private long id;

    public String nombre;
    public String foto;
    public String categoria;

    public Prenda(long id, String nombre, String foto, String categoria)
    {
        this.id = id;
        this.nombre = nombre;
        this.foto = foto;
        this.categoria = categoria;
    }



}
