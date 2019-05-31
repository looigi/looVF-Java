package com.looigi.loovf;


import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.VideoView;

import com.looigi.loovf.Soap.DBRemoto;
import com.looigi.loovf.db_locale.db_dati;

import java.util.List;

import okhttp3.internal.Util;

public class MainActivity extends AppCompatActivity {
    private Runnable runRiga;
    private Handler hSelezionaRiga;
    private LinearLayout layContenitore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        Permessi p=new Permessi();
        p.ControllaPermessi(this);

        VariabiliGlobali.getInstance().setContext(this);
        VariabiliGlobali.getInstance().setFragmentActivityPrincipale(this);

        // VariabiliGlobali.getInstance().setCaricataPagina(false);
        // if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this)) {
        //     DialogMessaggio.getInstance().show(VariabiliGlobali.getInstance().getContext(),
        //             "Vitamio library doesn't setup properly!",
        //             true,
        //             "looVF",
        //             false);
        //     return;
        // }

        db_dati db = new db_dati();
        db.CreazioneTabelle();

        // mediaPlayer = new MediaPlayer();
        // mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        // btn.setOnClickListener(pausePlay);

        // Spinner sItems = (Spinner) findViewById(R.id.spnCategorie);
        VariabiliGlobali.getInstance().setsItems((Spinner) findViewById(R.id.spnCategorie));

