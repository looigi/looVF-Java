package com.looigi.loovf;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.looigi.loovf.Soap.DBRemoto;
import com.looigi.loovf.Soap.DownloadFileFromURL;
import com.looigi.loovf.db_locale.db_dati;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Runnable runRiga;
    private Handler hSelezionaRiga;
    private LinearLayout layContenitore;
    private db_dati db;
    private boolean CiSonoPermessi;
    protected PowerManager.WakeLock mWakeLock;

    @Override
    public void onBackPressed() {
        // boolean amm = VariabiliGlobali.getInstance().isAmministratore();
        // VariabiliGlobali.getInstance().setAmministratore(false);
        // VariabiliGlobali.getInstance().getConfigurazione().setVisuaTutto(false);
        // Utility.getInstance().AccendeSpegneOggettiInBaseAiPermessi();

        VariabiliGlobali.getInstance().setModalita("PHOTO");

        VariabiliGlobali.getInstance().getImgPlayVideo().setVisibility(LinearLayout.GONE);
        VariabiliGlobali.getInstance().getLaySettingsPanel().setVisibility(LinearLayout.GONE);
        VariabiliGlobali.getInstance().getiView().setVisibility(LinearLayout.VISIBLE);
        VariabiliGlobali.getInstance().getLayGriglia().setVisibility(LinearLayout.GONE);
        VariabiliGlobali.getInstance().getImgRefresh().setVisibility(LinearLayout.GONE);
        VariabiliGlobali.getInstance().getLayScroller().setVisibility(LinearLayout.VISIBLE);

        if (VariabiliGlobali.getInstance().getUltimoRitornoImmagine().isEmpty()) {
            Utility.getInstance().PrendeUltimoMultimedia(false);
        } else {
            Utility.getInstance().ScriveInformazioni();
            Utility.getInstance().VisualizzaMultimedia(VariabiliGlobali.getInstance().getUltimoRitornoImmagine());
        }

        Utility.getInstance().riempieSpinner();
        db_dati db = new db_dati();
        db.ScriveConfigurazione();
        // VariabiliGlobali.getInstance().setAmministratore(amm);

        ExitActivity.exitApplicationAndRemoveFromRecent(MainActivity.this);
        finish();

        super.onBackPressed();
    }

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
        CiSonoPermessi = p.ControllaPermessi(this);

        if (CiSonoPermessi) {
            EsegueEntrata();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (!CiSonoPermessi) {
            int index = 0;
            Map<String, Integer> PermissionsMap = new HashMap<String, Integer>();
            for (String permission : permissions) {
                PermissionsMap.put(permission, grantResults[index]);
                index++;
            }

            EsegueEntrata();
        }
    }

    private void EsegueEntrata() {
        PowerManager pm = (PowerManager) getSystemService(this.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "myapp:mywakelocktag");
        this.mWakeLock.acquire();

        VariabiliGlobali.getInstance().setContext(this);
        VariabiliGlobali.getInstance().setFragmentActivityPrincipale(this);

        InfoOnPhone i = new InfoOnPhone();
        String Device = i.getDeviceName();
        String User = i.getUser(this);
        String IMEI_IMSI = i.getIMEI();
        VariabiliGlobali.getInstance().setTipoTelefono(Device);
        VariabiliGlobali.getInstance().setUtenza(User);
        VariabiliGlobali.getInstance().setIMEI_IMSI(IMEI_IMSI);

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
        // db.PulisceDati();

        // StrutturaConfig sc = db.CaricaConfigurazione();
        // if (sc != null) {
        //     VariabiliGlobali.getInstance().setConfigurazione(sc);
        // } else {
        //     sc = new StrutturaConfig();
        //     sc.setRandom(true);
//
        //     StrutturaCategorie sc1 = new StrutturaCategorie();
        //     sc1.setIdCategoria(999);
        //     sc1.setNomeCategoria("Tutto");
        //     sc1.setPercorsoCategoria("");
        //     sc1.setProtetta(false);
//
        //     sc.setUltimaCategoriaImmagini(sc1);
        //     sc.setUltimaCategoriaVideo(sc1);
        //     VariabiliGlobali.getInstance().setConfigurazione(sc);
        //     String Ritorno = db.ScriveConfigurazione();
        //     if (!Ritorno.isEmpty()) {
        //         DialogMessaggio.getInstance().show(this,
        //                 "ERRORE nel salvataggio della configurazione\n" + Ritorno,
        //                 true,
        //                 "looVF",
        //                 false);
        //     }
        // }

        // SEZIONE RICERCA
        ImageView imgRicercaFuori = (ImageView) findViewById(R.id.imgRicercaFuori);
        imgRicercaFuori.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO") ||
                        VariabiliGlobali.getInstance().getModalita().equals("PHOTO")) {
                    VariabiliGlobali.getInstance().getLayRicerca().setVisibility(LinearLayout.VISIBLE);
                }
            }
        });
        VariabiliGlobali.getInstance().setLayRicerca((LinearLayout) findViewById(R.id.layRicerca));
        VariabiliGlobali.getInstance().getLayRicerca().setVisibility(LinearLayout.GONE);
        VariabiliGlobali.getInstance().setLstRicerca((ListView) findViewById(R.id.lstRicerca));
        final EditText edtRicerca = (EditText) findViewById(R.id.edtRicerca);
        ImageView imgRicerca = (ImageView) findViewById(R.id.imgRicerca);
        imgRicerca.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String ric = edtRicerca.getText().toString().trim();
                if (ric.isEmpty() || ric.length() < 3) {
                    DialogMessaggio.getInstance().show(VariabiliGlobali.getInstance().getContext(),
                            "Inserire un parametro di ricerca di almeno 3 caratteri",
                            true,
                            "looVF",
                            false,
                            "");
                } else {
                    StrutturaCategorie Categoria= new StrutturaCategorie();

                    if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
                        Categoria = VariabiliGlobali.getInstance().getConfigurazione().getUltimaCategoriaVideo();
                    } else {
                        if (VariabiliGlobali.getInstance().getModalita().equals("PHOTO")) {
                            Categoria = VariabiliGlobali.getInstance().getConfigurazione().getUltimaCategoriaImmagini();
                        }
                    }
                    if (!Categoria.getNomeCategoria().isEmpty()) {
                        DBRemoto dbr = new DBRemoto();
                        dbr.EffettuaRicerca(Categoria.getNomeCategoria(), ric);
                    }
                }
            }
        });
        ImageView imgUscitaRicerca = (ImageView) findViewById(R.id.imgUscitaRicerca);
        imgUscitaRicerca.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VariabiliGlobali.getInstance().getLayRicerca().setVisibility(LinearLayout.GONE);
            }
        });
        // SEZIONE RICERCA

        VariabiliGlobali.getInstance().setChkVisuaTutto((CheckBox) findViewById(R.id.chkVisualizzaTutto));
        VariabiliGlobali.getInstance().getChkVisuaTutto().setChecked(false);
        // VariabiliGlobali.getInstance().getChkVisuaTutto().setVisibility(LinearLayout.GONE);

        VariabiliGlobali.getInstance().getChkVisuaTutto().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VariabiliGlobali.getInstance().getConfigurazione().setVisuaTutto(VariabiliGlobali.getInstance().getChkVisuaTutto().isChecked());
                String Ritorno = db.ScriveConfigurazione();
                if (!Ritorno.isEmpty()) {
                    DialogMessaggio.getInstance().show(VariabiliGlobali.getInstance().getContext(),
                            "ERRORE nel salvataggio della configurazione\n" + Ritorno,
                            true,
                            "looVF",
                            false,
                            "");
                } else {
                    Utility.getInstance().AccendeSpegneOggettiInBaseAiPermessi();

                    if (!VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
                        VariabiliGlobali.getInstance().setModalita("PHOTO");

                        VariabiliGlobali.getInstance().getImgPlayVideo().setVisibility(LinearLayout.GONE);
                        VariabiliGlobali.getInstance().getLaySettingsPanel().setVisibility(LinearLayout.GONE);
                        VariabiliGlobali.getInstance().getiView().setVisibility(LinearLayout.VISIBLE);
                        VariabiliGlobali.getInstance().getLayGriglia().setVisibility(LinearLayout.GONE);
                        VariabiliGlobali.getInstance().getImgRefresh().setVisibility(LinearLayout.GONE);
                        VariabiliGlobali.getInstance().getLayScroller().setVisibility(LinearLayout.VISIBLE);

                        // Utility.getInstance().riempieSpinner();

                        if (VariabiliGlobali.getInstance().getUltimoRitornoImmagine().isEmpty()) {
                            Utility.getInstance().PrendeUltimoMultimedia(false);
                        } else {
                            Utility.getInstance().ScriveInformazioni();
                            Utility.getInstance().VisualizzaMultimedia(VariabiliGlobali.getInstance().getUltimoRitornoImmagine());
                        }
                    }

                    Utility.getInstance().riempieSpinner();
                }

                // Utility.getInstance().AccendeSpegneOggettiInBaseAiPermessi();
            }
        });

        VariabiliGlobali.getInstance().setImgCondividi((ImageView) findViewById(R.id.imgCondividi));
        VariabiliGlobali.getInstance().getImgCondividi().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (VariabiliGlobali.getInstance().getModalita().equals("PHOTO")) {
                    StrutturaFiles nome = VariabiliGlobali.getInstance().getImmagineCaricata();
                    int idCategoria = nome.getCategoria();
                    // StrutturaCategorie Categoria = VariabiliGlobali.getInstance().getCategorieImmagini().get(idCategoria);

                    StrutturaCategorie sc = VariabiliGlobali.getInstance().RitornaCategoriaDaID("1", idCategoria);
                    String Categoria = sc.getNomeCategoria();

                    nome.setNomeFile(nome.getNomeFile().replace("\\", "/"));
                    nome.setNomeFile(VariabiliGlobali.getInstance().getPercorsoURL() + "/" + Categoria + "/" + nome.getNomeFile());
                    // Uri fileUri = Uri.parse(nome.getNomeFile());

                    new DownloadFileFromURL("1").execute(nome.getNomeFile());
                } else {
                    if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
                        StrutturaFiles nome = VariabiliGlobali.getInstance().getVideoCaricato();
                        int idCategoria = nome.getCategoria();
                        StrutturaCategorie Categoria = VariabiliGlobali.getInstance().getCategorieVideo().get(idCategoria);
                        nome.setNomeFile(nome.getNomeFile().replace("\\", "/"));
                        nome.setNomeFile(VariabiliGlobali.getInstance().getPercorsoURL() + "/" + Categoria.getNomeCategoria() + "/" + nome.getNomeFile());
                        // Uri fileUri = Uri.parse(nome.getNomeFile());

                        new DownloadFileFromURL("2").execute(nome.getNomeFile());
                    }
                }
            }
        });

        VariabiliGlobali.getInstance().setImgElimina((ImageView) findViewById(R.id.imgElimina));
        VariabiliGlobali.getInstance().getImgElimina().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String cosa = "";
                if (VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
                    cosa = "il Video selezionato";
                } else {
                    cosa = "l'Immagine selezionata";
                }
                DialogMessaggio.getInstance().show(VariabiliGlobali.getInstance().getContext(),
                        "Si vuole eliminare " + cosa + " ?",
                        false,
                        "looVF",
                        true,
                        "ELIMINA");
            }
        });

        VariabiliGlobali.getInstance().setLayScroller((LinearLayout) findViewById(R.id.layScroller));
        VariabiliGlobali.getInstance().getLayScroller().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                // VariabiliGlobali.getInstance().setTastopremuto("PHOTO");
                gdt.onTouchEvent(event);
                return true;
            }
        });

        VariabiliGlobali.getInstance().setChkRandom((CheckBox) findViewById(R.id.chkRandom));
        VariabiliGlobali.getInstance().getChkRandom().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VariabiliGlobali.getInstance().getConfigurazione().setRandom(VariabiliGlobali.getInstance().getChkRandom().isChecked());
                String Ritorno = db.ScriveConfigurazione();
                if (!Ritorno.isEmpty()) {
                    DialogMessaggio.getInstance().show(VariabiliGlobali.getInstance().getContext(),
                            "ERRORE nel salvataggio della configurazione\n" + Ritorno,
                            true,
                            "looVF",
                            false,
                            "");
                }
            }
        });

        // mediaPlayer = new MediaPlayer();
        // mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        // btn.setOnClickListener(pausePlay);

        // Spinner sItems = (Spinner) findViewById(R.id.spnCategorie);
        VariabiliGlobali.getInstance().setsItems((Spinner) findViewById(R.id.spnCategorie));

        VariabiliGlobali.getInstance().setImgRefresh((ImageView) findViewById(R.id.imgRefreshGriglia));
        VariabiliGlobali.getInstance().getImgRefresh().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DBRemoto dbr = new DBRemoto();
                dbr.RitornaImmaginiGriglia();
            }
        });

        // LinearLayout laySettings = findViewById(R.id.laySettings);
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

        Button btnCompatta = (Button) findViewById(R.id.cmdCompatta);
        btnCompatta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                db.CompattaDB();
                DialogMessaggio.getInstance().show(VariabiliGlobali.getInstance().getContext(),
                        "DB Compattato",
                        false,
                        "looVF",
                        false,
                        "");
            }
        });

        /* ImageView mImageScegliCategoria = findViewById(R.id.imgCambiaCategoria);
        mImageScegliCategoria.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String item = VariabiliGlobali.getInstance().getsItems().getSelectedItem().toString();
                StrutturaCategorie sc = VariabiliGlobali.getInstance().RitornaCategoriaDaNome("1", item);

                VariabiliGlobali.getInstance().getLayRicerca().setVisibility(LinearLayout.GONE);

                if (VariabiliGlobali.getInstance().getModalita().equals("GRIGLIA")) {

                    VariabiliGlobali.getInstance().getConfigurazione().setUltimaCategoriaImmagini(sc);
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
                        VariabiliGlobali.getInstance().getConfigurazione().setUltimaCategoriaVideo(sc);
                    } else {
                        VariabiliGlobali.getInstance().getConfigurazione().setUltimaCategoriaImmagini(sc);
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
        }); */

        if (!VariabiliGlobali.getInstance().isCaricataPagina()) {
            VariabiliGlobali.getInstance().setModalita("PHOTO");
        }

        // VariabiliGlobali.getInstance().setvView((VideoView) findViewById(R.id.myVideo));
        VariabiliGlobali.getInstance().setImgPlayVideo((ImageView) findViewById(R.id.imgPlayVideo));
        VariabiliGlobali.getInstance().setiView((ImageView) findViewById(R.id.imgImmagine));
        VariabiliGlobali.getInstance().setLayGriglia((LinearLayout) findViewById(R.id.layGriglia));

        // VariabiliGlobali.getInstance().getImgPlayVideo().setOnTouchListener(new View.OnTouchListener() {
        //     @Override
        //     public boolean onTouch(final View view, final MotionEvent event) {
        //         // VariabiliGlobali.getInstance().setTastopremuto("VIDEO");
        //         gdt.onTouchEvent(event);
        //         return true;
        //     }
        // });
