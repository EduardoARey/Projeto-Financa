package com.example.projectfinance;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectfinance.DatabaseHelper.DatabaseHelper;
import com.example.projectfinance.Models.Gasto;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CadastroActivity extends AppCompatActivity {
    private EditText editDescricao, editValor;
    private Spinner spinnerCategoria;
    private Button btnData, btnSalvar;
    private Date dataGasto;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        editDescricao = findViewById(R.id.editDescricao);
        editValor = findViewById(R.id.editValor);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        btnData = findViewById(R.id.btnData);
        btnSalvar = findViewById(R.id.btnSalvar);

        db = new DatabaseHelper(this);


        dataGasto = new Date();
        atualizarBotaoData();


        String[] categorias = {"Alimentação", "Transporte", "Moradia", "Lazer", "Saúde", "Educação", "Outros"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categorias);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(spinnerAdapter);


        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dataGasto);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        CadastroActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Calendar selectedCalendar = Calendar.getInstance();
                                selectedCalendar.set(year, month, dayOfMonth);
                                dataGasto = selectedCalendar.getTime();
                                atualizarBotaoData();
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );

                datePickerDialog.show();
            }
        });


        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarGasto();
            }
        });
    }

    private void atualizarBotaoData() {
        btnData.setText(dateFormat.format(dataGasto));
    }

    private void salvarGasto() {

        String descricao = editDescricao.getText().toString().trim();
        String valorStr = editValor.getText().toString().trim();
        String categoria = spinnerCategoria.getSelectedItem().toString();

        if (descricao.isEmpty()) {
            editDescricao.setError("Informe a descrição");
            return;
        }

        if (valorStr.isEmpty()) {
            editValor.setError("Informe o valor");
            return;
        }

        try {
            double valor = Double.parseDouble(valorStr);


            Gasto gasto = new Gasto(descricao, valor, categoria, dataGasto);


            long id = db.adicionarGasto(gasto);

            if (id > 0) {
                Toast.makeText(this, "Gasto salvo com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Erro ao salvar gasto", Toast.LENGTH_SHORT).show();
            }

        } catch (NumberFormatException e) {
            editValor.setError("Valor inválido");
        }
    }
}