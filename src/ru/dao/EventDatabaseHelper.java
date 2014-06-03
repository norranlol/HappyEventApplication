package ru.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EventDatabaseHelper extends SQLiteOpenHelper {

	//БД
	private static final String DATABASE_NAME = "events.db";
	private static final int DATABASE_VERSION = 1;
	
	//Таблицы
	public static final String EVENT_TABLE_NAME = "event";
	public static final String BIRTHDAY_TABLE_NAME = "birthday";
	public static final String DEMOBEE_TABLE_NAME = "demobee";
	public static final String HOLIDAY_TABLE_NAME = "holiday";
	public static final String CUSTOMEVENT_TABLE_NAME = "other";
	
	//Общие
	public static final String UID = "_id";
    public static final String EVENT_ID = "event_id";
    public static final String WHOM = "whom";
	
	//Таблица "Событие"
	public static final String EVENTTYPE = "eventtype";
	public static final String COMMENTARY = "commentary";
	
	//Таблица "День Рождения"
	public static final String DATE_OF_BIRTH = "dateOfBirth";

    //Таблица "Праздник"
    public static final String TYPE_OF_HOLIDAY = "typeOfHoliday";
    public static final String DATE_OF_HOLIDAY = "dateOfHoliday";

    //Таблица "День Дембеля"
    public static final String DATE_OF_DEMOBEE = "dateOfDemobee";

    //Таблица "Другое событие"
    public static final String TITLE = "title";
    public static final String DATE_OF_CUSTOMEVENT = "dateOfCustomEvent";
	
	private static final String SQL_CREATE_TABLE_EVENT =
            "CREATE TABLE " + EVENT_TABLE_NAME +
                    " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			        + EVENTTYPE + " VARCHAR(50) NOT NULL,"
                    + COMMENTARY + " VARCHAR(500) NULL);";
	
	private static final String SQL_CREATE_TABLE_BIRTHDAY =
            "CREATE TABLE " + BIRTHDAY_TABLE_NAME +
                    " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			        + WHOM + " VARCHAR(100) NOT NULL,"
                    + DATE_OF_BIRTH + " VARCHAR(10) NOT NULL,"
                    + EVENT_ID + " INTEGER NOT NULL," +
                    "FOREIGN KEY(" + EVENT_ID + ") REFERENCES " + EVENT_TABLE_NAME + "(" + UID + ")" + ");";

    private static final String SQL_CREATE_TABLE_DEMOBEE =
            "CREATE TABLE " + DEMOBEE_TABLE_NAME +
                    " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + WHOM + " VARCHAR(100) NOT NULL,"
                    + DATE_OF_DEMOBEE + " VARCHAR(10) NOT NULL,"
                    + EVENT_ID + " INTEGER NOT NULL," +
                    "FOREIGN KEY(" + EVENT_ID + ") REFERENCES " + EVENT_TABLE_NAME + "(" + UID + ")" + ");";

    private static final String SQL_CREATE_TABLE_HOLIDAY =
            "CREATE TABLE " + HOLIDAY_TABLE_NAME +
                    " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                       + TYPE_OF_HOLIDAY + " VARCHAR(40) NOT NULL,"
                       + DATE_OF_HOLIDAY + " VARCHAR(10) NOT NULL,"
                        + EVENT_ID + " INTEGER NOT NULL," +
                        "FOREIGN KEY(" + EVENT_ID + ") REFERENCES " + EVENT_TABLE_NAME + "(" + UID + ")" + ");";

    private static final String SQL_CREATE_TABLE_CUSTOMEVENT =
            "CREATE TABLE " + CUSTOMEVENT_TABLE_NAME +
                    " ("  + UID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + TITLE + " VARCHAR(40) NOT NULL,"
                    + DATE_OF_CUSTOMEVENT + " VARCHAR(10) NOT NULL,"
                    + EVENT_ID + " INTEGER NOT NULL," +
                    "FOREIGN KEY(" + EVENT_ID + ") REFERENCES " + EVENT_TABLE_NAME + "(" + UID + ")" + ");";

	private static final String SQL_DELETE_ENTRIES_EVENT = "DROP TABLE IF EXISTS "
			+ EVENT_TABLE_NAME;

	private static final String SQL_DELETE_ENTRIES_BIRTHDAY = "DROP TABLE IF EXISTS "
			+ BIRTHDAY_TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_DEMOBEE = "DROP TABLE IF EXISTS "
            + EVENT_TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_HOLIDAY = "DROP TABLE IF EXISTS "
            + BIRTHDAY_TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_CUSTOMEVENT = "DROP TABLE IF EXISTS "
            + EVENT_TABLE_NAME;
	
	public EventDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_TABLE_EVENT);
		db.execSQL(SQL_CREATE_TABLE_BIRTHDAY);
        db.execSQL(SQL_CREATE_TABLE_HOLIDAY);
        db.execSQL(SQL_CREATE_TABLE_DEMOBEE);
        db.execSQL(SQL_CREATE_TABLE_CUSTOMEVENT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("EVENTS_RECORDS", "Обновление базы данных с версии " + oldVersion +
                " до версии " + newVersion + ", которое удалит все старые данные");
		db.execSQL(SQL_DELETE_ENTRIES_EVENT);
		db.execSQL(SQL_DELETE_ENTRIES_BIRTHDAY);
        db.execSQL(SQL_DELETE_ENTRIES_HOLIDAY);
        db.execSQL(SQL_DELETE_ENTRIES_DEMOBEE);
        db.execSQL(SQL_DELETE_ENTRIES_CUSTOMEVENT);
		onCreate(db);
	}
}
