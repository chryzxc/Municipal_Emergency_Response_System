package system.municipalemergencyresponse;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private List<NotificationList> myListList;
    private Context ct;

    public NotificationAdapter(List<NotificationList> myListList, Context ct) {
        this.myListList = myListList;
        this.ct = ct;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_popup,parent,false);




        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationList myList=myListList.get(position);


        holder.notificationDate.setText(DateFormat.format("MMM dd, yyyy hh:mm aa",myList.getTime()));
        holder.notificationName.setText(myList.getName());
        holder.notificationDetails.setText(myList.getDetails());


    }



    @Override
    public int getItemCount() {
        return myListList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView notificationDate,notificationName,notificationDetails;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            notificationDate = (TextView) itemView.findViewById(R.id.notificationDate);
            notificationName = (TextView) itemView.findViewById(R.id.notificationName);
            notificationDetails = (TextView) itemView.findViewById(R.id.notificationDetails);




        }
    }
}