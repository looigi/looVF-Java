package com.looigi.loovf.Soap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.looigi.loovf.DialogMessaggio;
import com.looigi.loovf.R;
import com.looigi.loovf.StrutturaFiles;
import com.looigi.loovf.Utility;
import com.looigi.loovf.VariabiliGlobali;
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
                    Utility.getInstance().PrendeUltimoMultimedia();
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

    public void RitornMultimediaDaId(final String Ritorno) {
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

                    if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
                        String[] Rit = Ritorno.split("§");
                        // ImageView vidView = VariabiliGlobali.getInstance().getImgPlayVideo();

                        String ImmVideo = VariabiliGlobali.getInstance().getPercorsoURL() + "/" + Rit[0].replace("\\", "/");

                        Picasso.get().load(ImmVideo).placeholder( R.drawable.progress_animation ).into(VariabiliGlobali.getInstance().getImgPlayVideo());

                        StrutturaFiles sf = new StrutturaFiles();
                        String[] c = Rit[1].split(";");
                        sf.setTipologia("2");
                        sf.setNomeFile(c[0].replace("***PV***",";"));
                        sf.setDimeFile(Long.parseLong(c[1]));
                        sf.setDataFile(c[2]);
                        sf.setCategoria(Integer.parseInt(c[3]));

                        VariabiliGlobali.getInstance().setVideoCaricato(sf);

                        // String vidAddress = sf.getNomeFile().replace("\\", "/");
                        // int Categoria = sf.getCategoria()-1;
                        // String sCategoria = VariabiliGlobali.getInstance().getCategorieVideo().get(Categoria);
                        // vidAddress = VariabiliGlobali.getInstance().getPercorsoURL() + "/" + sCategoria + "/" + vidAddress;

                        // Uri vidUri = Uri.parse(vidAddress);
                        // vidView.setVideoURI(vidUri);
//
                        // MediaController vidControl = new MediaController(VariabiliGlobali.getInstance().getContext());
                        // vidControl.setAnchorView(vidView);
                        // vidView.setMediaController(vidControl);
//
                        // vidView.start();

                        // VideoPlayer v = new VideoPlayer(VariabiliGlobali.getInstance().getContext(),
                        //         vidView,
                        //         VariabiliGlobali.getInstance().getPgrBar(),
                        //         vidAddress);

                        // Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                        // Uri data = Uri.parse(vidAddress);
                        // String estensione = vidAddress.substring(vidAddress.length()-3,vidAddress.length());
//
                        // intent.setDataAndType(data, "video/" + estensione);
                        // VariabiliGlobali.getInstance().getContext().startActivity(intent);

                        // Utility.getInstance().LoadVideo(vidView, vidAddress, sf.getNomeFile(), sCategoria, sf.getDimeFile());
                    } else {
                        StrutturaFiles sf = new StrutturaFiles();

                        String[] Rit = Ritorno.split("§");

                        String[] c = Rit[1].split(";");
                        sf.setTipologia("1");
                        sf.setNomeFile(c[0].replace("***PV***",";"));
                        sf.setDimeFile(Long.parseLong(c[1]));
                        sf.setDataFile(c[2]);
                        sf.setCategoria(Integer.parseInt(c[3]));
                        VariabiliGlobali.getInstance().setImmagineCaricata(sf);

                        String NomeImmagine = sf.getNomeFile().replace("\\", "/");
                        int Categoria = sf.getCategoria()-1;
                        String sCategoria = VariabiliGlobali.getInstance().getCategorieImmagini().get(Categoria);
                        NomeImmagine = VariabiliGlobali.getInstance().getPercorsoURL() + "/" + sCategoria + "/" + NomeImmagine;
                        NomeImmagine = NomeImmagine.replace(" ","%20");

                        ImageView mImageView = VariabiliGlobali.getInstance().getiView();

                        Picasso.get().load(NomeImmagine).placeholder( R.drawable.progress_animation ).into(mImageView);
                    }

                    Utility.getInstance().ScriveInformazioni();
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
}
