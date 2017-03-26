package ar.uba.fi.pruebalistasypersistencia.prendas;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import ar.uba.fi.pruebalistasypersistencia.R;
import ar.uba.fi.pruebalistasypersistencia.config.ConfigTablaPrenda;

/**
 * Created by Alfredo on 3/26/2017.
 */

public class PrendasCursorAdapter extends CursorAdapter {

    public PrendasCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.lista_prenda_item, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView txtId = (TextView) view.findViewById(R.id.id);
        TextView txtNombre = (TextView) view.findViewById(R.id.nombre);
        TextView txtFoto = (TextView) view.findViewById(R.id.foto);
        TextView txtCategoria = (TextView) view.findViewById(R.id.categoria);

        // Populate the data into the template view using the data object
        txtId.setText(Long.toString(cursor.getLong(cursor.getColumnIndexOrThrow(ConfigTablaPrenda._ID))));
        txtNombre.setText(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
        txtFoto.setText(cursor.getString(cursor.getColumnIndexOrThrow("foto")));
        txtCategoria.setText(cursor.getString(cursor.getColumnIndexOrThrow("categoria")));
    }

}
