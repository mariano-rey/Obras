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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class TrabajadoresActivity extends AppCompatActivity {

    private String nombreCapataz;
    private int capatazId, obraId;
    private RecyclerView rvTrabajadores;
    private TrabajadoresAdapter adapter;
    private List<Trabajadores> listaTrabajadores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabajadores);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        capatazId = getIntent().getExtras().getInt("CapatazId");
        obraId = getIntent().getExtras().getInt("Obra");
        nombreCapataz = getIntent().getExtras().getString("NombreCapataz");
        setTitle(nombreCapataz);

        BuscarTrabajadores();
    }

    private void MostrarTrabajadores() {
        rvTrabajadores = (RecyclerView) findViewById(R.id.rvTrabajadores);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvTrabajadores.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        rvTrabajadores.addItemDecoration(dividerItemDecoration);
        rvTrabajadores.setHasFixedSize(true);

        adapter = new TrabajadoresAdapter(this, listaTrabajadores);
        rvTrabajadores.setAdapter(adapter);
    }

    private void BuscarTrabajadores() {
        ObrasServicio.getInstance().getObrasServicio().trabajadores()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> {
                            if (item.get("Exitoso").getAsBoolean()) {
                                JsonArray jsonArray = item.getAsJsonArray("Trabajadores");
                                Type listType = new TypeToken<List<Trabajadores>>() {
                                }.getType();
                                listaTrabajadores.clear();
                                listaTrabajadores = new Gson().fromJson(String.valueOf(jsonArray), listType);
                                adapter.notifyDataSetChanged();
                            }
                        },
                        throwable -> runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error: " + throwable.getMessage(), Toast.LENGTH_LONG).show()));

        MostrarTrabajadores();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.finalizar:
//                GuardarTrabajadores();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    private int GuardarTrabajadores() {
//        List<Trabajadores> trabajadores = ((TrabajadoresAdapter) rvTrabajadores.getAdapter()).getDataSet();
//        for (int x = 0; x < trabajadores.size(); x++) {
//            Trabajadores esta = trabajadores.get(x);
//            if (esta.seleccionado())
//                return esta.getIdTrabajador();
//        }
//        return -1;
//    }

    private class TrabajadoresAdapter extends RecyclerView.Adapter<TrabajadoresViewHolder> {
        private List<Trabajadores> listaTrabajadores;

        TrabajadoresAdapter(TrabajadoresActivity trabajadoresActivity, List<Trabajadores> listaTrabajadores) {
            this.listaTrabajadores = listaTrabajadores;
        }

//        public List<Trabajadores> getDataSet(){
//            return listaTrabajadores;
//        }

        @Override
        public TrabajadoresViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lista_trabajadores, viewGroup, false);
            return new TrabajadoresViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(TrabajadoresViewHolder holder, int position) {
            Trabajadores trabajador = listaTrabajadores.get(position);

            holder.nombre.setText(trabajador.getNombre());
            holder.dni.setText(trabajador.getDni());
//            holder.nombre.setVisibility(trabajador.getNombre() == null ? View.GONE : View.VISIBLE);

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
