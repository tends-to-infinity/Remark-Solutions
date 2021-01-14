package com.example.remarksolutions.MainArea.RedeemFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.remarksolutions.Models.CouponModel;
import com.example.remarksolutions.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RedeemParentRVAdapter extends RecyclerView.Adapter<RedeemParentRVAdapter.RedeemParentViewHolder> {

    ArrayList<String> titlestr;
    Context context;
    int size;

    public RedeemParentRVAdapter(ArrayList<String> titlestr, Context context) {
        this.titlestr = titlestr;
        this.context = context;
        size= titlestr.size();
    }

    public static class RedeemParentViewHolder extends RecyclerView.ViewHolder
    {

        TextView redeemParentRVTitle;
        RecyclerView couponRecyclerView;

        public RedeemParentViewHolder(@NonNull View itemView) {
            super(itemView);
            redeemParentRVTitle = itemView.findViewById(R.id.tvRedeemRVParentTitle);
            couponRecyclerView=itemView.findViewById(R.id.rvRedeemchildrv);

        }
    }

    @NonNull
    @Override
    public RedeemParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.parentrvredeem_layout, parent, false);
        RedeemParentViewHolder redeemParentViewHolder=new RedeemParentViewHolder(v);
        return redeemParentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RedeemParentViewHolder holder, final int position) {


        final ArrayList<CouponModel> couponModelArrayList = new ArrayList<>();
        FirebaseFirestore.getInstance()
                .collection("DEALS")
                .whereEqualTo("category",titlestr.get(position))
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                        for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                        {
                            couponModelArrayList.add(new CouponModel(documentSnapshot));
                        }
                        if (couponModelArrayList.size()==0)
                        {
                            notifyItemRemoved(position);
                            size--;
                        }
                        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
                        holder.couponRecyclerView.setLayoutManager(layoutManager);
                        holder.couponRecyclerView.setHasFixedSize(true);
                        CouponRecyclerAdapter couponRecyclerAdapter = new CouponRecyclerAdapter(couponModelArrayList);
                        holder.couponRecyclerView.setAdapter(couponRecyclerAdapter);

                    }
                });



        holder.redeemParentRVTitle.setText(titlestr.get(position));

    }

    @Override
    public int getItemCount() {
        return size;
    }
}
