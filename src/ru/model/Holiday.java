package ru.model;

public class Holiday {

    private int ID;

    private String type;

    private String dateOfHoliday;

    private int event_ID;

    public Holiday(int ID, String type, String dateOfHoliday, int event_ID){
        this.ID = ID;
        this.type = type;
        this.dateOfHoliday = dateOfHoliday;
        this.event_ID = event_ID;
    }

    public Holiday(){}

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

    public int getEvent_ID() {
        return event_ID;
    }

    public void setEvent_ID(int event_ID) {
        this.event_ID = event_ID;
    }

    public String getDateOfHoliday() {
        return dateOfHoliday;
    }

    public void setDateOfHoliday(String dateOfHoliday) {
        this.dateOfHoliday = dateOfHoliday;
    }

    public String getDateOfHolidayByTitleOfHoliday(String title){
        String resultDate = null;
        if (title.equals("Новый Год"))
            resultDate = "01-01-0000";
        else if (title.equals("Рождество"))
            resultDate = "07-01-0000";
        else if (title.equals("Старый Новый Год"))
            resultDate = "13-01-0000";
        else if (title.equals("День Святого Валентина"))
            resultDate = "14-02-0000";
        else if (title.equals("День Защитника Отечества"))
            resultDate = "23-02-0000";
        else if (title.equals("Международный Женский День"))
            resultDate = "08-03-0000";
        else if (title.equals("Праздник Весны и Труда"))
            resultDate = "01-05-0000";
        else if (title.equals("День Победы"))
            resultDate = "09-05-0000";
        return resultDate;
    }
}
