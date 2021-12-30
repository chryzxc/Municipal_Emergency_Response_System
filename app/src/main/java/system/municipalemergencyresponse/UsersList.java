package system.municipalemergencyresponse;

import java.util.Date;

public class UsersList {

    private String id,name, address,age,classification,phone,type;
    Date date;
    Double attempts;


    public UsersList(String id, String name, String address, String age, String classification, String phone, Date date,String type, Double attempts) {

        this.id = id;
        this.name = name;
        this.address = address;
        this.age = age;
        this.classification = classification;
        this.phone = phone;
        this.date = date;
        this.type = type;
        this.attempts = attempts;


    }

    public String getId() {

        return id;
    }


    public String getType() {
        return type;

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

    public String getAddress() {

        return address;
    }

    public String getAge() {

        return age;
    }

    public Double getAttempts() {

        return attempts;
    }

    public Date getDate(){
        return date;
    }












}