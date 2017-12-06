package edu.illinois.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class Gallery extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        final GalleryAdapter galleryAdapter = new GalleryAdapter();

        final RecyclerView restaurants = (RecyclerView) findViewById(R.id.galleryPhotos);

        restaurants.setAdapter(galleryAdapter);
        restaurants.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}
