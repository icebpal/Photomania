package edu.illinois.finalproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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
        String urlForImage = extras.getString("urlstring");

        Glide.with(EditPhotoActivity.this)
                .load(urlForImage)
                .into(imageViewForPhotoToEdit);

        Button returnPhotoToNormal = (Button) findViewById(R.id.noFilter);
        returnPhotoToNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (originalPhoto != null) {
                    imageViewForPhotoToEdit.setImageBitmap(originalPhoto);
                }
            }
        });

        Button makeBlackAndWhite = (Button) findViewById(R.id.blackAndWhite);
        makeBlackAndWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        Button savePhoto = (Button) findViewById(R.id.savePhoto);
        savePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();
                Intent intent = new Intent(context, MainActivity.class);
                imageViewForPhotoToEdit.buildDrawingCache();
                Bitmap photoToSave = imageViewForPhotoToEdit.getDrawingCache();

                MediaStore.Images.Media.insertImage(getContentResolver(), photoToSave, "Photomania!" , "YourImage");

                context.startActivity(intent);
            }
        });

    }
}
