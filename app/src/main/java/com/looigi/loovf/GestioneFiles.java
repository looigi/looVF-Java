package com.looigi.loovf;

import android.os.Handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GestioneFiles {
    //-------- Singleton ----------//
    private static GestioneFiles instance = null;

    private GestioneFiles() {
    }

    public static GestioneFiles getInstance() {
        if (instance == null) {
            instance = new GestioneFiles();
        }

        return instance;
    }

    public void EliminaCartella(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                EliminaCartella(child);

        fileOrDirectory.delete();
    }

    public void CreaCartella(String PercorsoDIR) {
        // VariabiliStaticheGlobali.getInstance().getLog().ScriveLog(new Object(){}.getClass().getEnclosingMethod().getName(), "Creazione cartella "+PercorsoDIR);

        File dDirectory = new File(PercorsoDIR);
        try {
            dDirectory.mkdirs();
        } catch (Exception e) {
            // VariabiliStaticheGlobali.getInstance().getLog().ScriveMessaggioDiErrore(e);
            // e.printStackTrace();
            // VariabiliStaticheGlobali.getInstance().getLog().ScriveLog(new Object(){}.getClass().getEnclosingMethod().getName(), "Creazione cartella. Errore: "+e.getMessage());
        }
    }

    public void CreaCartelle(final String Percorso) {
        // hCreaCartelle = new Handler();
        // hCreaCartelle.postDelayed(runCreaCartelle = new Runnable() {
        //    @Override
        //     public void run() {
                // VariabiliStaticheGlobali.getInstance().getLog().ScriveLog(new Object(){}.getClass().getEnclosingMethod().getName(), "Creazione cartelle "+Percorso);
                String Campi[]=(Percorso+"/").split("/",-1);
                String ss="";

                for (String s : Campi) {
                    if (!s.isEmpty()) {
                        ss += "/" + s;
                        CreaCartella(ss);
                        if (!fileExistsInSD(".noMedia",ss )) {
                            // Crea file per nascondere alla galleria i files immagine della cartella
                            generateNoteOnSD(ss, ".noMedia","");
                        }
                    }
                }
           //  }
        // }, 50);
    }

    private String PrendeOraAttuale() {
        String Ritorno="";

        Calendar Oggi = Calendar.getInstance();
        int Ore=Oggi.get(Calendar.HOUR_OF_DAY);
        int Minuti=Oggi.get(Calendar.MINUTE);
        int Secondi=Oggi.get(Calendar.SECOND);
        int MilliSecondi=Oggi.get(Calendar.MILLISECOND);
        String sOre=Integer.toString(Ore).trim();
        String sMinuti=Integer.toString(Minuti).trim();
        String sSecondi=Integer.toString(Secondi).trim();
        String sMilliSecondi=Integer.toString(MilliSecondi).trim();
        if (sOre.length()==1) {
            sOre="0"+sOre;
        }
        if (sMinuti.length()==1) {
            sMinuti="0"+sMinuti;
        }
        if (sSecondi.length()==1) {
            sSecondi="0"+sSecondi;
        }
        if (sMilliSecondi.length()==1) {
            sMilliSecondi="0"+sMilliSecondi;
        }
        if (sMilliSecondi.length()==2) {
            sMilliSecondi="0"+sMilliSecondi;
        }
        Ritorno=sOre+":"+sMinuti+":"+sSecondi+"."+sMilliSecondi;

        return Ritorno;
    }

    private String PrendeDataAttuale() {
        String Ritorno="";

        Calendar Oggi = Calendar.getInstance();
        int Giorno=Oggi.get(Calendar.DAY_OF_MONTH);
        int Mese=Oggi.get(Calendar.MONTH)+1;
        int Anno=Oggi.get(Calendar.YEAR);
        String sGiorno=Integer.toString(Giorno).trim();
        String sMese=Integer.toString(Mese).trim();
        String sAnno=Integer.toString(Anno).trim();
        if (sGiorno.length()==1) {
            sGiorno="0"+sGiorno;
        }
        if (sMese.length()==1) {
            sMese="0"+sMese;
        }
        Ritorno=sGiorno+"/"+sMese+"/"+sAnno;

        return Ritorno;
    }

    public void ScriveLog(String Messaggio) {
        CreaCartella(VariabiliGlobali.getInstance().getPercorsoDIR());

        String Datella="";
        Datella=PrendeDataAttuale()+";"+PrendeOraAttuale()+";";

        String sBody=Datella+Messaggio.replace(";", "_");

        File gpxfile = new File(VariabiliGlobali.getInstance().getPercorsoDIR(), "LogErrori.txt");

        FileWriter writer;
        try {
            writer = new FileWriter(gpxfile,true);
            writer.append(sBody+"\n");
            writer.flush();
            writer.close();
        } catch (IOException ignored) {
        }
    }

    private void generateNoteOnSD(String Percorso, String sFileName, String sBody) {
        try {
            File gpxfile = new File(Percorso, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            // e.printStackTrace();
            // VariabiliStaticheGlobali.getInstance().getLog().ScriveLog(new Object(){}.getClass().getEnclosingMethod().getName(), "Generazione file di testo. Errore: "+e.getMessage());
        }
    }

    public void EliminaFile(String path) {
        File f = new File(path);
        f.delete();
    }

    public String LeggeFileDiTesto(String path){
        File file = new File(path);

        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
            }
            br.close();
        }
        catch (IOException ignored) {
        }

        return text.toString();
    }

    public void CreaFileDiTesto(String Percorso, String sFileName, String sBody) {
        try {
            File gpxfile = new File(Percorso, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            //VariabiliStaticheGlobali.getInstance().getLog().ScriveLog(new Object(){}.getClass().getEnclosingMethod().getName(), "Crea file di testo. Errore: "+e.getMessage());
        }
    }

    public boolean fileExistsInSD(String sFileName, String Percorso){
        // VariabiliStaticheGlobali.getInstance().getLog().ScriveLog(new Object(){}.getClass().getEnclosingMethod().getName(), "Controllo esistenza file: "+Percorso+"/"+sFileName);
        String sFile=Percorso+"/"+sFileName;
        File file = new File(sFile);

        return file.exists();
    }

    public void copyFile(File src, File dst) throws IOException {
        FileInputStream inStream = new FileInputStream(src);
        FileOutputStream outStream = new FileOutputStream(dst);
        FileChannel inChannel = inStream.getChannel();
        FileChannel outChannel = outStream.getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        inStream.close();
        outStream.close();
    }

    public List<String> RitornaListaDirectory(String Path) {
        List<String> Dirs = new ArrayList<>();

        File f = new File(Path);
        File[] files = f.listFiles();
        for (File inFile : files) {
            if (inFile.isDirectory()) {
                Dirs.add(inFile.getName());
            }
        }

        return Dirs;
    }

    public List<String> RitornaListaFilesInDirectory(String Path) {
        List<String> files = new ArrayList<>();

        File f = new File(Path);
        File[] ff = f.listFiles();
        for (File inFile : ff) {
            files.add(inFile.getName());
        }

        return files;
    }
}
