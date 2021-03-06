package fi.uba.ar.listas;

import android.util.Log;

import com.orm.dsl.Unique;

import java.util.List;

import fi.uba.ar.api.persistencia.ObjetoPersistente;

/**
 * Created by Alfredo on 6/4/2017.
 */

public class Etiqueta extends ItemLista {

    @Unique
    public String nombre;

    public Etiqueta() {
//        Log.d("DANE","nueva Etiqueta");
    }

    public Etiqueta(String nombre) {
        this.nombre = nombre;
//        Log.d("DANE","nueva Etiqueta(" + nombre + ")");
    }

    public void Renombrar(String nuevoNombre)
    {
        Log.d("DANE","Renombrar Etiqueta(" + nombre + ") -> " + nuevoNombre);
        this.nombre = nuevoNombre;
        save();
    }

    public String toString() {
        return "{Etiqueta(" + getId() + ") - Nombre: " + nombre + "}";
    }

    public static Etiqueta obtenerOCrear(String nombreEtiqueta)
    {
        Etiqueta etiqueta = obtener(nombreEtiqueta);
        if(etiqueta == null)
        {
            etiqueta = new Etiqueta(nombreEtiqueta);
            etiqueta.save();
        }
        return etiqueta;
    }

    public static Etiqueta obtener(String nombreEtiqueta)
    {
        List<Etiqueta> etiquetas = ObjetoPersistente.find(Etiqueta.class, "nombre = ?", nombreEtiqueta);
        if (etiquetas == null || etiquetas.size() == 0) return null;
        return etiquetas.get(0);
    }

    @Override
    public boolean delete()
    {
        // Antes de eliminar Etiqueta, elminar las relaciones Many-to-Many con Etiquetas
        ParEtiquetaItem.eliminarRelacionesParaEtiqueta(this);
        return super.delete();
    }
}
