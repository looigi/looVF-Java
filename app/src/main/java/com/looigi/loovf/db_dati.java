package com.looigi.loovf;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;

import static android.content.Context.MODE_PRIVATE;

public class db_dati {
    private String PathDB = VariabiliGlobali.getInstance().getPercorsoDIR()+"/DB/";
    private String NomeDB = "dati.db";

    public db_dati() {
        File f = new File(PathDB);
        try {
            f.mkdirs();
        } catch (Exception ignored) {

        }
    }

    private SQLiteDatabase ApreDB() {
        SQLiteDatabase db = null;
        try {
            db = VariabiliGlobali.getInstance().getContext().openOrCreateDatabase(
                    PathDB + NomeDB, MODE_PRIVATE, null);
        } catch (Exception e) {
            int a = 0;
        }
        return  db;
    }

    public void CreazioneTabellaVisti() {
        try {
            SQLiteDatabase myDB = ApreDB();
            if (myDB != null) {
                myDB.execSQL("CREATE TABLE IF NOT EXISTS "
                        + "Visti "
                        + " (Tipologia VARCHAR, Progressivo INT(7), id INT(7));");
            }
        } catch (Exception ignored) {
            int a = 0;
        }
    }

    public boolean ScriveVisti(String idMultimedia, String Tipologia) {
        boolean Ok = true;

        SQLiteDatabase myDB = ApreDB();
        if (myDB != null) {
            int Progressivo = 0;

            Cursor c = myDB.rawQuery("SELECT Max(Progressivo) FROM Visti WHERE Tipologia = ? AND id = ?", new String[]{ Tipologia, idMultimedia});
            c.moveToFirst();
            if (c.getCount() > 0) {
                Progressivo = c.getInt(0);
            }
            c.close();

            try {
                Progressivo++;
                myDB.execSQL("INSERT INTO"
                        + " Visti"
                        + " (Tipologia, Progressivo, id)"
                        + " VALUES ('" + Tipologia + "', " + Progressivo + ", " + idMultimedia + ");");
            } catch (SQLException e) {
                Ok = false;
            }
        }

        return Ok;
    }

    public int LeggeUltimaVista(String Tipologia) {
        int Numero = -1;

        SQLiteDatabase myDB = ApreDB();
        if (myDB != null) {
            int Progressivo = 0;

            Cursor c = myDB.rawQuery("SELECT id FROM Visti WHERE Tipologia = ? Order By Progressivo Desc", new String[]{ Tipologia });
            c.moveToFirst();
            if (c.getCount() > 0) {
                Numero = c.getInt(0);
            }
            c.close();
        }

        return Numero;
    }

    public int LeggeVistaDaNumero(String Tipologia, String Numero) {
        int Ritorno = -1;

        SQLiteDatabase myDB = ApreDB();
        if (myDB != null) {
            int Progressivo = 0;

            Cursor c = myDB.rawQuery("SELECT id FROM Visti WHERE Tipologia = ? And Progressivo = ?", new String[]{ Tipologia, Numero });
            c.moveToFirst();
            if (c.getCount() > 0) {
                Ritorno = c.getInt(0);
            }
            c.close();
        }

        return Ritorno;
    }
}
