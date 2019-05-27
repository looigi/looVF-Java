package com.looigi.loovf.Soap;

import com.looigi.loovf.DialogMessaggio;
import com.looigi.loovf.GestioneFiles;
import com.looigi.loovf.Utility;
import com.looigi.loovf.VariabiliGlobali;

public class wsRitorno {
    private Runnable runRiga;

    public void RitornaListe(final String Ritorno) {
        if (Ritorno.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliGlobali.getInstance().getContext(),
                    Ritorno,
                    true,
                    "looVF");
        } else {
            Utility.getInstance().CreaListaFiles(Ritorno);
            GestioneFiles.getInstance().CreaFileDiTesto(VariabiliGlobali.getInstance().getPercorsoDIR(),
                    "Lista.dat",
                    Ritorno);
        }
    }
}
