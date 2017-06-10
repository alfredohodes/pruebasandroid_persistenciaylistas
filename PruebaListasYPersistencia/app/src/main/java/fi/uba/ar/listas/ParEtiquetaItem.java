package fi.uba.ar.listas;

import android.util.Log;

import com.orm.dsl.Unique;

import ar.uba.fi.pruebalistasypersistencia.Prenda;
import fi.uba.ar.api.persistencia.ObjetoPersistente;

/**
 * Created by Alfredo on 6/4/2017.
 */

public class ParEtiquetaItem<T extends ItemLista> extends ItemLista {

    public Etiqueta etiqueta;
    public T item;

    private Class<T> itemType;
    private String typeStr;

    public ParEtiquetaItem()
    {
        Log.d("DANE","nuevo ParEtiquetaItem");
    }

    public ParEtiquetaItem(T item, Etiqueta etiqueta)
    {
        this.item = item;
        this.etiqueta = etiqueta;
        Log.d("DANE","nuevo ParEtiquetaItem(item: " + item + ",etiqueta: " + etiqueta + ")");

        itemType = (Class<T>)item.getClass();
        Log.d("DANE","new ParEtiquetaItem - itemType: " + itemType);
        typeStr = itemType.getName();
    }

    public String toString()
    {
        return "{ParEtiquetaItem(" + getId() + ") item: " + item + " - etiqueta: " + etiqueta.nombre + "}";
    }

    @Override
    public void InicializarPostLoaded()
    {
        super.InicializarPostLoaded();
        Log.d("DANE","Prenda ItemType: " + itemType + " - typeStr: " + typeStr);
        if(item == null || item.getClass().toString() !=  typeStr)
        {
            Log.d("DANE","Buscando item de tipo " + typeStr);
            try
            {
                Log.d("DANE","Class.forName(typeStr): " + Class.forName(typeStr));
                itemType = (Class<T>)Class.forName(typeStr);
            } catch (Exception ignored)
            {
                Log.d("DANE","Exception: " + ignored);
            }
            Log.d("DANE","Encontrado itemType: " + itemType);
            if(itemType != null)
            {
                long itemId = 1; //TODO: Encontrarlo bien
                item = ObjetoPersistente.findById(itemType, itemId);
            }
            Log.d("DANE","Encontrado item: " + item);
        }
    }
}