//
        // VariabiliGlobali.getInstance().getiView().setOnTouchListener(new View.OnTouchListener() {
        //     @Override
        //     public boolean onTouch(final View view, final MotionEvent event) {
        //         // VariabiliGlobali.getInstance().setTastopremuto("PHOTO");
        //         gdt.onTouchEvent(event);
        //         return true;
        //     }
        // });

        VariabiliGlobali.getInstance().setLaySettingsPanel((LinearLayout) findViewById(R.id.laySettingsPanel));
        VariabiliGlobali.getInstance().getLaySettingsPanel().setVisibility(LinearLayout.GONE);

        VariabiliGlobali.getInstance().getiView().setVisibility(LinearLayout.GONE);
        VariabiliGlobali.getInstance().getImgPlayVideo().setVisibility(LinearLayout.GONE);
        VariabiliGlobali.getInstance().getLayGriglia().setVisibility(LinearLayout.GONE);

        // mImageAvanti.setVisibility(LinearLayout.VISIBLE);
        // mImageIndietro.setVisibility(LinearLayout.VISIBLE);

        layContenitore = (LinearLayout) findViewById(R.id.layContenitore);
        ImageView imgLinguetta = (ImageView) findViewById(R.id.imgLinguetta);
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

        LinearLayout layGriglia = (LinearLayout) findViewById(R.id.layContGriglia);
        layGriglia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // if (!VariabiliGlobali.getInstance().getModalita().equals("GRIGLIA")) {
                VariabiliGlobali.getInstance().getLayRicerca().setVisibility(LinearLayout.GONE);
                VariabiliGlobali.getInstance().getImgCondividi().setVisibility(LinearLayout.GONE);
                VariabiliGlobali.getInstance().setModalita("GRIGLIA");

                VariabiliGlobali.getInstance().getImgPlayVideo().setVisibility(LinearLayout.GONE);
                VariabiliGlobali.getInstance().getLaySettingsPanel().setVisibility(LinearLayout.GONE);
                VariabiliGlobali.getInstance().getiView().setVisibility(LinearLayout.GONE);
                VariabiliGlobali.getInstance().getLayGriglia().setVisibility(LinearLayout.VISIBLE);
                VariabiliGlobali.getInstance().getImgRefresh().setVisibility(LinearLayout.VISIBLE);
                VariabiliGlobali.getInstance().getLayScroller().setVisibility(LinearLayout.GONE);

                Utility.getInstance().riempieSpinner();

                // String idCategoria = Integer.toString(Utility.getInstance().TornaIdCategoria(VariabiliGlobali.getInstance().getCategoriaSceltaImmagine()));
                StrutturaCategorie sc = VariabiliGlobali.getInstance().RitornaCategoriaDaNome("1", VariabiliGlobali.getInstance().getConfigurazione().getUltimaCategoriaImmagini().getNomeCategoria());
                String Ritorno = db.RitornaRigheGriglia("1", Integer.toString(sc.getIdCategoria()));

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

        LinearLayout layPhoto = (LinearLayout) findViewById(R.id.layPhoto);
        layPhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VariabiliGlobali.getInstance().getLayRicerca().setVisibility(LinearLayout.GONE);
                VariabiliGlobali.getInstance().getImgCondividi().setVisibility(LinearLayout.VISIBLE);

                if (!VariabiliGlobali.getInstance().getModalita().equals("PHOTO")) {
                    VariabiliGlobali.getInstance().setModalita("PHOTO");

                    VariabiliGlobali.getInstance().getImgPlayVideo().setVisibility(LinearLayout.GONE);
                    VariabiliGlobali.getInstance().getLaySettingsPanel().setVisibility(LinearLayout.GONE);
                    VariabiliGlobali.getInstance().getiView().setVisibility(LinearLayout.VISIBLE);
                    VariabiliGlobali.getInstance().getLayGriglia().setVisibility(LinearLayout.GONE);
                    VariabiliGlobali.getInstance().getImgRefresh().setVisibility(LinearLayout.GONE);
                    VariabiliGlobali.getInstance().getLayScroller().setVisibility(LinearLayout.VISIBLE);

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

        VariabiliGlobali.getInstance().setLayTastoVideo((LinearLayout) findViewById(R.id.layVideo));
        VariabiliGlobali.getInstance().getLayTastoVideo().setVisibility(LinearLayout.GONE);
        VariabiliGlobali.getInstance().getLayTastoVideo().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VariabiliGlobali.getInstance().getLayRicerca().setVisibility(LinearLayout.GONE);
                VariabiliGlobali.getInstance().getImgCondividi().setVisibility(LinearLayout.VISIBLE);

                if (!VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
                    VariabiliGlobali.getInstance().setModalita("VIDEO");

                    VariabiliGlobali.getInstance().getImgPlayVideo().setVisibility(LinearLayout.VISIBLE);
                    VariabiliGlobali.getInstance().getLaySettingsPanel().setVisibility(LinearLayout.GONE);
                    VariabiliGlobali.getInstance().getiView().setVisibility(LinearLayout.GONE);
                    VariabiliGlobali.getInstance().getLayGriglia().setVisibility(LinearLayout.GONE);
                    VariabiliGlobali.getInstance().getImgRefresh().setVisibility(LinearLayout.GONE);
                    VariabiliGlobali.getInstance().getLayScroller().setVisibility(LinearLayout.VISIBLE);

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

        LinearLayout laySettings = (LinearLayout) findViewById(R.id.laySettings);
        laySettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VariabiliGlobali.getInstance().getLayRicerca().setVisibility(LinearLayout.GONE);
                VariabiliGlobali.getInstance().getImgPlayVideo().setVisibility(LinearLayout.GONE);
                VariabiliGlobali.getInstance().getLaySettingsPanel().setVisibility(LinearLayout.VISIBLE);
                VariabiliGlobali.getInstance().getiView().setVisibility(LinearLayout.GONE);
                VariabiliGlobali.getInstance().getLayGriglia().setVisibility(LinearLayout.GONE);
                VariabiliGlobali.getInstance().getImgRefresh().setVisibility(LinearLayout.GONE);
                VariabiliGlobali.getInstance().getLayScroller().setVisibility(LinearLayout.GONE);
                VariabiliGlobali.getInstance().getImgCondividi().setVisibility(LinearLayout.GONE);

                // Utility.getInstance().ScriveInformazioni();
            }
        });

        LinearLayout layRefresh = (LinearLayout) findViewById(R.id.layRefresh);
        layRefresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VariabiliGlobali.getInstance().getLayRicerca().setVisibility(LinearLayout.GONE);

                DialogMessaggio.getInstance().show(VariabiliGlobali.getInstance().getContext(),
                        "Si vogliono riscaricare tutti i dati ?",
                        false,
                        "looVF",
                        true,
                        "REFRESH");
            }
        });

        LinearLayout layAbout = (LinearLayout) findViewById(R.id.layAbout);
        layAbout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VariabiliGlobali.getInstance().getLayRicerca().setVisibility(LinearLayout.GONE);
            }
        });

        TextView txtInfo = (TextView) findViewById(R.id.txtInfo);
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
            VariabiliGlobali.getInstance().getImgRefresh().setVisibility(LinearLayout.GONE);
            VariabiliGlobali.getInstance().getLayScroller().setVisibility(LinearLayout.VISIBLE);

            DBRemoto dbr = new DBRemoto();
            dbr.RitornaPermessi();
            dbr.RitornaCategorie();
            dbr.RitornaQuantiFilesPhoto();
            dbr.RitornaQuantiFilesVideo();

            // Utility.getInstance().riempieSpinner();

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
