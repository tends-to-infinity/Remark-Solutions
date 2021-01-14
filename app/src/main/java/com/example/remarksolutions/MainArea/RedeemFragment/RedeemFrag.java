package com.example.remarksolutions.MainArea.RedeemFragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.remarksolutions.Login.CouponCategory;
import com.example.remarksolutions.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RedeemFrag extends Fragment {


    public RedeemFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_redeem, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        RecyclerView recyclerViewparent = view.findViewById(R.id.RVRedeemParentRV);
        RedeemParentRVAdapter redeemParentRVAdapter = new RedeemParentRVAdapter(new CouponCategory().getB(),view.getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false);
        recyclerViewparent.setLayoutManager(layoutManager);
        recyclerViewparent.hasFixedSize();
        recyclerViewparent.setAdapter(redeemParentRVAdapter);



    }
}
