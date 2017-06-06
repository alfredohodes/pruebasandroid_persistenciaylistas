package fi.uba.ar.listas;

import java.util.List;

/**
 * Created by Alfredo on 6/4/2017.
 */

public class ListaUtils {


    public static <T extends ItemLista> List<T> listarTodos(Class<T> type)
    {
        return T.listAll(type);
    }

    public static <T extends ItemLista> List<T> listarPorCategoria(Class<T> type, Categoria cat)
    {
        return null;
    }
}
