package fi.uba.ar.listas;

import android.util.Log;
import java.util.List;

/**
 * Created by Alfredo on 6/4/2017.
 */

public class ListaPersistente<T extends ItemLista> {

    private Class<T> tipoItem;

    public T obtenerPorId(long id)
    {
        return T.findById(tipoItem, id);
    }

    public static <T extends ItemLista> ListaPersistente obtenerLista(Class<T> type)
    {
        return new ListaPersistente(type);
    }

    public List<T> obtenerTodo()
    {
        return T.listAll(tipoItem);
    }

    private ListaPersistente(Class<T> type)
    {
        this.tipoItem = type;
    }

    public void agregar(T item) {
        item.save();
    }

    public void eliminar(T item) {
        item.delete();
    }

    public void actualizar(T item) {
        item.update();
    }
}
