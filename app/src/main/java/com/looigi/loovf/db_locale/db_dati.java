package com.looigi.loovf.db_locale;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.looigi.loovf.StrutturaCategorie;
import com.looigi.loovf.StrutturaConfig;
import com.looigi.loovf.StrutturaFiles;
import com.looigi.loovf.Utility;
import com.looigi.loovf.VariabiliGlobali;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
                        + " (Tipologia VARCHAR, Progressivo INT(7), id INT(7), categoria INT(2));");
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
                        + " (Random INT(1), UltimaCategoriaImmagini VARCHAR, UltimaCategoriaVideo VARCHAR, VisuaTutto INT(1));");
                myDB.execSQL("CREATE TABLE IF NOT EXISTS "
                        + "Griglia "
                        + " (idTipologia INT(1), idCategoria INT(2), Progressivo INT(2), Titolo VARCHAR, "+
                        "Dimensione INT(10), Data VARCHAR, Numero INT(10));");
                myDB.execSQL("CREATE INDEX IF NOT EXISTS Griglia_Index ON Visti(idTipologia, idCategoria, Progressivo);");
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
            myDB.execSQL("Delete From Griglia");
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
                StrutturaCategorie sc = VariabiliGlobali.getInstance().RitornaCategoriaDaNome("1", c.getString(1));
                if (sc.getNomeCategoria() == null) {
                    sc = new StrutturaCategorie();
                    sc.setNomeCategoria("Tutto");
                    sc.setIdCategoria(999);
                    sc.setProtetta(false);
                    sc.setPercorsoCategoria("");
                }
                Ritorno.setUltimaCategoriaImmagini(sc);
                sc = VariabiliGlobali.getInstance().RitornaCategoriaDaNome("2", c.getString(2));
                if (sc.getNomeCategoria() == null) {
                    sc = new StrutturaCategorie();
                    sc.setNomeCategoria("Tutto");
                    sc.setIdCategoria(999);
                    sc.setProtetta(false);
                    sc.setPercorsoCategoria("");
                }
                Ritorno.setUltimaCategoriaVideo(sc);
                boolean visuaTutto = true;
                if (c.getInt(3) == 0) {
                    visuaTutto = false;
                }
                Ritorno.setVisuaTutto(visuaTutto);
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

            int visuaTutto = 0;
            if (VariabiliGlobali.getInstance().getConfigurazione().isVisuaTutto()) {
                visuaTutto = 1;
            }

            try {
                myDB.execSQL("Delete From Configurazione");
                myDB.execSQL("INSERT INTO"
                        + " Configurazione"
                        + " (Random, UltimaCategoriaImmagini, UltimaCategoriaVideo, VisuaTutto)"
                        + " VALUES (" + Integer.toString(random) + ", "
                        + "'" + VariabiliGlobali.getInstance().getConfigurazione().getUltimaCategoriaImmagini().getNomeCategoria() + "', "
                        + "'" + VariabiliGlobali.getInstance().getConfigurazione().getUltimaCategoriaVideo().getNomeCategoria() + "', "
                        + "0"
                        + ");");
            } catch (SQLException e) {
                Ok = Utility.getInstance().PrendeErroreDaException(e);
            }
        }

        return Ok;
    }

    public boolean ScriveVisti(String idMultimedia, String Tipologia, String Categoria) {
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
                        + " (Tipologia, Progressivo, id, categoria)"
                        + " VALUES ('" + Tipologia + "', " + Progressivo + ", " + idMultimedia + ", " + Categoria + ");");
            } catch (SQLException e) {
                Ok = false;
            }
        }

        return Ok;
    }

    public String LeggeUltimaVista(String Tipologia) {
        long Numero = -1;
        int Categoria = -1;

        // SQLiteDatabase myDB = ApreDB();
        if (myDB != null) {
            // int Progressivo = 0;

            Cursor c = myDB.rawQuery("SELECT id, categoria FROM Visti WHERE Tipologia = ? Order By Progressivo Desc",
                    new String[]{ Tipologia });
            c.moveToFirst();
            if (c.getCount() > 0) {
                Numero = c.getLong(0);
                Categoria = c.getInt(1);
            }
            c.close();
        }

        return Long.toString(Numero) + ";" + Integer.toString(Categoria);
    }

    public long LeggeTutteLeViste() {
        long Ritorno = -1;

        // // SQLiteDatabase myDB = ApreDB();
        // if (myDB != null) {
        //     Cursor c = myDB.rawQuery("SELECT Tipologia, id FROM Visti Where Progressivo>? " +
        //                     "Order By Tipologia, Progressivo Desc",
        //             new String[]{ "0" });
        //     c.moveToFirst();
        //     if (c.getCount() > 0) {
        //         do {
        //             String idTipologia = c.getString(0);
//
        //             if (idTipologia.equals("VIDEO")) {
        //                 VariabiliGlobali.getInstance().getVideoVisualizzati().add(c.getLong(1));
        //                 VariabiliGlobali.getInstance().setIndiceVideo(VariabiliGlobali.getInstance().getVideoVisualizzati().size() - 1);
        //             } else {
        //                 VariabiliGlobali.getInstance().getImmaginiVisualizzate().add(c.getLong(1));
        //                 VariabiliGlobali.getInstance().setIndiceImmagine(VariabiliGlobali.getInstance().getImmaginiVisualizzate().size() - 1);
        //             }
        //         } while (c.moveToNext());
        //     }
        //     c.close();
        // }

        VariabiliGlobali.getInstance().setIndiceVideo(0);
        VariabiliGlobali.getInstance().setVideoVisualizzati(new ArrayList<Long>());

        VariabiliGlobali.getInstance().setIndiceImmagine(0);
        VariabiliGlobali.getInstance().setImmaginiVisualizzate(new ArrayList<Long>());

        return Ritorno;
    }

    public String RitornaRigheGriglia(String idTipologia, String idCategoria) {
        String Ok = "";

        if (myDB != null) {
            Cursor c = myDB.rawQuery("SELECT * FROM Griglia WHERE idTipologia = ? And idCategoria = ? Order By Progressivo Desc",
                    new String[]{ idTipologia, idCategoria });
            c.moveToFirst();
            if (c.getCount() > 0) {
                do {
                    Ok += c.getString(3) + ";" + c.getString(4) + ";" + c.getString(5)+ ";" + Integer.toString(c.getInt(1)) + ";" + Integer.toString(c.getInt(6)) + ";§";
                } while (c.moveToNext());
            }
            c.close();
        }

        return Ok;
    }

    public String ScriveRigheGriglia(String idTipologia, String idCategoria, String Ritorno) {
        String Ok = "";

        if (myDB != null) {
            try {
                myDB.execSQL("Delete From Griglia Where idTipologia=" + idTipologia + " And idCategoria=" + idCategoria);

                String[] c = Ritorno.split("§");
                int contatore = 0;

                for (String cc : c) {
                    String[] ccc = cc.split(";");

                    myDB.execSQL("INSERT INTO"
                            + " Griglia"
                            + " (idTipologia, idCategoria, Progressivo, Titolo, Dimensione, Data, Numero)"
                            + " VALUES (" + idTipologia + ", "
                            + " " + idCategoria + ", "
                            + " " + contatore + " , "
                            + "'" + ccc[0].replace("'","''") + "', "
                            + "'" + ccc[1] + "', "
                            + "'" + ccc[2] + "', "
                            + " " + ccc[4] + " "
                            + ");");
                    contatore++;
                }
            } catch (SQLException e) {
                Ok = Utility.getInstance().PrendeErroreDaException(e);
            }
        }

        return Ok;
    }
}
