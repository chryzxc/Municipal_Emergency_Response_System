package system.municipalemergencyresponse;

import java.util.Date;

public class MyNotificationList {

    private String name, id,classification,phone,status;
    private Date time;




    public MyNotificationList(String id, String name, String classification,String phone,String status,Date time) {
        this.id = id;
        this.name = name;
        this.classification = classification;
        this.phone = phone;
        this.status = status;
        this.time = time;
    }

    public String getId() {

        return id;
    }


    public String getName() {
        return name;

    }


    public Date getTime() {

        return time;

    }

    public String getClassification(){
        return classification;
    }

    public String getPhone(){
        return phone;
    }

    public String getStatus(){
        return status;
    }


}