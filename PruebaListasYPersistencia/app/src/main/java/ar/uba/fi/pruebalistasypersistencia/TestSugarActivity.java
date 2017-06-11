package ar.uba.fi.pruebalistasypersistencia;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import fi.uba.ar.api.persistencia.DBUtils;
import fi.uba.ar.listas.Categoria;
import fi.uba.ar.listas.Etiqueta;
import fi.uba.ar.listas.ListaUtils;

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

        DBUtils.Dump("Sugar.db", getPackageName(), "Sugar.db");
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

}
