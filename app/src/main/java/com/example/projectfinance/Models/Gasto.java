package com.example.projectfinance.Models;

import java.io.Serializable;
import java.util.Date;

public class Gasto implements Serializable {
    private long id;
    private String descricao;
    private double valor;
    private String categoria;
    private Date data;

    public Gasto() {
    }

    public Gasto(String descricao, double valor, String categoria, Date data) {
        this.descricao = descricao;
        this.valor = valor;
        this.categoria = categoria;
        this.data = data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

}
