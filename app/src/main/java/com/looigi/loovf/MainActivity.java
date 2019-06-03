package com.looigi.loovf;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.looigi.loovf.Soap.DBRemoto;
import com.looigi.loovf.db_locale.db_dati;
import com.looigi.loovf.griglia.AdapterRecyclerView;
import com.looigi.loovf.griglia.ImageListForRecycler;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Runnable runRiga;
    private Handler hSelezionaRiga;
    private LinearLayout layContenitore;
    private db_dati db;

    @SuppressLint("ClickableViewAccessibility")
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

        final GestureDetector gdt = new GestureDetector(new GestureListener());

        // VariabiliGlobali.getInstance().setCaricataPagina(false);
        // if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this)) {
        //     DialogMessaggio.getInstance().show(VariabiliGlobali.getInstance().getContext(),
        //             "Vitamio library doesn't setup properly!",
        //             true,
        //             "looVF",
        //             false);
        //     return;
        // }

        db = new db_dati();
        db.CreazioneTabelle();

        StrutturaConfig sc = db.CaricaConfigurazione();
        if (sc != null) {
            VariabiliGlobali.getInstance().setConfigurazione(sc);
        } else {
            sc = new StrutturaConfig();
            sc.setRandom(true);
            sc.setUltimaCategoriaImmagini("Tutto");
            sc.setUltimaCategoriaVideo("Tutto");
            VariabiliGlobali.getInstance().setConfigurazione(sc);
            String Ritorno = db.ScriveConfigurazione();
            if (!Ritorno.isEmpty()) {
                DialogMessaggio.getInstance().show(this,
                        "ERRORE nel salvataggio della configurazione\n" + Ritorno,
                        true,
                        "looVF",
                        false);
            }
        }

        final CheckBox chkRandom = findViewById(R.id.chkRandom);
        chkRandom.setChecked(VariabiliGlobali.getInstance().getConfigurazione().isRandom());
        chkRandom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VariabiliGlobali.getInstance().getConfigurazione().setRandom(chkRandom.isChecked());
                String Ritorno = db.ScriveConfigurazione();
                if (!Ritorno.isEmpty()) {
                    DialogMessaggio.getInstance().show(VariabiliGlobali.getInstance().getContext(),
                            "ERRORE nel salvataggio della configurazione\n" + Ritorno,
                            true,
                            "looVF",
                            false);
                }
            }
        });

        // mediaPlayer = new MediaPlayer();
        // mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        // btn.setOnClickListener(pausePlay);

        // Spinner sItems = (Spinner) findViewById(R.id.spnCategorie);
        VariabiliGlobali.getInstance().setsItems((Spinner) findViewById(R.id.spnCategorie));

        LinearLayout laySettingsPanel = findViewById(R.id.laySettingsPanel);
        // ImageView mImageIndietro = findViewById(R.id.imgIndietro);
        // mImageIndietro.setOnClickListener(new View.OnClickListener() {
        //     public void onClick(View v) {
        //         Utility.getInstance().IndietreggiaMultimedia();
        //     }
        // });
//
        // ImageView mImageAvanti = findViewById(R.id.imgAvanti);
        // mImageAvanti.setOnClickListener(new View.OnClickListener() {
        //     public void onClick(View v) {
        //         Utility.getInstance().AvanzaMultimedia();
        //     }
        // });

        Button btnCompatta = findViewById(R.id.cmdCompatta);
        btnCompatta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                db.CompattaDB();
                DialogMessaggio.getInstance().show(VariabiliGlobali.getInstance().getContext(),
                        "DB Compattato",
                        false,
                        "looVF",
                        false);
            }
        });

        ImageView mImageScegliCategoria = findViewById(R.id.imgCambiaCategoria);
        mImageScegliCategoria.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String item = VariabiliGlobali.getInstance().getsItems().getSelectedItem().toString();

                if (VariabiliGlobali.getInstance().getModalita().equals("GRIGLIA")) {
                    String idCategoria = Integer.toString(VariabiliGlobali.getInstance().getsItems().getSelectedItemPosition());
                    String Ritorno = db.RitornaRigheGriglia("1", idCategoria);

                    if (Ritorno.isEmpty()) {
                        DBRemoto dbr = new DBRemoto();
                        dbr.RitornaImmaginiGriglia();
                    } else {
                        Utility.getInstance().CaricaRecyclerView(Ritorno, false);
                    }
                } else {
                    if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
                        VariabiliGlobali.getInstance().getConfigurazione().setUltimaCategoriaVideo(item);
                    } else {
                        VariabiliGlobali.getInstance().getConfigurazione().setUltimaCategoriaImmagini(item);
                    }
                    String Ritorno = db.ScriveConfigurazione();
                    if (!Ritorno.isEmpty()) {
                        DialogMessaggio.getInstance().show(VariabiliGlobali.getInstance().getContext(),
                                "ERRORE nel salvataggio della configurazione\n" + Ritorno,
                                true,
                                "looVF",
                                false);
                    }

                    Utility.getInstance().AvanzaMultimedia();
                }
            }
        });

        if (!VariabiliGlobali.getInstance().isCaricataPagina()) {
            VariabiliGlobali.getInstance().setModalita("PHOTO");
        }

        // VariabiliGlobali.getInstance().setvView((VideoView) findViewById(R.id.myVideo));
        VariabiliGlobali.getInstance().setImgPlayVideo((ImageView) findViewById(R.id.imgPlayVideo));
        VariabiliGlobali.getInstance().setiView((ImageView) findViewById(R.id.imgImmagine));
        VariabiliGlobali.getInstance().setLayGriglia((LinearLayout) findViewById(R.id.layGriglia));

        VariabiliGlobali.getInstance().getImgPlayVideo().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                VariabiliGlobali.getInstance().setTastopremuto("VIDEO");
                gdt.onTouchEvent(event);
                return true;
            }
        });

        VariabiliGlobali.getInstance().getiView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                VariabiliGlobali.getInstance().setTastopremuto("PHOTO");
                gdt.onTouchEvent(event);
                return true;
            }
        });

        VariabiliGlobali.getInstance().setLaySettings(laySettingsPanel);
        laySettingsPanel.setVisibility(LinearLayout.GONE);

        VariabiliGlobali.getInstance().getiView().setVisibility(LinearLayout.GONE);
        VariabiliGlobali.getInstance().getImgPlayVideo().setVisibility(LinearLayout.GONE);
        VariabiliGlobali.getInstance().getLayGriglia().setVisibility(LinearLayout.GONE);

        // mImageAvanti.setVisibility(LinearLayout.VISIBLE);
        // mImageIndietro.setVisibility(LinearLayout.VISIBLE);

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

        // VariabiliGlobali.getInstance().getImgPlayVideo().setOnClickListener(new View.OnClickListener() {
        //     public void onClick(View v) {
        //         StrutturaFiles sf = VariabiliGlobali.getInstance().getVideoCaricato();
