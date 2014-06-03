package ru.model;

public class Birthday {

    private int ID;

    private String whom;

    private String dateOfBirth;

    private int event_ID;

    public Birthday(int ID, String whom, String dateOfBirth, int event_ID){
        this.ID = ID;
        this.whom = whom;
        this.dateOfBirth = dateOfBirth;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getEvent_ID() {
        return event_ID;
    }

    public void setEvent_ID(int event_ID) {
        this.event_ID = event_ID;
    }
}
