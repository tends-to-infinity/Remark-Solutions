package com.example.remarksolutions.MainArea.RedeemFragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.remarksolutions.Login.CouponCategory;
import com.example.remarksolutions.MainArea.ExploreFragment.ShopRecycleAdapter;
import com.example.remarksolutions.Models.CouponModel;
import com.example.remarksolutions.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class CouponRecyclerAdapter extends RecyclerView.Adapter<CouponRecyclerAdapter.CouponRecyclerViewHolder> {

    ArrayList<CouponModel> couponModelArrayList;

    public CouponRecyclerAdapter(ArrayList<CouponModel> couponModelArrayList) {
        this.couponModelArrayList = couponModelArrayList;
    }

    public class CouponRecyclerViewHolder extends RecyclerView.ViewHolder
    {

        TextView title,desc,quantity;

        public CouponRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvCouponTitle);
            desc= itemView.findViewById(R.id.tvRedeemDescription);
            quantity=itemView.findViewById(R.id.tvCouponCoin);
        }
    }


    @NonNull
    @Override
    public CouponRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.childrv_redeem_layout,parent,false);

        return new CouponRecyclerAdapter.CouponRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CouponRecyclerViewHolder holder, final int position) {

        holder.title.setText(couponModelArrayList.get(position).getTitle());
        holder.desc.setText(couponModelArrayList.get(position).getDescription());
        final CouponCategory couponCategory = new CouponCategory();
        holder.quantity.setText(""+couponCategory.getAmt()[couponCategory.getB().indexOf(couponModelArrayList.get(position).getCategory())]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                showpopup(couponModelArrayList.get(position),view);
            }
        });

    }

    private void showpopup(final CouponModel couponModel, final View tview) {



        final Dialog coupRedeem=new Dialog(tview.getContext());
        coupRedeem.setContentView(R.layout.popup_coupon_redeem);
        ImageView close = coupRedeem.findViewById(R.id.imageView4);
        final TextView mTitle, mDescription, mCoins;
        final RelativeLayout relativeLayout=coupRedeem.findViewById(R.id.rlCoinLayout);
        mTitle = coupRedeem.findViewById(R.id.tvPopupCouponTitle);
        mDescription= coupRedeem.findViewById(R.id.tvPopupDescription);
        mCoins=coupRedeem.findViewById(R.id.tvCouponCoin);
        mTitle.setText(couponModel.getTitle());
        mDescription.setText(couponModel.getDescription());
        final CouponCategory couponCategory = new CouponCategory();
        mCoins.setText(""+couponCategory.getAmt()[couponCategory.getB().indexOf(couponModel.getCategory())]);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coupRedeem.dismiss();
            }

        });

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Context context =view.getContext();
                relativeLayout.setEnabled(false);




                final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                final DocumentReference documentReference = firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());


                firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).collection("COUPON")
                        .document(couponModel.getCoupid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists())
                        {
                            Toast.makeText(context, "Already", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {

                            firebaseFirestore.runTransaction(new Transaction.Function<Void>() {


                                @Override
                                public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                                    DocumentSnapshot snapshot = transaction.get(documentReference);
                                    int cCOins=Integer.parseInt(snapshot.get("coins").toString());
                                    int nCoins=cCOins;



                                    if (cCOins>=(Integer.parseInt(mCoins.getText().toString())))
                                    {
                                        nCoins=cCOins-(Integer.parseInt(mCoins.getText().toString()));
                                        firebaseFirestore.collection("DEALS").document(couponModel.getCoupid()).update("quantity",couponModel.getQuantity()-1);
                                        final int finalNCoins = nCoins;
                                        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).collection("COUPON")
                                                .document(couponModel.getCoupid()).set(couponModel)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isComplete())
                                                        {
                                                            Toast.makeText(view.getContext(), "updated", Toast.LENGTH_SHORT).show();
                                                            firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).update("coins", finalNCoins);


                                                        }
                                                    }
                                                });
                                        Log.e("yes","Enough");

                                    }
                                    else
                                    {


                                    }


                                    transaction.update(documentReference, "Coins", nCoins);

                                    // Success
                                    return null;
                                }
                            }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("Success", "Transaction success!");
                                    Toast.makeText(view.getContext(), "Transaction success!", Toast.LENGTH_SHORT).show();
                                    relativeLayout.setEnabled(true);
                                    coupRedeem.dismiss();

                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("Failed", "Transaction failure.",e);
                                            Toast.makeText(view.getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                            coupRedeem.dismiss();

                                        }
                                    });


                        }

                    }
                });







            }
        });
        coupRedeem.onWindowFocusChanged(true);
        coupRedeem.show();

    }

    @Override
    public int getItemCount() {
        return couponModelArrayList.size();
    }
}
