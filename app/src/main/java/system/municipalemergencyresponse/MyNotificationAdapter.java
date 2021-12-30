package system.municipalemergencyresponse;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyNotificationAdapter extends RecyclerView.Adapter<MyNotificationAdapter.ViewHolder> {
    private List<MyNotificationList> myListList;
    private Context ct;

    public MyNotificationAdapter(List<MyNotificationList> myListList, Context ct) {
        this.myListList = myListList;
        this.ct = ct;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_rec,parent,false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyNotificationList myList=myListList.get(position);

        holder.respondNumberNotification.setText(myList.getPhone());
        holder.respondUserNotification.setText(myList.getName());
        if (myList.getTime() != null){
            holder.respondTimeNotification.setText(myList.getTime().toString());

        }





    }



    @Override
    public int getItemCount() {
        return myListList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView respondUserNotification,respondTimeNotification,respondNumberNotification;

        private ImageView respondIconNotification;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            respondUserNotification = (TextView)itemView.findViewById(R.id.respondUserNotification);
            respondTimeNotification = (TextView)itemView.findViewById(R.id.respondTimeNotification);

            respondNumberNotification = (TextView)itemView.findViewById(R.id.respondNumberNotification);
            respondIconNotification = (ImageView)itemView.findViewById(R.id.respondIconNotification);







        }
    }
}