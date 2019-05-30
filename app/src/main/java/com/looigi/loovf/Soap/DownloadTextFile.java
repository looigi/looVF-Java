package com.looigi.loovf.Soap;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.looigi.loovf.Utility;
import com.looigi.loovf.VariabiliGlobali;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTextFile {
    private String Path;
    private String PathNomeFile;
    private static String messErrore="";
    private static DownloadTxtFile downloadFile;
    private String Url;
    private String tOperazione;

    public void setOperazione(String Operazione) {
        tOperazione = Operazione;
    }

    public void setPathNomeFile(String pathNomeFile) {
        PathNomeFile = pathNomeFile;
    }

    public void setPath(String path) {
        Path = path;
    }

    public void startDownload(String sUrl, boolean ApriDialog) {
        this.Url=sUrl;

        downloadFile = new DownloadTxtFile(Path, PathNomeFile, ApriDialog, tOperazione, Url);
        downloadFile.execute(Url);
    }

    public void StoppaEsecuzione() {
        if (downloadFile != null) {
            downloadFile.cancel(true);
        }

        downloadFile.ChiudeDialog();
    }

    private static class DownloadTxtFile extends AsyncTask<String, Integer, String> {
        private String Path;
        private String PathNomeFile;
        private int NumeroBrano;
        private int QuantiTentativi;
        private int Tentativo;
        private Handler hAttesaNuovoTentativo;
        private Runnable rAttesaNuovoTentativo;
        private int SecondiAttesa;
        private boolean ApriDialog;
        private ProgressDialog progressDialog;
        private String tOperazione;
        private String Url;

        public DownloadTxtFile(String Path, String PathNomeFile, boolean ApriDialog, String tOperazione, String Url) {
            this.Path = Path;
            this.PathNomeFile = PathNomeFile;
            this.ApriDialog = ApriDialog;
            this.tOperazione = tOperazione;
            this.Url = Url;

            this.QuantiTentativi = 3;
            this.Tentativo = 0;
        }

        private void ChiudeDialog() {
            if (ApriDialog) {
                try {
                    progressDialog.dismiss();
                } catch (Exception ignored) {
                }
            }
        }

        private void ApriDialog() {
            if (ApriDialog) {
                try {
                    progressDialog = new ProgressDialog(VariabiliGlobali.getInstance().getContext());
                    progressDialog.setMessage("Attendere Prego\n"+tOperazione);
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();
                } catch (Exception ignored) {

                }
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ApriDialog();
        }

        @Override
        protected String doInBackground(String... sUrl) {
            messErrore = "";

            try {
                URL url = new URL(sUrl[0]);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setConnectTimeout(10000);
                c.setReadTimeout(25000);
                c.connect();

                if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    messErrore = "ERROR: Server returned HTTP " + c.getResponseCode()
                            + " " + c.getResponseMessage();
                } else {
                    File dirFile = new File(Path + "/");
                    File outputFile = new File(Path + "/" + PathNomeFile);
                    dirFile.mkdirs();

                    if (!outputFile.exists()) {
                        outputFile.createNewFile();
                    }

                    FileOutputStream fos = new FileOutputStream(outputFile);

                    InputStream is = c.getInputStream();

                    byte[] buffer = new byte[1024];
                    int len1 = 0;
                    while ((len1 = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, len1);
                        if (isCancelled()) {
                            break;
                        }
                    }

                    fos.close();
                    is.close();
                    dirFile = null;
                    outputFile = null;

                    if (isCancelled() || messErrore.equals("ESCI")) {
                        try {
                            if (outputFile.exists()) {
                                outputFile.delete();
                            }
                        } catch (Exception ignored) {
                        }
                    }
                }
            } catch (Exception e) {
                messErrore = "ERROR:" + Utility.getInstance().PrendeErroreDaException(e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ControllaFineCiclo();
        }

        public void ControllaFineCiclo() {
            ChiudeDialog();

            if (messErrore.isEmpty()) {
                // String Ritorno = GestioneFiles.getInstance().LeggeFileDiTesto(Path + "/" + PathNomeFile);
                // Utility.getInstance().CreaListaFiles();
                // File f = new File(Path, PathNomeFile);
                // f.delete();
                // GestioneFiles.getInstance().CreaFileDiTesto(VariabiliGlobali.getInstance().getPercorsoDIR(),
                //         "Lista.dat",
                //         Ritorno);
                // CaricamentoDati cd = new CaricamentoDati();
                // cd.IniziaCaricamento();
            } else {
                if (messErrore.equals("ESCI")) {
                    // Errore... Riprovo ad eseguire la funzione
                    final int TempoAttesa = 5000;

                    SecondiAttesa = 0;
                    hAttesaNuovoTentativo = new Handler(Looper.getMainLooper());
                    rAttesaNuovoTentativo = (new Runnable() {
                        @Override
                        public void run() {
                            SecondiAttesa++;
                            if (SecondiAttesa>=TempoAttesa) {
                                ApriDialog();

                                downloadFile = new DownloadTxtFile(Path, PathNomeFile, ApriDialog, tOperazione, Url);
                                downloadFile.execute(Url);

                                hAttesaNuovoTentativo.removeCallbacks(rAttesaNuovoTentativo);
                                hAttesaNuovoTentativo = null;
                            } else {
                                hAttesaNuovoTentativo.postDelayed(rAttesaNuovoTentativo, 1000);
                            }
                        }
                    });
                    hAttesaNuovoTentativo.postDelayed(rAttesaNuovoTentativo, 1000);
                    // Errore... Riprovo ad eseguire la funzione
                }
            }
        }
    }
}