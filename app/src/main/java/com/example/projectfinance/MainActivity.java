package com.example.projectfinance;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectfinance.Adapter.GastoAdapter;
import com.example.projectfinance.DatabaseHelper.DatabaseHelper;
import com.example.projectfinance.Models.Gasto;
import com.example.projectfinance.Models.ResumoDados;
import com.example.projectfinance.Service.CalculoResumoService;
import com.example.projectfinance.Service.ResumoResultReceiver;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ResumoResultReceiver.Receiver {
    private ListView listViewGastos;
    private Button btnNovoGasto, btnResumo;
    private ProgressBar progressBar;
    private List<Gasto> listaGastos;
    private GastoAdapter adapter;
    private DatabaseHelper db;
    private ResumoResultReceiver resumoResultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listViewGastos = findViewById(R.id.listViewGastos);
        btnNovoGasto = findViewById(R.id.btnNovoGasto);
        btnResumo = findViewById(R.id.btnResumo);
        progressBar = findViewById(R.id.progressBar);

        db = new DatabaseHelper(this);
        listaGastos = new ArrayList<>();
        adapter = new GastoAdapter(this, listaGastos);
        listViewGastos.setAdapter(adapter);

        resumoResultReceiver = new ResumoResultReceiver(new Handler());
        resumoResultReceiver.setReceiver(this);


        btnNovoGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });

        btnResumo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);


                Intent intent = new Intent(MainActivity.this, CalculoResumoService.class);
                intent.putExtra(CalculoResumoService.RECEIVER, resumoResultReceiver);
                startService(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarGastos();
    }

    private void carregarGastos() {
        listaGastos.clear();
        listaGastos.addAll(db.getTodosGastos());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        progressBar.setVisibility(View.GONE);

        if (resultCode == CalculoResumoService.RESULTADO_OK) {

            ResumoDados resumo = (ResumoDados) resultData.getSerializable(CalculoResumoService.RESUMO_DADOS);

            Intent intent = new Intent(MainActivity.this, ResumoActivity.class);
            intent.putExtra("resumo", resumo);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Erro ao calcular resumo", Toast.LENGTH_SHORT).show();
        }
    }
}
