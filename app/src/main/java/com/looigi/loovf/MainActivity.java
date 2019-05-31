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

public class MainActivity extends AppCompatActivity {
    private Runnable runRiga;
    private Handler hSelezionaRiga;
    private LinearLayout layContenitore;
    private Spinner sItems;

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

        sItems = (Spinner) findViewById(R.id.spnCategorie);
        VariabiliGlobali.getInstance().setLayCaricamento((RelativeLayout) findViewById(R.id.layCaricamento));
        VariabiliGlobali.getInstance().setTxtCaricamento((TextView) findViewById(R.id.txtCaricamento));
        VariabiliGlobali.getInstance().setPgrBar((ProgressBar) findViewById(R.id.pgrBar));
        VariabiliGlobali.getInstance().getLayCaricamento().setVisibility(LinearLayout.GONE);

        final LinearLayout laySettingsPanel = findViewById(R.id.laySettingsPanel);
        final ImageView mImageIndietro = findViewById(R.id.imgIndietro);
        mImageIndietro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utility.getInstance().IndietreggiaMultimedia();
            }
        });

        final ImageView mImageAvanti = findViewById(R.id.imgAvanti);
        mImageAvanti.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utility.getInstance().AvanzaMultimedia();
            }
        });

        VariabiliGlobali.getInstance().setModalita("PHOTO");
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

                    if (VariabiliGlobali.getInstance().gethSpinner()!=null) {
                        VariabiliGlobali.getInstance().gethSpinner().removeCallbacks(VariabiliGlobali.getInstance().getRunSpinner());
                        VariabiliGlobali.getInstance().setRunSpinner(null);
                        VariabiliGlobali.getInstance().getLayCaricamento().setVisibility(LinearLayout.GONE);
                    }

                    riempieSpinner();

                    Utility.getInstance().PrendeUltimoMultimedia();
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

                    if (VariabiliGlobali.getInstance().gethSpinner()!=null) {
                        VariabiliGlobali.getInstance().gethSpinner().removeCallbacks(VariabiliGlobali.getInstance().getRunSpinner());
                        VariabiliGlobali.getInstance().setRunSpinner(null);
                        VariabiliGlobali.getInstance().getLayCaricamento().setVisibility(LinearLayout.GONE);
                    }

                    riempieSpinner();

                    Utility.getInstance().PrendeUltimoMultimedia();
                }
            }
        });

        LinearLayout laySettings = findViewById(R.id.laySettings);
        laySettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VariabiliGlobali.getInstance().getImgPlayVideo().setVisibility(LinearLayout.GONE);
                VariabiliGlobali.getInstance().getLaySettings().setVisibility(LinearLayout.VISIBLE);
                VariabiliGlobali.getInstance().getiView().setVisibility(LinearLayout.GONE);

                if (VariabiliGlobali.getInstance().gethSpinner()!=null) {
                    VariabiliGlobali.getInstance().gethSpinner().removeCallbacks(VariabiliGlobali.getInstance().getRunSpinner());
                    VariabiliGlobali.getInstance().setRunSpinner(null);
                    VariabiliGlobali.getInstance().getLayCaricamento().setVisibility(LinearLayout.GONE);
                }

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

        TextView t = VariabiliGlobali.getInstance().getTxtInfo();
        t.setText("");

        DBRemoto dbr = new DBRemoto();
        dbr.RitornaCategorie();
        dbr.RitornaQuantiFilesPhoto();
        dbr.RitornaQuantiFilesVideo();

        riempieSpinner();

        VariabiliGlobali.getInstance().setLinguettaAperta(true);
        ChiudeLinguetta();

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

    private void riempieSpinner() {
        List<String> spinnerArray;
        if (VariabiliGlobali.getInstance().getModalita().equals("PHOTO")) {
            spinnerArray = VariabiliGlobali.getInstance().getCategorieImmagini();
        } else {
            spinnerArray = VariabiliGlobali.getInstance().getCategorieVideo();
        }
        spinnerArray.add("Tutto");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sItems.setAdapter(adapter);
        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
                    VariabiliGlobali.getInstance().setCategoriaSceltaVideo(item);
                } else {
                    VariabiliGlobali.getInstance().setCategoriaSceltaImmagine(item);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
            if (!VariabiliGlobali.getInstance().getCategoriaSceltaVideo().isEmpty()) {
                int spinnerPosition = adapter.getPosition(VariabiliGlobali.getInstance().getCategoriaSceltaVideo());
                sItems.setSelection(spinnerPosition);
            }
        } else {
            if (!VariabiliGlobali.getInstance().getCategoriaSceltaImmagine().isEmpty()) {
                int spinnerPosition = adapter.getPosition(VariabiliGlobali.getInstance().getCategoriaSceltaImmagine());
                sItems.setSelection(spinnerPosition);
            }
        }
    }
}
