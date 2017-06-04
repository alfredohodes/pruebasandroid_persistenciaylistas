package ar.uba.fi.pruebalistasypersistencia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.orm.SugarApp;
import com.orm.SugarDb;
import com.orm.util.SugarConfig;

import java.util.List;

public class TestSugarActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Categoria.deleteAll(Categoria.class);
        crearCategorias();
        leerCategorias();

        insertarRegistro();
        leerRegistros();
    }

    private void crearCategorias() {
        Log.d("DANE","crearCategorias!");
        Categoria catPadre = new Categoria("cat padre");
        catPadre.save();
        Categoria catHija = new Categoria("catHija");
        catHija.padre = catPadre;
        catHija.save();
        Log.d("DANE","catHija: " + catHija);
    }

    private void leerCategorias() {
        Log.d("DANE","leerCategorias!");
        List<Categoria> categorias = Categoria.listAll(Categoria.class);
        for (int i=0; i<categorias.size(); i++) {
            Log.d("DANE","categorias[" + i + "]: " + categorias.get(i));
        }
    }

    private void insertarRegistro() {
        Log.d("DANE","insertarRegistro!");
        ItemLista item = new ItemLista("nomre1","categoria1");
        Log.d("DANE","item creado!");
        item.save();
        Log.d("DANE","item guardado!");

    }

    private void leerRegistros() {
        Log.d("DANE","leerRegistros!");
        List<ItemLista> items = ItemLista.listAll(ItemLista.class);
        for (int i=0; i<items.size(); i++) {
            Log.d("DANE","item[" + i + "]: " + items.get(i));
        }
    }

}
