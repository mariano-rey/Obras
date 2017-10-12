package lds.obras;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

    private AppCompatSpinner spinnerObras, spinnerCapataces;
    private ArrayAdapter<Capataces> adapterCapataces;
    private ArrayAdapter<Obras> adapterObras;
    private ProgressBar cargando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerCapataces = (AppCompatSpinner) findViewById(R.id.spinnerCapataces);
        adapterCapataces = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        spinnerCapataces.setAdapter(adapterCapataces);
        spinnerObras = (AppCompatSpinner) findViewById(R.id.spinnerObras);
        adapterObras = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        spinnerObras.setAdapter(adapterObras);
        spinnerCapataces.setVisibility(View.INVISIBLE);
        spinnerObras.setVisibility(View.INVISIBLE);

        cargando = (ProgressBar) findViewById(R.id.cargando);
        Cargando();

        MostrarCapataces();
        MostrarObras();

        Button continuar = (Button) findViewById(R.id.continuar);
        continuar.setOnClickListener(v -> ActivityTrabajadores());
    }

    private void Cargando() {
        if (adapterCapataces.isEmpty() || adapterObras.isEmpty()) {
            cargando.setVisibility(View.VISIBLE);
        } else {
            cargando.setVisibility(View.GONE);
        }
    }

    private void MostrarCapataces() {
        ObrasServicio.getInstance().getObrasServicio().capataces()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> {

                            if (item.get("Exitoso").getAsBoolean()) {
                                JsonArray jsonElements = item.getAsJsonArray("Capataces");
                                List<Capataces> capataces = new Gson().fromJson(String.valueOf(jsonElements), new TypeToken<List<Capataces>>() {
                                }.getType());
                                adapterCapataces.addAll(capataces);
                                adapterCapataces.notifyDataSetChanged();
                                spinnerCapataces.setVisibility(View.VISIBLE);
                                Cargando();
                            }
                        },
                        throwable -> runOnUiThread(() -> {
                            Toast.makeText(getApplicationContext(), "Error: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                            MostrarCapataces();
                        }));
    }


    private void MostrarObras() {
        ObrasServicio.getInstance().getObrasServicio().obras()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> {

                            if (item.get("Exitoso").getAsBoolean()) {
                                JsonArray jsonElements = item.getAsJsonArray("Obras");
                                List<Obras> obras = new Gson().fromJson(String.valueOf(jsonElements), new TypeToken<List<Obras>>() {
                                }.getType());
                                adapterObras.addAll(obras);
                                adapterObras.notifyDataSetChanged();
                                spinnerObras.setVisibility(View.VISIBLE);
                                Cargando();
                            }
                        },
                        throwable -> runOnUiThread(() -> {
                            Toast.makeText(getApplicationContext(), "Error: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                            MostrarObras();
                        }));

    }

    private void ActivityTrabajadores() {

        int obraId = ((Obras) spinnerObras.getSelectedItem()).getIdObra();
        int capatazId = ((Capataces) spinnerCapataces.getSelectedItem()).getIdCapataz();
        String nombreCapataz = (((Capataces) spinnerCapataces.getSelectedItem()).getNombre());

        Intent intent = new Intent(this, TrabajadoresActivity.class);
        if (nombreCapataz != null) {
            intent.putExtra("CapatazId", capatazId);
            intent.putExtra("Obra", obraId);
            intent.putExtra("NombreCapataz", nombreCapataz);
            startActivity(intent);
        } else
            startActivity(intent);
    }
}
