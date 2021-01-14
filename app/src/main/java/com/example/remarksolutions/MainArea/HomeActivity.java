package com.example.remarksolutions.MainArea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.remarksolutions.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.Objects;

import javax.annotation.Nullable;

public class HomeActivity extends AppCompatActivity {

    public static final String TAG = "Home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final TextView textView = findViewById(R.id.tvHomeCoins);
        int coins;
        Toolbar toolbar =findViewById(R.id.myToolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


        firebaseFirestore.collection("USERS").document(Objects.requireNonNull(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber())).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    textView.setText(snapshot.get("coins").toString());
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.home_bottomNavigation);
        NavController navController = Navigation.findNavController(this, R.id.host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }
}
