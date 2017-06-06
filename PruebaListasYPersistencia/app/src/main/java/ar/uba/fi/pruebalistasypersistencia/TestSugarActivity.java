package ar.uba.fi.pruebalistasypersistencia;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.orm.SchemaGenerator;
import com.orm.SugarContext;
import com.orm.SugarDb;

import java.util.List;

import fi.uba.ar.api.persistencia.ObjetoPersistente;
import fi.uba.ar.listas.AdministradorListas;
import fi.uba.ar.listas.Categoria;
import fi.uba.ar.listas.Etiqueta;
import fi.uba.ar.listas.ItemLista;
import fi.uba.ar.listas.ListaPersistente;
import fi.uba.ar.listas.ListaUtils;
import fi.uba.ar.listas.ParEtiquetaItem;

public class TestSugarActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarBD();

        crearCategorias();
        leerCategorias();

        crearEtiquetas();
        leerEtiquetas();

        crearPrendas();
        leerPrendas();
    }

    private void inicializarBD() {

        // Borrar tablas
        SugarContext.terminate();
        SchemaGenerator schemaGenerator = new SchemaGenerator(getApplicationContext());
        schemaGenerator.deleteTables(new SugarDb(getApplicationContext()).getDB());
        SugarContext.init(getApplicationContext());
        schemaGenerator.createDatabase(new SugarDb(getApplicationContext()).getDB());
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

        remera.agregarEtiqueta(Etiqueta.obtenerOCrear("calor"));
        campera.agregarEtiqueta("frio");

        remera.save();
        pantalon.save();
        campera.save();

        Log.d("DANE","obtener etiquetas");
        remera.obtenerEtiquetas();
    }

    private void leerPrendas() {

        List<Prenda> prendas = ListaUtils.listarTodos(Prenda.class);
        for (int i=0; i<prendas.size(); i++) {
            Log.d("DANE","prendas[" + i + "]: " + prendas.get(i));
        }
    }

}
