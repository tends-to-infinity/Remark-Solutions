package com.example.remarksolutions.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.remarksolutions.MainActivity;
import com.example.remarksolutions.Models.UserModel;
import com.example.remarksolutions.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {


    TextView resend;
    String number,id;
    Long pho;
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    Spinner  locality;
    UserModel userModel;
    Locality lc= new Locality();
    String loca;
    EditText name,phone,otp,age;
    Button next,submit;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        mAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        age=findViewById(R.id.etLoginAge);
        resend = findViewById(R.id.tvLoginResend);
//        shopRegister=findViewById(R.id.tvLoginShopRegister);
//        coupRegister=findViewById(R.id.tvLoginCouponRegister);
        next = findViewById(R.id.btnLoginNext);
        submit= findViewById(R.id.btnLoginSubmit);
        name=findViewById(R.id.etLoginName);
        phone = findViewById(R.id.etLoginMobileNo);
        otp = findViewById(R.id.etLoginOTP);
        locality=findViewById(R.id.spLoginLocality);

        ArrayAdapter<String> arrayAdapter =new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,lc.getLocList());
        locality.setAdapter(arrayAdapter);
        locality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loca=lc.getA()[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(phone.getText().toString())||TextUtils.isEmpty(name.getText().toString())||TextUtils.isEmpty(age.getText().toString())||phone.getText().toString().replace(" ", "").length() != 10) {
                    if (TextUtils.isEmpty(phone.getText().toString())) {
                        Toast.makeText(view.getContext(), "Enter A number", Toast.LENGTH_SHORT).show();
                    }
                    else if (phone.getText().toString().replace(" ", "").length() != 10) {
                        Toast.makeText(view.getContext(), "Enter a Valid Number", Toast.LENGTH_SHORT).show();
                    }
                    if (TextUtils.isEmpty(name.getText().toString())) {
                        Toast.makeText(view.getContext(), "Enter A Name", Toast.LENGTH_SHORT).show();
                    }
                    if (TextUtils.isEmpty(age.getText().toString())) {
                        Toast.makeText(view.getContext(), "Enter A number", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    pho=Long.parseLong(phone.getText().toString());
                    otp.setVisibility(View.VISIBLE);
                    next.setVisibility(View.GONE);
                    submit.setVisibility(View.VISIBLE);
                    resend.setVisibility(View.VISIBLE);




                     userModel= new UserModel(name.getText().toString(),loca,500,Long.parseLong(phone.getText().toString()),Integer.parseInt(age.getText().toString()));

                    number = "+91"+phone.getText().toString();
                    sendVeri();




                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            if(TextUtils.isEmpty(otp.getText().toString()))
                            {
                                Toast.makeText(LoginActivity.this, "Enter OTP", Toast.LENGTH_SHORT).show();
                            }
                            else if(otp.getText().toString().replace(" ","").length()!=6)
                            {
                                Toast.makeText(LoginActivity.this, "Enter Correct", Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(id, otp.getText().toString().replace(" ",""));
                                signInWithPhoneAuthCredential(credential);


                            }






                        }
                    });




                }




            }
        });


//        shopRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(view.getContext(),ShopRegisterActivity.class));
//            }
//        });
//        coupRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(view.getContext(),CouponRegisterActivity.class));
//            }
//        });





    }

    private void sendVeri() {


        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
                if (pho!= Long.parseLong(phone.getText().toString()))
                {
                    Log.w("pho",pho.toString());
                    otp.setVisibility(View.GONE);
                    next.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.GONE);
                    resend.setVisibility(View.GONE);
                }
                resend.setText(""+l/1000);
                resend.setEnabled(false);

            }

            @Override
            public void onFinish() {
                resend.setText("Resend");
                resend.setEnabled(true);

            }
        }.start();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        id=s;
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);

                    }



                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(LoginActivity.this, "Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Error",""+e.getMessage());


                    }
                });


    }



    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            firebaseFirestore.collection("USERS").document(number).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(!documentSnapshot.contains("name"))
                                    {
                                        firebaseFirestore.collection("USERS").document(number).set(userModel);
                                        Toast.makeText(LoginActivity.this, "New User", Toast.LENGTH_SHORT).show();
                                        Log.e("Status","new");
                                    }
                                    else
                                    {
                                        Toast.makeText(LoginActivity.this, "New User", Toast.LENGTH_SHORT).show();
                                        Log.e("Status","old");
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("faill",e.getMessage());

                                }
                            });

                            Intent intent =new Intent(LoginActivity.this, MainActivity.class);




                            startActivity(intent);
                            finish();




                        } else {
                            Toast.makeText(LoginActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();
                            Log.e("login","failed");


                        }
                    }
                });
    }




}
