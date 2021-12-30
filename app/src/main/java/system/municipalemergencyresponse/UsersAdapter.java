package system.municipalemergencyresponse;

import android.content.Context;
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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static system.municipalemergencyresponse.MainActivity.db;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    private List<UsersList> myListList;
    private Context ct;

    public UsersAdapter(List<UsersList> myListList, Context ct) {
        this.myListList = myListList;
        this.ct = ct;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.users_rec,parent,false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UsersList myList=myListList.get(position);

        holder.userName.setText(myList.getName());
        holder.userName.setSelected(true);


        if (myList.getAttempts() != null){

            if (Integer.valueOf(myList.getAttempts().intValue()) <= 0){
                holder.userStatus.setVisibility(View.VISIBLE);

                holder.userStatus.setText("Banned");
                holder.enableButton.setVisibility(View.VISIBLE);
                holder.userStatus.setTextColor(ContextCompat.getColor(ct,R.color.red));

            }else{
                holder.userStatus.setVisibility(View.VISIBLE);
                holder.enableButton.setVisibility(View.GONE);

                if (Integer.valueOf(myList.getAttempts().intValue()) > 1){
                    holder.userStatus.setText(String.valueOf(Integer.valueOf(myList.getAttempts().intValue())) + " attempts");
                    holder.userStatus.setTextColor(ContextCompat.getColor(ct,R.color.red));
                }else{
                    holder.userStatus.setText(String.valueOf(Integer.valueOf(myList.getAttempts().intValue())) + " attempt");
                    holder.userStatus.setTextColor(ContextCompat.getColor(ct,R.color.red));
                }


            }

        }else{
            holder.userStatus.setVisibility(View.GONE);
            holder.enableButton.setVisibility(View.GONE);
        }


        if (myList.getType().matches("responder")){
            holder.userType.setText("Responder");
        }else{
            holder.userType.setText("Nonresponder");
        }

        holder.enableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference docRef = db.collection("Users").document(myList.getId());


                Map<String,Object> updates = new HashMap<>();
                updates.put("attempts", FieldValue.delete());

                docRef.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ct, "Success", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });



        //holder.user.setText(DateFormat.format("MMM dd yyyy hh:mm aa",new Date(String.valueOf(myList.getDate()))));

        if (!myList.getClassification().matches("nonresponder")){

            if (myList.getClassification().matches("Fire")){
                holder.userImage.setImageResource(R.drawable.flame);

            }else if (myList.getClassification().matches("Medical")){
                holder.userImage.setImageResource(R.drawable.hospital);
            }
            else if (myList.getClassification().matches("Police")){
                holder.userImage.setImageResource(R.drawable.police);
            }
            else if (myList.getClassification().matches("Traffic")){
                holder.userImage.setImageResource(R.drawable.traffic);
            }
            else if (myList.getClassification().matches("Others")){
                holder.userImage.setImageResource(R.drawable.others);
            }

        }else{
            holder.userImage.setImageResource(R.drawable.account);
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });





    }



    @Override
    public int getItemCount() {
        return myListList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView userImage;
        private TextView userName,userType,userStatus;
        private Button enableButton;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userImage=(ImageView)itemView.findViewById(R.id.userImage);
            userName=(TextView)itemView.findViewById(R.id.userName);
            userType=(TextView)itemView.findViewById(R.id.userType);
            userStatus=(TextView)itemView.findViewById(R.id.userStatus);
            enableButton=(Button)itemView.findViewById(R.id.enableButton);





        }
    }
}