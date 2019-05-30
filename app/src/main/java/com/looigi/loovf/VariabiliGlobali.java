package com.looigi.loovf;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    // private List<StrutturaFiles> listaVideo = new ArrayList<>();
    // private List<StrutturaFiles> listaImmagini = new ArrayList<>();
    private long QuantiVideo;
    private long QuanteImmagini;
    private long ImmagineVisualizzata = 0;
    private long VideoVisualizzato = 0;
    private String PercorsoDIR= Environment.getExternalStorageDirectory().getPath()+"/LooigiSoft/looVF";
    private String PercorsoURL="http://looigi.no-ip.biz:12345/looVF";
    // private boolean DatiCaricati = false;
    private TextView txtInfo;
    private String Modalita;
    private CheckBox chkRandom;
    private List<Long> VideoVisualizzati = new ArrayList<>();
    private long IndiceVideo=0;
    private List<Long> ImmaginiVisualizzate = new ArrayList<>();
    private long IndiceImmagine=0;
    private VideoView vView;
    private ImageView iView;
    private List<String> categorieVideo=new ArrayList<>();
    private List<String> categorieImmagini=new ArrayList<>();
    private StrutturaFiles ImmagineCaricata;
    private StrutturaFiles VideoCaricato;
    private LinearLayout laySettings;
    private boolean LinguettaAperta = true;

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

    public boolean isLinguettaAperta() {
        return LinguettaAperta;
    }

    public void setLinguettaAperta(boolean linguettaAperta) {
        LinguettaAperta = linguettaAperta;
    }

    public LinearLayout getLaySettings() {
        return laySettings;
    }

    public void setLaySettings(LinearLayout laySettings) {
        this.laySettings = laySettings;
    }

    public StrutturaFiles getImmagineCaricata() {
        return ImmagineCaricata;
    }

    public void setImmagineCaricata(StrutturaFiles immagineCaricata) {
        ImmagineCaricata = immagineCaricata;
    }

    public StrutturaFiles getVideoCaricato() {
        return VideoCaricato;
    }

    public void setVideoCaricato(StrutturaFiles videoCaricato) {
        VideoCaricato = videoCaricato;
    }

    public long getQuantiVideo() {
        return QuantiVideo;
    }

    public void setQuantiVideo(long quantiVideo) {
        QuantiVideo = quantiVideo;
    }

    public long getQuanteImmagini() {
        return QuanteImmagini;
    }

    public void setQuanteImmagini(long quanteImmagini) {
        QuanteImmagini = quanteImmagini;
    }

    public String getPercorsoDIR() {
        return PercorsoDIR;
    }

    public String getPercorsoURL() {
        return PercorsoURL;
    }

    public long getImmagineVisualizzata() {
        return ImmagineVisualizzata;
    }

    public void setImmagineVisualizzata(long immagineVisualizzata) {
        ImmagineVisualizzata = immagineVisualizzata;
    }

    public long getVideoVisualizzato() {
        return VideoVisualizzato;
    }

    public void setVideoVisualizzato(long videoVisualizzato) {
        VideoVisualizzato = videoVisualizzato;
    }

    // public boolean isDatiCaricati() {
    //     return DatiCaricati;
    // }

    // public void setDatiCaricati(boolean datiCaricati) {
    //     DatiCaricati = datiCaricati;
    // }

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

    // public List<StrutturaFiles> getListaVideo() {
    //     return listaVideo;
    // }

    // public void setListaVideo(List<StrutturaFiles> listaVideo) {
    //     this.listaVideo = listaVideo;
    // }

    // public List<StrutturaFiles> getListaImmagini() {
    //     return listaImmagini;
    // }

    // public void setListaImmagini(List<StrutturaFiles> listaImmagini) {
    //     this.listaImmagini = listaImmagini;
    // }

    public CheckBox getChkRandom() {
        return chkRandom;
    }

    public void setChkRandom(CheckBox chkRandom) {
        this.chkRandom = chkRandom;
    }

    public List<Long> getVideoVisualizzati() {
        return VideoVisualizzati;
    }

    public List<Long> getImmaginiVisualizzate() {
        return ImmaginiVisualizzate;
    }

    public long getIndiceVideo() {
        return IndiceVideo;
    }

    public void setIndiceVideo(long indiceVideo) {
        IndiceVideo = indiceVideo;
    }

    public long getIndiceImmagine() {
        return IndiceImmagine;
    }

    public void setIndiceImmagine(long indiceImmagine) {
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

    public List<String> getCategorieVideo() {
        return categorieVideo;
    }

    public void setCategorieVideo(List<String> categorieVideo) {
        this.categorieVideo = categorieVideo;
    }

    public List<String> getCategorieImmagini() {
        return categorieImmagini;
    }

    public void setCategorieImmagini(List<String> categorieImmagini) {
        this.categorieImmagini = categorieImmagini;
    }
}
