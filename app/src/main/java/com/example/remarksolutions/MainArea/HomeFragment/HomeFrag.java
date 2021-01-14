package com.example.remarksolutions.MainArea.HomeFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.remarksolutions.MainActivity;
import com.example.remarksolutions.Models.AdsModel;
import com.example.remarksolutions.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFrag extends Fragment {


    public HomeFrag() {
        // Required empty public constructor
    }

    private static final String TAG = "HomeFrag";

    // special offers



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         final Dialog coupRedeem=new Dialog(view.getContext());
        coupRedeem.setContentView(R.layout.loading_layout);
        coupRedeem.setCanceledOnTouchOutside(false);
        coupRedeem.show();


        final FirebaseFirestore firebaseFirestore =FirebaseFirestore.getInstance();
        final TextView mTextField= view.findViewById(R.id.tvHomefragCounter);
        final Button play = view.findViewById(R.id.btnHomePlay);
        final String[] val = new String[1];
        final String[] curr = new String[1];
//        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().setTimestampsInSnapshotsEnabled(true).build();
//        firebaseFirestore.setFirestoreSettings(settings);
        final long[] diff = new long[1];
        final AdsModel[] adsModel = new AdsModel[1];
        String currentDateString = java.text.DateFormat.getDateInstance().format(new Date());
        final String currentTimeString = java.text.DateFormat.getTimeInstance().format(new Date());
        final Map<String, Object>[] ads = new Map[]{new HashMap<>()};
        firebaseFirestore.collection("DAILY-ADS").document("25-Mar-2020").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                adsModel[0] = documentSnapshot.toObject(AdsModel.class);
                Log.e("Mapaa", adsModel[0].getAds().get("1"));



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Map Error",e.getMessage()+"");

            }
        }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isComplete())
                {



                    firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).collection("AdsRecords").document("25-Mar-2020").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {


                            String last = documentSnapshot.getString("last");
                            Timestamp timestamp;
                            if(last==null)
                            {

                                timestamp=null;
                                check(timestamp,last);


                            }
                            else if (Integer.parseInt(last)==adsModel[0].getAds().size())
                            {

                                mTextField.setText("Everything CaughtUp for Today\nThank You");



                            }
                            else
                            {
                                timestamp = documentSnapshot.getTimestamp(last);
                                check(timestamp,last);



                            }


                        }

                        private void check(Timestamp timestamp, final String last) {
                            coupRedeem.dismiss();

                            if(timestamp==null)
                            {
                                mTextField.setVisibility(View.INVISIBLE);

                                val[0] =adsModel[0].getAds().get("1");
                                curr[0] ="1";
                                playv();
                                Map<String,Object> tri = new HashMap<String, Object>();
                                tri.put("last",0);
                                firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).collection("AdsRecords")
                                        .document("25-Mar-2020").set(tri);


                            }
                            else
                            {
                                if(new Date().getTime()-timestamp.toDate().getTime()>900000)
                                {
                                    mTextField.setVisibility(View.INVISIBLE);

                                    val[0] =adsModel[0].getAds().get(String.valueOf(Integer.parseInt(last)+1));
                                    curr[0] =String.valueOf(Integer.parseInt(last)+1);
                                    playv();
                                }
                                else
                                {
                                    new CountDownTimer(900000-(new Date().getTime()-timestamp.toDate().getTime()), 1000) {

                                        public void onTick(long millisUntilFinished) {
                                            mTextField.setText("Next Video will be available in: " + millisUntilFinished / 1000+" Sec");
                                            play.setEnabled(false);


                                        }

                                        public void onFinish() {
                                            mTextField.setVisibility(View.INVISIBLE);
                                            play.setEnabled(true);
                                            val[0] =adsModel[0].getAds().get(String.valueOf(Integer.parseInt(last)+1));
                                            curr[0] =String.valueOf(Integer.parseInt(last)+1);
                                            playv();

                                        }
                                    }.start();
                                }
                            }



                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("diffError",e.getMessage());
                        }
                    });


                }
            }

            private void playv() {
                mTextField.setText("Click To Play");
                mTextField.setVisibility(View.VISIBLE);

                Toast.makeText(view.getContext(), ""+val[0]+"curr==="+curr[0], Toast.LENGTH_SHORT).show();


                play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(),AdsPlayerActivity.class);
                        intent.putExtra("ref",val[0]);
                        intent.putExtra("curr",curr[0]);
                        startActivity(intent);

                    }
                });
            }
        });
        Log.e("time",new Date().toString());
        Log.e("map",ads[0].toString());















    }


}















