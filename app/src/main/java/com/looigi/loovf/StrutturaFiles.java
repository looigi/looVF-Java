package com.looigi.loovf;

import java.util.Date;

public class StrutturaFiles {
    private String Tipologia;
    private String NomeFile;
    private long DimeFile;
    private String DataFile;
    private int Categoria;

    public String getTipologia() {
        return Tipologia;
    }

    public void setTipologia(String tipologia) {
        Tipologia = tipologia;
    }

    public String getNomeFile() {
        return NomeFile;
    }

    public void setNomeFile(String nomeFile) {
        NomeFile = nomeFile;
    }

    public long getDimeFile() {
        return DimeFile;
    }

    public void setDimeFile(long dimeFile) {
        DimeFile = dimeFile;
    }

    public String getDataFile() {
        return DataFile;
    }

    public void setDataFile(String dataFile) {
        DataFile = dataFile;
    }

    public int getCategoria() {
        return Categoria;
    }

    public void setCategoria(int categoria) {
        Categoria = categoria;
    }
}
