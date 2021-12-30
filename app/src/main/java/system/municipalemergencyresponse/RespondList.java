package system.municipalemergencyresponse;

import java.util.Date;

public class RespondList {

    private String name, id,classification,number;
    private Date time;
    private int image;
    private Double map_lat,map_long;


    public RespondList(String id, String name, Date time,String classification, String number, String status, Double map_lat, Double map_long) {
        this.name = name;
        this.id = id;
        this.classification = classification;
        this.number = number;
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


    public String getClassification() {
        return classification;

    }

    public String getNumber() {
        return number;

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