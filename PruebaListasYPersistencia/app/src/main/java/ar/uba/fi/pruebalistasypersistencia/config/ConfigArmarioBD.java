package ar.uba.fi.pruebalistasypersistencia.config;

import fi.uba.ar.api.persistencia.ConfigBD;
import fi.uba.ar.api.persistencia.ConfigTabla;

/**
 * Created by Alfredo on 3/19/2017.
 */

public class ConfigArmarioBD implements ConfigBD {

    private static final int VERSION_BD = 2;
    private static final String NOMBRE_BD = "Armario.db";
    private static final  ConfigTabla[] TABLAS = new ConfigTabla[]{new ConfigTablaPrenda(), new ConfigTablaEtiqueta()};

    @Override
    public String getNombre() {
        return NOMBRE_BD;
    }

    @Override
    public int getVersion() {
        return VERSION_BD;
    }

    @Override
    public ConfigTabla[] getTablas() {
        return TABLAS;
    }
}
