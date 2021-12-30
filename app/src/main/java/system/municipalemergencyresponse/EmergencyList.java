package system.municipalemergencyresponse;

import java.util.Date;

public class EmergencyList {

    private String id,reporter_name,reporter_address,reporter_number,reporter_id,reporter_details;
    private Date time;
    private int image;
    private Double map_lat,map_long;


    public EmergencyList(String id,String reporter_id, String reporter_name, String reporter_address, Date time, String reporter_number,String reporter_details, Double map_lat, Double map_long) {
        this.reporter_name = reporter_name;
        this.reporter_address = reporter_address;
        this.reporter_id = reporter_id;
        this.reporter_number = reporter_number;
        this.id = id;
        this.reporter_details = reporter_details;
        this.time = time;
        this.map_lat = map_lat;
        this.map_long = map_long;
        this.image = image;

    }

    public String getId() {

        return id;
    }

    public String getReporter_id(){
        return  reporter_id;
    }


    public String getReporter_name() {
        return reporter_name;

    }

    public String getReporter_address() {
        return reporter_address;

    }



    public String getNumber() {
        return reporter_number;

    }

    public String getDetails() {
        return reporter_details;

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







}