        LinearLayout laySettingsPanel = findViewById(R.id.laySettingsPanel);
        ImageView mImageIndietro = findViewById(R.id.imgIndietro);
        mImageIndietro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utility.getInstance().IndietreggiaMultimedia();
            }
        });

        ImageView mImageAvanti = findViewById(R.id.imgAvanti);
        mImageAvanti.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utility.getInstance().AvanzaMultimedia();
            }
        });

        ImageView mImageScegliCategoria = findViewById(R.id.imgCambiaCategoria);
        mImageScegliCategoria.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String item = VariabiliGlobali.getInstance().getsItems().getSelectedItem().toString();;

                if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
                    VariabiliGlobali.getInstance().setCategoriaSceltaVideo(item);
                } else {
                    VariabiliGlobali.getInstance().setCategoriaSceltaImmagine(item);
                }

                Utility.getInstance().AvanzaMultimedia();
            }
        });

        if (!VariabiliGlobali.getInstance().isCaricataPagina()) {
            VariabiliGlobali.getInstance().setModalita("PHOTO");
        }

        // VariabiliGlobali.getInstance().setvView((VideoView) findViewById(R.id.myVideo));
        VariabiliGlobali.getInstance().setImgPlayVideo((ImageView) findViewById(R.id.imgPlayVideo));
        VariabiliGlobali.getInstance().setiView((ImageView) findViewById(R.id.imgImmagine));

        VariabiliGlobali.getInstance().setLaySettings(laySettingsPanel);
        laySettingsPanel.setVisibility(LinearLayout.GONE);
        VariabiliGlobali.getInstance().getiView().setVisibility(LinearLayout.GONE);
        VariabiliGlobali.getInstance().getImgPlayVideo().setVisibility(LinearLayout.GONE);
        mImageAvanti.setVisibility(LinearLayout.VISIBLE);
        mImageIndietro.setVisibility(LinearLayout.VISIBLE);

        layContenitore = findViewById(R.id.layContenitore);
        ImageView imgLinguetta = findViewById(R.id.imgLinguetta);
        imgLinguetta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (VariabiliGlobali.getInstance().isLinguettaAperta()) {
                    VariabiliGlobali.getInstance().setLinguettaAperta(false);
                    layContenitore.setVisibility(LinearLayout.GONE);
                } else {
                    VariabiliGlobali.getInstance().setLinguettaAperta(true);
                    layContenitore.setVisibility(LinearLayout.VISIBLE);
                }
            }
        });

        // VariabiliGlobali.getInstance().setDatiCaricati(false);
        GestioneFiles.getInstance().CreaCartella(VariabiliGlobali.getInstance().getPercorsoDIR());
        // File f = new File(VariabiliGlobali.getInstance().getPercorsoDIR() + "/Lista.dat");
        // if (f.exists()) {
            // String Ritorno = GestioneFiles.getInstance().LeggeFileDiTesto(VariabiliGlobali.getInstance().getPercorsoDIR() + "/Lista.dat");
            // Utility.getInstance().CreaListaFiles(Ritorno);
            // f.delete();

        //     CaricamentoDati cd = new CaricamentoDati();
        //     cd.IniziaCaricamento();
        // }

        VariabiliGlobali.getInstance().getImgPlayVideo().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                StrutturaFiles sf = VariabiliGlobali.getInstance().getVideoCaricato();

                String vidAddress = sf.getNomeFile().replace("\\", "/");
                int Categoria = sf.getCategoria()-1;
                String sCategoria = VariabiliGlobali.getInstance().getCategorieVideo().get(Categoria);
                vidAddress = VariabiliGlobali.getInstance().getPercorsoURL() + "/" + sCategoria + "/" + vidAddress;

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                Uri data = Uri.parse(vidAddress);
                String estensione = vidAddress.substring(vidAddress.length()-3,vidAddress.length());

                intent.setDataAndType(data, "video/" + estensione);
                VariabiliGlobali.getInstance().getContext().startActivity(intent);
            }
        });

        LinearLayout layPhoto = findViewById(R.id.layPhoto);
        layPhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!VariabiliGlobali.getInstance().getModalita().equals("PHOTO")) {
                    VariabiliGlobali.getInstance().setModalita("PHOTO");

                    VariabiliGlobali.getInstance().getImgPlayVideo().setVisibility(LinearLayout.GONE);
                    VariabiliGlobali.getInstance().getLaySettings().setVisibility(LinearLayout.GONE);
                    VariabiliGlobali.getInstance().getiView().setVisibility(LinearLayout.VISIBLE);

                    Utility.getInstance().riempieSpinner(false);

                    if (VariabiliGlobali.getInstance().getUltimoRitornoImmagine().isEmpty()) {
                        Utility.getInstance().PrendeUltimoMultimedia();
                    } else {
                        Utility.getInstance().ScriveInformazioni();
                        Utility.getInstance().VisualizzaMultimedia(VariabiliGlobali.getInstance().getUltimoRitornoImmagine());
                    }
                }
            }
        });

        LinearLayout layVideo = findViewById(R.id.layVideo);
        layVideo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
                    VariabiliGlobali.getInstance().setModalita("VIDEO");

                    VariabiliGlobali.getInstance().getImgPlayVideo().setVisibility(LinearLayout.VISIBLE);
                    VariabiliGlobali.getInstance().getLaySettings().setVisibility(LinearLayout.GONE);
                    VariabiliGlobali.getInstance().getiView().setVisibility(LinearLayout.GONE);

                    Utility.getInstance().riempieSpinner(false);

                    if (VariabiliGlobali.getInstance().getUltimoRitornoVideo().isEmpty()) {
                        Utility.getInstance().PrendeUltimoMultimedia();
                    } else {
                        Utility.getInstance().ScriveInformazioni();
                        Utility.getInstance().VisualizzaMultimedia(VariabiliGlobali.getInstance().getUltimoRitornoVideo());
                    }
                }
            }
        });

        LinearLayout laySettings = findViewById(R.id.laySettings);
        laySettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VariabiliGlobali.getInstance().getImgPlayVideo().setVisibility(LinearLayout.GONE);
                VariabiliGlobali.getInstance().getLaySettings().setVisibility(LinearLayout.VISIBLE);
                VariabiliGlobali.getInstance().getiView().setVisibility(LinearLayout.GONE);

                Utility.getInstance().ScriveInformazioni();
            }
        });

        CheckBox chkRandom = findViewById(R.id.chkRandom);
        chkRandom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
        VariabiliGlobali.getInstance().setChkRandom(chkRandom);

        LinearLayout layRefresh = findViewById(R.id.layRefresh);
        layRefresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogMessaggio.getInstance().show(VariabiliGlobali.getInstance().getContext(),
                        "Si vogliono riscaricare tutti i dati ?",
                        false,
                    "looVF",
                        true);
            }
        });

        LinearLayout layAbout = findViewById(R.id.layAbout);
        layAbout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });

        TextView txtInfo = findViewById(R.id.txtInfo);
        VariabiliGlobali.getInstance().setTxtInfo(txtInfo);

        // if (VariabiliGlobali.getInstance().isDatiCaricati()) {
        //     mImageIndietro.setVisibility(LinearLayout.VISIBLE);
        //     mImageAvanti.setVisibility(LinearLayout.VISIBLE);
        //     VariabiliGlobali.getInstance().getvView().setVisibility(LinearLayout.VISIBLE);
        //     VariabiliGlobali.getInstance().getiView().setVisibility(LinearLayout.VISIBLE);
        // } else {
        //     mImageIndietro.setVisibility(LinearLayout.GONE);
        //     mImageAvanti.setVisibility(LinearLayout.GONE);
        //     VariabiliGlobali.getInstance().getvView().setVisibility(LinearLayout.GONE);
        //     VariabiliGlobali.getInstance().getiView().setVisibility(LinearLayout.GONE);
        // }

        if (!VariabiliGlobali.getInstance().isCaricataPagina()) {
            TextView t = VariabiliGlobali.getInstance().getTxtInfo();
            t.setText("");

            DBRemoto dbr = new DBRemoto();
            dbr.RitornaCategorie();
            dbr.RitornaQuantiFilesPhoto();
            dbr.RitornaQuantiFilesVideo();

            Utility.getInstance().riempieSpinner(true);

            VariabiliGlobali.getInstance().setLinguettaAperta(true);
            ChiudeLinguetta();
        } else {
            Utility.getInstance().riempieSpinner(false);

            Utility.getInstance().ScriveInformazioni();
            if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
                Utility.getInstance().VisualizzaMultimedia(VariabiliGlobali.getInstance().getUltimoRitornoVideo());
            } else {
                Utility.getInstance().VisualizzaMultimedia(VariabiliGlobali.getInstance().getUltimoRitornoImmagine());
            }
        }

        // MediaController mc = new MediaController(this);
        // VideoView vidView = VariabiliGlobali.getInstance().getvView();
        // vidView.setMediaController(mc);
        // String str = "http://looigi.no-ip.biz:12345/looVF/Filmatini/Rows/Gyn/Speculum/DoppioSpeculumPerforante.avi";
        // Uri uri = Uri.parse(str);
        // vidView.setVideoURI(uri);
        // vidView.requestFocus();
        // vidView.start();
    }

    private void ChiudeLinguetta() {
        hSelezionaRiga = new Handler(Looper.getMainLooper());
        hSelezionaRiga.postDelayed(runRiga = new Runnable() {
            @Override
            public void run() {
                hSelezionaRiga.removeCallbacks(runRiga);
                runRiga = null;

                layContenitore.setVisibility(LinearLayout.GONE);
                VariabiliGlobali.getInstance().setLinguettaAperta(false);
            }
        },5000);
    }
}
