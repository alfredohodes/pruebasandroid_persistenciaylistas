package fi.uba.ar.listas;

import android.util.Log;

import com.orm.dsl.Unique;

import java.util.List;

import fi.uba.ar.api.persistencia.ObjetoPersistente;

/**
 * Created by Alfredo on 6/4/2017.
 */

public class Categoria extends ItemLista {

    @Unique
    public String nombre;

    public Categoria padre;

    public Categoria()
    {

    }

    public Categoria(String nombre)
    {
        this.nombre = nombre;
    }

    public String toString()
    {
        return "{Categoria(" + getId() + ") - Nombre: " + nombre + " - padre: " + padre + "}";
    }

    public void Renombrar(String nuevoNombre)
    {
        Log.d("DANE","Renombrar Categoria(" + nombre + ") -> " + nuevoNombre);
        this.nombre = nuevoNombre;
        save();
    }

    public void SetPadre(Categoria padre)
    {
        this.padre = padre;
        save();
    }

    /**
     *
     * @param nombreCategoria
     * @return
     */
    public static Categoria crear(String nombreCategoria, Categoria padre) {
        Categoria categoria = obtener(nombreCategoria, padre);
        if(categoria == null)
        {
            categoria = new Categoria(nombreCategoria);
            categoria.padre = padre;
            categoria.save();
        }
        return  categoria;
    }

    /**
     *
     * @param nombreCategoria
     * @return
     */
    public static Categoria obtener(String nombreCategoria, Categoria padre) {
        List<Categoria> categorias;
        String padreId = (padre != null)?padre.getId().toString():"0";
        return ObjetoPersistente.findFirst(Categoria.class, "nombre = ? AND padre=?", nombreCategoria, padreId);
    }

    public static Categoria obtenerDeCualquierPadre(String nombreCategoria) {
        List<Categoria> categorias;
        return ObjetoPersistente.findFirst(Categoria.class, "nombre = ?", nombreCategoria);
    }

    public static Categoria obtenerOCrear(String nombreCategoria, Categoria padre) {

        Categoria categoria = obtener(nombreCategoria, padre);
        if(categoria == null)
        {
            categoria = crear(nombreCategoria, padre);
        }
        return  categoria;
    }
}
