package com.looigi.loovf;

import java.util.Date;

public class StrutturaFiles {
    private String Tipologia;
    private String NomeFile;
    private long DimeFile;
    private Date DataFile;

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

    public Date getDataFile() {
        return DataFile;
    }

    public void setDataFile(Date dataFile) {
        DataFile = dataFile;
    }
}
