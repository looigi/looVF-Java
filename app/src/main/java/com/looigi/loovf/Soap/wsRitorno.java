package com.looigi.loovf.Soap;

import android.os.Handler;
import android.os.Looper;

import com.looigi.loovf.DialogMessaggio;
import com.looigi.loovf.VariabiliGlobali;

public class wsRitorno {
    private Runnable runRiga;
    private Handler hSelezionaRiga;

    public void RitornaListe(final String Ritorno) {
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

                    // C:\inetpub\wwwroot\looVF\Temp\28_05_2019_10_20_38.txt
                    String sRitorno = Ritorno;
                    sRitorno = sRitorno.substring(sRitorno.indexOf("Temp"), sRitorno.length());

                    DownloadTextFile d = new DownloadTextFile();

                    d.setPath(VariabiliGlobali.getInstance().getPercorsoDIR());
                    d.setPathNomeFile("Lista.dat");
                    d.setOperazione("Lettura lista multimedia");
                    d.startDownload(
                            VariabiliGlobali.getInstance().getPercorsoURL()+ "/" + sRitorno.replace("\\", "/"),
                            true);
                }
            }, 50);
        }
    }
}
