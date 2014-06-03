package ru.model;

public class Event {

    private int ID;

    private String type;

    private String commentary;

    private Birthday birthday;

    private Demobee demobee;

    private CustomEvent customEvent;

    private  Holiday holiday;

    public Event(int id, String type, String commentary){
        this.ID = id;
        this.type = type;
        this.commentary = commentary;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public Birthday getBirthday() {
        return birthday;
    }

    public void setBirthday(Birthday birthday) {
        this.birthday = birthday;
    }

    public Demobee getDemobee() {
        return demobee;
    }

    public void setDemobee(Demobee demobee) {
        this.demobee = demobee;
    }

    public CustomEvent getCustomEvent() {
        return customEvent;
    }

    public void setCustomEvent(CustomEvent customEvent) {
        this.customEvent = customEvent;
    }

    public Holiday getHoliday() {
        return holiday;
    }

    public void setHoliday(Holiday holiday) {
        this.holiday = holiday;
    }
}
