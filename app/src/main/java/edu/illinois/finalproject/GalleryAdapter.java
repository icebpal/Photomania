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
 * GalleryAdapter to use for the Gallery RecyclerView. Contains all the methods for making the
 * RecyclerView contain the correct images.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private List<String> galleryPhotoList = new ArrayList<>();

    /**
     * Constructor that takes a list of urls for the photos to display in the RecyclerView and adds
     * them to the list in the adapter.
     *
     * @param galleryPhotos List of url for the photos.
     */
    GalleryAdapter(List<String> galleryPhotos) {
        galleryPhotoList.addAll(galleryPhotos);
    }

    /**
     * Method that runs on the create of the RecyclerView, it inflates the specific layout to be
     * used for the view and returns its ViewHolder.
     *
     * @param parent Parent ViewGroup
     * @param viewType viewType
     * @return the new ViewHolder for the RecyclerView
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View galleryItem = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.gallery_layout, parent, false);

        return new ViewHolder(galleryItem);
    }

    /**
     * Sets the photo into the ImageView for the RecyclerView using the respective URL for the photo
     *
     * @param holder The ViewHolder for the View that has the imageView.
     * @param position Position in the list to get the right photo.
     */
    @Override
    public void onBindViewHolder(GalleryAdapter.ViewHolder holder, int position) {
        final String photo1 = galleryPhotoList.get(position);

        Context context1 = holder.galleryPhoto1View.getContext();

        Glide.with(context1).load(photo1).into(holder.galleryPhoto1View);
    }

    /**
     * Gets the total count of URLs for photos in the list.
     *
     * @return The number of URLs in the list.
     */
    @Override
    public int getItemCount() {
        return galleryPhotoList.size();
    }

    /**
     * ViewHolder class which contains and sets all the right ImageViews and such from the XML to
     * the code.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public ImageView galleryPhoto1View;

        /**
         * Constructor that assigns all ImageViews to their respective imageViews in the layout by
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
