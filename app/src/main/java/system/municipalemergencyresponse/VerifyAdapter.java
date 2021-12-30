package system.municipalemergencyresponse;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.List;

public class VerifyAdapter extends RecyclerView.Adapter<VerifyAdapter.ViewHolder> {
    private List<VerifyList> myListList;
    private Context ct;

    public VerifyAdapter(List<VerifyList> myListList, Context ct) {
        this.myListList = myListList;
        this.ct = ct;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.verify_rec,parent,false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VerifyList myList=myListList.get(position);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        holder.verifyName.setText(myList.getName());
        holder.verifyName.setSelected(true);
        holder.verifyClass.setText(myList.getClassification());

        holder.verifyDate.setText(DateFormat.format("MMM dd yyyy hh:mm aa",new Date(String.valueOf(myList.getDate()))));


        if (myList.getClassification().matches("Fire")){
            holder.verifyImage.setImageResource(R.drawable.flame);

        }else if (myList.getClassification().matches("Medical")){
            holder.verifyImage.setImageResource(R.drawable.hospital);
        }
        else if (myList.getClassification().matches("Police")){
            holder.verifyImage.setImageResource(R.drawable.police);
        }
        else if (myList.getClassification().matches("Traffic")){
            holder.verifyImage.setImageResource(R.drawable.traffic);
        }
        else if (myList.getClassification().matches("Others")){
            holder.verifyImage.setImageResource(R.drawable.others);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(ct);

                MainActivity.myLayout = MainActivity.inflater.inflate(R.layout.verification_user_box, null);



                TextView verificationNumber = (TextView) MainActivity.myLayout.findViewById(R.id.userType);
                TextView verificationType = (TextView) MainActivity.myLayout.findViewById(R.id.verificationType);
                TextView verificationDate = (TextView) MainActivity.myLayout.findViewById(R.id.verificationDate);
                TextView verificationName = (TextView) MainActivity.myLayout.findViewById(R.id.verificationName);
                TextView verificationAddress = (TextView) MainActivity.myLayout.findViewById(R.id.verificationAddress);
                TextView verificationAge = (TextView) MainActivity.myLayout.findViewById(R.id.verificationAge);
                TextView verificationDocu = (TextView) MainActivity.myLayout.findViewById(R.id.verificationDocu);
                TextView verificationSelfie = (TextView) MainActivity.myLayout.findViewById(R.id.verificationSelfie);
                ImageView verificationImage = (ImageView) MainActivity.myLayout.findViewById(R.id.verificationImage);

                Button verificationApprove=(Button)MainActivity.myLayout.findViewById(R.id.verificationApprove);
                Button verificationReject=(Button)MainActivity.myLayout.findViewById(R.id.verificationReject);

                verificationNumber.setText(myList.getPhone());
                verificationType.setText(myList.getClassification());
                verificationDate.setText(DateFormat.format("MMM dd yyyy hh:mm aa",new Date(String.valueOf(myList.getDate()))));
                verificationName.setText(myList.getName());
                verificationAddress.setText(myList.getAddress());
                verificationAge.setText(myList.getAge());

                if (myList.getClassification().matches("Fire")){
                    verificationImage.setImageResource(R.drawable.flame);

                }else if (myList.getClassification().matches("Medical")){
                    verificationImage.setImageResource(R.drawable.hospital);
                }
                else if (myList.getClassification().matches("Police")){
                    verificationImage.setImageResource(R.drawable.police);
                }
                else if (myList.getClassification().matches("Traffic")){
                    verificationImage.setImageResource(R.drawable.traffic);
                }
                else if (myList.getClassification().matches("Others")){
                    verificationImage.setImageResource(R.drawable.others);
                }

                verificationDocu.setPaintFlags(verificationDocu.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                verificationSelfie.setPaintFlags(verificationSelfie.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


                verificationDocu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     //   Toast.makeText(ct, "users/"+myList.getId()+"/document.jpg", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ct, ImageViewer.class);
                        intent.putExtra("url","users/"+myList.getId()+"/document.jpg");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ct.startActivity(intent);

                    }
                });

                verificationSelfie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ct, ImageViewer.class);
                        intent.putExtra("url","users/"+myList.getId()+"/selfie.jpg");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ct.startActivity(intent);
                    }
                });

                verificationApprove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        db.collection("Users").document(myList.getId())
                                .update("verified", true)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(ct, "Success", Toast.LENGTH_SHORT).show();
                                        MainActivity.verifyDialog.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ct, e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                                    }
                                });


                    }
                });

                verificationReject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        db.collection("Users").document(myList.getId())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(ct, "Rejected", Toast.LENGTH_SHORT).show();
                                        MainActivity.verifyDialog.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ct, e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                                    }
                                });


                    }
                });





                builder.setView(MainActivity.myLayout);
                MainActivity.verifyDialog = builder.create();

                MainActivity.verifyDialog.setCancelable(true);
                MainActivity.verifyDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                MainActivity.verifyDialog.show();


            }
        });





    }



    @Override
    public int getItemCount() {
        return myListList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView verifyImage;
        private TextView verifyClass,verifyName,verifyDate;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            verifyImage=(ImageView)itemView.findViewById(R.id.verifyImage);
            verifyClass=(TextView)itemView.findViewById(R.id.verificationType);
            verifyName=(TextView)itemView.findViewById(R.id.verificationName);
            verifyDate=(TextView)itemView.findViewById(R.id.userType);






        }
    }
}