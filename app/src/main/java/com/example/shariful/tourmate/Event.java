package com.example.shariful.tourmate;

public class Event {
    private String id;
   private String destination;
   private String budget;
   private String fromDate;
   private String toDate;


    public Event() {
    }

    public Event(String id, String destination, String budget, String fromDate, String toDate) {
        this.id = id;
        this.destination = destination;
        this.budget = budget;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
