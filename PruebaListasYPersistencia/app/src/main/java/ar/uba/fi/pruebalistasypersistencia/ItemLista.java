package ar.uba.fi.pruebalistasypersistencia;

import com.orm.SugarRecord;

/**
 * Created by Alfredo on 6/4/2017.
 */

public class ItemLista extends SugarRecord
{
    String categoria;

    String nombre;



    public ItemLista()
    {

    }

    public ItemLista(String categoria, String nombre)
    {
        this.categoria = categoria;
        this.nombre = nombre;
    }

    public String toString()
    {
        return "{ItemLista(" + getId() + ") - Nombre: " + nombre +" - Categoria: " + categoria + "}";
    }
}
