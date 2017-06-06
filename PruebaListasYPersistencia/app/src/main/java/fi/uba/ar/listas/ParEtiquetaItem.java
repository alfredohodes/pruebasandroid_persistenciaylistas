package fi.uba.ar.listas;

import com.orm.dsl.Unique;

/**
 * Created by Alfredo on 6/4/2017.
 */

public class ParEtiquetaItem extends ItemLista {

    public Etiqueta etiqueta;
    public ItemLista item;

    public ParEtiquetaItem()
    {

    }

    public ParEtiquetaItem(ItemLista item, Etiqueta etiqueta)
    {
        this.item = item;
        this.etiqueta = etiqueta;
    }

    public String toString()
    {
        return "{ParEtiquetaItem(" + getId() + ") item: " + item + " - etiqueta: " + etiqueta.nombre + "}";
    }
}
