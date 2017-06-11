package fi.uba.ar.listas;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import fi.uba.ar.api.persistencia.ObjetoPersistente;

/**
 * Created by Alfredo on 6/4/2017.
 */

public class ListaUtils {


    public static <T extends ItemLista> List<T> listarTodos(Class<T> type)
    {
        return T.listAll(type);
    }

    public static <T extends ItemLista> List<T> listarPorCategoria(Class<T> type, Categoria categoriaBase, boolean incluirSubcategorias)
    {
        Log.d("DANE","LISTAR POR CATEGORIA - " + categoriaBase);
        if(categoriaBase == null) return null;
        List<Categoria> categorias = new ArrayList<Categoria>();
        categorias.add(categoriaBase);
        if(incluirSubcategorias)
        {
            Stack<Categoria> categoriasABuscarHijos = new Stack<Categoria>();
            // 1: Agrego la categoria base para procesar
            categoriasABuscarHijos.push(categoriaBase);
            while(categoriasABuscarHijos.size() > 0)
            {
                // 2: Tomar un elemento y buscar todos sus hijos.
                Categoria categoriaActual = categoriasABuscarHijos.pop();
                Log.d("DANE","categoriaActual: " + categoriaActual);
                List<Categoria> hijos = ObjetoPersistente.find(Categoria.class, "PADRE =?", categoriaActual.getId().toString());
                for(int i = 0; i < hijos.size(); i++)
                {
                    Categoria catHijo = hijos.get(i);
                    Log.d("DANE","CATEGORIA HIJO ENCONTRADA! - " + catHijo.nombre + " hija de " + categoriaActual.nombre);
                    categoriasABuscarHijos.push(catHijo);
                    categorias.add(catHijo);
                }
            }
        }

        String whereClause = "";
        List<String> whereArgsList = new ArrayList<String>();
        for(int i = 0; i < categorias.size(); i++)
        {
//            nombreAtributoItem + "=? AND " + nombreAtributoItemType + "=?";
            whereClause += "CATEGORIA =?";
            if(i < categorias.size() - 1) whereClause+= " OR ";

            whereArgsList.add(categorias.get(i).getId().toString());
            Log.d("DANE","whereArgsList.add(id:" + categorias.get(i).getId().toString() + ")");
        }
        Log.d("DANE","whereClause: " + whereClause);
        String[] whereArgs = whereArgsList.toArray(new String[whereArgsList.size()]);
        return ObjetoPersistente.find(type, whereClause, whereArgs);
    }

    public static <T extends ItemLista> List<T> listarPorEtiqueta(Class<T> type, Etiqueta etiqueta)
    {
        Log.d("DANE","LISTAR POR ETIQUETA - " + etiqueta);
        if(etiqueta == null) return null;

        // 1: Obtener todas los "pares"
        String nombreAtributoEtiqueta   = "ETIQUETA";
        String nombreAtributoItemType   = "ITEM_TYPE_NAME";

        String idEtiqueta = etiqueta.getId().toString();
        String itemTypeName = type.getName();

        String whereClause = nombreAtributoEtiqueta + "=? AND " + nombreAtributoItemType + "=?";
//        Log.d("DANE","whereClause: " + whereClause + "  --  (" + idEtiqueta + ")");
        List<ParEtiquetaItem> list = ObjetoPersistente.find(ParEtiquetaItem.class, whereClause, idEtiqueta, itemTypeName);

        // 2: Recorrer todos los pares para obtener los items a devolver
        List<T> items = new ArrayList<T>();
        for(int i = 0; i < list.size(); i++)
        {
            items.add((T)list.get(i).item);
        }
        return  items;
    }

    public static <T extends ItemLista> List<T> listarPorEtiquetas(Class<T> type, Etiqueta[] etiquetas, String condicionEtiquetas)
    {
        Log.d("DANE","LISTAR POR ETIQUETAS - " + etiquetas);
        if(etiquetas == null || etiquetas.length == 0) return null;

        if(condicionEtiquetas != "OR") condicionEtiquetas = "AND";

        // 1: Pbtener todas los "pares"
        List<String> queryArgsList = new ArrayList<String>();
        // 1.a: Armar where clause con todas las etiquetas
        String nombreAtributoEtiqueta   = "ETIQUETA";
        String etiquetasWhereClause = "";
        for(int i = 0; i < etiquetas.length; i++)
        {
//            nombreAtributoItem + "=? AND " + nombreAtributoItemType + "=?";
            etiquetasWhereClause += nombreAtributoEtiqueta + " =?";
            if(i < etiquetas.length - 1) etiquetasWhereClause+= " " + condicionEtiquetas + " ";

            queryArgsList.add(etiquetas[i].getId().toString());
            Log.d("DANE","whereArgsList.add(id:" + etiquetas[i].getId().toString() + ")");
        }
        String nombreAtributoItemType   = "ITEM_TYPE_NAME";
        String itemTypeName = type.getName();

        queryArgsList.add(itemTypeName);

        String whereClause = "(" + etiquetasWhereClause + ") AND " + nombreAtributoItemType + "=?";
        Log.d("DANE","whereClause: " + whereClause + "  --  itemTypeName: " + itemTypeName);
        String[] whereArgs = queryArgsList.toArray(new String[queryArgsList.size()]);
        List<ParEtiquetaItem> list = ObjetoPersistente.find(ParEtiquetaItem.class, whereClause, whereArgs);

        // 2: Recorrer todos los pares para obtener los items a devolver
        List<T> items = new ArrayList<T>();
        for(int i = 0; i < list.size(); i++)
        {
            items.add((T)list.get(i).item);
        }
        return  items;
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
