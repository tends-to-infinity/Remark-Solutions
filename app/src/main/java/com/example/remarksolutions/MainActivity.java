package com.example.remarksolutions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.remarksolutions.Login.LoginActivity;
import com.example.remarksolutions.MainArea.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    static final int PERMISSION_READ_State = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET,Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ_State);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch (requestCode) {
            case PERMISSION_READ_State: {
                if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED&&grantResults[1] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "Yessss", Toast.LENGTH_SHORT).show();
                    Log.e("123","Per");
                    updatee();

                }
                else {
                    Toast.makeText(this, "Noi permission", Toast.LENGTH_SHORT).show();
                }

            }
        }



    }

    private void updatee() {
        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
        else
        {
            startActivity(new Intent(this, LoginActivity.class));
            finish();

        }

    }
}
