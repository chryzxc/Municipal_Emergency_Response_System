package system.municipalemergencyresponse;

import java.util.Date;

public class ResponderList {

    private String name, id,classification,phone;
    private Date last_active;
    private int image;
    private Double map_lat,map_long;


    public ResponderList(String id, String name, Date last_active, String classification, String phone, Double map_lat, Double map_long) {
        this.name = name;
        this.id = id;
        this.classification = classification;
        this.phone = phone;
        this.last_active = last_active;
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

    public String getClassification() {

        return classification;
    }

    public String getPhone() {

        return phone;
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



    public Date getLast_active() {

        return last_active;

    }







}