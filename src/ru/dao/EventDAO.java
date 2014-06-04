package ru.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import ru.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventDAO {

    private void insertInEventTable(String eventType, String commentary, SQLiteDatabase sqldb){
        ContentValues cv = new ContentValues();
        cv.put(EventDatabaseHelper.EVENTTYPE, eventType);
        cv.put(EventDatabaseHelper.COMMENTARY, commentary);
        sqldb.insert(EventDatabaseHelper.EVENT_TABLE_NAME, EventDatabaseHelper.EVENTTYPE, cv);
    }

    private int getLastIdFromEventTable(SQLiteDatabase sqldb){
        String selectQueryLastRow = "SELECT " + EventDatabaseHelper.UID +
                " FROM " + EventDatabaseHelper.EVENT_TABLE_NAME;
        Cursor cursor = sqldb.rawQuery(selectQueryLastRow, null);
        cursor.moveToLast();
        int id = 0;
        id = cursor.getInt(cursor.getColumnIndex(EventDatabaseHelper.UID));
        return id;
    }

	public void insertInBirthdayTable(String eventType, String commentary, String whom,
                                   String dateOfBirth,SQLiteDatabase sqldb){
        insertInEventTable(eventType, commentary, sqldb);
        int id = getLastIdFromEventTable(sqldb);
        if (id != 0){
            ContentValues cv = new ContentValues();
            cv.put(EventDatabaseHelper.WHOM, whom);
            cv.put(EventDatabaseHelper.DATE_OF_BIRTH, dateOfBirth);
            cv.put(EventDatabaseHelper.EVENT_ID, id);
            sqldb.insert(EventDatabaseHelper.BIRTHDAY_TABLE_NAME, EventDatabaseHelper.WHOM, cv);
        }
	}

    public void insertInDemobeeTable(String eventType, String commentary, String whom,
                                      String dateOfDemobee,SQLiteDatabase sqldb){
        insertInEventTable(eventType, commentary, sqldb);
        int id = getLastIdFromEventTable(sqldb);
        if (id != 0){
            ContentValues cv = new ContentValues();
            cv.put(EventDatabaseHelper.WHOM, whom);
            cv.put(EventDatabaseHelper.DATE_OF_DEMOBEE, dateOfDemobee);
            cv.put(EventDatabaseHelper.EVENT_ID, id);
            sqldb.insert(EventDatabaseHelper.DEMOBEE_TABLE_NAME, EventDatabaseHelper.WHOM, cv);
        }
    }

    public void insertInHolidayTable(String eventType, String commentary, String typeOfHoliday,
                                     String dateOfHoliday, SQLiteDatabase sqldb){
        insertInEventTable(eventType, commentary, sqldb);
        int id = getLastIdFromEventTable(sqldb);
        if (id != 0){
            ContentValues cv = new ContentValues();
            cv.put(EventDatabaseHelper.TYPE_OF_HOLIDAY, typeOfHoliday);
            cv.put(EventDatabaseHelper.DATE_OF_HOLIDAY, dateOfHoliday);
            cv.put(EventDatabaseHelper.EVENT_ID, id);
            sqldb.insert(EventDatabaseHelper.HOLIDAY_TABLE_NAME, EventDatabaseHelper.TYPE_OF_HOLIDAY, cv);
        }
    }

    public void insertInCustomEventTable(String eventType, String commentary, String title,
                                         String dateOfCustomEvent, SQLiteDatabase sqldb){
        insertInEventTable(eventType, commentary, sqldb);
        int id = getLastIdFromEventTable(sqldb);
        if (id != 0){
            ContentValues cv = new ContentValues();
            cv.put(EventDatabaseHelper.TITLE, title);
            cv.put(EventDatabaseHelper.DATE_OF_CUSTOMEVENT, dateOfCustomEvent);
            cv.put(EventDatabaseHelper.EVENT_ID, id);
            sqldb.insert(EventDatabaseHelper.CUSTOMEVENT_TABLE_NAME, EventDatabaseHelper.TITLE, cv);
        }
    }

    public Birthday selectBirthdayByEventId(int id, SQLiteDatabase sqldb){
        Cursor cursorBD = sqldb.query(EventDatabaseHelper.BIRTHDAY_TABLE_NAME,
                new String[]{EventDatabaseHelper.UID, EventDatabaseHelper.WHOM, EventDatabaseHelper.DATE_OF_BIRTH,
                        EventDatabaseHelper.EVENT_ID}, EventDatabaseHelper.EVENT_ID + "= ?", new String[]{String.valueOf(id)},
                null, null, null);
        while (cursorBD.moveToNext()){
            int birthdayId = cursorBD.getInt(cursorBD.getColumnIndex(EventDatabaseHelper.UID));
            String whom = cursorBD.getString(cursorBD.getColumnIndex(EventDatabaseHelper.WHOM));
            String dateOfBirth = cursorBD.getString(cursorBD.getColumnIndex(EventDatabaseHelper.DATE_OF_BIRTH));
            int eventId = cursorBD.getInt(cursorBD.getColumnIndex(EventDatabaseHelper.EVENT_ID));
            Birthday birthday = new Birthday(birthdayId, whom, dateOfBirth, eventId);
            return birthday;
        }
        return null;
    }

    public Demobee selectDemobeeByEventId(int id, SQLiteDatabase sqldb){
        Cursor cursorDemobee = sqldb.query(EventDatabaseHelper.DEMOBEE_TABLE_NAME,
                new String[]{EventDatabaseHelper.UID, EventDatabaseHelper.WHOM, EventDatabaseHelper.DATE_OF_DEMOBEE,
                        EventDatabaseHelper.EVENT_ID}, EventDatabaseHelper.EVENT_ID + "= ?", new String[]{String.valueOf(id)},
                null, null, null);
        while (cursorDemobee.moveToNext()){
            int demobeeId = cursorDemobee.getInt(cursorDemobee.getColumnIndex(EventDatabaseHelper.UID));
            String whom = cursorDemobee.getString(cursorDemobee.getColumnIndex(EventDatabaseHelper.WHOM));
            String dateOfDemobee = cursorDemobee.getString(cursorDemobee.getColumnIndex(EventDatabaseHelper.DATE_OF_DEMOBEE));
            int eventId = cursorDemobee.getInt(cursorDemobee.getColumnIndex(EventDatabaseHelper.EVENT_ID));
            Demobee demobee = new Demobee(demobeeId, whom, dateOfDemobee, eventId);
            return  demobee;
        }
        return null;
    }

    public CustomEvent selectCustomEventByEventId(int id, SQLiteDatabase sqldb){
        Cursor cursorCE = sqldb.query(EventDatabaseHelper.CUSTOMEVENT_TABLE_NAME,
                new String[]{EventDatabaseHelper.UID, EventDatabaseHelper.TITLE, EventDatabaseHelper.DATE_OF_CUSTOMEVENT,
                        EventDatabaseHelper.EVENT_ID}, EventDatabaseHelper.EVENT_ID + "= ?", new String[]{String.valueOf(id)},
                null, null, null);
        while (cursorCE.moveToNext()){
            int customEventId = cursorCE.getInt(cursorCE.getColumnIndex(EventDatabaseHelper.UID));
            String title = cursorCE.getString(cursorCE.getColumnIndex(EventDatabaseHelper.TITLE));
            String dateOfCustomEvent = cursorCE.getString(cursorCE.getColumnIndex(EventDatabaseHelper.DATE_OF_CUSTOMEVENT));
            int eventId = cursorCE.getInt(cursorCE.getColumnIndex(EventDatabaseHelper.EVENT_ID));
            CustomEvent customEvent = new CustomEvent(customEventId, title, dateOfCustomEvent, eventId);
            return customEvent;
        }
        return null;
    }

    public Holiday selectHolidayByEventId(int id, SQLiteDatabase sqldb){
        Cursor cursorHoliday = sqldb.query(EventDatabaseHelper.HOLIDAY_TABLE_NAME,
                new String[]{EventDatabaseHelper.UID, EventDatabaseHelper.TYPE_OF_HOLIDAY,
                        EventDatabaseHelper.DATE_OF_HOLIDAY, EventDatabaseHelper.EVENT_ID},
                EventDatabaseHelper.EVENT_ID + "= ?", new String[]{String.valueOf(id)},
                null, null, null);
        while (cursorHoliday.moveToNext()){
            int holidayId = cursorHoliday.getInt(cursorHoliday.getColumnIndex(EventDatabaseHelper.UID));
            String typeOfHoliday = cursorHoliday.getString(cursorHoliday.getColumnIndex(EventDatabaseHelper.TYPE_OF_HOLIDAY));
            String dateOfHoliday = cursorHoliday.getString(cursorHoliday.getColumnIndex(EventDatabaseHelper.DATE_OF_HOLIDAY));
            int eventId = cursorHoliday.getInt(cursorHoliday.getColumnIndex(EventDatabaseHelper.EVENT_ID));
            Holiday holiday = new Holiday(holidayId, typeOfHoliday, dateOfHoliday, eventId);
            return holiday;
        }
        return null;
    }

    public Event selectEventByEventId(int id, SQLiteDatabase sqldb){
        Cursor cursorEvent = sqldb.query(EventDatabaseHelper.EVENT_TABLE_NAME,
                new String[]{EventDatabaseHelper.UID, EventDatabaseHelper.EVENTTYPE,
                        EventDatabaseHelper.COMMENTARY},
                EventDatabaseHelper.UID + "= ?", new String[]{String.valueOf(id)},
                null, null, null);
        while (cursorEvent.moveToNext()){
            int eventId = cursorEvent.getInt(cursorEvent.getColumnIndex(EventDatabaseHelper.UID));
            String typeOfEvent = cursorEvent.getString(cursorEvent.getColumnIndex(EventDatabaseHelper.EVENTTYPE));
            String commentary = cursorEvent.getString(cursorEvent.getColumnIndex(EventDatabaseHelper.COMMENTARY));
            Event event = new Event(eventId, typeOfEvent, commentary);
            return  event;
        }
        return  null;
    }
}
