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

    private final String URL_FOR_FIRST_PHOTO = "https://firebasestorage.googleapis.com/v0/b/final-project-icebpal.appspot.com/o/content%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A119?alt=media&token=b3bc8928-af8b-496b-afc9-d363aff120b5";
    private final String URL_FOR_SECOND_PHOTO = "https://firebasestorage.googleapis.com/v0/b/final-project-icebpal.appspot.com/o/content%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A76?alt=media&token=fc1dd957-93d1-40d3-bf7e-e9b7c3a74410";

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View galleryItem = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.gallery_layout, parent, false);

        return new ViewHolder(galleryItem);
    }

    public void addGalleryPhoto(String photoURL) {
        galleryPhotoList.add(photoURL);
    }

    @Override
    public void onBindViewHolder(GalleryAdapter.ViewHolder holder, int position) {
        final String photo1 = galleryPhotoList.get(position);
        final String photo2 = URL_FOR_SECOND_PHOTO; // galleryPhotoList.get(position + 1);

        Context context1 = holder.galleryPhoto1View.getContext();
        Context context2 = holder.galleryPhoto2View.getContext();

        Glide.with(context1).load(photo1).into(holder.galleryPhoto1View);
        Glide.with(context2).load(photo2).into(holder.galleryPhoto2View);

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
            this.galleryPhoto2View = (ImageView) itemView.findViewById(R.id.galleryImage2);
        }
    }
}
