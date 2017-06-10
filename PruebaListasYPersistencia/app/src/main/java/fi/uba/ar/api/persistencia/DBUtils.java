package fi.uba.ar.api.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.orm.SchemaGenerator;
import com.orm.SugarContext;
import com.orm.SugarDb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by Alfredo on 6/10/2017.
 */

public class DBUtils {

    private  static SQLiteDatabase _db;
    public static SQLiteDatabase GetDB()
    {
        return _db;
    }

    public  static void Inicializar(Context context, boolean borrarTablas)
    {
        SugarContext.terminate();

        SchemaGenerator schemaGenerator = new SchemaGenerator(context);
        if(borrarTablas) schemaGenerator.deleteTables(new SugarDb(context).getDB());
        SugarContext.init(context);
        SugarDb sugarDb = new SugarDb(context);
        _db = sugarDb.getDB();
        schemaGenerator.createDatabase(_db);
    }
    public static void Dump(String dbName, String packageName, String outputDBName)
    {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

//            Log.d("DANE", "sd.canWrite(): " + sd.canWrite());
            if (sd.canWrite()) {
                String currentDBPath = "/data/data/" + packageName + "/databases/" + dbName;
                String backupDBPath = outputDBName;
                File currentDB = new File(currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                Log.d("DANE", "currentDB: " + currentDB);
                Log.d("DANE", "backupDB: " + backupDB);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {

        }
    }
}
