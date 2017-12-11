package edu.illinois.finalproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class EditPhotoActivity extends AppCompatActivity {

    private Bitmap originalPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);

        final ImageView imageViewForPhotoToEdit = (ImageView) findViewById(R.id.photoToEditView);

        Bundle extras = getIntent().getExtras();
        final String URL_FOR_IMAGE = extras.getString("urlstring");

        Glide.with(EditPhotoActivity.this)
                .load(URL_FOR_IMAGE)
                .into(imageViewForPhotoToEdit);

        Button returnPhotoToNormal = (Button) findViewById(R.id.noFilter);
        returnPhotoToNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(EditPhotoActivity.this).load(URL_FOR_IMAGE).into(imageViewForPhotoToEdit);
                imageViewForPhotoToEdit.clearColorFilter();
            }
        });

        final Button makeBlackAndWhite = (Button) findViewById(R.id.blackAndWhite);
        makeBlackAndWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);

                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                imageViewForPhotoToEdit.setColorFilter(filter);
            }
        });

        Button blue = (Button) findViewById(R.id.blueFilterButton);
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewForPhotoToEdit.setColorFilter(Color.argb(140, 0, 0, 120));
            }
        });

        Button red = (Button) findViewById(R.id.redFilterButton);
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewForPhotoToEdit.setColorFilter(Color.argb(140, 150, 0, 0));
            }
        });

        Button purple = (Button) findViewById(R.id.purpleFilterButton);
        purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewForPhotoToEdit.setColorFilter(Color.argb(140, 150, 0, 150));
            }
        });

        Button gradientFilter = (Button) findViewById(R.id.gradientFilter);
        gradientFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewForPhotoToEdit.clearColorFilter();
                imageViewForPhotoToEdit.buildDrawingCache();
                originalPhoto = imageViewForPhotoToEdit.getDrawingCache();

                int width = originalPhoto.getWidth();
                int height = originalPhoto.getHeight();
                // create output bitmap
                Bitmap bmOut = Bitmap.createBitmap(width, height, originalPhoto.getConfig());
                // color information
                int A, R, G, B;
                int pixel;
                for (int x = 0; x < width; ++x) {
                    for (int y = 0; y < height; ++y) {
                        // get pixel color
                        pixel = originalPhoto.getPixel(x, y);
                        A = Color.alpha(pixel);
                        R = Color.red(pixel);
                        G = Color.green(pixel);
                        B = Color.blue(pixel);
                        int gray = (int) (0.2989 * R + 0.5870 * G + 0.1140 * B);
                        // use 128 as threshold, above -> white, below -> black
                        if (gray > 128) {
                            gray = 255;
                        } else {
                            gray = 0;
                        }
                        // set new pixel color to output bitmap
                        bmOut.setPixel(x, y, Color.argb(A, gray, gray, gray));
                    }
                }
                imageViewForPhotoToEdit.setImageBitmap(bmOut);
            }
        });

        Button sepia = (Button) findViewById(R.id.sepiaFilter);
        sepia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewForPhotoToEdit.setColorFilter(Color.argb(140, 142, 66, 20));
            }
        });

        Button savePhoto = (Button) findViewById(R.id.savePhoto);
        savePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();
                final Intent intent = new Intent(context, MainActivity.class);
                imageViewForPhotoToEdit.buildDrawingCache();
                Bitmap photoToSave = imageViewForPhotoToEdit.getDrawingCache();

                MediaStore.Images.Media.insertImage(getContentResolver(), photoToSave, "Photo", "Description");

                context.startActivity(intent);
            }
        });
    }
}
