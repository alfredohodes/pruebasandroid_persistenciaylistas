package fi.uba.ar.listas;

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
        if(padre != null)
        {
            // Busca dentro de cat padre
            categorias = ObjetoPersistente.find(Categoria.class, "nombre = ? AND padre=?", nombreCategoria, padre.getId().toString());
        }
        else
        {
            // Busca entre las cats sin padre
            categorias = ObjetoPersistente.find(Categoria.class, "nombre = ?", nombreCategoria);
        }
        if (categorias == null || categorias.size() == 0) {
            return null;
        } else {
            return categorias.get(0);
        }
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
