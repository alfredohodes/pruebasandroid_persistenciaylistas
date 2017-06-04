package ar.uba.fi.pruebalistasypersistencia;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

/**
 * Created by Alfredo on 6/4/2017.
 */

public class Categoria extends SugarRecord
{
    @Unique
    String nombre;

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
}
