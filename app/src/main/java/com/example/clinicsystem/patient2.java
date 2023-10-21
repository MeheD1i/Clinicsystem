package com.example.clinicsystem;

public class patient2 {
    String name1;
    String time1;
    String date;
    public patient2(){
    }
    public patient2(String name1, String time1, String date) {
        this.name1 = name1;
        this.time1 = time1;
        this.date = date;
    }
    public String getName1() {
        return name1;
    }
    public void setName1(String name1) {
        this.name1 = name1;
    }
    public String getTime1() {
        return time1;
    }
    public void setTime1(String time1) {
        this.time1 = time1;
    }
    public String getDate(){
        return date; }
}
