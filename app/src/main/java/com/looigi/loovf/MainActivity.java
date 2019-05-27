package com.looigi.loovf;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.looigi.loovf.Soap.DBRemoto;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GestioneFiles.getInstance().CreaCartella(VariabiliGlobali.getInstance().getPercorsoDIR());
        File f = new File(VariabiliGlobali.getInstance().getPercorsoDIR() + "/Lista.dat");
        if (f.exists()) {
            String Ritorno = GestioneFiles.getInstance().LeggeFileDiTesto(VariabiliGlobali.getInstance().getPercorsoDIR() + "/Lista.dat");
            Utility.getInstance().CreaListaFiles(Ritorno);
        } else {
            DBRemoto dbr = new DBRemoto();
            dbr.RitornaListe();
        }

        VariabiliGlobali.getInstance().setContext(this);
        VariabiliGlobali.getInstance().setFragmentActivityPrincipale(this);

        VideoView vidView = (VideoView)findViewById(R.id.myVideo);
        String vidAddress = "https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
        Uri vidUri = Uri.parse(vidAddress);
        vidView.setVideoURI(vidUri);

        MediaController vidControl = new MediaController(this);
        vidControl.setAnchorView(vidView);
        vidView.setMediaController(vidControl);

        vidView.start();
    }
}
