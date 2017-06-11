package fi.uba.ar.listas;

import android.util.Log;

import com.orm.SugarRecord;

import java.util.List;

import ar.uba.fi.pruebalistasypersistencia.Prenda;
import fi.uba.ar.api.persistencia.ObjetoPersistente;

/**
 * Created by Alfredo on 6/4/2017.
 */

public class ItemLista extends ObjetoPersistente
{
    public Categoria categoria;
    public ItemLista()
    {
        super();
    }

    public ItemLista(Categoria categoria)
    {
        super();
        this.categoria = categoria;
        Log.d("DANE","nuevo ItemLista");
    }

    public String toString()
    {
        return "{ItemLista(" + getId() + ") - Categoria: " + categoria + "}";
    }

    public void agregarEtiqueta(String nombreEtiqueta)
    {
        agregarEtiqueta(Etiqueta.obtenerOCrear(nombreEtiqueta));
    }
    public void agregarEtiqueta(Etiqueta etiqueta)
    {
        ParEtiquetaItem<Prenda> parEtiquetaItem = new ParEtiquetaItem<Prenda>((Prenda)this,etiqueta);
        parEtiquetaItem.save();
        Log.d("DANE","agregarEtiqueta - parEtiquetaItem: " + parEtiquetaItem + " - etiqueta: " + etiqueta.nombre);
    }

    public void setCategoria(Categoria categoria)
    {
        this.categoria = categoria;
        save();
    }

    public void quitarEtiqueta(String nombreEtiqueta)
    {
        Log.d("DANE","quitarEtiqueta(" + nombreEtiqueta + ")");
        ParEtiquetaItem.eliminarPar(this, Etiqueta.obtener(nombreEtiqueta));
    }
    public void quitarEtiqueta(Etiqueta etiqueta)
    {
        if(etiqueta != null)
        {
            Log.d("DANE","quitarEtiqueta(" + etiqueta.nombre + ")");
            ParEtiquetaItem.eliminarPar(this, etiqueta);
        }
    }
    public List<Etiqueta> obtenerEtiquetas()
    {
        Log.d("DANE","LISTAR ETIQUETAS DE " + this.getId().toString());
        List<ParEtiquetaItem> pares = ObjetoPersistente.find(ParEtiquetaItem.class, "item = ?", this.getId().toString());
        for (int i=0; i<pares.size(); i++) {
            Log.d("DANE","---ET_IT[" + i + "]: + " + pares.get(i));
        }
        return null;
    }
}
