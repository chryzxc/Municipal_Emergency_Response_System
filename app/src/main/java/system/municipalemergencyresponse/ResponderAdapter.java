package system.municipalemergencyresponse;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ResponderAdapter extends RecyclerView.Adapter<ResponderAdapter.ViewHolder> {
    private List<ResponderList> myListList;
    private Context ct;

    public ResponderAdapter(List<ResponderList> myListList, Context ct) {
        this.myListList = myListList;
        this.ct = ct;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.responder_rec,parent,false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponderList myList=myListList.get(position);

        holder.responderName.setText(myList.getName());
        holder.responderName.setSelected(true);
        holder.responderClass.setText(myList.getClassification());

        if (myList.getClassification().matches("Fire")){
            holder.responderImage.setImageResource(R.drawable.flame);

        }else if (myList.getClassification().matches("Medical")){
            holder.responderImage.setImageResource(R.drawable.hospital);
        }
        else if (myList.getClassification().matches("Police")){
            holder.responderImage.setImageResource(R.drawable.police);
        }
        else if (myList.getClassification().matches("Traffic")){
            holder.responderImage.setImageResource(R.drawable.traffic);
        }
        else if (myList.getClassification().matches("Others")){
            holder.responderImage.setImageResource(R.drawable.others);
        }

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
                    Date active = format.parse(myList.getLast_active().toString());
                    Date now = new Date();
                    long seconds= TimeUnit.MILLISECONDS.toSeconds(now.getTime() - active.getTime());
                    long minutes=TimeUnit.MILLISECONDS.toMinutes(now.getTime() - active.getTime());
                    long hours=TimeUnit.MILLISECONDS.toHours(now.getTime() - active.getTime());
                    long days=TimeUnit.MILLISECONDS.toDays(now.getTime() - active.getTime());



                    if(seconds<60)
                    {
                        holder.responderLastActive.setText("Active now");
                        holder.responderStatus.setCardBackgroundColor(ContextCompat.getColor(ct, R.color.lightGreen));
                        //System.out.println(seconds+" seconds ago");
                    }
                    else if(minutes<=1)
                    {
                        holder.responderLastActive.setText(minutes+" minute ago");
                        holder.responderStatus.setCardBackgroundColor(Color.RED);
                        // System.out.println(minutes+" minutes ago");
                    }

                    else if(minutes<60)
                    {
                        holder.responderLastActive.setText(minutes+" minutes ago");
                        holder.responderStatus.setCardBackgroundColor(Color.RED);
                        // System.out.println(minutes+" minutes ago");
                    }
                    else if(hours<=1)
                    {
                        holder.responderLastActive.setText("an hour ago");
                        holder.responderStatus.setCardBackgroundColor(Color.RED);
                        //  System.out.println(hours+" hours ago");
                    }
                    else if(hours<24)
                    {
                        holder.responderLastActive.setText(hours+" hours ago");
                        holder.responderStatus.setCardBackgroundColor(Color.RED);
                        //  System.out.println(hours+" hours ago");
                    }
                    else
                    {
                        holder.responderLastActive.setText(days+" day(s) ago");
                        holder.responderStatus.setCardBackgroundColor(Color.RED);
                        //  System.out.println(days+" days ago");
                    }
                }
                catch (Exception j){
                    j.printStackTrace();
                }






            }
        }.start();

        holder.responderLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(myList.getMap_lat(), myList.getMap_long()))
                        .zoom(16.5)
                        .tilt(60)
                        .build();

               MainActivity.mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 1000);

            }
        });






    }



    @Override
    public int getItemCount() {
        return myListList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView responderImage;
        private CardView responderStatus;
        private TextView responderClass,responderName,responderLastActive;
        private Button responderLocation;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            responderImage=(ImageView)itemView.findViewById(R.id.responderImage);
            responderClass=(TextView)itemView.findViewById(R.id.responderClass);
            responderName=(TextView)itemView.findViewById(R.id.responderName);
            responderStatus=(CardView)itemView.findViewById(R.id.responderStatus);
            responderLastActive=(TextView)itemView.findViewById(R.id.responderLastActive);
            responderLocation=(Button)itemView.findViewById(R.id.responderLocation);




        }
    }
}