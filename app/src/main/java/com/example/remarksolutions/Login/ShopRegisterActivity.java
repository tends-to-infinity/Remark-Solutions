package com.example.remarksolutions.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.remarksolutions.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.UUID;

public class ShopRegisterActivity extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    Map<String,String> shopDetail=new HashMap<>();
    EditText name,desc,add,phone;
    Spinner category;
    Button submit, upload;
    ShopCategories sc= new ShopCategories();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_register);



        firebaseFirestore=FirebaseFirestore.getInstance();
        name=findViewById(R.id.etShopRegisterShopName);
        desc=findViewById(R.id.etShopRegisterDescription);
        add=findViewById(R.id.etShopRegisterAddress);
        phone=findViewById(R.id.etShopRegisterPhone);
        category=findViewById(R.id.spShopRegisterShopCategory);
        upload=findViewById(R.id.btnShopRegisterUploadImage);

        submit=findViewById(R.id.btnShopRegisterSubmit);

        ArrayAdapter<String> arrayAdapter =new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,sc.getA());
        category.setAdapter(arrayAdapter);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                shopDetail.put("category",sc.getA()[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImages();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                shopDetail.put("name",name.getText().toString());
                shopDetail.put("description",desc.getText().toString());
                shopDetail.put("address",add.getText().toString());
                shopDetail.put("phone",phone.getText().toString());





                firebaseFirestore.collection("SHOP").document(phone.getText().toString()).set(shopDetail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ShopRegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ShopRegisterActivity.this, "Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    private void chooseImages() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Title"),88);






    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==88&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null)
        {


            uploadImage(data.getData());
        }
        else
        {
        }

    }

    private void uploadImage(Uri filePath) {

        if(filePath!=null)
        {
            final Dialog coupRedeem=new Dialog(this);
            coupRedeem.setContentView(R.layout.loading_layout);
            coupRedeem.setCanceledOnTouchOutside(false);
            coupRedeem.show();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Images/"+ UUID.randomUUID().toString());
            storageReference.putFile(filePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful())
                    {
                        coupRedeem.dismiss();
                        Toast.makeText(ShopRegisterActivity.this, "Done", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    coupRedeem.dismiss();
                    Toast.makeText(ShopRegisterActivity.this, "Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }
        else
        {
            Toast.makeText(this, "HAtt", Toast.LENGTH_SHORT).show();

        }
    }
}
