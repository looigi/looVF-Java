package com.looigi.loovf;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Utility {
    private static final Utility ourInstance = new Utility();

    public static Utility getInstance() {
        return ourInstance;
    }

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

    public void CreaListaFiles(String Ritorno) {
        List<StrutturaFiles> lista= new ArrayList<>();

        String c[] = Ritorno.split("§");
        for (String cc: c) {
            String ccc[] = cc.split(";");
            StrutturaFiles sf = new StrutturaFiles();
            sf.setTipologia(ccc[0]);
            sf.setNomeFile(ccc[1]);
            sf.setDimeFile(Long.parseLong(ccc[2]));
            sf.setDataFile(Date.valueOf(ccc[3]));
            lista.add(sf);
        }

        VariabiliGlobali.getInstance().setListaFiles(lista);
    }
}
