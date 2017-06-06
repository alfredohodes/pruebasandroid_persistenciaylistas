package fi.uba.ar.listas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import ar.uba.fi.pruebalistasypersistencia.config.ConfigArmarioBD;
import ar.uba.fi.pruebalistasypersistencia.config.ConfigTablaPrenda;
import ar.uba.fi.pruebalistasypersistencia.prendas.Prenda;
import fi.uba.ar.api.persistencia.BaseDeDatosHelper;
import fi.uba.ar.api.persistencia.ConfigTabla;

/**
 * Created by Alfredo on 3/26/2017.
 */

public class ListaPersistenteManual<T extends ElementoListaPersistente> {

    private ConfigTabla _configTabla;
    private BaseDeDatosHelper _bdHelper;

    private ListaPersistenteManual(Context context)
    {
        _bdHelper = BaseDeDatosHelper.crearHelper(context, new ConfigArmarioBD());
    }

    public static ListaPersistenteManual crearLista(Context context)
    {
        return new ListaPersistenteManual<Prenda>(context);
    }

    public ArrayList<T> obtenerTodo()
    {

        ArrayList<T> prendas = new ArrayList<T>();

        Log.d("DANE","ListaPersistente - obtenerTodo");
        SQLiteDatabase db = _bdHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                ConfigTablaPrenda._ID,
                ConfigTablaPrenda.COLUMNA_NOMBRE,
                ConfigTablaPrenda.COLUMNA_FOTO,
                ConfigTablaPrenda.COLUMNA_CATEGORIA
        };

// Filter results WHERE "title" = 'My Title'
        String selection = ConfigTablaPrenda.COLUMNA_NOMBRE + " = ?";
        String[] selectionArgs = { "My Title" };

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                ConfigTablaPrenda.COLUMNA_NOMBRE + " DESC";

        // No selection
        selection = null;
        selectionArgs = null;

        Cursor cursor = db.query(
                ConfigTablaPrenda.NOMBRE_TABLA, // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        Log.d("DANE","cursor: " + cursor);
        while(cursor.moveToNext()) {
            long valor_id = cursor.getLong(
                    cursor.getColumnIndexOrThrow(ConfigTablaPrenda._ID));
            String valor_nombre = cursor.getString(
                    cursor.getColumnIndexOrThrow(ConfigTablaPrenda.COLUMNA_NOMBRE));
            String valor_foto = cursor.getString(
                    cursor.getColumnIndexOrThrow(ConfigTablaPrenda.COLUMNA_FOTO));
            String valor_categoria = cursor.getString(
                    cursor.getColumnIndexOrThrow(ConfigTablaPrenda.COLUMNA_CATEGORIA));
            T.crearDesdeCursor();
           // prendas.add(new Prenda(valor_id, valor_nombre, valor_foto, valor_categoria));
            Log.d("DANE","Item - valor_id: " + valor_id + " - valor_nombre: " + valor_nombre + " - valor_foto: " + valor_foto + " - valor_categoria: " + valor_categoria);
        }
        cursor.close();

        if(db != null && db.isOpen())
        {
            db.close();
        }

        return prendas;
    }
}
