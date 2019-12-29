package com.looigi.loovf;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.looigi.loovf.db_locale.db_dati;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterMultimedia extends ArrayAdapter
{
    private Context context;
    private List<String> lista;

    public AdapterMultimedia(Context context, int textViewResourceId, List<String> objects)
    {
        super(context, textViewResourceId, objects);

        this.context = context;
        this.lista=objects;
    }

    @Override
    @Nullable
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.lstview_multimedia, null);

        String riga = lista.get(position);
        String[] r = riga.split(";");

        String Thumb = r[0];
        String NomeMultimedia = r[1].replace("***PV***", ";");
        long Dimensioni = Long.parseLong(r[2]);
        String Data = r[3];
        int idMultimedia = Integer.parseInt(r[5]);
        String idTipologia = r[6];
        int idCategoria = Integer.parseInt(r[4]);
        StrutturaCategorie sc = VariabiliGlobali.getInstance().RitornaCategoriaDaID(idTipologia, idCategoria);
        String sCategoria = sc.getNomeCategoria();
        String sDimensioni = "";
        String[] tipo={"b.", "Kb.", "Mb.", "Gb."};
        int quale = 0;
        while (Dimensioni>1024) {
            Dimensioni /= 1024;
            quale++;
        }
        sDimensioni = Long.toString(Dimensioni) + " " + tipo[quale];

        if (Utility.getInstance().ePari(position)) {
            convertView.setBackgroundColor(Color.WHITE);
        } else {
            convertView.setBackgroundColor(Color.argb(255, 230, 230, 230));
        }

        TextView txtId = convertView.findViewById(R.id.idMultimedia);
        TextView txtNomeMultimedia = convertView.findViewById(R.id.txtNomeMultimedia);
        TextView txtDimensioni = convertView.findViewById(R.id.txtDimensioni);
        TextView txtData = convertView.findViewById(R.id.txtData);
        TextView txtCategoria = convertView.findViewById(R.id.txtCategoria);
        ImageView imgMultimedia = convertView.findViewById(R.id.imgMultimedia);

        txtId.setText(Integer.toString(idMultimedia));
        txtNomeMultimedia.setText(NomeMultimedia);
        txtDimensioni.setText(sDimensioni);
        txtData.setText(Data);
        txtCategoria.setText(Integer.toString(idCategoria) + "-" + sCategoria);

        String path = "";
        if (Thumb.isEmpty()) {
            path = VariabiliGlobali.getInstance().getPercorsoURL() + "/" + sCategoria + "/" + NomeMultimedia.replace("\\", "/");
        } else {
            path = VariabiliGlobali.getInstance().getPercorsoURL() + "/" + sCategoria + "/" + Thumb.replace("\\", "/");
        }

        Picasso.get().load(path).placeholder(R.drawable.progress_animation).into(imgMultimedia);

        convertView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String[] r = lista.get(position).split(";");

                String Thumb = r[0];
                int prossimo = Integer.parseInt(r[5]);
                String idTipologia = r[6];
                String NomeMultimedia = r[1].replace("***PV***", ";");
                long Dimensioni = Long.parseLong(r[2]);
                String Data = r[3];
                int idCategoria = Integer.parseInt(r[4]);
                StrutturaCategorie sc = VariabiliGlobali.getInstance().RitornaCategoriaDaID(idTipologia, idCategoria);
                String sCategoria = sc.getNomeCategoria();

                VariabiliGlobali.getInstance().getImgCondividi().setVisibility(LinearLayout.VISIBLE);
                if (idTipologia.equals("1")) {
                    VariabiliGlobali.getInstance().setModalita("PHOTO");
                    VariabiliGlobali.getInstance().setImmagineVisualizzata(prossimo);

                    VariabiliGlobali.getInstance().getImmaginiVisualizzate().add(VariabiliGlobali.getInstance().getImmagineVisualizzata());
                    VariabiliGlobali.getInstance().setIndiceImmagine(VariabiliGlobali.getInstance().getImmaginiVisualizzate().size() - 1);

                    ImageView mImageView = VariabiliGlobali.getInstance().getiView();

                    StrutturaFiles sf = new StrutturaFiles();
                    sf.setTipologia("1");
                    sf.setNomeFile(NomeMultimedia);
                    sf.setDimeFile(Dimensioni);
                    sf.setDataFile(Data);
                    sf.setCategoria(idCategoria);
                    VariabiliGlobali.getInstance().setImmagineCaricata(sf);

                    String path = VariabiliGlobali.getInstance().getPercorsoURL() + "/" + sCategoria + "/" + NomeMultimedia.replace("\\", "/");

                    Picasso.get().load(path).placeholder(R.drawable.progress_animation).into(mImageView);
                } else {
                    VariabiliGlobali.getInstance().setModalita("VIDEO");
                    VariabiliGlobali.getInstance().setVideoVisualizzato(prossimo);

                    VariabiliGlobali.getInstance().getVideoVisualizzati().add(VariabiliGlobali.getInstance().getVideoVisualizzato());
                    VariabiliGlobali.getInstance().setIndiceVideo(VariabiliGlobali.getInstance().getVideoVisualizzati().size() - 1);

                    ImageView mImageView = VariabiliGlobali.getInstance().getiView();

                    StrutturaFiles sf = new StrutturaFiles();
                    sf.setTipologia("2");
                    sf.setNomeFile(NomeMultimedia);
                    sf.setDimeFile(Dimensioni);
                    sf.setDataFile(Data);
                    sf.setCategoria(idCategoria);
                    VariabiliGlobali.getInstance().setVideoCaricato(sf);

                    String path = VariabiliGlobali.getInstance().getPercorsoURL() + "/" + sCategoria + "/" + Thumb.replace("\\", "/");

                    Picasso.get().load(path).placeholder(R.drawable.progress_animation).into(mImageView);
                }

                VariabiliGlobali.getInstance().getImgPlayVideo().setVisibility(LinearLayout.GONE);
                VariabiliGlobali.getInstance().getLaySettingsPanel().setVisibility(LinearLayout.GONE);
                VariabiliGlobali.getInstance().getiView().setVisibility(LinearLayout.VISIBLE);
                VariabiliGlobali.getInstance().getLayGriglia().setVisibility(LinearLayout.GONE);
                VariabiliGlobali.getInstance().getImgRefresh().setVisibility(LinearLayout.GONE);
                VariabiliGlobali.getInstance().getLayScroller().setVisibility(LinearLayout.VISIBLE);

                db_dati db = new db_dati();
                db.ScriveVisti(Long.toString(prossimo), VariabiliGlobali.getInstance().getModalita(), Integer.toString(idCategoria));

                VariabiliGlobali.getInstance().getLayRicerca().setVisibility(LinearLayout.GONE);

                Utility.getInstance().riempieSpinner();
                Utility.getInstance().ScriveInformazioni();
            }
        });

        return convertView;
    }
}
