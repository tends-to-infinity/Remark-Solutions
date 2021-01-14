package com.example.remarksolutions.MainArea.ExploreFragment.ShopLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.remarksolutions.Models.ShopModel;
import com.example.remarksolutions.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ShopLayoutActivity extends AppCompatActivity {
    RecyclerView recyclerView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_layout);

        final TextView name,desc;
        name=findViewById(R.id.tvShopLayoutShopName);
        desc=findViewById(R.id.tvShopLayoutShopDescription);
        recyclerView = findViewById(R.id.rvShopLayoutPhotos);
        FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
        firebaseFirestore.collection("SHOP").document(getIntent().getStringExtra("ID")).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ShopModel shopModel = documentSnapshot.toObject(ShopModel.class);
                shopModel.setShopID(documentSnapshot.getId());
                name.setText(shopModel.name);
                desc.setText(shopModel.description);
                recyclerView.setLayoutManager(new LinearLayoutManager(ShopLayoutActivity.this,RecyclerView.HORIZONTAL,false));
                ShopPhotoAdapter shopPhotoAdapter = new ShopPhotoAdapter(shopModel.getImages());
                recyclerView.setAdapter(shopPhotoAdapter);
                recyclerView.setHasFixedSize(true);


            }
        });
    }
}
