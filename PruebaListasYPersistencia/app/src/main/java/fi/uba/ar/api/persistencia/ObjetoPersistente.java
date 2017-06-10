package fi.uba.ar.api.persistencia;

import android.database.Cursor;
import android.util.Log;

import com.orm.SugarContext;
import com.orm.SugarDb;
import com.orm.SugarRecord;
import com.orm.util.NamingHelper;
import com.orm.util.QueryBuilder;
import com.orm.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ar.uba.fi.pruebalistasypersistencia.TestSugarActivity;

import static com.orm.SugarContext.getSugarContext;

/**
 * Created by Alfredo on 6/4/2017.
 */

public class ObjetoPersistente extends SugarRecord {
    public ObjetoPersistente(){super();}

    public void InicializarPostLoaded()
    {
        Log.d("DANE","InicializarPostLoaded--- " + this);
    }

    public static <T> List<T> find(Class<T> type, String whereClause, String[] whereArgs, String groupBy, String orderBy, String limit) {

        Log.d("DANE","Custom FIND - antes!");
        Cursor cursor = TestSugarActivity.sugarDb.getDB().query(NamingHelper.toSQLName(type), null, whereClause, whereArgs,
                groupBy, null, orderBy, limit);
        Log.d("DANE","Custom FIND - dps!");

        List<T> entities = getEntitiesFromCursor(cursor, type);
        Log.d("DANE","Custom FIND - fin!");

        if(entities != null)
        {
            for(int i = 0; i < entities.size(); i++)
            {
                if(entities.get(0) instanceof ObjetoPersistente)
                {
                    ObjetoPersistente objPers = ObjetoPersistente.class.cast(entities.get(0));
                    objPers.InicializarPostLoaded();
                }
            }
        }
        return entities;
    }


    /**
     * Metodos estáticos de de SugarRecord pasados a esta clase para poder llamar a "InicializarPostLoaded"
     */
    public static <T> List<T> listAll(Class<T> type) {
        return find(type, null, null, null, null, null);
    }

    public static <T> List<T> listAll(Class<T> type, String orderBy) {
        return find(type, null, null, null, orderBy, null);
    }

    public static <T> T findById(Class<T> type, Long id) {
        List<T> list = find(type, "id=?", new String[]{String.valueOf(id)}, null, null, "1");
        if (list.isEmpty()) return null;
        return list.get(0);
    }

    public static <T> T findById(Class<T> type, Integer id) {
        return findById(type, Long.valueOf(id));
    }

    public static <T> List<T> findById(Class<T> type, String[] ids) {
        String whereClause = "id IN (" + QueryBuilder.generatePlaceholders(ids.length) + ")";
        return find(type, whereClause, ids);
    }

    public static <T> T first(Class<T>type) {
        List<T> list = findWithQuery(type,
                "SELECT * FROM " + NamingHelper.toSQLName(type) + " ORDER BY ID ASC LIMIT 1");
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public static <T> T last(Class<T>type) {
        List<T> list = findWithQuery(type,
                "SELECT * FROM " + NamingHelper.toSQLName(type) + " ORDER BY ID DESC LIMIT 1");
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public static <T> Iterator<T> findAll(Class<T> type) {
        return findAsIterator(type, null, null, null, null, null);
    }

    public static <T> Iterator<T> findAsIterator(Class<T> type, String whereClause, String... whereArgs) {
        return findAsIterator(type, whereClause, whereArgs, null, null, null);
    }

    public static <T> List<T> find(Class<T> type, String whereClause, String... whereArgs) {
        return find(type, whereClause, whereArgs, null, null, null);
    }

    public static <T> List<T> findWithQuery(Class<T> type, String query, String... arguments) {
        Cursor cursor = TestSugarActivity.sugarDb.getDB().rawQuery(query, arguments);


        List<T> entities = getEntitiesFromCursor(cursor, type);
        Log.d("DANE","Custom FIND - fin!");

        if(entities != null)
        {
            for(int i = 0; i < entities.size(); i++)
            {
                if(entities.get(0) instanceof ObjetoPersistente)
                {
                    ObjetoPersistente objPers = ObjetoPersistente.class.cast(entities.get(0));
                    objPers.InicializarPostLoaded();
                }
            }
        }
        return  entities;
    }
}
