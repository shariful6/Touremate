package com.example.shariful.tourmate;

public class Expense {
    private String id;
    private String time;
    private String details;
    private String taka;

    public Expense() {
    }


    public Expense(String id, String time, String details, String taka) {
        this.id = id;
        this.time = time;
        this.details = details;
        this.taka = taka;
    }

    public String getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTaka() {
        return taka;
    }

    public void setTaka(String taka) {
        this.taka = taka;
    }
}
