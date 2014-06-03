package ru.model;

public class Demobee {

    private int ID;

    private String whom;

    private String dateOfDemobee;

    private int event_ID;

    public Demobee(int ID, String whom, String dateOfDemobee, int event_ID){
        this.ID = ID;
        this.whom = whom;
        this.dateOfDemobee = dateOfDemobee;
        this.event_ID = event_ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getWhom() {
        return whom;
    }

    public void setWhom(String whom) {
        this.whom = whom;
    }

    public String getDateOfDemobee() {
        return dateOfDemobee;
    }

    public void setDateOfDemobee(String dateOfDemobee) {
        this.dateOfDemobee = dateOfDemobee;
    }

    public int getEvent_ID() {
        return event_ID;
    }

    public void setEvent_ID(int event_ID) {
        this.event_ID = event_ID;
    }

}
