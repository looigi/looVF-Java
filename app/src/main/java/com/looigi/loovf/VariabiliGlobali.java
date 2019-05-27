package com.looigi.loovf;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

public class VariabiliGlobali {
    private static final VariabiliGlobali ourInstance = new VariabiliGlobali();

    public static VariabiliGlobali getInstance() {
        return ourInstance;
    }

    private VariabiliGlobali() {
    }

    private Context context;
    private Activity FragmentActivityPrincipale;
    private List<StrutturaFiles> listaVideo = new ArrayList<>();
    private List<StrutturaFiles> listaImmagini = new ArrayList<>();
    private int ImmagineVisualizzata;
    private int VideoVisualizzato;
    private String PercorsoDIR= Environment.getExternalStorageDirectory().getPath()+"/LooigiSoft/looVF";
    private String PercorsoURL="http://looigi.no-ip.biz:12345/looVF";
    private boolean DatiCaricati = false;
    private TextView txtInfo;
    private String Modalita;
    private CheckBox chkRandom;
    private List<Integer> VideoVisualizzati = new ArrayList<>();
    private int IndiceVideo=0;
    private List<Integer> ImmaginiVisualizzate = new ArrayList<>();
    private int IndiceImmagine=0;
    private VideoView vView;
    private ImageView iView;
    private List<String> categoriaVideo= new ArrayList<>();
    private List<String> categoriaImmagini= new ArrayList<>();

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Activity getFragmentActivityPrincipale() {
        return FragmentActivityPrincipale;
    }

    public void setFragmentActivityPrincipale(Activity fragmentActivityPrincipale) {
        FragmentActivityPrincipale = fragmentActivityPrincipale;
    }

    public String getPercorsoDIR() {
        return PercorsoDIR;
    }

    public String getPercorsoURL() {
        return PercorsoURL;
    }

    public int getImmagineVisualizzata() {
        return ImmagineVisualizzata;
    }

    public void setImmagineVisualizzata(int immagineVisualizzata) {
        ImmagineVisualizzata = immagineVisualizzata;
    }

    public int getVideoVisualizzato() {
        return VideoVisualizzato;
    }

    public void setVideoVisualizzato(int videoVisualizzato) {
        VideoVisualizzato = videoVisualizzato;
    }

    public boolean isDatiCaricati() {
        return DatiCaricati;
    }

    public void setDatiCaricati(boolean datiCaricati) {
        DatiCaricati = datiCaricati;
    }

    public TextView getTxtInfo() {
        return txtInfo;
    }

    public void setTxtInfo(TextView txtInfo) {
        this.txtInfo = txtInfo;
    }

    public String getModalita() {
        return Modalita;
    }

    public void setModalita(String modalita) {
        Modalita = modalita;
    }

    public List<StrutturaFiles> getListaVideo() {
        return listaVideo;
    }

    public void setListaVideo(List<StrutturaFiles> listaVideo) {
        this.listaVideo = listaVideo;
    }

    public List<StrutturaFiles> getListaImmagini() {
        return listaImmagini;
    }

    public void setListaImmagini(List<StrutturaFiles> listaImmagini) {
        this.listaImmagini = listaImmagini;
    }

    public CheckBox getChkRandom() {
        return chkRandom;
    }

    public void setChkRandom(CheckBox chkRandom) {
        this.chkRandom = chkRandom;
    }

    public List<Integer> getVideoVisualizzati() {
        return VideoVisualizzati;
    }

    public List<Integer> getImmaginiVisualizzate() {
        return ImmaginiVisualizzate;
    }

    public int getIndiceVideo() {
        return IndiceVideo;
    }

    public void setIndiceVideo(int indiceVideo) {
        IndiceVideo = indiceVideo;
    }

    public int getIndiceImmagine() {
        return IndiceImmagine;
    }

    public void setIndiceImmagine(int indiceImmagine) {
        IndiceImmagine = indiceImmagine;
    }

    public VideoView getvView() {
        return vView;
    }

    public void setvView(VideoView vView) {
        this.vView = vView;
    }

    public ImageView getiView() {
        return iView;
    }

    public void setiView(ImageView iView) {
        this.iView = iView;
    }

    public List<String> getCategoriaVideo() {
        return categoriaVideo;
    }

    public void setCategoriaVideo(List<String> categoriaVideo) {
        this.categoriaVideo = categoriaVideo;
    }

    public List<String> getCategoriaImmagini() {
        return categoriaImmagini;
    }

    public void setCategoriaImmagini(List<String> categoriaImmagini) {
        this.categoriaImmagini = categoriaImmagini;
    }
}
