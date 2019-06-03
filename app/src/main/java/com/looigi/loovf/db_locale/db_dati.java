package com.looigi.loovf.db_locale;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.looigi.loovf.StrutturaConfig;
import com.looigi.loovf.StrutturaFiles;
import com.looigi.loovf.Utility;
import com.looigi.loovf.VariabiliGlobali;

import java.io.File;

import static android.content.Context.MODE_PRIVATE;

public class db_dati {
    private String PathDB = VariabiliGlobali.getInstance().getPercorsoDIR()+"/DB/";
    private String NomeDB = "dati.db";
    private SQLiteDatabase myDB;

    public db_dati() {
        File f = new File(PathDB);
        try {
            f.mkdirs();
        } catch (Exception ignored) {

        }
        myDB = ApreDB();
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

    public void CreazioneTabelle() {
        try {
            // SQLiteDatabase myDB = ApreDB();
            if (myDB != null) {
                myDB.execSQL("CREATE TABLE IF NOT EXISTS "
                        + "Visti "
                        + " (Tipologia VARCHAR, Progressivo INT(7), id INT(7));");
                myDB.execSQL("CREATE INDEX IF NOT EXISTS Visti_Index ON Visti(Tipologia, Progressivo);");
                // myDB.execSQL("CREATE TABLE IF NOT EXISTS "
                //         + "Dati "
                //         + " (idCategoria INT(2), Tipologia VARCHAR, Progressivo INT(7), " +
                //         "NomeFile VARCHAR, Dimensione INT(10), Data VARCHAR);");
                // myDB.execSQL("CREATE INDEX IF NOT EXISTS Dati_Index ON Dati(idCategoria, Tipologia, Progressivo);");
                // myDB.execSQL("CREATE TABLE IF NOT EXISTS "
                //         + "Categorie "
                //         + " (Tipologia VARCHAR, idCategoria INT(2), Categoria VARCHAR);");
                // myDB.execSQL("CREATE INDEX IF NOT EXISTS Categorie_Index ON Dati(Tipologia, idCategoria);");
                myDB.execSQL("CREATE TABLE IF NOT EXISTS "
                        + "Configurazione "
                        + " (Random INT(1), UltimaCategoriaImmagini VARCHAR, UltimaCategoriaVideo VARCHAR);");
            }
        } catch (Exception ignored) {
            int a = 0;
        }
    }

    // public void CreaIndiciDati() {
    //     if (myDB != null) {
    //         myDB.execSQL("CREATE INDEX IF NOT EXISTS Dati_Index ON Dati(idCategoria, Tipologia, Progressivo);");
    //     }
    // }

    public void CompattaDB() {
        // SQLiteDatabase myDB = ApreDB();
        if (myDB != null) {
            myDB.execSQL("VACUUM");
        }
    }

    // public boolean ScriveCategoria(String Tipologia, String Categoria) {
    //     boolean Ok = true;
//
    //     // SQLiteDatabase myDB = ApreDB();
    //     if (myDB != null) {
    //         int idCategoria = 0;
//
    //         Cursor c = myDB.rawQuery("SELECT Max(idCategoria) FROM Categorie WHERE Tipologia = ?",
    //                 new String[]{ Tipologia});
    //         c.moveToFirst();
    //         if (c.getCount() > 0) {
    //             idCategoria = c.getInt(0);
    //         }
    //         c.close();
//
    //         try {
    //             idCategoria++;
    //             myDB.execSQL("INSERT INTO"
    //                     + " Categorie"
    //                     + " (Tipologia, idCategoria, Categoria)"
    //                     + " VALUES ('" + Tipologia + "', " + idCategoria + ", '" + Categoria + "');");
    //         } catch (SQLException e) {
    //             Ok = false;
    //         }
    //     }
//
    //     return Ok;
    // }

    public void PulisceDati() {
        // SQLiteDatabase myDB = ApreDB();
        if (myDB != null) {
            // myDB.execSQL("Delete From Categorie");
            // myDB.execSQL("Delete From Dati");
            myDB.execSQL("Delete From Visti");
            myDB.execSQL("Delete From Configurazione");
        }
    }

    // public long RitornaQuantiDati(String Tipologia) {
    //     long Quanti = 0;
//
    //     // SQLiteDatabase myDB = ApreDB();
    //     if (myDB != null) {
    //         Cursor c = myDB.rawQuery("SELECT Count(*) FROM Dati WHERE Tipologia = ?",
    //                 new String[]{ Tipologia });
    //         c.moveToFirst();
    //         if (c.getCount() > 0) {
    //             Quanti = c.getLong(0);
    //         }
    //         c.close();
    //     }
//
    //     return Quanti;
    // }

    // public StrutturaFiles RitornaDati(String Tipologia, String Progressivo) {
    //     StrutturaFiles sf = new StrutturaFiles();
//
    //     // SQLiteDatabase myDB = ApreDB();
    //     if (myDB != null) {
    //         Cursor c = myDB.rawQuery("SELECT * FROM Dati WHERE Tipologia = ? And Progressivo = ?",
    //                 new String[]{ Tipologia, Progressivo});
    //         c.moveToFirst();
    //         if (c.getCount() > 0) {
    //             sf.setCategoria(c.getInt(0));
    //             sf.setTipologia(c.getString(1));
    //             sf.setNomeFile(c.getString(3));
    //             sf.setDimeFile(c.getInt(4));
    //             sf.setDataFile(c.getString(5));
    //         }
    //         c.close();
    //     }
//
    //     return sf;
    // }

    // public boolean ScriveDati(Long Progressivo, String idCategoria, String Tipologia, String NomeFile, String Dimensione, String Data) {
    //     boolean Ok = true;
//
    //     if (myDB != null) {
    //         // long Progressivo = 0;
//
    //         // Cursor c = myDB.rawQuery("SELECT Max(Progressivo) FROM Dati WHERE idCategoria = ? And Tipologia = ?",
    //         //         new String[]{ idCategoria, Tipologia});
    //         // c.moveToFirst();
    //         // if (c.getCount() > 0) {
    //         //     Progressivo = c.getLong(0);
    //         // }
    //         // c.close();
//
    //         try {
    //             // Progressivo++;
    //             myDB.execSQL("INSERT INTO"
    //                     + " Dati"
    //                     + " (idCategoria, Tipologia, Progressivo, NomeFile, Dimensione, Data)"
    //                     + " VALUES (" + idCategoria + ", '" + Tipologia + "', " + Progressivo + ", '" +
    //                     NomeFile + "', " + Dimensione + ", '" + Data + "');");
    //         } catch (SQLException e) {
    //             Ok = false;
    //         }
    //     }
//
    //     return Ok;
    // }

    public StrutturaConfig CaricaConfigurazione() {
        StrutturaConfig Ritorno = new StrutturaConfig();

        if (myDB != null) {
            Cursor c = myDB.rawQuery("SELECT * FROM Configurazione Where UltimaCategoriaImmagini<>?",
                    new String[]{ "***NIENTE***" });
            c.moveToFirst();
            if (c.getCount() > 0) {
                boolean random = true;
                if (c.getInt(0) == 0) {
                    random = false;
                }
                Ritorno.setRandom(random);
                Ritorno.setUltimaCategoriaImmagini(c.getString(1));
                Ritorno.setUltimaCategoriaVideo(c.getString(2));
            } else {
                Ritorno = null;
            }
            c.close();
        }

        return Ritorno;
    }

    public String ScriveConfigurazione() {
        String Ok = "";

        if (myDB != null) {
            int random = 0;
            if (VariabiliGlobali.getInstance().getConfigurazione().isRandom()) {
                random = 1;
            }

            try {
                myDB.execSQL("Delete From Configurazione");
                myDB.execSQL("INSERT INTO"
                        + " Configurazione"
                        + " (Random, UltimaCategoriaImmagini, UltimaCategoriaVideo)"
                        + " VALUES (" + Integer.toString(random) + ", "
                        + "'" + VariabiliGlobali.getInstance().getConfigurazione().getUltimaCategoriaImmagini() + "', "
                        + "'" + VariabiliGlobali.getInstance().getConfigurazione().getUltimaCategoriaVideo() + "' "
                        + ");");
            } catch (SQLException e) {
                Ok = Utility.getInstance().PrendeErroreDaException(e);
            }
        }

        return Ok;
    }

    public boolean ScriveVisti(String idMultimedia, String Tipologia) {
        boolean Ok = true;

        // SQLiteDatabase myDB = ApreDB();
        if (myDB != null) {
            long Progressivo = 0;

            Cursor c = myDB.rawQuery("SELECT Max(Progressivo) FROM Visti WHERE Tipologia = ?",
                    new String[]{ Tipologia });
            c.moveToFirst();
            if (c.getCount() > 0) {
                Progressivo = c.getLong(0);
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

    public long LeggeUltimaVista(String Tipologia) {
        long Numero = -1;

        // SQLiteDatabase myDB = ApreDB();
        if (myDB != null) {
            // int Progressivo = 0;

            Cursor c = myDB.rawQuery("SELECT id FROM Visti WHERE Tipologia = ? Order By Progressivo Desc",
                    new String[]{ Tipologia });
            c.moveToFirst();
            if (c.getCount() > 0) {
                Numero = c.getLong(0);
            }
            c.close();
        }

        return Numero;
    }

    public long LeggeTutteLeViste() {
        long Ritorno = -1;

        // SQLiteDatabase myDB = ApreDB();
        if (myDB != null) {
            Cursor c = myDB.rawQuery("SELECT Tipologia, id FROM Visti Where Progressivo>? " +
                            "Order By Tipologia, Progressivo Desc",
                    new String[]{ "0" });
            c.moveToFirst();
            if (c.getCount() > 0) {
                do {
                    String idTipologia = c.getString(0);

                    if (idTipologia.equals("VIDEO")) {
                        VariabiliGlobali.getInstance().getVideoVisualizzati().add(c.getLong(1));
                        VariabiliGlobali.getInstance().setIndiceVideo(VariabiliGlobali.getInstance().getVideoVisualizzati().size() - 1);
                    } else {
                        VariabiliGlobali.getInstance().getImmaginiVisualizzate().add(c.getLong(1));
                        VariabiliGlobali.getInstance().setIndiceImmagine(VariabiliGlobali.getInstance().getImmaginiVisualizzate().size() - 1);
                    }
                } while (c.moveToNext());
            }
            c.close();
        }

        return Ritorno;
    }
}
