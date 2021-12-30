package system.municipalemergencyresponse;

import java.util.Date;

public class NotificationList {

    private String name, id,details;
    private Date time;
    private int image;
    private Double map_lat,map_long;


    public NotificationList(String id, String name, Date time,String details, Double map_lat, Double map_long) {
        this.name = name;
        this.id = id;
        this.details = details;
        this.time = time;
        this.map_lat = map_lat;
        this.map_long = map_long;
        this.image = image;

    }

    public String getId() {

        return id;
    }


    public String getName() {
        return name;

    }

    public Double getMap_lat() {
        return map_lat;

    }

    public Double getMap_long() {
        return map_long;

    }

    public int getImage() {
        return image;

    }


    public Date getTime() {

        return time;

    }

    public String getDetails(){
        return details;
    }


}