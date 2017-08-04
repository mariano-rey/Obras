package lds.obras;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class TrabajadoresActivity extends AppCompatActivity {

    private String capatazId, obraId, nombreCapataz;
    private List<Trabajadores> listaTrabajadores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabajadores);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        capatazId = getIntent().getExtras().getString("CapatazId");
        obraId = getIntent().getExtras().getString("Obra");
        nombreCapataz = getIntent().getExtras().getString("NombreCapataz");
        setTitle(nombreCapataz);

        MostrarTrabajadores();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.finalizar:
                GuardarTrabajadores();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void GuardarTrabajadores() {

    }


    private void MostrarTrabajadores() {
        RecyclerView rvTrabajadores = (RecyclerView) findViewById(R.id.rvTrabajadores);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvTrabajadores.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        rvTrabajadores.addItemDecoration(dividerItemDecoration);
        rvTrabajadores.setHasFixedSize(true);


        TrabajadoresAdapter adapter = new TrabajadoresAdapter(this, listaTrabajadores);
        rvTrabajadores.setAdapter(adapter);
    }

    private class TrabajadoresAdapter extends RecyclerView.Adapter<TrabajadoresViewHolder> {
        private List<Trabajadores> listaTrabajadores;

        TrabajadoresAdapter(TrabajadoresActivity trabajadoresActivity, List<Trabajadores> listaTrabajadores) {
            this.listaTrabajadores = listaTrabajadores;
        }

        @Override
        public TrabajadoresViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lista_trabajadores, viewGroup, false);
            return new TrabajadoresViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(TrabajadoresViewHolder holder, int position) {
            ObrasServicio.getInstance().getObrasServicio().trabajadores()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(item -> {
                                if (item.get("Exitoso").getAsBoolean()) {
                                    JsonArray jsonArray = item.getAsJsonArray("Trabajadores");
                                    List<Trabajadores> trabajadores = new Gson().fromJson(String.valueOf(jsonArray), new TypeToken<List<Trabajadores>>() {
                                    }.getType());
                                    listaTrabajadores.addAll(trabajadores);
                                    notifyDataSetChanged();
                                }
                            },
                            throwable -> runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error: " + throwable.getMessage(), Toast.LENGTH_LONG).show()));

            holder.nombre.setText(listaTrabajadores.get(position).getNombre());
            holder.dni.setText(listaTrabajadores.get(position).getDni());

//            if (holder.estaPresente.isChecked()) {
//                int trabajadorPresente = listaTrabajadores.get(position).getIdTrabajador();
//            }
        }

        @Override
        public int getItemCount() {
            return listaTrabajadores.size();
        }
    }

    private class TrabajadoresViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, dni;
        SwitchCompat estaPresente;

        TrabajadoresViewHolder(View itemView) {
            super(itemView);

            nombre = (TextView) findViewById(R.id.nombre);
//            nombre.setTypeface(null, Typeface.BOLD);
            dni = (TextView) findViewById(R.id.dni);
            estaPresente = (SwitchCompat) findViewById(R.id.estaPresente);
        }
    }
}
