package ar.uba.fi.pruebalistasypersistencia;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import ar.uba.fi.pruebalistasypersistencia.config.ConfigArmarioBD;
import ar.uba.fi.pruebalistasypersistencia.config.ConfigTablaPrenda;
import ar.uba.fi.pruebalistasypersistencia.prendas.Prenda;
import ar.uba.fi.pruebalistasypersistencia.prendas.PrendasAdapter;
import ar.uba.fi.pruebalistasypersistencia.prendas.PrendasCursorAdapter;
import fi.uba.ar.api.persistencia.BaseDeDatosHelper;

public class MostrarListaActivity extends Activity {

    private BaseDeDatosHelper _bdHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We'll define a custom screen layout here (the one shown above), but
        // typically, you could just use the standard ListActivity layout.
        setContentView(R.layout.activity_mostrar_lista);

        _bdHelper = BaseDeDatosHelper.crearHelper(getApplicationContext(), new ConfigArmarioBD());
//        mostrarUsandoArrayList();
        mostrarUsandoCusor();
    }

    private void mostrarUsandoArrayList() {

        ArrayList<Prenda> prendas = obtenerPrendas();

        PrendasAdapter prendasAdapter = new PrendasAdapter(getApplicationContext(), prendas);

        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lista);
        listView.setAdapter(prendasAdapter);
    }

    private void mostrarUsandoCusor() {

        Cursor prendas = obtenerPrendasCursor();

        // Find ListView to populate
        ListView listView = (ListView) findViewById(R.id.lista);

        // Setup cursor adapter using cursor from last step
        PrendasCursorAdapter prendasAdapter = new PrendasCursorAdapter(getApplicationContext(), prendas);

        // Attach cursor adapter to the ListView
        listView.setAdapter(prendasAdapter);
    }

    private Cursor obtenerPrendasCursor() {
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

        return cursor;
    }

    private ArrayList<Prenda> obtenerPrendas()
    {
        ArrayList<Prenda> prendas = new ArrayList<Prenda>();

        Log.d("DANE","leerRegistros!");

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
            prendas.add(new Prenda(valor_id, valor_nombre, valor_foto, valor_categoria));
            Log.d("DANE","Item - valor_id: " + valor_id + " - valor_nombre: " + valor_nombre + " - valor_foto: " + valor_foto + " - valor_categoria: " + valor_categoria);
        }
        cursor.close();

        if(db != null && db.isOpen())
        {
            db.close();
        }

        return prendas;
    }

    private void insertarRegistro() {


        // Gets the data repository in write mode
        SQLiteDatabase db = _bdHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ConfigTablaPrenda.COLUMNA_NOMBRE, "remera");
        values.put(ConfigTablaPrenda.COLUMNA_FOTO, "foto uri");
        values.put(ConfigTablaPrenda.COLUMNA_CATEGORIA, "torso");

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(ConfigTablaPrenda.NOMBRE_TABLA, null, values);
        Log.d("DANE","newRowId: " + newRowId);

        if(db != null && db.isOpen())
        {
            db.close();
        }
    }

    private void leerRegistros() {
        Log.d("DANE","leerRegistros!");
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

            Log.d("DANE","Item - valor_id: " + valor_id + " - valor_nombre: " + valor_nombre + " - valor_foto: " + valor_foto + " - valor_categoria: " + valor_categoria);
        }
        cursor.close();

        if(db != null && db.isOpen())
        {
            db.close();
        }
    }
}
