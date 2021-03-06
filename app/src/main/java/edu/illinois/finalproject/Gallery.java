package edu.illinois.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Gallery class for displaying all shared photos by users.
 */
public class Gallery extends AppCompatActivity {

    /**
     * onCreate method for the Gallery, sets up the RecyclerView with its adapter and puts all the
     * URLs for the images to display into the adapter.
     *
     * @param savedInstanceState last state of the Gallery
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        final RecyclerView gallery = (RecyclerView) findViewById(R.id.galleryPhotos);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("PictureURLS");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> imageURLs = (List<String>) dataSnapshot.getValue();
                gallery.setAdapter(new GalleryAdapter(imageURLs));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        gallery.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}
