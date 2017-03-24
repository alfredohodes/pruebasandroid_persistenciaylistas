package fi.uba.ar.api.persistencia;

import android.provider.BaseColumns;

/**
 * Created by Alfredo on 3/19/2017.
 */

public interface ConfigTabla extends BaseColumns {
    public String getNombre();
    public String getScriptCrear();
}
