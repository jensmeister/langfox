package com.langfox.langfoxandroid.data;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.model.StreamEncoder;
import com.langfox.langfoxandroid.R;
import com.langfox.langfoxandroid.SvgDecoder;
import com.langfox.langfoxandroid.SvgDrawableTranscoder;
import com.langfox.langfoxandroid.SvgSoftwareLayerSetter;
import com.bumptech.glide.Glide;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by pengchengliu on 20/07/2017.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private ArrayList<Language> language;
    private Context context;
    private GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;


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

        //Picasso.with(context).load(language.get(i).getImageURL()).resize(100, 100).into(viewHolder.img_language);
        //reference of usage of Glide
        //https://www.androidhive.info/2016/04/android-glide-image-library-building-image-gallery-app/
        //load svg via Glide
        //https://stackoverflow.com/questions/28215625/androidload-svg-file-from-web-and-show-it-on-image-view
        //https://github.com/bumptech/glide/tree/v3.6.0/samples/svg/src/main/java/com/bumptech/svgsample/app
        requestBuilder = Glide.with(context)
                .using(Glide.buildStreamModelLoader(Uri.class, context), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .placeholder(R.drawable.battle)
                .error(R.drawable.finding)
                .animate(android.R.anim.fade_in)
                .listener(new SvgSoftwareLayerSetter<Uri>());

        //Uri uri = Uri.parse("http://www.clker.com/cliparts/u/Z/2/b/a/6/android-toy-h.svg");
        requestBuilder
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                // SVG cannot be serialized so it's not worth to cache it
                .load(Uri.parse(language.get(i).getImageURL()))
                .into(viewHolder.img_language);
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