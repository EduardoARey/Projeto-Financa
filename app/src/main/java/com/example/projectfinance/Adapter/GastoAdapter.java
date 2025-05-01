package com.example.projectfinance.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projectfinance.Models.Gasto;
import com.example.projectfinance.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class GastoAdapter extends ArrayAdapter<Gasto> {
    private Context context;
    private List<Gasto> gastos;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public GastoAdapter(Context context, List<Gasto> gastos) {
        super(context, 0, gastos);
        this.context = context;
        this.gastos = gastos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;

        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_gasto, parent, false);
        }

        Gasto gasto = gastos.get(position);

        TextView txtDescricao = itemView.findViewById(com.example.projectfinance.R.id.txtDescricao);
        TextView txtValor = itemView.findViewById(R.id.txtValor);
        TextView txtCategoria = itemView.findViewById(R.id.txtCategoria);
        TextView txtData = itemView.findViewById(R.id.txtData);

        txtDescricao.setText(gasto.getDescricao());
        txtValor.setText(currencyFormat.format(gasto.getValor()));
        txtCategoria.setText(gasto.getCategoria());
        txtData.setText(dateFormat.format(gasto.getData()));

        return itemView;
    }
}
