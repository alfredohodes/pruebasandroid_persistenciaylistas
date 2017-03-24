package fi.uba.ar.api.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Alfredo on 3/19/2017.
 */

public class BaseDeDatosHelper extends SQLiteOpenHelper {

    private  ConfigBD _config;

    private BaseDeDatosHelper(Context context, ConfigBD config)
    {
        super(context, config.getNombre(), null, config.getVersion());
        _config = config;
    }

    public static BaseDeDatosHelper crearHelper(Context context, ConfigBD config)
    {
        return new BaseDeDatosHelper(context, config);
    }

    public void onCreate(SQLiteDatabase db) {
        for (ConfigTabla configT: _config.getTablas()) {
            db.execSQL(configT.getScriptCrear());
        }
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        for (ConfigTabla configT: _config.getTablas()) {
            db.execSQL("DROP TABLE IF EXISTS " + configT.getNombre());
        }
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
