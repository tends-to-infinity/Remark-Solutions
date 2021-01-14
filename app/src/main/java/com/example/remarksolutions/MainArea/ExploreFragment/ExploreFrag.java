package com.example.remarksolutions.MainArea.ExploreFragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.remarksolutions.Login.ShopCategories;
import com.example.remarksolutions.MainArea.ExploreFragment.ShopLayout.ShopLayoutActivity;
import com.example.remarksolutions.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFrag extends Fragment {


    public ExploreFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ArrayList shopNames=new ArrayList();
        final ArrayList shopId = new ArrayList();


        final AutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView);
        final FirebaseFirestore firebaseFirestore =FirebaseFirestore.getInstance();
        firebaseFirestore.collection("SHOP").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot document : queryDocumentSnapshots)
                {
                    shopNames.add(document.get("name"));
                    shopId.add(document.getId());

                }
            }
        }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, shopNames);

                autoCompleteTextView.setAdapter(arrayAdapter);

            }
        });
        Button srchbtn= view.findViewById(R.id.btnExploreSearch);
        srchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (shopNames.contains(autoCompleteTextView.getText().toString()))
                {
                    Toast.makeText(view.getContext(), "Present", Toast.LENGTH_SHORT).show();
                    String id = shopId.get(shopNames.indexOf(autoCompleteTextView.getText().toString())).toString();
                    Intent intent = new Intent(view.getContext(),ShopLayoutActivity.class);
                    intent.putExtra("ID",id);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(view.getContext(), "Not Present", Toast.LENGTH_SHORT).show();
                }
            }
        });


        RecyclerView recyclerView =view.findViewById(R.id.rvExploreCategory);
        CategoryRecyclerAdapter categoryRecyclerAdapter =new CategoryRecyclerAdapter(new ShopCategories().getB(),view.getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(categoryRecyclerAdapter);





    }
}
