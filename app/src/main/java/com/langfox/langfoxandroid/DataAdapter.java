package com.langfox.langfoxandroid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by pengchengliu on 20/07/2017.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private ArrayList<Language> language;
    private Context context;

    public DataAdapter(Context context,ArrayList<Language> language) {
        this.language = language;
        this.context = context;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lan_obj_gird_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {

        viewHolder.tv_language.setText(language.get(i).getName());
        Picasso.with(context).load(language.get(i).getImageURL()).resize(100, 100).into(viewHolder.img_language);
    }

    @Override
    public int getItemCount() {
        return language.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_language;
        private ImageView img_language;

        public ViewHolder(View view) {
            super(view);

            tv_language = (TextView)view.findViewById(R.id.info_text);
            img_language = (ImageView) view.findViewById(R.id.info_image);
        }
    }

}