package fi.uba.ar.api.persistencia;

import android.database.Cursor;

import com.orm.SugarRecord;
import com.orm.util.NamingHelper;
import com.orm.util.QueryBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Alfredo on 6/4/2017.
 */

public class ObjetoPersistente extends SugarRecord {
    public ObjetoPersistente(){super();}

    public void InicializarPostLoaded()
    {
//        Log.d("DANE","InicializarPostLoaded--- " + this);
    }

    public static <T> List<T> find(Class<T> type, String whereClause, String[] whereArgs, String groupBy, String orderBy, String limit) {

//        Log.d("DANE","Custom FIND - antes!");
        Cursor cursor = DBUtils.GetDB().query(NamingHelper.toSQLName(type), null, whereClause, whereArgs,
                groupBy, null, orderBy, limit);
//        Log.d("DANE","Custom FIND - dps!");

        List<T> entities = getEntitiesFromCursor(cursor, type);
//        Log.d("DANE","Custom FIND - fin!");

        if(entities != null)
        {
//            Log.d("DANE","entities.size(): " + entities.size());
            for(int i = 0; i < entities.size(); i++)
            {
//                Log.d("DANE","i: " + i);
                if(entities.get(i) instanceof ObjetoPersistente)
                {
                    ObjetoPersistente objPers = ObjetoPersistente.class.cast(entities.get(i));
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

    public static <T> T findFirst(Class<T> type, String whereClause, String... whereArgs) {
        List<T> list = find(type, whereClause, whereArgs, null, null, null);
        if(list != null && list.size() > 0) return list.get(0);
        return null;
    }

    public static <T> List<T> findWithQuery(Class<T> type, String query, String... arguments) {
        Cursor cursor = DBUtils.GetDB().rawQuery(query, arguments);


        List<T> entities = getEntitiesFromCursor(cursor, type);
//        Log.d("DANE","Custom FIND - fin!");

        if(entities != null)
        {
            for(int i = 0; i < entities.size(); i++)
            {
                if(entities.get(i) instanceof ObjetoPersistente)
                {
                    ObjetoPersistente objPers = ObjetoPersistente.class.cast(entities.get(i));
                    objPers.InicializarPostLoaded();
                }
            }
        }
        return  entities;
    }

    public static <T> List<T> rawQuery(Class<T> type, String query, String... arguments)
    {
//        Log.d("DANE","rawQuery: " + query);
        Cursor cursor = DBUtils.GetDB().rawQuery(query, arguments);
        List<T> result = new ArrayList<T>();
        try {
            while (cursor.moveToNext()) {
//                Log.d("DANE","cursor: " + cursor.getColumnNames() + " - column(0): " + cursor.getString(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return  result;
    }

    public static <T> T encontrarAtributo(ObjetoPersistente objeto, String nombreAtributo, Class tipoAtributo)
    {
        String tablaObjeto      = NamingHelper.toSQLName(objeto.getClass());
        String columnaAtributo  = nombreAtributo;

//        Log.d("DANE","Par - tablaObjeto: " + tablaObjeto);
//        Log.d("DANE","Par - columnaAtributo: " + columnaAtributo);
//        Log.d("DANE","Par - atributoVar: " + columnaAtributo);
        String query = "SELECT " + columnaAtributo + " FROM " + tablaObjeto + " WHERE ID = ? LIMIT 1";
//        Log.d("DANE","rawQuery: " + query);
        String[] selectionArgs = {Long.toString(objeto.getId())};
        Cursor cursor = DBUtils.GetDB().rawQuery(query, selectionArgs);
        if(cursor.getCount() > 0)
        {
            cursor.moveToNext();
            long atributoId = cursor.getLong(0);
//            Log.d("DANE","atributoId: " + atributoId);
            return (T) findById(tipoAtributo, atributoId);
        }
        else
        {
//            Log.d("DANE","not found");
            return null;
        }
    }

    public static <T> T encontrarRelacionMuchosAMuchos(ObjetoPersistente objeto, String nombreAtributo, Class tipoAtributo)
    {
        String tablaObjeto      = NamingHelper.toSQLName(objeto.getClass());
        String columnaAtributo  = nombreAtributo;

//        Log.d("DANE","Par - tablaObjeto: " + tablaObjeto);
//        Log.d("DANE","Par - columnaAtributo: " + columnaAtributo);
//        Log.d("DANE","Par - atributoVar: " + columnaAtributo);
        String query = "SELECT " + columnaAtributo + " FROM " + tablaObjeto + " WHERE ID = ? LIMIT 1";
//        Log.d("DANE","rawQuery: " + query);
        String[] selectionArgs = {Long.toString(objeto.getId())};
        Cursor cursor = DBUtils.GetDB().rawQuery(query, selectionArgs);
        if(cursor.getCount() > 0)
        {
            cursor.moveToNext();
            long atributoId = cursor.getLong(0);
//            Log.d("DANE","atributoId: " + atributoId);
            return (T) findById(tipoAtributo, atributoId);
        }
        else
        {
//            Log.d("DANE","not found");
            return null;
        }
    }


}
