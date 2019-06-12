package com.looigi.loovf;

public class StrutturaConfig {
    private boolean Random = true;
    private StrutturaCategorie UltimaCategoriaVideo;
    private StrutturaCategorie UltimaCategoriaImmagini;
    private boolean VisuaTutto;

    public boolean isVisuaTutto() {
        return VisuaTutto;
    }

    public void setVisuaTutto(boolean visuaTutto) {
        VisuaTutto = visuaTutto;
    }

    public boolean isRandom() {
        return Random;
    }

    public void setRandom(boolean random) {
        Random = random;
    }

    public StrutturaCategorie getUltimaCategoriaVideo() {
        return UltimaCategoriaVideo;
    }

    public void setUltimaCategoriaVideo(StrutturaCategorie ultimaCategoriaVideo) {
        UltimaCategoriaVideo = ultimaCategoriaVideo;
    }

    public StrutturaCategorie getUltimaCategoriaImmagini() {
        return UltimaCategoriaImmagini;
    }

    public void setUltimaCategoriaImmagini(StrutturaCategorie ultimaCategoriaImmagini) {
        UltimaCategoriaImmagini = ultimaCategoriaImmagini;
    }
}
