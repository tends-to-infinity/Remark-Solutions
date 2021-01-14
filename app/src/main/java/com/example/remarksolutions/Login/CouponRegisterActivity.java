package com.example.remarksolutions.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.remarksolutions.MainActivity;
import com.example.remarksolutions.Models.CouponModel;
import com.example.remarksolutions.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CouponRegisterActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    EditText shopNo,coupTitle,coupDesc,coupQuantity;
    Spinner coupCat;
    Button submit;
    CouponCategory couponCategory = new CouponCategory();
    CouponModel coupon =new CouponModel();
    AutoCompleteTextView place, genders, agegroup;
    Map <String,String> condition = new HashMap<>();
    AgeGroups ageGroups= new AgeGroups();
    Gender gender=new Gender();
    Locality locality= new Locality();
    TextView tvcheck;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_register);

        firebaseFirestore=FirebaseFirestore.getInstance();
        shopNo= findViewById(R.id.etCouponRegisterShopRegPhone);
        coupTitle=findViewById(R.id.etCouponRegisterCouponTitle);
        coupDesc=findViewById(R.id.etCouponRegisterCouponDesc);
        coupCat=findViewById(R.id.spCouponCat);
        submit=findViewById(R.id.btnCouponRegisterSubmit);
        coupQuantity= findViewById(R.id.etCouponRegisterQuantity);

        place=findViewById(R.id.actvCouponRegisterPlace);
        genders=findViewById(R.id.actvCouponRegisterGender);
        agegroup=findViewById(R.id.actvCouponRegisterAgeG);




        ArrayAdapter padapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,locality.getLocList());
        ArrayAdapter aadapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,ageGroups.getB());
        ArrayAdapter gadapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,gender.getB());
        place.setAdapter(padapter);
        genders.setAdapter(gadapter);
        agegroup.setAdapter(aadapter);

        ArrayAdapter<String> arrayAdapter =new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,couponCategory.getA());
        coupCat.setAdapter(arrayAdapter);
        coupCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                coupon.setCategory(couponCategory.getA()[i]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                coupon.setShopNo(shopNo.getText().toString());
                coupon.setTitle(coupTitle.getText().toString());
                coupon.setDescription(coupDesc.getText().toString());
                coupon.setRewardID("null");
                coupon.setQuantity(Integer.parseInt(coupQuantity.getText().toString()));
                condition.put("place",place.getText().toString());
                condition.put("gender",genders.getText().toString());
                condition.put("ageGroup",agegroup.getText().toString());

                coupon.setConditions(condition);
                firebaseFirestore.collection("DEALS").add(coupon).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(CouponRegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CouponRegisterActivity.this, MainActivity.class));
                    }
                });




            }
        });


    }


}
