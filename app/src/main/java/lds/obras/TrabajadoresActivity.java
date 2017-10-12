package lds.obras;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class TrabajadoresActivity extends AppCompatActivity {

    private int capatazId, obraId;
    private RecyclerView rvTrabajadores;
    private TrabajadoresAdapter adapter;
    private List<Trabajadores> listaTrabajadores = new ArrayList<>();
    private ProgressBar cargandoTrabajadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trabajadores);
        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.celeste));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cargandoTrabajadores = (ProgressBar) findViewById(R.id.progressBar);

        capatazId = getIntent().getExtras().getInt("CapatazId");
        obraId = getIntent().getExtras().getInt("Obra");
        String nombreCapataz = getIntent().getExtras().getString("NombreCapataz");
        if (nombreCapataz != null) {
            setTitle(nombreCapataz);
            toolbar.setTitleTextColor(Color.WHITE);
        }

        MostrarTrabajadores();
        CargandoTrabajadores();
        BuscarTrabajadores();
    }

    private void CargandoTrabajadores() {
        if (adapter.getItemCount() == 0)
            cargandoTrabajadores.setVisibility(View.VISIBLE);
        else
            cargandoTrabajadores.setVisibility(View.GONE);
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
                                Type listType = new TypeToken<ArrayList<Trabajadores>>() {
                                }.getType();
                                listaTrabajadores.clear();
                                listaTrabajadores.addAll(new Gson().fromJson(jsonArray, listType));
                                adapter.notifyDataSetChanged();
                                CargandoTrabajadores();
                            }
                        },
                        throwable -> runOnUiThread(() -> {
                            Toast.makeText(getApplicationContext(), "Error: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                            BuscarTrabajadores();
                        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
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
        List<Trabajadores> trabajadores = ((TrabajadoresAdapter) rvTrabajadores.getAdapter()).getDataSet();
        List<Integer> trabajadoresPresentes = new ArrayList<>();
        for (int x = 0; x < trabajadores.size(); x++) {
            Trabajadores esta = trabajadores.get(x);
            if (esta.seleccionado()) {
                int idTrabajador = esta.getIdTrabajador();
                trabajadoresPresentes.add(idTrabajador);
            }
        }

//        ObrasServicio.getInstance().getObrasServicio().trabajadores()
//                .subscribe(item -> {
//
//                        }
//                );

        RequestParams params = new RequestParams();
        params.put("idObra", obraId);
        params.put("idCapataz", capatazId);
        params.put("trabajadores", trabajadoresPresentes);
    }

    private class TrabajadoresAdapter extends RecyclerView.Adapter<TrabajadoresViewHolder> {
        private List<Trabajadores> listaTrabajadores;

        TrabajadoresAdapter(TrabajadoresActivity trabajadoresActivity, List<Trabajadores> listaTrabajadores) {
            this.listaTrabajadores = listaTrabajadores;
        }

        List<Trabajadores> getDataSet() {
            return listaTrabajadores;
        }

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

//            itemView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

            nombre = (TextView) itemView.findViewById(R.id.nombre);
            nombre.setTextColor(Color.WHITE);
            nombre.setTypeface(null, Typeface.BOLD);
            dni = (TextView) itemView.findViewById(R.id.dni);
            dni.setTextColor(Color.WHITE);
            estaPresente = (SwitchCompat) itemView.findViewById(R.id.estaPresente);
        }
    }
}
