package fi.uba.ar.listas;

import android.util.Log;

import com.orm.dsl.Unique;
import com.orm.util.NamingHelper;

import java.lang.reflect.Field;
import java.util.List;

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
//        Log.d("DANE","nuevo ParEtiquetaItem");
    }

    public ParEtiquetaItem(T item, Etiqueta etiqueta)
    {
        this.item = item;
        this.etiqueta = etiqueta;
//        Log.d("DANE","nuevo ParEtiquetaItem(item: " + item + ",etiqueta: " + etiqueta + ")");

        itemType = (Class<T>)item.getClass();
//        Log.d("DANE","new ParEtiquetaItem - itemType: " + itemType);
        typeStr = "class " + itemType.getName();
        Log.d("DANE","new ParEtiquetaItem - itemType.getName(): " + typeStr);
    }

    public String toString()
    {
        return "{ParEtiquetaItem(" + getId() + ") item: " + item + " - etiqueta: " + etiqueta.nombre + "}";
    }

    @Override
    public void InicializarPostLoaded()
    {
        super.InicializarPostLoaded();
        Log.d("DANE","Prenda ItemType: " + itemType + " - typeStr: " + typeStr + " parId: " + getId());
        if(item == null || item.getClass().toString() !=  typeStr)
        {
//            Log.d("DANE","Buscando item de tipo " + typeStr);
            try
            {
//                Log.d("DANE","Class.forName(typeStr): " + Class.forName(typeStr));
                itemType = (Class<T>)Class.forName(typeStr);
            } catch (Exception ignored)
            {
//                Log.d("DANE","Exception: " + ignored);
            }
//            Log.d("DANE","Encontrado itemType: " + itemType);
            if(itemType != null)
            {
                String nombreAtributoItem = "ITEM";
                item = ObjetoPersistente.encontrarAtributo(this, nombreAtributoItem, itemType);
            }
//            Log.d("DANE","Encontrado item: " + item);
        }
    }

    public static <T extends ItemLista> ParEtiquetaItem encontrarPar(T itemLista, Etiqueta etiqueta)
    {
        Log.d("DANE","encontrarPar - itemLista: " + itemLista + " - etiqueta: " + etiqueta);
        if(itemLista == null || etiqueta == null) return  null;

        String nombreAtributoItem       = "ITEM";
        String nombreAtributoEtiqueta   = "ETIQUETA";
        String nombreAtributoItemType   = "ITEM_TYPE";

        String idItem = itemLista.getId().toString();
        String idEtiqueta = etiqueta.getId().toString();
        String valorItemType = "class " + itemLista.getClass().getName();
//        valorItemType = "class ar.uba.fi.pruebalistasypersistencia.Prenda";

        // TODO: Agregar condición de tipo de item!
        String whereClause = nombreAtributoItem + "=? AND " + nombreAtributoEtiqueta + "=? AND " + nombreAtributoItemType + "=?";
        Log.d("DANE","whereClause: " + whereClause + "  --  (" + idItem + "," + idEtiqueta + "," + valorItemType + ")");
        List<T> list = (List<T>)ObjetoPersistente.find(ParEtiquetaItem.class, whereClause, idItem, idEtiqueta, valorItemType);
        if (list.isEmpty())
        {
            Log.d("DANE","par not found");
            return null;
        }
        Log.d("DANE","par: " + list.get(0));
        return (ParEtiquetaItem)list.get(0);
    }

    public static <T extends ItemLista>  void eliminarPar(T itemLista, Etiqueta etiqueta)
    {
        Log.d("DANE","eliminarPar");
        ParEtiquetaItem par = encontrarPar(itemLista, etiqueta);
        if(par != null) par.delete();
        Log.d("DANE","eliminarPar - par: " + par);
    }
}
