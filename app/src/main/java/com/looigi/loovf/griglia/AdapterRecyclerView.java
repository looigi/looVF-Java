package com.looigi.loovf.griglia;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.looigi.loovf.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.ViewHolder> {
    private ArrayList<ImageListForRecycler> galleryList;
    private Context context;

    public AdapterRecyclerView(Context context, ArrayList<ImageListForRecycler> galleryList) {
        this.galleryList = galleryList;
        this.context = context;
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
        // viewHolder.img.setImageResource((galleryList.get(i).getImage_ID()));
        Picasso.get().load(galleryList.get(i).getImage_ID()).placeholder(R.drawable.progress_animation).into(viewHolder.img);
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