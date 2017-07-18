package lds.obras;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

    private AppCompatSpinner elegirUsuario, elegirObra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button continuar = (Button) findViewById(R.id.continuar);
        elegirUsuario = (AppCompatSpinner) findViewById(R.id.spinnerUsuario);
        elegirObra = (AppCompatSpinner) findViewById(R.id.spinnerObra);

        ObrasServicio.getInstance().getObrasServicio().obras()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(respuesta -> {
                    System.out.println(respuesta);
                }, throwable -> {
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error: " + throwable.getMessage(), Toast.LENGTH_LONG).show());

                });

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostrarTrabajadores();
            }
        });
    }

    private void MostrarTrabajadores() {
        Intent intent = new Intent(this, Trabajadores.class);
        intent.putExtra("Usuario", "INSERTAR VARIABLE USUARIO ACA!!");
        startActivity(intent);
    }
}
