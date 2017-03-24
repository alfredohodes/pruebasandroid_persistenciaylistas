package fi.uba.ar.api.persistencia;

/**
 * Created by Alfredo on 3/19/2017.
 */

public interface ConfigBD {

    public String getNombre();
    public int getVersion();
    public ConfigTabla[] getTablas();
}
