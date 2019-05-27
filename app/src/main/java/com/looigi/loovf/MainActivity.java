package com.looigi.loovf;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.looigi.loovf.Soap.DBRemoto;

import org.kobjects.util.Util;

import java.io.File;

import uk.co.senab.photoview.PhotoViewAttacher;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VariabiliGlobali.getInstance().setDatiCaricati(false);
        GestioneFiles.getInstance().CreaCartella(VariabiliGlobali.getInstance().getPercorsoDIR());
        File f = new File(VariabiliGlobali.getInstance().getPercorsoDIR() + "/Lista.dat");
        if (f.exists()) {
            String Ritorno = GestioneFiles.getInstance().LeggeFileDiTesto(VariabiliGlobali.getInstance().getPercorsoDIR() + "/Lista.dat");
            Utility.getInstance().CreaListaFiles(Ritorno);
            VariabiliGlobali.getInstance().setDatiCaricati(true);
        }

        VariabiliGlobali.getInstance().setContext(this);
        VariabiliGlobali.getInstance().setFragmentActivityPrincipale(this);

        VariabiliGlobali.getInstance().setvView((VideoView) findViewById(R.id.myVideo));
        VariabiliGlobali.getInstance().setiView((ImageView) findViewById(R.id.imgImmagine));
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

        VariabiliGlobali.getInstance().setModalita("VIDEO");
        VariabiliGlobali.getInstance().getvView().setVisibility(LinearLayout.VISIBLE);
        laySettingsPanel.setVisibility(LinearLayout.GONE);
        VariabiliGlobali.getInstance().getiView().setVisibility(LinearLayout.GONE);
        mImageAvanti.setVisibility(LinearLayout.VISIBLE);
        mImageIndietro.setVisibility(LinearLayout.VISIBLE);

        LinearLayout layPhoto = findViewById(R.id.layPhoto);
        layPhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!VariabiliGlobali.getInstance().getModalita().equals("PHOTO")) {
                    VariabiliGlobali.getInstance().setModalita("PHOTO");
                    VariabiliGlobali.getInstance().getvView().setVisibility(LinearLayout.GONE);
                    laySettingsPanel.setVisibility(LinearLayout.GONE);
                    VariabiliGlobali.getInstance().getiView().setVisibility(LinearLayout.VISIBLE);
                    mImageAvanti.setVisibility(LinearLayout.VISIBLE);
                    mImageIndietro.setVisibility(LinearLayout.VISIBLE);

                    Utility.getInstance().ScriveInformazioni();
                }
            }
        });

        LinearLayout layVideo = findViewById(R.id.layVideo);
        layVideo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!VariabiliGlobali.getInstance().getModalita().equals("VIDEO")) {
                    VariabiliGlobali.getInstance().setModalita("VIDEO");
                    VariabiliGlobali.getInstance().getvView().setVisibility(LinearLayout.VISIBLE);
                    laySettingsPanel.setVisibility(LinearLayout.GONE);
                    VariabiliGlobali.getInstance().getiView().setVisibility(LinearLayout.GONE);
                    mImageAvanti.setVisibility(LinearLayout.VISIBLE);
                    mImageIndietro.setVisibility(LinearLayout.VISIBLE);

                    Utility.getInstance().ScriveInformazioni();
                }
            }
        });

        LinearLayout laySettings = findViewById(R.id.laySettings);
        laySettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VariabiliGlobali.getInstance().getvView().setVisibility(LinearLayout.GONE);
                laySettingsPanel.setVisibility(LinearLayout.VISIBLE);
                VariabiliGlobali.getInstance().getiView().setVisibility(LinearLayout.GONE);
                mImageAvanti.setVisibility(LinearLayout.GONE);
                mImageIndietro.setVisibility(LinearLayout.GONE);

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
                DBRemoto dbr = new DBRemoto();
                dbr.RitornaListe();
            }
        });

        LinearLayout layAbout = findViewById(R.id.layAbout);
        layAbout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });

        TextView txtInfo = findViewById(R.id.txtInfo);
        VariabiliGlobali.getInstance().setTxtInfo(txtInfo);

        if (VariabiliGlobali.getInstance().isDatiCaricati()) {
            mImageIndietro.setVisibility(LinearLayout.VISIBLE);
            mImageAvanti.setVisibility(LinearLayout.VISIBLE);
            VariabiliGlobali.getInstance().getvView().setVisibility(LinearLayout.VISIBLE);
            VariabiliGlobali.getInstance().getiView().setVisibility(LinearLayout.VISIBLE);
        } else {
            mImageIndietro.setVisibility(LinearLayout.GONE);
            mImageAvanti.setVisibility(LinearLayout.GONE);
            VariabiliGlobali.getInstance().getvView().setVisibility(LinearLayout.GONE);
            VariabiliGlobali.getInstance().getiView().setVisibility(LinearLayout.GONE);
        }

        Utility.getInstance().ScriveInformazioni();
    }
}
