package com.looigi.loovf.griglia;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.looigi.loovf.DialogMessaggio;
import com.looigi.loovf.R;
import com.looigi.loovf.StrutturaFiles;
import com.looigi.loovf.Utility;
import com.looigi.loovf.VariabiliGlobali;
import com.looigi.loovf.db_locale.db_dati;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.ViewHolder> {
    private ArrayList<ImageListForRecycler> galleryList;
    private Context context;
    // private final View.OnClickListener mOnClickListener = new MyOnClickListener();

    public AdapterRecyclerView(Context context, ArrayList<ImageListForRecycler> galleryList) {
        this.galleryList = galleryList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position, List<Object> payloads) {
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DialogMessaggio.getInstance().show(VariabiliGlobali.getInstance().getContext(),
                //         galleryList.get(position).getImage_id(),
                //         false,
                //         "looVF",
                //         false
                //         );
                // String[] Rit = galleryList.get(position).getImage_URL().split("§");
//
                // String[] c = Rit[1].split(";");
                // int Categoria = Integer.parseInt(c[3]) - 1;
                // String sCategoria = VariabiliGlobali.getInstance().getCategorieImmagini().get(Categoria);
//
                // String url = VariabiliGlobali.getInstance().getPercorsoURL() + "/" + sCategoria + "/" + c[0];
                VariabiliGlobali.getInstance().setModalita("PHOTO");

                int prossimo=Integer.parseInt(galleryList.get(position).getImage_number());
                VariabiliGlobali.getInstance().setImmagineVisualizzata(prossimo);

                db_dati db = new db_dati();
                db.ScriveVisti(Long.toString(prossimo), VariabiliGlobali.getInstance().getModalita());

                VariabiliGlobali.getInstance().getImmaginiVisualizzate().add(VariabiliGlobali.getInstance().getImmagineVisualizzata());
                VariabiliGlobali.getInstance().setIndiceImmagine(VariabiliGlobali.getInstance().getImmaginiVisualizzate().size() - 1);

                ImageView mImageView = VariabiliGlobali.getInstance().getiView();

                StrutturaFiles sf = new StrutturaFiles();
                sf.setTipologia("1");
                sf.setNomeFile(galleryList.get(position).getImage_title().replace("***PV***", ";"));
                sf.setDimeFile(Long.parseLong(galleryList.get(position).getImage_dime()));
                sf.setDataFile(galleryList.get(position).getImage_date());
                sf.setCategoria(Integer.parseInt(galleryList.get(position).getImage_category()));
                VariabiliGlobali.getInstance().setImmagineCaricata(sf);

                Picasso.get().load(galleryList.get(position).getImage_URL()).placeholder(R.drawable.progress_animation).into(mImageView);

                VariabiliGlobali.getInstance().getImgPlayVideo().setVisibility(LinearLayout.GONE);
                VariabiliGlobali.getInstance().getLaySettings().setVisibility(LinearLayout.GONE);
                VariabiliGlobali.getInstance().getiView().setVisibility(LinearLayout.VISIBLE);
                VariabiliGlobali.getInstance().getLayGriglia().setVisibility(LinearLayout.GONE);
                VariabiliGlobali.getInstance().getImgRefresh().setVisibility(LinearLayout.GONE);
                VariabiliGlobali.getInstance().getLayScroller().setVisibility(LinearLayout.VISIBLE);

                Utility.getInstance().riempieSpinner();
                Utility.getInstance().ScriveInformazioni();
            }
        });
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public AdapterRecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_layout, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterRecyclerView.ViewHolder viewHolder, int i) {
        viewHolder.title.setText(galleryList.get(i).getImage_title());
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.img.setTag(galleryList.get(i).getImage_URL());
        // viewHolder.img.setImageResource((galleryList.get(i).getImage_URL()));
        Picasso.get().load(galleryList.get(i).getImage_URL()).placeholder(R.drawable.progress_animation).into(viewHolder.img);
    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView img;

        public ViewHolder(View view) {
            super(view);

            title = (TextView)view.findViewById(R.id.title);
            img = (ImageView) view.findViewById(R.id.img);
        }
    }
}