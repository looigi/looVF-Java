package com.looigi.loovf;

public class StrutturaCategorie {
    private int idCategoria;
    private String NomeCategoria;
    private String PercorsoCategoria;
    private boolean Protetta;

    public String getPercorsoCategoria() {
        return PercorsoCategoria;
    }

    public void setPercorsoCategoria(String percorsoCategoria) {
        PercorsoCategoria = percorsoCategoria;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNomeCategoria() {
        return NomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        NomeCategoria = nomeCategoria;
    }

    public boolean isProtetta() {
        return Protetta;
    }

    public void setProtetta(boolean protetta) {
        Protetta = protetta;
    }
}
