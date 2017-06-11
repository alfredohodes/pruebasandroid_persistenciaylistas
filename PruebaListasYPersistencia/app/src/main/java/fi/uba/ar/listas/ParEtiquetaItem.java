package fi.uba.ar.listas;

import com.orm.dsl.Ignore;

import java.util.List;

import fi.uba.ar.api.persistencia.ObjetoPersistente;

/**
 * Created by Alfredo on 6/4/2017.
 */

public class ParEtiquetaItem<T extends ItemLista> extends ItemLista {

    public Etiqueta etiqueta;
    public T item;

    @Ignore
    private Class<T> itemType;

    private String itemTypeName;

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
        itemTypeName = itemType.getName();
//        Log.d("DANE","new ParEtiquetaItem - itemType.getName(): " + typeStr);
    }

    public String toString()
    {
        return "{ParEtiquetaItem(" + getId() + ") item: " + item + " - etiqueta: " + etiqueta.nombre + "}";
    }

    @Override
    public void InicializarPostLoaded()
    {
        super.InicializarPostLoaded();
//        Log.d("DANE","Prenda ItemType: " + itemType + " - typeStr: " + typeStr + " parId: " + getId());
        if(item == null || item.getClass().toString() !=  itemTypeName)
        {
//            Log.d("DANE","Buscando item de tipo " + typeStr);
            try
            {
//                Log.d("DANE","Class.forName(typeStr): " + Class.forName(typeStr));
                itemType = (Class<T>)Class.forName(itemTypeName);
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

    public static ParEtiquetaItem encontrarPar(ItemLista itemLista, Etiqueta etiqueta)
    {
//        Log.d("DANE","encontrarPar - itemLista: " + itemLista + " - etiqueta: " + etiqueta);
        if(itemLista == null || etiqueta == null) return  null;

        String nombreAtributoItem       = "ITEM";
        String nombreAtributoEtiqueta   = "ETIQUETA";
        String nombreAtributoItemType   = "ITEM_TYPE_NAME";

        String idItem = itemLista.getId().toString();
        String idEtiqueta = etiqueta.getId().toString();
        String valorItemType = itemLista.getClass().getName();

        String whereClause = nombreAtributoItem + "=? AND " + nombreAtributoEtiqueta + "=? AND " + nombreAtributoItemType + "=?";
//        Log.d("DANE","whereClause: " + whereClause + "  --  (" + idItem + "," + idEtiqueta + "," + valorItemType + ")");
        List<ParEtiquetaItem> list = (List<ParEtiquetaItem>)ObjetoPersistente.find(ParEtiquetaItem.class, whereClause, idItem, idEtiqueta, valorItemType);
        if (list.isEmpty())
        {
//            Log.d("DANE","par not found");
            return null;
        }
//        Log.d("DANE","par: " + list.get(0));
        return (ParEtiquetaItem)list.get(0);
    }

    public static  void eliminarPar(ItemLista itemLista, Etiqueta etiqueta)
    {
//        Log.d("DANE","eliminarPar");
        ParEtiquetaItem par = encontrarPar(itemLista, etiqueta);
        if(par != null) par.delete();
//        Log.d("DANE","eliminarPar - par: " + par);
    }

    public static void eliminarRelacionesParaItem(ItemLista itemLista)
    {
//        Log.d("DANE","eliminarRelacionesParaItem - itemLista: " + itemLista);
        if(itemLista == null) return;

        String nombreAtributoItem       = "ITEM";
        String nombreAtributoItemType   = "ITEM_TYPE_NAME";

        String idItem = itemLista.getId().toString();
        String valorItemType = itemLista.getClass().getName();

        String whereClause = nombreAtributoItem + "=? AND " + nombreAtributoItemType + "=?";
//        Log.d("DANE","whereClause: " + whereClause + "  --  (" + idItem + "," + valorItemType + ")");
        List<ParEtiquetaItem> list = ObjetoPersistente.find(ParEtiquetaItem.class, whereClause, idItem, valorItemType);
        for(int i = 0; i < list.size(); i++)
        {
//            Log.d("DANE","Borrando Par " + list.get(i));
            list.get(i).delete();
        }
    }

    public static void eliminarRelacionesParaEtiqueta(Etiqueta etiqueta)
    {
//        Log.d("DANE","eliminarRelacionesParaEtiqueta - etiqueta: " + etiqueta);
        if(etiqueta == null) return;

        String nombreAtributoEtiqueta   = "ETIQUETA";
        String idEtiqueta = etiqueta.getId().toString();

        String whereClause = nombreAtributoEtiqueta + "=?";
//        Log.d("DANE","whereClause: " + whereClause + "  --  (" + idEtiqueta + ")");
        List<ParEtiquetaItem> list = ObjetoPersistente.find(ParEtiquetaItem.class, whereClause, idEtiqueta);
        for(int i = 0; i < list.size(); i++)
        {
//            Log.d("DANE","Borrando Par " + list.get(i));
            list.get(i).delete();
        }
    }
}
