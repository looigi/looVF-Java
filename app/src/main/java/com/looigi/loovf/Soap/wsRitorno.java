package com.looigi.loovf.Soap;

import android.os.Handler;
import android.os.Looper;

import com.looigi.loovf.DialogMessaggio;
import com.looigi.loovf.GestioneFiles;
import com.looigi.loovf.Utility;
import com.looigi.loovf.VariabiliGlobali;

public class wsRitorno {
    private Runnable runRiga;
    private Handler hSelezionaRiga;

    public void RitornaListe(final String Ritorno) {
        if (Ritorno.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliGlobali.getInstance().getContext(),
                    Ritorno,
                    true,
                    "looVF");
        } else {
            hSelezionaRiga = new Handler(Looper.getMainLooper());
            hSelezionaRiga.postDelayed(runRiga = new Runnable() {
                @Override
                public void run() {
                    hSelezionaRiga.removeCallbacks(runRiga);
                    runRiga = null;

                    DownloadTextFile d = new DownloadTextFile();

                    d.setPath(VariabiliGlobali.getInstance().getModalita());
                    d.setPathNomeFile("Lista.dat");
                    d.setOperazione("Lettura lista kmultimedia");
                    d.startDownload(
                            VariabiliGlobali.getInstance().getPercorsoURL()+ "/" + Ritorno.replace("\\", "/"),
                            true);
                }
            }, 50);
        }
    }
}
