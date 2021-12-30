package system.municipalemergencyresponse;

import java.util.Date;

public class VerifyList {

    private String id,name, address,age,classification,phone;
    Date date;


    public VerifyList(String id, String name, String address, String age, String classification, String phone,Date date) {

        this.id = id;
        this.name = name;
        this.address = address;
        this.age = age;
        this.classification = classification;
        this.phone = phone;
        this.date = date;


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

    public String getAddress() {

        return address;
    }

    public String getAge() {

        return age;
    }

    public Date getDate(){
        return date;
    }












}