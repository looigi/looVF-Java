package com.looigi.loovf;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

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
    // private CheckBox chkRandom;
    private List<Long> VideoVisualizzati = new ArrayList<>();
    private long IndiceVideo=0;
    private List<Long> ImmaginiVisualizzate = new ArrayList<>();
    private long IndiceImmagine=0;
    // private VideoView vView;
    private ImageView iView;
    private List<StrutturaCategorie> categorieVideo=new ArrayList<>();
    private List<StrutturaCategorie> categorieImmagini=new ArrayList<>();
    private StrutturaFiles ImmagineCaricata;
    private StrutturaFiles VideoCaricato;
    private LinearLayout laySettingsPanel;
    private boolean LinguettaAperta = true;
    private String CategoriaSceltaVideo="Tutto";
    private String CategoriaSceltaImmagine="Tutto";
    private ImageView imgPlayVideo;
    private boolean CaricataPagina=false;
    private Spinner sItems;
    private String UltimoRitornoVideo="";
    private String UltimoRitornoImmagine="";
    private boolean DeveCaricare=false;
    private StrutturaConfig Configurazione;
    // private String Tastopremuto;
    private LinearLayout layGriglia;
    private RecyclerView recyclerView;
    private ImageView imgRefresh;
    private LinearLayout layScroller;
    private LinearLayout layRicerca;
    private ListView lstRicerca;
    private ImageView imgCondividi;
    private CheckBox chkRandom;
    private String TipoTelefono;
    private String Utenza;
    private String IMEI_IMSI;
    private boolean Amministratore=false;
    private CheckBox chkVisuaTutto;
    // private int idCategoriaSceltaImmagini;
    // private int idCategoriaSceltaVideo;

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

    public CheckBox getChkVisuaTutto() {
        return chkVisuaTutto;
    }

    public void setChkVisuaTutto(CheckBox chkVisuaTutto) {
        this.chkVisuaTutto = chkVisuaTutto;
    }

    public boolean isAmministratore() {
        return Amministratore;
    }

    public void setAmministratore(boolean amministratore) {
        Amministratore = amministratore;
    }

    public String getIMEI_IMSI() {
        return IMEI_IMSI;
    }

    public void setIMEI_IMSI(String IMEI_IMSI) {
        this.IMEI_IMSI = IMEI_IMSI;
    }

    public String getTipoTelefono() {
        return TipoTelefono;
    }

    public void setTipoTelefono(String tipoTelefono) {
        TipoTelefono = tipoTelefono;
    }

    public String getUtenza() {
        return Utenza;
    }

    public void setUtenza(String utenza) {
        Utenza = utenza;
    }

    public CheckBox getChkRandom() {
        return chkRandom;
    }

    public void setChkRandom(CheckBox chkRandom) {
        this.chkRandom = chkRandom;
    }

    public ImageView getImgCondividi() {
        return imgCondividi;
    }

    public void setImgCondividi(ImageView imgCondividi) {
        this.imgCondividi = imgCondividi;
    }

    public ListView getLstRicerca() {
        return lstRicerca;
    }

    public void setLstRicerca(ListView lstRicerca) {
        this.lstRicerca = lstRicerca;
    }

    public LinearLayout getLayRicerca() {
        return layRicerca;
    }

    public void setLayRicerca(LinearLayout layRicerca) {
        this.layRicerca = layRicerca;
    }

    public ImageView getImgRefresh() {
        return imgRefresh;
    }

    public void setImgRefresh(ImageView imgRefresh) {
        this.imgRefresh = imgRefresh;
    }

    public LinearLayout getLayScroller() {
        return layScroller;
    }

    public void setLayScroller(LinearLayout layScroller) {
        this.layScroller = layScroller;
    }

    // public int getIdCategoriaSceltaImmagini() {
    //     return idCategoriaSceltaImmagini;
    // }
//
    // public void setIdCategoriaSceltaImmagini(int idCategoriaSceltaImmagini) {
    //     this.idCategoriaSceltaImmagini = idCategoriaSceltaImmagini;
    // }
//
    // public int getIdCategoriaSceltaVideo() {
    //     return idCategoriaSceltaVideo;
    // }
//
    // public void setIdCategoriaSceltaVideo(int idCategoriaSceltaVideo) {
    //     this.idCategoriaSceltaVideo = idCategoriaSceltaVideo;
    // }
//
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public LinearLayout getLayGriglia() {
        return layGriglia;
    }

    public void setLayGriglia(LinearLayout layGriglia) {
        this.layGriglia = layGriglia;
    }

    // public String getTastopremuto() {
    //     return Tastopremuto;
    // }
