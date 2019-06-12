package com.looigi.loovf.Soap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;

import com.looigi.loovf.GestioneFiles;
import com.looigi.loovf.VariabiliGlobali;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadFileFromURL extends AsyncTask<String, String, String> {
    private ProgressDialog progressDialog;
    private String idTipologia;
    private String NomeFileAppoggio;

    public DownloadFileFromURL(String idTipologia) {
        this.idTipologia = idTipologia;
        if (this.idTipologia.equals("1")) {
            NomeFileAppoggio = VariabiliGlobali.getInstance().getPercorsoDIR() + "/Temp/Download.jpg";
        } else {
            NomeFileAppoggio = VariabiliGlobali.getInstance().getPercorsoDIR() + "/Temp/Download.avi";
        }
        GestioneFiles.getInstance().CreaCartelle(VariabiliGlobali.getInstance().getPercorsoDIR() + "/Temp/");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        showDialog();
    }

    private void showDialog() {
        try {
            progressDialog = new ProgressDialog(VariabiliGlobali.getInstance().getContext());
            progressDialog.setMessage("Attendere Prego...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
        } catch (Exception ignored) {

        }
    }

    @Override
    protected String doInBackground(String... f_url) {
        int count;
        File f = new File(NomeFileAppoggio);
        try {
            f.delete();
        } catch (Exception ignored) {

        }

        try {
            URL url = new URL(f_url[0]);
            URLConnection conection = url.openConnection();
            conection.connect();

            int lenghtOfFile = conection.getContentLength();

            InputStream input = new BufferedInputStream(url.openStream(),
                    8192);

            OutputStream output = new FileOutputStream(NomeFileAppoggio);

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();

        } catch (Exception e) {
            // Log.e("Error: ", e.getMessage());
        }

        return null;
    }

    protected void onProgressUpdate(String... progress) {
        progressDialog.setProgress(Integer.parseInt(progress[0]));
    }

    @Override
    protected void onPostExecute(String file_url) {
        try {
            progressDialog.dismiss();
        } catch (Exception ignored) {
        }

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        File f = new File(NomeFileAppoggio);
        Uri fileUri = Uri.fromFile(f);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, fileUri);

        if (idTipologia.equals("1")) {
            intent.setType("image/*");
        } else {
            intent.setType("video/*");
        }
        VariabiliGlobali.getInstance().getFragmentActivityPrincipale().startActivity(Intent.createChooser(intent, "Share Image:"));
    }

}