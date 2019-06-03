package com.looigi.loovf;

public class StrutturaConfig {
    private boolean Random = true;
    private String UltimaCategoriaVideo = "Tutto";
    private String UltimaCategoriaImmagini = "Tutto";

    public boolean isRandom() {
        return Random;
    }

    public void setRandom(boolean random) {
        Random = random;
    }

    public String getUltimaCategoriaVideo() {
        return UltimaCategoriaVideo;
    }

    public void setUltimaCategoriaVideo(String ultimaCategoriaVideo) {
        UltimaCategoriaVideo = ultimaCategoriaVideo;
    }

    public String getUltimaCategoriaImmagini() {
        return UltimaCategoriaImmagini;
    }

    public void setUltimaCategoriaImmagini(String ultimaCategoriaImmagini) {
        UltimaCategoriaImmagini = ultimaCategoriaImmagini;
    }
}
