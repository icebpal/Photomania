package edu.illinois.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class EditPhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);

        ImageView imageViewForPhotoToEdit = (ImageView) findViewById(R.id.photoToEditView);

        Bundle extras = getIntent().getExtras();
        String urlForImage = extras.getString("urlstring");

        Glide.with(EditPhotoActivity.this)
                .load(urlForImage)
                .into(imageViewForPhotoToEdit);

    }
}
