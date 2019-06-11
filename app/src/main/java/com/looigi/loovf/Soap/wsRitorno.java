package com.looigi.loovf.Soap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.looigi.loovf.AdapterMultimedia;
import com.looigi.loovf.DialogMessaggio;
import com.looigi.loovf.R;
import com.looigi.loovf.StrutturaFiles;
import com.looigi.loovf.Utility;
import com.looigi.loovf.VariabiliGlobali;
import com.looigi.loovf.db_locale.db_dati;
import com.looigi.loovf.griglia.AdapterRecyclerView;
import com.looigi.loovf.griglia.ImageListForRecycler;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class wsRitorno {
    private Runnable runRiga;
    private Handler hSelezionaRiga;

    public void RitornaQuantiFilesVideo(final String Ritorno) {
        if (Ritorno.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliGlobali.getInstance().getContext(),
                    Ritorno,
                    true,
                    "looVF",
                    false);
        } else {
            hSelezionaRiga = new Handler(Looper.getMainLooper());
            hSelezionaRiga.postDelayed(runRiga = new Runnable() {
                @Override
                public void run() {
                    hSelezionaRiga.removeCallbacks(runRiga);
                    runRiga = null;

                    VariabiliGlobali.getInstance().setQuantiVideo(Long.parseLong(Ritorno));

                    Utility.getInstance().ScriveInformazioni();
                    Utility.getInstance().PrendeUltimoMultimedia(true);

                    Utility.getInstance().riempieSpinner();

                    VariabiliGlobali.getInstance().setCaricataPagina(true);
                }
            },50);
        }
    }

    public void RitornaQuantiFilesPhoto(final String Ritorno) {
        if (Ritorno.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliGlobali.getInstance().getContext(),
                    Ritorno,
                    true,
                    "looVF",
                    false);
        } else {
            hSelezionaRiga = new Handler(Looper.getMainLooper());
            hSelezionaRiga.postDelayed(runRiga = new Runnable() {
                @Override
                public void run() {
                    hSelezionaRiga.removeCallbacks(runRiga);
                    runRiga = null;

                    VariabiliGlobali.getInstance().setQuanteImmagini(Long.parseLong(Ritorno));

                    // DBRemoto dbr = new DBRemoto();
                    // dbr.RitornaQuantiFilesVideo();
                }
            },50);
        }
    }

    public void RitornaMultimediaDaId(final String Ritorno) {
        if (Ritorno.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliGlobali.getInstance().getContext(),
                    Ritorno,
                    true,
                    "looVF",
                    false);
        } else {
            hSelezionaRiga = new Handler(Looper.getMainLooper());
            hSelezionaRiga.postDelayed(runRiga = new Runnable() {
                @Override
                public void run() {
                    hSelezionaRiga.removeCallbacks(runRiga);
                    runRiga = null;

                    Utility.getInstance().VisualizzaMultimedia(Ritorno);
                }
            }, 50);
        }
    }

    public void EffettuaRicerca(final String Ritorno) {
        if (Ritorno.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliGlobali.getInstance().getContext(),
                    Ritorno,
                    true,
                    "looVF",
                    false);
        } else {
            hSelezionaRiga = new Handler(Looper.getMainLooper());
            hSelezionaRiga.postDelayed(runRiga = new Runnable() {
                @Override
                public void run() {
                    hSelezionaRiga.removeCallbacks(runRiga);
                    runRiga = null;

                    List<String> ll = new ArrayList<>();
                    String[] r = Ritorno.split("§");
                    for (String rr : r) {
                        ll.add(rr);
                    }
                    AdapterMultimedia a = new AdapterMultimedia(VariabiliGlobali.getInstance().getContext(),
                            android.R.layout.simple_list_item_1, ll);
                    VariabiliGlobali.getInstance().getLstRicerca().setAdapter(a);
                }
            }, 50);
        }
    }

    public void RitornaCategorie(final String Ritorno) {
        if (Ritorno.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliGlobali.getInstance().getContext(),
                    Ritorno,
                    true,
                    "looVF",
                    false);
        } else {
            hSelezionaRiga = new Handler(Looper.getMainLooper());
            hSelezionaRiga.postDelayed(runRiga = new Runnable() {
                @Override
                public void run() {
                    hSelezionaRiga.removeCallbacks(runRiga);
                    runRiga = null;

                    List<String> cv = new ArrayList();
                    List<String> ci = new ArrayList();

                    String[] c = Ritorno.split("§");
                    for (String cc: c) {
                        String ccc[] = cc.split(";");

                        if (ccc[1].equals("1")) {
                            ci.add(ccc[2].replace("***PV***",";"));
                        } else {
                            cv.add(ccc[2].replace("***PV***",";"));
                        }
                    }

                    VariabiliGlobali.getInstance().setCategorieImmagini(ci);
                    VariabiliGlobali.getInstance().setCategorieVideo(cv);

                    // DBRemoto dbr = new DBRemoto();
                    // dbr.RitornaQuantiFilesPhoto();
                }
            }, 50);
        }
    }

    public void RitornaSuccessivoMultimedia(final String Ritorno) {
        if (Ritorno.toUpperCase().contains("ERROR:") || Ritorno.equals("0")) {
            DialogMessaggio.getInstance().show(VariabiliGlobali.getInstance().getContext(),
                    "Errore nella lettura del successivo multimedia:\n" + Ritorno,
                    true,
                    "looVF",
                    false);
        } else {
            long prossimo = Long.parseLong(Ritorno);
            db_dati db = new db_dati();

            if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
                VariabiliGlobali.getInstance().setVideoVisualizzato(prossimo);

                db.ScriveVisti(Long.toString(prossimo), VariabiliGlobali.getInstance().getModalita());

                VariabiliGlobali.getInstance().getVideoVisualizzati().add(VariabiliGlobali.getInstance().getVideoVisualizzato());
                VariabiliGlobali.getInstance().setIndiceVideo(VariabiliGlobali.getInstance().getVideoVisualizzati().size() - 1);
            } else {
                if (VariabiliGlobali.getInstance().getModalita().equals("PHOTO")) {
                    VariabiliGlobali.getInstance().setImmagineVisualizzata(prossimo);

                    db.ScriveVisti(Long.toString(prossimo), VariabiliGlobali.getInstance().getModalita());

                    VariabiliGlobali.getInstance().getImmaginiVisualizzate().add(VariabiliGlobali.getInstance().getImmagineVisualizzata());
                    VariabiliGlobali.getInstance().setIndiceImmagine(VariabiliGlobali.getInstance().getImmaginiVisualizzate().size() - 1);
                }
            }

            hSelezionaRiga = new Handler(Looper.getMainLooper());
            hSelezionaRiga.postDelayed(runRiga = new Runnable() {
                @Override
                public void run() {
                    hSelezionaRiga.removeCallbacks(runRiga);
                    runRiga = null;

                    if (VariabiliGlobali.getInstance().isDeveCaricare()) {
                        VariabiliGlobali.getInstance().setDeveCaricare(false);
                        Utility.getInstance().ScriveInformazioni();
                        Utility.getInstance().CaricaMultimedia();
                    }
                }
            }, 50);
        }
    }

    public void RitornaListe(final String Ritorno) {
        if (Ritorno.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliGlobali.getInstance().getContext(),
                    Ritorno,
                    true,
                    "looVF",
                    false);
        } else {
            DialogMessaggio.getInstance().show(VariabiliGlobali.getInstance().getContext(),
                    "Dati ricaricati dal server",
                    false,
                    "looVF",
                    false);

            hSelezionaRiga = new Handler(Looper.getMainLooper());
            hSelezionaRiga.postDelayed(runRiga = new Runnable() {
                @Override
                public void run() {
                    hSelezionaRiga.removeCallbacks(runRiga);
                    runRiga = null;

                    DBRemoto dbr = new DBRemoto();
                    dbr.RitornaCategorie();
                    dbr.RitornaQuantiFilesPhoto();
                    dbr.RitornaQuantiFilesVideo();

                    // // C:\inetpub\wwwroot\looVF\Temp\28_05_2019_10_20_38.txt
                    // String sRitorno = Ritorno;
                    // sRitorno = sRitorno.substring(sRitorno.indexOf("Temp"), sRitorno.length());
//
                    // DownloadTextFile d = new DownloadTextFile();
                    // d.setPath(VariabiliGlobali.getInstance().getPercorsoDIR());
                    // d.setPathNomeFile("Lista.dat");
                    // d.setOperazione("Lettura lista multimedia");
                    // d.startDownload(
                    //         VariabiliGlobali.getInstance().getPercorsoURL()+ "/" + sRitorno.replace("\\", "/"),
                    //         true);
                }
            }, 50);
        }
    }

    public void RitornaImmaginiPerGriglia(final String Ritorno) {
        if (Ritorno.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliGlobali.getInstance().getContext(),
                    Ritorno,
                    true,
                    "looVF",
                    false);
        } else {
            Utility.getInstance().CaricaRecyclerView(Ritorno, true);
        }
    }
}
