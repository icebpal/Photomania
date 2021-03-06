package edu.illinois.finalproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;

/**
 * Main activity for the app. Allows the user to access all the primary functions: Taking photos,
 * editing photos, sharing photos, and viewing the gallery which contains shared photos.
 */
public class MainActivity extends AppCompatActivity {

    final int REQUEST_IMAGE_CAPTURE = 1;
    final int PICK_IMAGE = 2;
    final int SHARE_IMAGE = 3;
    private String urlImage;
    private ImageView imageView;
    private StorageReference mStorageRef;

    /**
     * onCreate method that runs at the beginning of the app, setting up all the buttons and views
     * for the MainActivity.
     *
     * @param savedInstanceState the last state of the app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        this.imageView = (ImageView) findViewById(R.id.thumbImage);
        Button takePhoto = (Button) findViewById(R.id.takePhoto);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        Button uploadPhoto = (Button) findViewById(R.id.uploadPhoto);
        uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        Button editPhoto = (Button) findViewById(R.id.editPhoto);
        editPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Context context = v.getContext();
                Intent editPhotoIntent = new Intent(context, EditPhotoActivity.class);
                editPhotoIntent.putExtra("urlstring", urlImage);
                startActivity(editPhotoIntent);
            }
        });

        Button sharePhoto = (Button) findViewById(R.id.sharePhoto);
        sharePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SHARE_IMAGE);
            }
        });

        Button viewGallery = (Button) findViewById(R.id.viewGalleryButton);
        viewGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();
                Intent galleryIntent = new Intent(context, Gallery.class);

                context.startActivity(galleryIntent);
            }
        });
    }

    /**
     * Method to run after the main activity ends, which in this case will be after the user has
     * taken, uploaded a picture, or chosen an image to share.
     * Then this will get that picture and display it on the screen, sharing it onto Firebase
     * Database as well if that is what the user chose to do.
     *
     * @param requestCode code for whether it was a picture being taken or an upload
     * @param resultCode code checking that the picture was taken/uploaded successfully
     * @param data the intent of the picture taken/uploaded
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);
                StorageReference imageRef = mStorageRef.child(imageUri.toString());
                imageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        urlImage = downloadUrl.toString();
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == SHARE_IMAGE && resultCode == RESULT_OK) {
            final Uri imageUri = data.getData();
            StorageReference imageRef = mStorageRef.child(imageUri.toString());
            imageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    String urlImage = downloadUrl.toString();

                    Glide.with(MainActivity.this).load(urlImage).into(imageView);

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("PictureURLS");

                    Random random = new Random();
                    int randomNum = random.nextInt(10);
                    myRef.child(Integer.toString(randomNum)).setValue(urlImage);
                }
            });
        }
    }
}
