package com.example.remarksolutions.MainArea.HomeFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.remarksolutions.MainArea.HomeActivity;
import com.example.remarksolutions.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Date;

public class AdsPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_player);

        final VideoView videoView = findViewById(R.id.vvAds);
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference= firebaseStorage.getReferenceFromUrl(getIntent().getStringExtra("ref"));
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                videoView.setVideoURI(uri);
                videoView.requestFocus();
                videoView.start();
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                        final DocumentReference documentReference = firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                        firebaseFirestore.runTransaction(new Transaction.Function<Void>() {


                            @Override
                            public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                                DocumentSnapshot snapshot = transaction.get(documentReference);

                                // Note: this could be done without a transaction
                                //       by updating the population using FieldValue.increment()


                                firebaseFirestore.collection("USERS")
                                        .document(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                                        .collection("AdsRecords")
                                        .document("25-Mar-2020")
                                        .update("last",getIntent().getStringExtra("curr"),getIntent().getStringExtra("curr"),new Date())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Log.e("wo complte","hao");
                                    }
                                });
                                int nCoins = Integer.parseInt(snapshot.get("coins").toString())+10;
                                transaction.update(documentReference, "coins", nCoins);

                                // Success
                                return null;
                            }
                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("Success", "Transaction success!");
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("Failed", "Transaction failure.", e);
                                    }
                        });

                        fini();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdsPlayerActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });





    }

    private void fini() {

        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
