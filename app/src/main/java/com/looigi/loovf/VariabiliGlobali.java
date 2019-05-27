package com.looigi.loovf;

import android.content.Context;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

public class VariabiliGlobali {
    private static final VariabiliGlobali ourInstance = new VariabiliGlobali();

    public static VariabiliGlobali getInstance() {
        return ourInstance;
    }

    private VariabiliGlobali() {
    }

    private Context context;
    private FragmentActivity FragmentActivityPrincipale;
    private List<StrutturaFiles> listaFiles = new ArrayList<>();
    private String PercorsoDIR= Environment.getExternalStorageDirectory().getPath()+"/LooigiSoft/looVF";
    private String PercorsoURL="http://looigi.no-ip.biz:12345/looVF";

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public FragmentActivity getFragmentActivityPrincipale() {
        return FragmentActivityPrincipale;
    }

    public void setFragmentActivityPrincipale(FragmentActivity fragmentActivityPrincipale) {
        FragmentActivityPrincipale = fragmentActivityPrincipale;
    }

    public List<StrutturaFiles> getListaFiles() {
        return listaFiles;
    }

    public void setListaFiles(List<StrutturaFiles> listaFiles) {
        this.listaFiles = listaFiles;
    }

    public String getPercorsoDIR() {
        return PercorsoDIR;
    }

    public String getPercorsoURL() {
        return PercorsoURL;
    }
}
