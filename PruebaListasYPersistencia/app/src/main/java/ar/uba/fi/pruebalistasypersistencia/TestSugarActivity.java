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

//        limpiarBD();
//
//        crearCategorias();
//        leerCategorias();
//
//        crearEtiquetas();
//        leerEtiquetas();
//
//        crearPrendas();
        leerPrendas();

        DBUtils.Dump("Sugar.db", getPackageName(), "Sugar.db");
    }

    private void crearCategorias() {
        Log.d("DANE","crearCategorias!");
        Categoria catPadre = new Categoria("cat padre");
        catPadre.save();
        Categoria catHija = new Categoria("catHija");
        catHija.padre = catPadre;
        catHija.save();
    }

    private void leerCategorias() {
        Log.d("DANE","leerCategorias!");
        List<Categoria> categorias = ListaUtils.listarTodos(Categoria.class);
        for (int i=0; i<categorias.size(); i++) {
            Log.d("DANE","categorias[" + i + "]: " + categorias.get(i));
        }
    }


    private void crearEtiquetas() {
        Log.d("DANE","crearEtiquetas!");

        Etiqueta etiquetaCalor = new Etiqueta("calor");
        Etiqueta etiquetaFrio = new Etiqueta("frio");
        Etiqueta etiquetaInformal = new Etiqueta("informal");

        etiquetaCalor.save();
        etiquetaFrio.save();
        etiquetaInformal.save();
    }

    private void leerEtiquetas() {
        Log.d("DANE","leerEtiquetas!");
        List<Etiqueta> etiquetas = ListaUtils.listarTodos(Etiqueta.class);
        for (int i=0; i<etiquetas.size(); i++) {
            Log.d("DANE","etiquetas[" + i + "]: " + etiquetas.get(i));
        }
    }

    private void crearPrendas() {

        Prenda remera = new Prenda("Remera","ruta/remera");
        Prenda pantalon = new Prenda("Pantalon","ruta/pantalon");
        Prenda campera = new Prenda("Campera","ruta/campera");

        Categoria catHija = Categoria.find(Categoria.class,"nombre = ?", "catHija").get(0);
        Log.d("DANE","catHija found: " + catHija);

        remera.categoria = catHija;

        remera.save();
        pantalon.save();
        campera.save();

        campera.agregarEtiqueta("frio");
        remera.agregarEtiqueta(Etiqueta.obtenerOCrear("calor"));
        pantalon.agregarEtiqueta("informal");
        campera.agregarEtiqueta(Etiqueta.obtenerOCrear("informal"));

//        remera.save();
//        pantalon.save();
//        campera.save();

        Log.d("DANE","obtener etiquetas de remera (prenda id: " + remera.getId() + ")");
        remera.obtenerEtiquetas();
        Log.d("DANE","obtener etiquetas de campera (prenda id: " + campera.getId() + ")");
        campera.obtenerEtiquetas();
        Log.d("DANE","obtener etiquetas de pantalon (prenda id: " + pantalon.getId() + ")");
        pantalon.obtenerEtiquetas();
    }

    private void leerPrendas() {

        List<Prenda> prendas = ListaUtils.listarTodos(Prenda.class);
        for (int i=0; i<prendas.size(); i++) {
            Log.d("DANE","prendas[" + i + "]: " + prendas.get(i));
            prendas.get(i).obtenerEtiquetas();
        }
    }

}
