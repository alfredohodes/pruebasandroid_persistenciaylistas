package ar.uba.fi.pruebalistasypersistencia.prendas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ar.uba.fi.pruebalistasypersistencia.R;

/**
 * Created by Alfredo on 3/26/2017.
 */

public class PrendasAdapter extends ArrayAdapter<Prenda> {
    public PrendasAdapter(Context context, ArrayList<Prenda> prendas) {
        super(context, 0, prendas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Prenda prenda = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lista_prenda_item, parent, false);
        }

        // Lookup view for data population
        TextView txtId = (TextView) convertView.findViewById(R.id.id);
        TextView txtNombre = (TextView) convertView.findViewById(R.id.nombre);
        TextView txtFoto = (TextView) convertView.findViewById(R.id.foto);
        TextView txtCategoria = (TextView) convertView.findViewById(R.id.categoria);

        // Populate the data into the template view using the data object
        txtId.setText(Long.toString(prenda.getId()));
        txtNombre.setText(prenda.nombre);
        txtFoto.setText(prenda.foto);
        txtCategoria.setText(prenda.categoria);

        // Return the completed view to render on screen
        return convertView;
    }
}
