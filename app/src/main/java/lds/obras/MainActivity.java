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

        Button continuar = (Button) findViewById(R.id.continuar);

        AppCompatSpinner spinnerCapataces = (AppCompatSpinner) findViewById(R.id.spinnerCapataces);
        ArrayList<String> listaCapataces = new ArrayList<>();
        ArrayAdapter adapterCapataces = new ArrayAdapter<>(this, R.layout.item_spinner_capataces, listaCapataces);
        spinnerCapataces.setAdapter(adapterCapataces);

        ObrasServicio.getInstance().getObrasServicio().capataces()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(System.out::println, throwable -> runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error: " + throwable.getMessage(), Toast.LENGTH_LONG).show()));

        AppCompatSpinner spinnerObras = (AppCompatSpinner) findViewById(R.id.spinnerObras);
        ArrayList<String> listaObras = new ArrayList<>();
        ArrayAdapter adapterObras = new ArrayAdapter<>(this, R.layout.item_spinner_obras, listaObras);
        spinnerObras.setAdapter(adapterObras);

        ObrasServicio.getInstance().getObrasServicio().obras()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(System.out::println, throwable -> runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error: " + throwable.getMessage(), Toast.LENGTH_LONG).show()));

//        try {
//            JSONArray jsonArray = new JSONArray("Obras");
//            JSONObject jsonObject = jsonArray.getJSONObject(0);
//            String nombre = jsonObject.getString("nombre");
//            String direccion = jsonObject.getString("direccion");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


        continuar.setOnClickListener(v -> ActivityTrabajadores());
    }

    private void ActivityTrabajadores() {
        Intent intent = new Intent(this, Trabajadores.class);
        intent.putExtra("Usuario", "INSERTAR VARIABLE USUARIO ACA!!");
        startActivity(intent);
    }
}
