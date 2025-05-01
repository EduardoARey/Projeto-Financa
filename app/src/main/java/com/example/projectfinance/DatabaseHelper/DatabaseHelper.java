package com.example.projectfinance.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.projectfinance.Models.Gasto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "minhas_financas.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_GASTOS = "gastos";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DESCRICAO = "descricao";
    private static final String COLUMN_VALOR = "valor";
    private static final String COLUMN_CATEGORIA = "categoria";
    private static final String COLUMN_DATA = "data";

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_GASTOS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DESCRICAO + " TEXT, " +
                COLUMN_VALOR + " REAL, " +
                COLUMN_CATEGORIA + " TEXT, " +
                COLUMN_DATA + " TEXT" +
                ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GASTOS);
        onCreate(db);
    }


    public long adicionarGasto(Gasto gasto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_DESCRICAO, gasto.getDescricao());
        values.put(COLUMN_VALOR, gasto.getValor());
        values.put(COLUMN_CATEGORIA, gasto.getCategoria());
        values.put(COLUMN_DATA, dateFormat.format(gasto.getData()));

        long id = db.insert(TABLE_GASTOS, null, values);
        db.close();
        return id;
    }


    public List<Gasto> getTodosGastos() {
        List<Gasto> listaGastos = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_GASTOS + " ORDER BY " + COLUMN_DATA + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Gasto gasto = new Gasto();


                try {
                    gasto.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                    gasto.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRICAO)));
                    gasto.setValor(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_VALOR)));
                    gasto.setCategoria(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORIA)));

                    String dataStr = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATA));
                    try {
                        gasto.setData(dateFormat.parse(dataStr));
                    } catch (ParseException e) {
                        Log.e(TAG, "Erro ao converter data: " + dataStr, e);
                        gasto.setData(new Date());
                    }

                    listaGastos.add(gasto);
                } catch (IllegalArgumentException e) {
                    Log.e(TAG, "Coluna não encontrada no banco de dados", e);

                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listaGastos;
    }


    public List<Gasto> getTodosGastosAlternative() {
        List<Gasto> listaGastos = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_GASTOS + " ORDER BY " + COLUMN_DATA + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            // Obtém os índices das colunas uma única vez antes do loop
            int idIndex = cursor.getColumnIndex(COLUMN_ID);
            int descricaoIndex = cursor.getColumnIndex(COLUMN_DESCRICAO);
            int valorIndex = cursor.getColumnIndex(COLUMN_VALOR);
            int categoriaIndex = cursor.getColumnIndex(COLUMN_CATEGORIA);
            int dataIndex = cursor.getColumnIndex(COLUMN_DATA);


            boolean todasColunasValidas = idIndex != -1 && descricaoIndex != -1 &&
                    valorIndex != -1 && categoriaIndex != -1 &&
                    dataIndex != -1;

            if (todasColunasValidas) {
                do {
                    Gasto gasto = new Gasto();

                    gasto.setId(cursor.getLong(idIndex));
                    gasto.setDescricao(cursor.getString(descricaoIndex));
                    gasto.setValor(cursor.getDouble(valorIndex));
                    gasto.setCategoria(cursor.getString(categoriaIndex));

                    try {
                        String dataStr = cursor.getString(dataIndex);
                        gasto.setData(dateFormat.parse(dataStr));
                    } catch (ParseException e) {
                        Log.e(TAG, "Erro ao converter data", e);
                        gasto.setData(new Date());
                    }

                    listaGastos.add(gasto);
                } while (cursor.moveToNext());
            } else {
                Log.e(TAG, "Uma ou mais colunas não foram encontradas no banco de dados");
            }
        }

        cursor.close();
        db.close();
        return listaGastos;
    }
}