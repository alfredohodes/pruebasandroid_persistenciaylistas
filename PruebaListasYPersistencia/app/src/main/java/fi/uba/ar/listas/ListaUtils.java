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
}
