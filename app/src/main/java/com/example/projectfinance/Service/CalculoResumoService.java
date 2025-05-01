package com.example.projectfinance.Service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.example.projectfinance.DatabaseHelper.DatabaseHelper;
import com.example.projectfinance.Models.Gasto;
import com.example.projectfinance.Models.ResumoDados;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculoResumoService extends IntentService {
    public static final String RECEIVER = "receiver";
    public static final int RESULTADO_OK = 100;
    public static final String RESUMO_DADOS = "resumo_dados";

    public CalculoResumoService() {
        super("CalculoResumoService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final ResultReceiver receiver = intent.getParcelableExtra(RECEIVER);


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        DatabaseHelper db = new DatabaseHelper(this);
        List<Gasto> gastos = db.getTodosGastos();

        ResumoDados resumo = calcularResumo(gastos);

        Bundle bundle = new Bundle();
        bundle.putSerializable(RESUMO_DADOS, resumo);


        receiver.send(RESULTADO_OK, bundle);
    }

    private ResumoDados calcularResumo(List<Gasto> gastos) {
        ResumoDados resumo = new ResumoDados();
        Map<String, Double> totalPorCategoria = new HashMap<>();
        double gastoTotalMes = 0;
        String categoriaMaiorGasto = "";
        double valorMaiorGasto = 0;

        for (Gasto gasto : gastos) {
            String categoria = gasto.getCategoria();
            double valor = gasto.getValor();


                if (totalPorCategoria.containsKey(categoria)) {
                double total = totalPorCategoria.get(categoria) + valor;
                totalPorCategoria.put(categoria, total);

                if (total > valorMaiorGasto) {
                    valorMaiorGasto = total;
                    categoriaMaiorGasto = categoria;
                }
            } else {
                totalPorCategoria.put(categoria, valor);

                if (valor > valorMaiorGasto) {
                    valorMaiorGasto = valor;
                    categoriaMaiorGasto = categoria;
                }
            }


            gastoTotalMes += valor;
        }

        resumo.setTotalPorCategoria(totalPorCategoria);
        resumo.setGastoTotalMes(gastoTotalMes);
        resumo.setCategoriaMaiorGasto(categoriaMaiorGasto);
        resumo.setValorMaiorGasto(valorMaiorGasto);

        return resumo;
    }
}
