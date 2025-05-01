package com.example.projectfinance.Models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ResumoDados implements Serializable {
    private Map<String, Double> totalPorCategoria;
    private double gastoTotalMes;
    private String categoriaMaiorGasto;
    private double valorMaiorGasto;

    public ResumoDados() {
        totalPorCategoria = new HashMap<>();
        gastoTotalMes = 0;
        categoriaMaiorGasto = "";
        valorMaiorGasto = 0;
    }

    // Getters e Setters
    public Map<String, Double> getTotalPorCategoria() {
        return totalPorCategoria;
    }

    public void setTotalPorCategoria(Map<String, Double> totalPorCategoria) {
        this.totalPorCategoria = totalPorCategoria;
    }

    public double getGastoTotalMes() {
        return gastoTotalMes;
    }

    public void setGastoTotalMes(double gastoTotalMes) {
        this.gastoTotalMes = gastoTotalMes;
    }

    public String getCategoriaMaiorGasto() {
        return categoriaMaiorGasto;
    }

    public void setCategoriaMaiorGasto(String categoriaMaiorGasto) {
        this.categoriaMaiorGasto = categoriaMaiorGasto;
    }

    public double getValorMaiorGasto() {
        return valorMaiorGasto;
    }

    public void setValorMaiorGasto(double valorMaiorGasto) {
        this.valorMaiorGasto = valorMaiorGasto;
    }
}
