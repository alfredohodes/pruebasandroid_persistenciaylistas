package ar.uba.fi.pruebalistasypersistencia;

import android.app.Application;
import com.orm.SugarContext;

/**
 * Created by Alfredo on 6/4/2017.
 */

public class DaneApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
