package system.municipalemergencyresponse;

import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RespondAdapter extends RecyclerView.Adapter<RespondAdapter.ViewHolder> {
    private List<RespondList> myListList;
    private Context ct;

    public RespondAdapter(List<RespondList> myListList, Context ct) {
        this.myListList = myListList;
        this.ct = ct;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.respond_rec,parent,false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RespondList myList=myListList.get(position);

        if (myList.getClassification().matches("Fire")){
            holder.respondIcon.setImageResource(R.drawable.flame);

        }else if (myList.getClassification().matches("Medical")){
            holder.respondIcon.setImageResource(R.drawable.hospital);
        }
        else if (myList.getClassification().matches("Police")){
            holder.respondIcon.setImageResource(R.drawable.police);
        }
        else if (myList.getClassification().matches("Traffic")){
            holder.respondIcon.setImageResource(R.drawable.traffic);
        }
        else if (myList.getClassification().matches("Others")){
            holder.respondIcon.setImageResource(R.drawable.others);
        }

        holder.respondUser.setText(myList.getName());
        holder.respondNumber.setText(myList.getNumber());


        new CountDownTimer(Long.MAX_VALUE, 1000) {
            StringBuilder time = new StringBuilder();
            @Override
            public void onFinish() {

            }

            @Override
            public void onTick(long millisUntilFinished) {
                try
                {


                    SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZ yyyy");
                    Date past = format.parse(myList.getTime().toString());
                    Date now = new Date();
                    long seconds= TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
                    long minutes=TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
                    long hours=TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
                    long days=TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());


                    if(seconds<60)
                    {
                        holder.respondTime.setText("Responded a while ago");
                        //System.out.println(seconds+" seconds ago");
                    }
                    else if(minutes<=1)
                    {
                        holder.respondTime.setText("Responded a minute ago");
                        // System.out.println(minutes+" minutes ago");
                    }

                    else if(minutes<60)
                    {
                        holder.respondTime.setText("Responded " +minutes+" minutes ago");
                        // System.out.println(minutes+" minutes ago");
                    }
                    else if(hours<=1)
                    {
                        holder.respondTime.setText("Responded an hour ago");
                        //  System.out.println(hours+" hours ago");
                    }
                    else if(hours<24)
                    {
                        holder.respondTime.setText("Responded "+hours+" hours ago");
                        //  System.out.println(hours+" hours ago");
                    }
                    else
                    {
                        holder.respondTime.setText("Responded "+days+" day(s) ago");
                        //  System.out.println(days+" days ago");
                    }
                }
                catch (Exception j){
                    j.printStackTrace();
                }






                // Glide.with(ct).load(myList.getImage()).fitCenter().centerCrop().apply(new RequestOptions().override(800, 200)).into(holder.postImage);

            }
        }.start();




    }



    @Override
    public int getItemCount() {
        return myListList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView respondUser,respondTime,respondNumber;
        private ImageView respondIcon;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            respondUser=(TextView)itemView.findViewById(R.id.respondUser);
            respondNumber=(TextView)itemView.findViewById(R.id.respondNumber);
            respondTime=(TextView)itemView.findViewById(R.id.respondTime);
            respondIcon=(ImageView)itemView.findViewById(R.id.respondIcon);


        }
    }
}