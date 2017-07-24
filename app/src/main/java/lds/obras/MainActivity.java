package lds.obras;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

    AppCompatSpinner spinnerObras, spinnerCapataces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MostrarCapataces();
        MostrarObras();

        Button continuar = (Button) findViewById(R.id.continuar);
        continuar.setOnClickListener(v -> ActivityTrabajadores());
    }

    private void MostrarCapataces() {
        spinnerCapataces = (AppCompatSpinner) findViewById(R.id.spinnerCapataces);
        ArrayAdapter<Capataces> adapterCapataces = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        spinnerCapataces.setAdapter(adapterCapataces);

        ObrasServicio.getInstance().getObrasServicio().capataces()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> {

                            if (item.get("Exitoso").getAsBoolean()) {
                                JsonArray jsonElements = item.getAsJsonArray("Capataces");
                                List<Capataces> capataces = new Gson().fromJson(String.valueOf(jsonElements), new TypeToken<List<Capataces>>() {
                                }.getType());
                                adapterCapataces.addAll(capataces);
                            }


                            adapterCapataces.notifyDataSetChanged();
                        },


                        throwable -> runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error: " + throwable.getMessage(), Toast.LENGTH_LONG).show()));
    }


    private void MostrarObras() {
        spinnerObras = (AppCompatSpinner) findViewById(R.id.spinnerObras);
        ArrayAdapter<Obras> adapterObras = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        spinnerObras.setAdapter(adapterObras);

        ObrasServicio.getInstance().getObrasServicio().obras()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> {

                            if (item.get("Exitoso").getAsBoolean()) {
                                JsonArray jsonElements = item.getAsJsonArray("Obras");
                                List<Obras> obras = new Gson().fromJson(String.valueOf(jsonElements), new TypeToken<List<Obras>>() {
                                }.getType());
                                adapterObras.addAll(obras);


                                adapterObras.notifyDataSetChanged();
                            }
                        },
                        throwable -> runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error: " + throwable.getMessage(), Toast.LENGTH_LONG).show()));

    }

    private void ActivityTrabajadores() {

        int obraId = ((Obras) spinnerObras.getSelectedItem()).getIdObra();
        int capatazId = spinnerCapataces.getId();

        Intent intent = new Intent(this, Trabajadores.class);
        intent.putExtra("Capataz", capatazId);
        intent.putExtra("Obra", obraId);
        startActivity(intent);
    }
}