//
    // public void setTastopremuto(String tastopremuto) {
    //     Tastopremuto = tastopremuto;
    // }

    public StrutturaConfig getConfigurazione() {
        return Configurazione;
    }

    public void setConfigurazione(StrutturaConfig configurazione) {
        Configurazione = configurazione;
    }

    public boolean isDeveCaricare() {
        return DeveCaricare;
    }

    public void setDeveCaricare(boolean deveCaricare) {
        DeveCaricare = deveCaricare;
    }

    public String getUltimoRitornoImmagine() {
        return UltimoRitornoImmagine;
    }

    public void setUltimoRitornoImmagine(String ultimoRitornoImmagine) {
        UltimoRitornoImmagine = ultimoRitornoImmagine;
    }

    public String getUltimoRitornoVideo() {
        return UltimoRitornoVideo;
    }

    public void setUltimoRitornoVideo(String ultimoRitorno) {
        UltimoRitornoVideo = ultimoRitorno;
    }

    public Spinner getsItems() {
        return sItems;
    }

    public void setsItems(Spinner sItems) {
        this.sItems = sItems;
    }

    public boolean isCaricataPagina() {
        return CaricataPagina;
    }

    public void setCaricataPagina(boolean caricataPagina) {
        CaricataPagina = caricataPagina;
    }

    public ImageView getImgPlayVideo() {
        return imgPlayVideo;
    }

    public void setImgPlayVideo(ImageView imgPlayVideo) {
        this.imgPlayVideo = imgPlayVideo;
    }

    public String getCategoriaSceltaVideo() {
        return CategoriaSceltaVideo;
    }
//
    public void setCategoriaSceltaVideo(String categoriaScelta) {
        CategoriaSceltaVideo = categoriaScelta;
    }
//
    public String getCategoriaSceltaImmagine() {
        return CategoriaSceltaImmagine;
    }
//
    public void setCategoriaSceltaImmagine(String categoriaSceltaImmagine) {
        CategoriaSceltaImmagine = categoriaSceltaImmagine;
    }

    public boolean isLinguettaAperta() {
        return LinguettaAperta;
    }

    public void setLinguettaAperta(boolean linguettaAperta) {
        LinguettaAperta = linguettaAperta;
    }

    public LinearLayout getLaySettingsPanel() {
        return laySettingsPanel;
    }

    public void setLaySettingsPanel(LinearLayout laySettingsPanel) {
        this.laySettingsPanel = laySettingsPanel;
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

    // public CheckBox getChkRandom() {
    //     return chkRandom;
    // }
//
    // public void setChkRandom(CheckBox chkRandom) {
    //     this.chkRandom = chkRandom;
    // }

    public void setVideoVisualizzati(List<Long> l) {
        VideoVisualizzati = l;
    }

    public List<Long> getVideoVisualizzati() {
        return VideoVisualizzati;
    }

    public void setImmaginiVisualizzate(List<Long> l) {
        ImmaginiVisualizzate = l;
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

    // public VideoView getvView() {
    //     return vView;
    // }
//
    // public void setvView(VideoView vView) {
    //     this.vView = vView;
    // }

    public ImageView getiView() {
        return iView;
    }

    public void setiView(ImageView iView) {
        this.iView = iView;
    }

    public List<StrutturaCategorie> getCategorieVideo() {
        return categorieVideo;
    }

    public void setCategorieVideo(List<StrutturaCategorie> categorieVideo) {
        this.categorieVideo = categorieVideo;
    }

    public List<StrutturaCategorie> getCategorieImmagini() {
        return categorieImmagini;
    }

    public void setCategorieImmagini(List<StrutturaCategorie> categorieImmagini) {
        this.categorieImmagini = categorieImmagini;
    }

    public StrutturaCategorie RitornaCategoriaDaNome(String idTipologia, String Nome) {
        StrutturaCategorie rit = new StrutturaCategorie();
        List <StrutturaCategorie> ls;
        if (idTipologia.equals("1")) {
            ls = this.categorieImmagini;
        } else {
            ls = this.categorieVideo;
        }

        for (StrutturaCategorie c : ls) {
            if (c.getNomeCategoria().equals(Nome)) {
                rit = c;
                break;
            }
        }

        return rit;
    }

    public StrutturaCategorie RitornaCategoriaDaID(String idTipologia, int ID) {
        StrutturaCategorie rit = new StrutturaCategorie();
        List <StrutturaCategorie> ls;
        if (idTipologia.equals("1")) {
            ls = this.categorieImmagini;
        } else {
            ls = this.categorieVideo;
        }

        for (StrutturaCategorie c : ls) {
            if (c.getIdCategoria()==ID) {
                rit = c;
                break;
            }
        }

        return rit;
    }
}
