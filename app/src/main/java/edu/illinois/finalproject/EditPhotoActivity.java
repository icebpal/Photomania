package edu.illinois.finalproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * EditPhotoActivity is the activity started when a user wishes to edit the photo they uploaded to
 * the app. It allows the user to use any combination of the filters and then lets them save their
 * photo back to their device.
 */
public class EditPhotoActivity extends AppCompatActivity {

    public ImageView imageViewForPhotoToEdit;

    /**
     * onCreate method that runs when this activity is launched. It sets up the imageView with the
     * photo to edit and sets up a lot of the buttons functions.
     *
     * @param savedInstanceState Last state of this activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);

        imageViewForPhotoToEdit = (ImageView) findViewById(R.id.photoToEditView);

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

        Button blue = (Button) findViewById(R.id.blueFilterButton);
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColorFilter(0, 0, 120);
            }
        });

        Button red = (Button) findViewById(R.id.redFilterButton);
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColorFilter(150, 0, 0);
            }
        });

        Button purple = (Button) findViewById(R.id.purpleFilterButton);
        purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColorFilter(150, 0, 150);
            }
        });

        Button sepia = (Button) findViewById(R.id.sepiaFilter);
        sepia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColorFilter(142, 66, 20);
            }
        });
    }

    /**
     * OnClick method for the black and white filter button. Desaturates the filter, removing all
     * color from it and puts that filter on the photo.
     * Got this code from:
     * // https://stackoverflow.com/questions/3373860/convert-a-bitmap-to-grayscale-in-android
     *
     * @param v View of app
     */
    public void setBlackAndWhiteFilter(View v) {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);

        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        imageViewForPhotoToEdit.setColorFilter(filter);
    }

    /**
     * Helper method that will set a color filter on the image with specified RGB values, ran in some
     * onClick listeners.
     *
     * @param red red value
     * @param green green value
     * @param blue blue value
     */
    private void setColorFilter(int red, int green, int blue) {
        imageViewForPhotoToEdit.setColorFilter(Color.argb(140, red, green, blue));
    }

    /**
     * OnClick method for the Gradient filter button which changes the pixels of the photo to be
     * either fully black or white creating a "gradient" filter.
     * Got this code from: https://gist.github.com/firzan/5848737
     *
     * @param v View of the app
     */
    public void setGradientFilter(View v) {
        imageViewForPhotoToEdit.clearColorFilter();
        imageViewForPhotoToEdit.buildDrawingCache();
        Bitmap originalPhoto = imageViewForPhotoToEdit.getDrawingCache();

        int width = originalPhoto.getWidth();
        int height = originalPhoto.getHeight();

        Bitmap bmOut = Bitmap.createBitmap(width, height, originalPhoto.getConfig());

        int A, R, G, B;
        int pixel;
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
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

    /**
     * OnClick method for the Save button. Saves the edited photo to the device and then goes back
     * to the MainActivity.
     *
     * @param v View of the app.
     */
    public void savePhoto(View v) {
        final Context context = v.getContext();
        final Intent intent = new Intent(context, MainActivity.class);
        imageViewForPhotoToEdit.buildDrawingCache();
        Bitmap photoToSave = imageViewForPhotoToEdit.getDrawingCache();

        MediaStore.Images.Media.insertImage(getContentResolver(), photoToSave, "Photo", "Description");

        context.startActivity(intent);
    }
}
