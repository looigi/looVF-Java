package com.looigi.loovf;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.looigi.loovf.Soap.DBRemoto;
import com.looigi.loovf.db_locale.db_dati;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Random;

public class Utility {
    private static final Utility ourInstance = new Utility();

    public static Utility getInstance() {
        return ourInstance;
    }

    private int Contatore;

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

    public void ScriveInformazioni() {
        TextView t = VariabiliGlobali.getInstance().getTxtInfo();
        // if (VariabiliGlobali.getInstance().isDatiCaricati()) {
        String Caricati = "";
        String Visualizzato = "";
        String Visualizzate = "";
        String NomeFile = "";

        if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
            Caricati = "Video caricati: " + Long.toString(VariabiliGlobali.getInstance().getQuantiVideo());
            Visualizzato = "Visualizzato: " + Long.toString(VariabiliGlobali.getInstance().getVideoVisualizzato());
            Visualizzate = "Visti: " + Long.toString(VariabiliGlobali.getInstance().getIndiceVideo()) + "/" +
                    Long.toString(VariabiliGlobali.getInstance().getVideoVisualizzati().size()-1);
            if (VariabiliGlobali.getInstance().getVideoCaricato()!=null) {
                long Dime2 = VariabiliGlobali.getInstance().getVideoCaricato().getDimeFile();
                String[] tipo={"b.", "Kb.", "Mb.", "Gb."};
                int quale = 0;
                while (Dime2>1024) {
                    Dime2 /= 1024;
                    quale++;
                }

                NomeFile = "\n" + VariabiliGlobali.getInstance().getVideoCaricato().getNomeFile();
                NomeFile += " dim: " + Long.toString(Dime2) + " " + tipo[quale];
                NomeFile += " data: " + VariabiliGlobali.getInstance().getVideoCaricato().getDataFile();

                VariabiliGlobali.getInstance().getImgPlayVideo().setVisibility(LinearLayout.VISIBLE);
                VariabiliGlobali.getInstance().getLaySettings().setVisibility(LinearLayout.GONE);
                VariabiliGlobali.getInstance().getiView().setVisibility(LinearLayout.GONE);
            }

            t.setText(Caricati + " - " + Visualizzato + NomeFile + "\n" + Visualizzate);
        } else {
            Caricati = "Immagini caricate: " + Long.toString(VariabiliGlobali.getInstance().getQuanteImmagini());
            Visualizzato = "Visualizzata: " + Long.toString(VariabiliGlobali.getInstance().getImmagineVisualizzata());
            Visualizzate = "Viste: " + Long.toString(VariabiliGlobali.getInstance().getIndiceImmagine()) + "/" +
                    Long.toString(VariabiliGlobali.getInstance().getImmaginiVisualizzate().size()-1);
            if (VariabiliGlobali.getInstance().getImmagineCaricata()!=null) {
                long Dime2 = VariabiliGlobali.getInstance().getImmagineCaricata().getDimeFile();
                String[] tipo={"b.", "Kb.", "Mb.", "Gb."};
                int quale = 0;
                while (Dime2>1024) {
                    Dime2 /= 1024;
                    quale++;
                }

                NomeFile = "\n" + VariabiliGlobali.getInstance().getImmagineCaricata().getNomeFile();
                NomeFile += " dim: " + Long.toString(Dime2) + " " + tipo[quale];
                NomeFile += " data: " + VariabiliGlobali.getInstance().getImmagineCaricata().getDataFile();

                VariabiliGlobali.getInstance().getImgPlayVideo().setVisibility(LinearLayout.GONE);
                VariabiliGlobali.getInstance().getLaySettings().setVisibility(LinearLayout.GONE);
                VariabiliGlobali.getInstance().getiView().setVisibility(LinearLayout.VISIBLE);
            }

            t.setText(Caricati + " - " + Visualizzato + NomeFile + "\n" + Visualizzate);
        }
    }

    public void PrendeUltimoMultimedia()  {
        db_dati db = new db_dati();
        boolean CeUltima=false;

        db.LeggeTutteLeViste();

        if (VariabiliGlobali.getInstance().getModalita().equals("PHOTO")) {
            long ultimaImmVista = db.LeggeUltimaVista("PHOTO");
            if (ultimaImmVista > -1) {
                VariabiliGlobali.getInstance().setImmagineVisualizzata(ultimaImmVista);
            }
            CeUltima=true;
        } else {
            long ultimoVideoVisto = db.LeggeUltimaVista("VIDEO");
            if (ultimoVideoVisto > -1) {
                VariabiliGlobali.getInstance().setVideoVisualizzato(ultimoVideoVisto);
            }
            CeUltima=true;
        }
        if (CeUltima) {
            ScriveInformazioni();
            CaricaMultimedia();
        }
    }

    public void IndietreggiaMultimedia() {
        if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
            long Quale = VariabiliGlobali.getInstance().getIndiceVideo()-1;
            if (Quale >= 0) {
                VariabiliGlobali.getInstance().setIndiceVideo(Quale);
                VariabiliGlobali.getInstance().setVideoVisualizzato(VariabiliGlobali.getInstance().getVideoVisualizzati().get((int) Quale));
            }
        } else {
            long Quale = VariabiliGlobali.getInstance().getIndiceImmagine()-1;
            if (Quale >= 0) {
                VariabiliGlobali.getInstance().setIndiceImmagine(Quale);
                VariabiliGlobali.getInstance().setImmagineVisualizzata(VariabiliGlobali.getInstance().getImmaginiVisualizzate().get((int) Quale));
            }
        }

        ScriveInformazioni();
        CaricaMultimedia();
    }

    public void AvanzaMultimedia() {
        db_dati db = new db_dati();
        boolean Ok = false;

        if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
            if (VariabiliGlobali.getInstance().getIndiceVideo() < VariabiliGlobali.getInstance().getVideoVisualizzati().size()-1) {
                long Quale = VariabiliGlobali.getInstance().getIndiceVideo()+1;
                VariabiliGlobali.getInstance().setIndiceVideo(Quale);
                VariabiliGlobali.getInstance().setVideoVisualizzato(VariabiliGlobali.getInstance().getVideoVisualizzati().get((int) Quale));
                Ok = true;
            } else {
                if (VariabiliGlobali.getInstance().getChkRandom().isChecked()) {
                    // Random r = new Random();
                    // long prossimo = r.nextInt((int) VariabiliGlobali.getInstance().getQuantiVideo());
                    // VariabiliGlobali.getInstance().setVideoVisualizzato(prossimo);
//
                    // db.ScriveVisti(Long.toString(prossimo), VariabiliGlobali.getInstance().getModalita());
                    DBRemoto dbr = new DBRemoto();
                    dbr.RitornaSuccessivoMultimedia();
                } else {
                    long prossimo = VariabiliGlobali.getInstance().getVideoVisualizzato();
                    prossimo++;
                    VariabiliGlobali.getInstance().setVideoVisualizzato(prossimo);

                    db.ScriveVisti(Long.toString(prossimo), VariabiliGlobali.getInstance().getModalita());

                    VariabiliGlobali.getInstance().getVideoVisualizzati().add(VariabiliGlobali.getInstance().getVideoVisualizzato());
                    VariabiliGlobali.getInstance().setIndiceVideo(VariabiliGlobali.getInstance().getVideoVisualizzati().size() - 1);
                    Ok = true;
                }
            }
        } else {
            if (VariabiliGlobali.getInstance().getIndiceImmagine() < VariabiliGlobali.getInstance().getImmaginiVisualizzate().size()-1) {
                long Quale = VariabiliGlobali.getInstance().getIndiceImmagine()+1;
                VariabiliGlobali.getInstance().setIndiceImmagine(Quale);
                VariabiliGlobali.getInstance().setImmagineVisualizzata(VariabiliGlobali.getInstance().getImmaginiVisualizzate().get((int) Quale));
                Ok = true;
            } else {
                if (VariabiliGlobali.getInstance().getChkRandom().isChecked()) {
                    // Random r = new Random();
                    // int prossima = r.nextInt((int) VariabiliGlobali.getInstance().getQuanteImmagini());
                    // VariabiliGlobali.getInstance().setImmagineVisualizzata(prossima);
//
                    // db.ScriveVisti(Integer.toString(prossima), VariabiliGlobali.getInstance().getModalita());
                    DBRemoto dbr = new DBRemoto();
                    dbr.RitornaSuccessivoMultimedia();
                } else {
                    long prossima = VariabiliGlobali.getInstance().getImmagineVisualizzata();
                    prossima++;
                    VariabiliGlobali.getInstance().setImmagineVisualizzata(prossima);

                    db.ScriveVisti(Long.toString(prossima), VariabiliGlobali.getInstance().getModalita());

                    VariabiliGlobali.getInstance().getImmaginiVisualizzate().add(VariabiliGlobali.getInstance().getImmagineVisualizzata());
                    VariabiliGlobali.getInstance().setIndiceImmagine(VariabiliGlobali.getInstance().getImmaginiVisualizzate().size() - 1);
                    Ok = true;
                }
            }
        }

        if (Ok) {
            ScriveInformazioni();
            CaricaMultimedia();
        }
    }

    public void CaricaMultimedia() {
        db_dati db = new db_dati();
        DBRemoto dbr = new DBRemoto();

        if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
            long Quale = VariabiliGlobali.getInstance().getVideoVisualizzato();
            dbr.RitornaMultimediaDaID(VariabiliGlobali.getInstance().getModalita(), Long.toString(Quale));
        } else {
            long Quale = VariabiliGlobali.getInstance().getImmagineVisualizzata();
            dbr.RitornaMultimediaDaID(VariabiliGlobali.getInstance().getModalita(), Long.toString(Quale));
        }
    }

    /* public void LoadVideo(final VideoView mVideoView, String videoUrl, String Titolo, String Categoria, long Dime) {
        // final ProgressDialog pDialog = new ProgressDialog(VariabiliGlobali.getInstance().getContext());

        // Set progressbar message
        long Dime2 = Dime;
        String[] tipo={"b.", "Kb.", "Mb.", "Gb."};
        int quale = 0;
        while (Dime2>1024) {
            Dime2 /= 1024;
            quale++;
        }

        String Testo = "Buffering...\n"+Titolo+"\nCategoria: "+Categoria+"\nSize: "+Long.toString(Dime2) + " " + tipo[quale];
        VariabiliGlobali.getInstance().getTxtCaricamento().setText(Testo);
        VariabiliGlobali.getInstance().getLayCaricamento().setVisibility(LinearLayout.VISIBLE);
        // pDialog.setMessage("Buffering...\n"+Titolo+"\nCategoria: "+Categoria+"\nSize: "+Long.toString(Dime2) + " " + tipo[quale]);
        // pDialog.setIndeterminate(false);
        // pDialog.setCancelable(false);
        // Show progressbar
        // pDialog.show();

        VariabiliGlobali.getInstance().getPgrBar().setIndeterminate(true);
        VariabiliGlobali.getInstance().getPgrBar().setMax(100);
        VariabiliGlobali.getInstance().getPgrBar().setProgress(0);
        Contatore = 0;
//
        VariabiliGlobali.getInstance().sethSpinner(new Handler(Looper.getMainLooper()));
        VariabiliGlobali.getInstance().setRunSpinner ( new Runnable() {
            @Override
            public void run() {
                Contatore++;
                if (Contatore>100) {
                    Contatore=0;
                }
                VariabiliGlobali.getInstance().getPgrBar().setProgress(0);
                VariabiliGlobali.getInstance().gethSpinner().postDelayed(VariabiliGlobali.getInstance().getRunSpinner(),100);
            }
        });
        VariabiliGlobali.getInstance().gethSpinner().postDelayed(VariabiliGlobali.getInstance().getRunSpinner(),100);

        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(VariabiliGlobali.getInstance().getContext());
            mediacontroller.setMediaPlayer(mVideoView);
            mediacontroller.setAnchorView(mVideoView);

            Uri videoUri = Uri.parse(videoUrl.replace(" ","%20"));
            mVideoView.setMediaController(mediacontroller);
            mVideoView.setVideoURI(videoUri);
        } catch (Exception e) {
            VariabiliGlobali.getInstance().gethSpinner().removeCallbacks(VariabiliGlobali.getInstance().getRunSpinner());
            VariabiliGlobali.getInstance().setRunSpinner(null);

            VariabiliGlobali.getInstance().getLayCaricamento().setVisibility(LinearLayout.GONE);
            DialogMessaggio.getInstance().show(VariabiliGlobali.getInstance().getContext(),
                    Utility.getInstance().PrendeErroreDaException(e),
                    true, "looVF", false);
        }

        mVideoView.requestFocus();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                VariabiliGlobali.getInstance().gethSpinner().removeCallbacks(VariabiliGlobali.getInstance().getRunSpinner());
                VariabiliGlobali.getInstance().setRunSpinner(null);

                VariabiliGlobali.getInstance().getLayCaricamento().setVisibility(LinearLayout.GONE);
                mVideoView.start();
                // pDialog.dismiss();
            }
        });
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                VariabiliGlobali.getInstance().gethSpinner().removeCallbacks(VariabiliGlobali.getInstance().getRunSpinner());
                VariabiliGlobali.getInstance().setRunSpinner(null);

                VariabiliGlobali.getInstance().getLayCaricamento().setVisibility(LinearLayout.GONE);
                // if (pDialog.isShowing()) {
                //     pDialog.dismiss();
                // }
                // finish();
            }
        });
    } */
}
