package ru.model;

public class CustomEvent {

    private int ID;

    private String title;

    private String dateOfCustomEvent;

    private int event_ID;

    public CustomEvent(int ID, String title, String dateOfCustomEvent, int event_ID){
        this.ID = ID;
        this.title = title;
        this.dateOfCustomEvent = dateOfCustomEvent;
        this.event_ID = event_ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateOfCustomEvent() {
        return dateOfCustomEvent;
    }

    public void setDateOfCustomEvent(String dateOfCustomEvent) {
        this.dateOfCustomEvent = dateOfCustomEvent;
    }

    public int getEvent_ID() {
        return event_ID;
    }

    public void setEvent_ID(int event_ID) {
        this.event_ID = event_ID;
    }
}
