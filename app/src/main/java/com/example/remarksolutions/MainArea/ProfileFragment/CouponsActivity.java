package com.example.remarksolutions.MainArea.ProfileFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.example.remarksolutions.MainArea.RedeemFragment.CouponRecyclerAdapter;
import com.example.remarksolutions.Models.CouponModel;
import com.example.remarksolutions.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CouponsActivity extends AppCompatActivity {

    ArrayList<CouponModel> couponModelArrayList ;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons);
        couponModelArrayList=new ArrayList<>();

        final RecyclerView recyclerView = findViewById(R.id.rvCouponsCoupons);
        context= this;
        FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).collection("COUPON").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                {
                    couponModelArrayList.add(new CouponModel(documentSnapshot));
                }

                RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                CouponAdapter couponRecyclerAdapter = new CouponAdapter(couponModelArrayList);
                recyclerView.setAdapter(couponRecyclerAdapter);



            }
        });


    }
}
