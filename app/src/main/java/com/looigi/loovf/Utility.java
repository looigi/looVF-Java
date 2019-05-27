package com.looigi.loovf;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uk.co.senab.photoview.PhotoViewAttacher;

public class Utility {
    private static final Utility ourInstance = new Utility();

    public static Utility getInstance() {
        return ourInstance;
    }

    private Utility() {
    }

    public String PrendeErroreDaException(Exception e) {
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));

        return TransformError(errors.toString());
    }

    private String TransformError(String error) {
        String Return=error;

        if (Return.length()>250) {
            Return=Return.substring(0,247)+"...";
        }
        Return=Return.replace("\n"," ");

        return Return;
    }

    public void CreaListaFiles(String Ritorno) {
        List<StrutturaFiles> listaVideo= new ArrayList<>();
        List<StrutturaFiles> listaImmagini= new ArrayList<>();
        List<String> categoriaVideo= new ArrayList<>();
        List<String> categoriaImmagini= new ArrayList<>();

        String[] c = Ritorno.split("§");
        for (String cc: c) {
            String[] ccc = cc.split(";");
            switch (ccc[0].toUpperCase().trim()) {
                case "CATEGORIAVIDEO":
                    categoriaVideo.add(ccc[1]);
                    break;
                case "CATEGORIAIMMAGINI":
                    categoriaImmagini.add(ccc[1]);
                    break;
                case "PIC":
                    StrutturaFiles sfP = new StrutturaFiles();
                    sfP.setCategoria(Integer.parseInt(ccc[0]));
                    sfP.setTipologia(ccc[1]);
                    sfP.setNomeFile(ccc[2]);
                    sfP.setDimeFile(Long.parseLong(ccc[3]));
                    sfP.setDataFile(Date.valueOf(ccc[4]));

                    listaVideo.add(sfP);
                    break;
                case "VIDEO":
                    StrutturaFiles sfV = new StrutturaFiles();
                    sfV.setCategoria(Integer.parseInt(ccc[0]));
                    sfV.setTipologia(ccc[1]);
                    sfV.setNomeFile(ccc[2]);
                    sfV.setDimeFile(Long.parseLong(ccc[3]));
                    sfV.setDataFile(Date.valueOf(ccc[4]));

                    listaImmagini.add(sfV);
                    break;
            }
        }

        VariabiliGlobali.getInstance().setListaVideo(listaVideo);
        VariabiliGlobali.getInstance().setListaImmagini(listaImmagini);
        VariabiliGlobali.getInstance().setCategoriaImmagini(categoriaImmagini);
        VariabiliGlobali.getInstance().setCategoriaVideo(categoriaVideo);
    }

    public void ScriveInformazioni() {
        TextView t = VariabiliGlobali.getInstance().getTxtInfo();
        if (VariabiliGlobali.getInstance().isDatiCaricati()) {
            if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
                t.setText("Video caricati: " + Integer.toString(VariabiliGlobali.getInstance().getListaVideo().size()-1) +
                        " - Visualizzato: " + Integer.toString(VariabiliGlobali.getInstance().getVideoVisualizzato()));
            } else {
                t.setText("Immagini caricate: " + Integer.toString(VariabiliGlobali.getInstance().getListaImmagini().size()-1) +
                        " - Visualizzata: " + Integer.toString(VariabiliGlobali.getInstance().getImmagineVisualizzata()));
            }
        } else {
            t.setText("Nessun dato caricato");
        }
    }

    public void IndietreggiaMultimedia() {
        if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
            int Quale = VariabiliGlobali.getInstance().getIndiceVideo()-1;
            if (Quale >= 0) {
                VariabiliGlobali.getInstance().setIndiceVideo(Quale);
                VariabiliGlobali.getInstance().setVideoVisualizzato(VariabiliGlobali.getInstance().getVideoVisualizzati().get(Quale));
            }
        } else {
            int Quale = VariabiliGlobali.getInstance().getIndiceImmagine()-1;
            if (Quale >= 0) {
                VariabiliGlobali.getInstance().setIndiceImmagine(Quale);
                VariabiliGlobali.getInstance().setImmagineVisualizzata(VariabiliGlobali.getInstance().getImmaginiVisualizzate().get(Quale));
            }
        }

        ScriveInformazioni();
        CaricaMultimedia();
    }

    public void AvanzaMultimedia() {
        if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
            if (VariabiliGlobali.getInstance().getIndiceVideo() < VariabiliGlobali.getInstance().getVideoVisualizzati().size()-1) {
                int Quale = VariabiliGlobali.getInstance().getIndiceVideo()+1;
                VariabiliGlobali.getInstance().setIndiceVideo(Quale);
                VariabiliGlobali.getInstance().setVideoVisualizzato(VariabiliGlobali.getInstance().getVideoVisualizzati().get(Quale));
            } else {
                int QuantiVideo = VariabiliGlobali.getInstance().getListaVideo().size() - 1;
                if (VariabiliGlobali.getInstance().getChkRandom().isChecked()) {
                    Random r = new Random();
                    VariabiliGlobali.getInstance().setVideoVisualizzato(r.nextInt(QuantiVideo));
                } else {
                    int prossimo = VariabiliGlobali.getInstance().getVideoVisualizzato();
                    prossimo++;
                    VariabiliGlobali.getInstance().setVideoVisualizzato(prossimo);
                }
                VariabiliGlobali.getInstance().getVideoVisualizzati().add(VariabiliGlobali.getInstance().getVideoVisualizzato());
                VariabiliGlobali.getInstance().setIndiceVideo(VariabiliGlobali.getInstance().getVideoVisualizzati().size() - 1);
            }
        } else {
            if (VariabiliGlobali.getInstance().getIndiceImmagine() < VariabiliGlobali.getInstance().getImmaginiVisualizzate().size()-1) {
                int Quale = VariabiliGlobali.getInstance().getIndiceImmagine()+1;
                VariabiliGlobali.getInstance().setIndiceImmagine(Quale);
                VariabiliGlobali.getInstance().setImmagineVisualizzata(VariabiliGlobali.getInstance().getImmaginiVisualizzate().get(Quale));
            } else {
                int QuanteImmagini = VariabiliGlobali.getInstance().getListaImmagini().size() - 1;
                if (VariabiliGlobali.getInstance().getChkRandom().isChecked()) {
                    Random r = new Random();
                    VariabiliGlobali.getInstance().setImmagineVisualizzata(r.nextInt(QuanteImmagini));
                } else {
                    int prossima = VariabiliGlobali.getInstance().getImmagineVisualizzata();
                    prossima++;
                    VariabiliGlobali.getInstance().setImmagineVisualizzata(prossima);
                }
                VariabiliGlobali.getInstance().getImmaginiVisualizzate().add(VariabiliGlobali.getInstance().getImmagineVisualizzata());
                VariabiliGlobali.getInstance().setIndiceImmagine(VariabiliGlobali.getInstance().getImmaginiVisualizzate().size() - 1);
            }
        }

        ScriveInformazioni();
        CaricaMultimedia();
    }

    public void CaricaMultimedia() {
        if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
            VideoView vidView = VariabiliGlobali.getInstance().getvView();

            int Quale = VariabiliGlobali.getInstance().getVideoVisualizzato();
            StrutturaFiles sf = VariabiliGlobali.getInstance().getListaVideo().get(Quale);
            String vidAddress = sf.getNomeFile();

            Uri vidUri = Uri.parse(vidAddress);
            vidView.setVideoURI(vidUri);

            MediaController vidControl = new MediaController(VariabiliGlobali.getInstance().getContext());
            vidControl.setAnchorView(vidView);
            vidView.setMediaController(vidControl);

            // vidView.start();
        } else {
            int Quale = VariabiliGlobali.getInstance().getImmagineVisualizzata();
            StrutturaFiles sf = VariabiliGlobali.getInstance().getListaImmagini().get(Quale);
            String NomeImmagine = sf.getNomeFile();

            ImageView mImageView = VariabiliGlobali.getInstance().getiView();
            mImageView.setImageBitmap(BitmapFactory.decodeFile(NomeImmagine));

            PhotoViewAttacher photoAttacher;
            photoAttacher= new PhotoViewAttacher(mImageView);
            photoAttacher.update();
        }
    }

}
