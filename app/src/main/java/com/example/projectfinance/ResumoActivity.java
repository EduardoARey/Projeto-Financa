package com.example.projectfinance;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectfinance.Models.ResumoDados;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ResumoActivity extends AppCompatActivity {
    private TextView txtGastoTotal, txtCategoriaMaior, txtValorMaior;
    private ListView listViewResumo;
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo);


        txtGastoTotal = findViewById(R.id.txtGastoTotal);
        txtCategoriaMaior = findViewById(R.id.txtCategoriaMaior);
        txtValorMaior = findViewById(R.id.txtValorMaior);
        listViewResumo = findViewById(R.id.listViewResumo);


        ResumoDados resumo = (ResumoDados) getIntent().getSerializableExtra("resumo");

        if (resumo != null) {

            txtGastoTotal.setText(currencyFormat.format(resumo.getGastoTotalMes()));
            txtCategoriaMaior.setText(resumo.getCategoriaMaiorGasto());
            txtValorMaior.setText(currencyFormat.format(resumo.getValorMaiorGasto()));


            List<ItemResumo> itensResumo = new ArrayList<>();

            for (Map.Entry<String, Double> entry : resumo.getTotalPorCategoria().entrySet()) {
                itensResumo.add(new ItemResumo(entry.getKey(), entry.getValue()));
            }


            ResumoAdapter adapter = new ResumoAdapter(this, itensResumo);
            listViewResumo.setAdapter(adapter);
        }
    }


    private class ItemResumo {
        private String categoria;
        private double valor;

        public ItemResumo(String categoria, double valor) {
            this.categoria = categoria;
            this.valor = valor;
        }

        public String getCategoria() {
            return categoria;
        }

        public double getValor() {
            return valor;
        }
    }


    private class ResumoAdapter extends ArrayAdapter<ItemResumo> {
        private Context context;
        private List<ItemResumo> itens;

        public ResumoAdapter(Context context, List<ItemResumo> itens) {
            super(context, 0, itens);
            this.context = context;
            this.itens = itens;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;

            if (itemView == null) {
                itemView = LayoutInflater.from(context).inflate(R.layout.item_resumo, parent, false);
            }

            ItemResumo item = itens.get(position);

            TextView txtResumoCategoria = itemView.findViewById(R.id.txtResumoCategoria);
            TextView txtResumoValor = itemView.findViewById(R.id.txtResumoValor);

            txtResumoCategoria.setText(item.getCategoria());
            txtResumoValor.setText(currencyFormat.format(item.getValor()));

            return itemView;
        }
    }
}
