package lds.obras;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

public class Trabajadores extends AppCompatActivity {

    private RecyclerView rvTrabajadores;
    private Button finalizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabajadores);

        finalizar = (Button) findViewById(R.id.finalizar);

        rvTrabajadores = (RecyclerView) findViewById(R.id.rvTrabajadores);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvTrabajadores.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        rvTrabajadores.addItemDecoration(dividerItemDecoration);
    }
}
