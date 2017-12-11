package edu.illinois.finalproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by icebp on 12/6/2017.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private List<String> galleryPhotoList = new ArrayList<>();

    GalleryAdapter(List<String> galleryPhotos) {
        galleryPhotoList.addAll(galleryPhotos);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View galleryItem = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.gallery_layout, parent, false);

        return new ViewHolder(galleryItem);
    }

    @Override
    public void onBindViewHolder(GalleryAdapter.ViewHolder holder, int position) {
        final String photo1 = galleryPhotoList.get(position);

        Context context1 = holder.galleryPhoto1View.getContext();

        Glide.with(context1).load(photo1).into(holder.galleryPhoto1View);
    }

    @Override
    public int getItemCount() {
        return galleryPhotoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public ImageView galleryPhoto1View;
        public ImageView galleryPhoto2View;

        /**
         * Constructor that assigns all TextViews to their respective textViews in the layout by
         * finding them through their id.
         *
         * @param itemView View of app.
         */
        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.galleryPhoto1View = (ImageView) itemView.findViewById(R.id.galleryImage1);
        }
    }
}