//
        //         String vidAddress = sf.getNomeFile().replace("\\", "/");
        //         int Categoria = sf.getCategoria()-1;
        //         String sCategoria = VariabiliGlobali.getInstance().getCategorieVideo().get(Categoria);
        //         vidAddress = VariabiliGlobali.getInstance().getPercorsoURL() + "/" + sCategoria + "/" + vidAddress;
//
        //         Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
        //         Uri data = Uri.parse(vidAddress);
        //         String estensione = vidAddress.substring(vidAddress.length()-3,vidAddress.length());
//
        //         intent.setDataAndType(data, "video/" + estensione);
        //         VariabiliGlobali.getInstance().getContext().startActivity(intent);
        //     }
        // });

        VariabiliGlobali.getInstance().setRecyclerView ((RecyclerView) findViewById(R.id.imageGallery));

        LinearLayout layGriglia = findViewById(R.id.layContGriglia);
        layGriglia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // if (!VariabiliGlobali.getInstance().getModalita().equals("GRIGLIA")) {
                    VariabiliGlobali.getInstance().setModalita("GRIGLIA");

                    VariabiliGlobali.getInstance().getImgPlayVideo().setVisibility(LinearLayout.GONE);
                    VariabiliGlobali.getInstance().getLaySettings().setVisibility(LinearLayout.GONE);
                    VariabiliGlobali.getInstance().getiView().setVisibility(LinearLayout.GONE);
                    VariabiliGlobali.getInstance().getLayGriglia().setVisibility(LinearLayout.VISIBLE);

                    Utility.getInstance().riempieSpinner();

                    String idCategoria = Integer.toString(Utility.getInstance().TornaIdCategoria(VariabiliGlobali.getInstance().getCategoriaSceltaImmagine()));
                    String Ritorno = db.RitornaRigheGriglia("1", idCategoria);

                    if (Ritorno.isEmpty()) {
                        DBRemoto dbr = new DBRemoto();
                        dbr.RitornaImmaginiGriglia();
                    } else {
                        Utility.getInstance().CaricaRecyclerView(Ritorno, false);
                    }
                // } else {

                // }
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
                    VariabiliGlobali.getInstance().getLayGriglia().setVisibility(LinearLayout.GONE);

                    Utility.getInstance().riempieSpinner();

                    if (VariabiliGlobali.getInstance().getUltimoRitornoImmagine().isEmpty()) {
                        Utility.getInstance().PrendeUltimoMultimedia(false);
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
                    VariabiliGlobali.getInstance().getLayGriglia().setVisibility(LinearLayout.GONE);

                    Utility.getInstance().riempieSpinner();

                    if (VariabiliGlobali.getInstance().getUltimoRitornoVideo().isEmpty()) {
                        Utility.getInstance().PrendeUltimoMultimedia(false);
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
                VariabiliGlobali.getInstance().getLayGriglia().setVisibility(LinearLayout.GONE);

                Utility.getInstance().ScriveInformazioni();
            }
        });

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

            Utility.getInstance().riempieSpinner();

            VariabiliGlobali.getInstance().setLinguettaAperta(true);
            ChiudeLinguetta();
        } else {
            Utility.getInstance().riempieSpinner();

            Utility.getInstance().ScriveInformazioni();
            if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
                Utility.getInstance().VisualizzaMultimedia(VariabiliGlobali.getInstance().getUltimoRitornoVideo());
            } else {
                if (VariabiliGlobali.getInstance().getModalita().equals("PHOTO")) {
                    Utility.getInstance().VisualizzaMultimedia(VariabiliGlobali.getInstance().getUltimoRitornoImmagine());
                }
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
