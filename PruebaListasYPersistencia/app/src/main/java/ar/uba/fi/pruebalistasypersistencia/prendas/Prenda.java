package ar.uba.fi.pruebalistasypersistencia.prendas;

import android.util.Log;

import fi.uba.ar.listas.ElementoListaPersistente;

/**
 * Created by Alfredo on 3/26/2017.
 */

public class Prenda extends ElementoListaPersistente {

    public static ElementoListaPersistente crearDesdeCursor(){
        Log.d("DANE", "Creando desde Prenda");
        return null;
    };

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
