package com.example.remarksolutions.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.remarksolutions.R;

public class RegisterRewardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_reward);

        final EditText etTest = findViewById(R.id.etRewardTest);
        final Button btnTest = findViewById(R.id.btnRewardTest);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent res= new Intent();
                res.putExtra("Res",etTest.getText().toString());
                setResult(RESULT_OK,res);
                finish();
            }
        });
    }
}
