package lds.obras;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

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
        AppCompatSpinner spinnerCapataces = (AppCompatSpinner) findViewById(R.id.spinnerCapataces);
        ArrayList<String> listaCapataces = new ArrayList<>();
        ArrayAdapter<String> adapterCapataces = new ArrayAdapter<>(this, R.layout.item_spinner_capataces, listaCapataces);
        spinnerCapataces.setAdapter(adapterCapataces);

        ObrasServicio.getInstance().getObrasServicio().capataces()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(System.out::println, throwable -> runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error: " + throwable.getMessage(), Toast.LENGTH_LONG).show()));
    }


    private void MostrarObras() {
        AppCompatSpinner spinnerObras = (AppCompatSpinner) findViewById(R.id.spinnerObras);
        ArrayList<String> listaObras = new ArrayList<>();
        ArrayAdapter<String> adapterObras = new ArrayAdapter<>(this, R.layout.item_spinner_obras, listaObras);
        spinnerObras.setAdapter(adapterObras);

        ObrasServicio.getInstance().getObrasServicio().obras()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> adapterObras.addAll(String.valueOf(item)), throwable -> runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error: " + throwable.getMessage(), Toast.LENGTH_LONG).show()));
        adapterObras.notifyDataSetChanged();
    }

    private void ActivityTrabajadores() {
        Intent intent = new Intent(this, Trabajadores.class);
        intent.putExtra("Capataz", "INSERTAR VARIABLE CAPATAZ ACA!!");
        startActivity(intent);
    }
}
