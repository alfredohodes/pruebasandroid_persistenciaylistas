package ar.uba.fi.pruebalistasypersistencia.config;

import fi.uba.ar.api.persistencia.ConfigTabla;

/**
 * Created by Alfredo on 3/19/2017.
 */

public class ConfigTablaPrenda implements ConfigTabla {

    public static final String NOMBRE_TABLA = "prenda";
    public static final String COLUMNA_NOMBRE = "nombre";
    public static final String COLUMNA_FOTO = "foto";
    public static final String COLUMNA_CATEGORIA = "categoria";

    @Override
    public String getNombre() {
        return "prenda";
    }

    @Override
    public String getScriptCrear() {
        return "CREATE TABLE " + NOMBRE_TABLA+ " (" +
                _ID + " INTEGER PRIMARY KEY," +
                COLUMNA_NOMBRE + " TEXT," +
                COLUMNA_FOTO + " TEXT," +
                COLUMNA_CATEGORIA + " TEXT)";
    }
}
