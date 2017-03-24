package ar.uba.fi.pruebalistasypersistencia.config;

import fi.uba.ar.api.persistencia.ConfigTabla;

/**
 * Created by Alfredo on 3/19/2017.
 */

public class ConfigTablaEtiqueta implements ConfigTabla {

    public static final String NOMBRE_TABLA = "etiqueta";
    public static final String COLUMNA_NOMBRE = "nombre";

    @Override
    public String getNombre() {
        return "prenda";
    }

    @Override
    public String getScriptCrear() {
        return "CREATE TABLE " + NOMBRE_TABLA+ " (" +
                _ID + " INTEGER PRIMARY KEY," +
                COLUMNA_NOMBRE + " TEXT)";
    }
}
