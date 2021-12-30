package system.municipalemergencyresponse;

import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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

public class EmergencyAdapter extends RecyclerView.Adapter<EmergencyAdapter.ViewHolder> {
    private List<EmergencyList> myListList;
    private Context ct;

    public EmergencyAdapter(List<EmergencyList> myListList, Context ct) {
        this.myListList = myListList;
        this.ct = ct;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.emergency_rec,parent,false);


        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EmergencyList myList=myListList.get(position);

        holder.emergencySender.setText(myList.getReporter_name());
        holder.emergencyDetails.setText(myList.getDetails());




        if (MainActivity.loadType != null && MainActivity.loadType.matches("responder")) {
            holder.emergencyLocation.setVisibility(VISIBLE);
        }else{
            holder.emergencyLocation.setVisibility(GONE);
        }

     //   MainActivity.countDownTimer.cancel();



         MainActivity.countDownTimer =  new CountDownTimer(Long.MAX_VALUE, 1000) {
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

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });



                    if(seconds<60)
                    {
                        holder.emergencyTime.setText(seconds + " seconds ago");
                        //System.out.println(seconds+" seconds ago");
                    }
                    else if(minutes<=1)
                    {
                        holder.emergencyTime.setText(minutes+" minute ago");
                        // System.out.println(minutes+" minutes ago");
                    }

                    else if(minutes<60)
                    {
                        holder.emergencyTime.setText(minutes+" minutes ago");
                        // System.out.println(minutes+" minutes ago");
                    }
                    else if(hours<=1)
                    {
                        holder.emergencyTime.setText("an hour ago");
                        //  System.out.println(hours+" hours ago");
                    }
                    else if(hours<24)
                    {
                        holder.emergencyTime.setText(hours+" hours ago");
                        //  System.out.println(hours+" hours ago");
                    }
                    else
                    {
                        holder.emergencyTime.setText(days+" day(s) ago");
                        //  System.out.println(days+" days ago");
                    }
                }
                catch (Exception j){
                    j.printStackTrace();
                }




                holder.emergencyLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        CameraPosition position = new CameraPosition.Builder()
                                .target(new LatLng(myList.getMap_lat(), myList.getMap_long()))
                                .zoom(19)
                                .tilt(60)
                                .build();

                        MainActivity.mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 10000);
                        MainActivity.emergencyCard.setVisibility(View.GONE);

                        MainActivity.respondCard.setVisibility(View.VISIBLE);
                        MainActivity.rSender.setText(myList.getReporter_name());
                        MainActivity.rNumber.setText("Phone: "+myList.getNumber());
                        MainActivity.rAddress.setText("Address: "+myList.getReporter_address());
                        MainActivity.rDetails.setText(myList.getDetails());
                        MainActivity.rDate.setText(DateFormat.format("MMM dd yyyy hh:mm aa",myList.getTime()));
                        MainActivity.rLocation.setText(myList.getMap_lat().toString() +", " +myList.getMap_long().toString());


                        MainActivity.bottomNavigationView.setItemSelected(R.id.map,true);
                        MainActivity.sideButtons.setVisibility(View.GONE);
                        MainActivity.respondCardList.setVisibility(View.GONE);
                        MainActivity.emergencyID = myList.getId();
                        MainActivity.reporterID = myList.getReporter_id();

                        MainActivity.currentEmergency(ct,myList.getReporter_id());
                        if (MainActivity.respondCardList.getVisibility() == GONE){


                            MainActivity.listMaximize.setVisibility(VISIBLE);
                            MainActivity.listMinimize.setVisibility(GONE);

                        }else{

                            MainActivity.listMaximize.setVisibility(GONE);
                            MainActivity.listMinimize.setVisibility(VISIBLE);

                        }




                    }
                });

                // Glide.with(ct).load(myList.getImage()).fitCenter().centerCrop().apply(new RequestOptions().override(800, 200)).into(holder.postImage);

            }
        };

        MainActivity.countDownTimer.start();




    }



    @Override
    public int getItemCount() {
        return myListList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private Button emergencyLocation;
        private TextView emergencySender,emergencyDetails,emergencyTime;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            emergencyLocation=(Button)itemView.findViewById(R.id.emergencyLocation);
            emergencySender=(TextView)itemView.findViewById(R.id.emergencySender);
            emergencyDetails=(TextView)itemView.findViewById(R.id.emergencyDetails);
            emergencyTime=(TextView)itemView.findViewById(R.id.emergencyTime);


           // receiptName=(TextView)itemView.findViewById(R.id.receiptName);
           // receiptDate=(TextView)itemView.findViewById(R.id.receiptDate);




        }
    }
}