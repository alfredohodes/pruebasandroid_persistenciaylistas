package ar.uba.fi.pruebalistasypersistencia;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import fi.uba.ar.api.persistencia.DBUtils;
import fi.uba.ar.api.persistencia.ObjetoPersistente;
import fi.uba.ar.listas.Categoria;
import fi.uba.ar.listas.Etiqueta;
import fi.uba.ar.listas.ListaUtils;
import fi.uba.ar.listas.ParEtiquetaItem;

public class TestSugarActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBUtils.Inicializar(getApplicationContext(), true);

        crearCategorias();
        leerCategorias();

        crearEtiquetas();
        leerEtiquetas();


        crearPrendas();
        leerPrendas();

        buscarPorCategoria();





        DBUtils.Dump("Sugar.db", getPackageName(), "Sugar_PreEliminado.db");
        
        testearAutoeliminadoDeRelacionesItemEtiqueta();

        leerPrendas();

        DBUtils.Dump("Sugar.db", getPackageName(), "Sugar_PostEliminado.db");
    }

    private void crearCategorias() {
        Log.d("DANE","*** crearCategorias ***");

        Categoria.obtenerOCrear("prenda", null);
        Categoria.obtenerOCrear("superior", Categoria.obtener("prenda", null));
        Categoria.obtenerOCrear("inferior", Categoria.obtener("prenda", null));
        Categoria.obtenerOCrear("accesorio", Categoria.obtener("prenda", null));
        Categoria.obtenerOCrear("remeras", Categoria.obtener("superior", Categoria.obtener("prenda", null)));
        Categoria.obtenerOCrear("camperas", Categoria.obtener("superior", Categoria.obtener("prenda", null)));
        Categoria.obtenerOCrear("pantalones", Categoria.obtener("inferior", Categoria.obtener("prenda", null)));
        Categoria.obtenerOCrear("guantes", Categoria.obtener("accesorio", Categoria.obtener("prenda", null)));
        Categoria.obtenerOCrear("corbatas", null);
        Categoria.obtenerOCrear("corbatas", null).SetPadre(Categoria.obtenerOCrear("accesorio", Categoria.obtener("prenda", null)));

        Categoria.obtenerDeCualquierPadre("corbatas").Renombrar("corbatines");
    }

    private void leerCategorias() {
        Log.d("DANE","*** leerCategorias ***");
        List<Categoria> categorias = ListaUtils.listarTodos(Categoria.class);
        for (int i=0; i<categorias.size(); i++) {
            Log.d("DANE","categorias[" + i + "]: " + categorias.get(i));
        }
    }


    private void crearEtiquetas() {
        Log.d("DANE","*** crearEtiquetas ***");

        Etiqueta.obtenerOCrear("calor");
        Etiqueta.obtenerOCrear("frio");
        Etiqueta.obtenerOCrear("formal");
        Etiqueta.obtenerOCrear("informal");
    }

    private void leerEtiquetas() {
        Log.d("DANE","*** leerEtiquetas ***");
        List<Etiqueta> etiquetas = ListaUtils.listarTodos(Etiqueta.class);
        for (int i=0; i<etiquetas.size(); i++) {
            Log.d("DANE","etiquetas[" + i + "]: " + etiquetas.get(i));
        }
    }

    private void crearPrendas() {
        Log.d("DANE","*** crearPrendas ***");
        Prenda remera = new Prenda("Remera","ruta/remera");
        Prenda pantalon = new Prenda("Pantalon","ruta/pantalon");
        Prenda campera = new Prenda("Campera","ruta/campera");

        remera.save();
        pantalon.save();
        campera.save();

        campera.agregarEtiqueta("frio");
        remera.agregarEtiqueta("calor");
        pantalon.agregarEtiqueta("informal");
        campera.agregarEtiqueta("informal");

        campera.agregarEtiqueta("etiqueta_a_eliminar");
        campera.quitarEtiqueta("etiqueta_a_eliminar");

        // Renombrar etiqueta
        Etiqueta.obtener("calor").Renombrar("lacalor");

        campera.setCategoria(Categoria.obtener("camperas", Categoria.obtener("superior", Categoria.obtener("prenda", null))));
        pantalon.setCategoria(Categoria.obtener("pantalones", Categoria.obtener("inferior", Categoria.obtener("prenda", null))));
        remera.setCategoria(Categoria.obtener("remeras", Categoria.obtener("superior", Categoria.obtener("prenda", null))));
    }

    private void leerPrendas() {
        Log.d("DANE","*** leerPrendas ***");
        List<Prenda> prendas = ListaUtils.listarTodos(Prenda.class);
        for (int i=0; i<prendas.size(); i++) {
            Log.d("DANE","prendas[" + i + "]: " + prendas.get(i));
            prendas.get(i).obtenerEtiquetas();
        }
    }

    private void buscarPorCategoria() {
        Log.d("DANE","*** buscarPorCategoria ***");
        Log.d("DANE","CATEGORIA: remeras");
        List<Prenda> remeras = ListaUtils.listarPorCategoria(Prenda.class, Categoria.obtenerDeCualquierPadre("remeras"), true);
        for (int i=0; i<remeras.size(); i++) {
            Log.d("DANE","remera[" + i + "]: " + remeras.get(i));
        }
        Log.d("DANE","CATEGORIA: accesorio");
        List<Prenda> accesorios = ListaUtils.listarPorCategoria(Prenda.class, Categoria.obtenerDeCualquierPadre("accesorio"), true);
        for (int i=0; i<accesorios.size(); i++) {
            Log.d("DANE","accesorios[" + i + "]: " + accesorios.get(i));
        }
        Log.d("DANE","CATEGORIA: superior");
        List<Prenda> superior = ListaUtils.listarPorCategoria(Prenda.class, Categoria.obtenerDeCualquierPadre("superior"), true);
        for (int i=0; i<superior.size(); i++) {
            Log.d("DANE","superior[" + i + "]: " + superior.get(i));
        }
        Log.d("DANE","CATEGORIA: prenda");
        List<Prenda> prendas = ListaUtils.listarPorCategoria(Prenda.class, Categoria.obtenerDeCualquierPadre("prenda"), true);
        for (int i=0; i<prendas.size(); i++) {
            Log.d("DANE","prendas[" + i + "]: " + prendas.get(i));
        }
    }

    private void testearAutoeliminadoDeRelacionesItemEtiqueta() {
        Log.d("DANE","*** testearAutoeliminadoDeRelacionesItemEtiqueta ***");

        Log.d("DANE","RELACIONES AL COMENZAR:");
        LoguearRelacionesItemsEtiquetas();

        // Borrar un item
        Prenda remera = ObjetoPersistente.findFirst(Prenda.class, "nombre = ?", "Remera");
        Log.d("DANE","ELIMINANDO ITEM remera...");
        remera.delete();
        Log.d("DANE","RELACIONES DESPUES DE ELIMINAR PRENDA remera:");
        LoguearRelacionesItemsEtiquetas();

        // Borrar una etiqueta
        Log.d("DANE","ELIMINANDO ETIQUETA informal...");
        Etiqueta etInformal = Etiqueta.obtener("informal");
        etInformal.delete();
        Log.d("DANE","RELACIONES DESPUES DE ELIMINAR ETIQUETA informal:");
        LoguearRelacionesItemsEtiquetas();
    }

    private void LoguearRelacionesItemsEtiquetas() {
        List<ParEtiquetaItem> pares = ListaUtils.listarTodos(ParEtiquetaItem.class);
        for (int i=0; i<pares.size(); i++)
        {
            Log.d("DANE", "Relación: " + pares.get(i));
        }
    }

}
