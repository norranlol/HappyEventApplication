package ru.happyevent;

import android.app.ListActivity;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import ru.dao.EventDAO;
import ru.dao.EventDatabaseHelper;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import ru.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListOfEventsActivity extends ListActivity {
	
	private EventDatabaseHelper eventDBHelper;
    private EventDAO eventDAO = new EventDAO();
	private SQLiteDatabase sqldb;
    private List<Event> listOfEvents = new ArrayList<Event>();
    private SimpleAdapter sa;
    ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_events);
        
		eventDBHelper = new EventDatabaseHelper(this);
		sqldb = eventDBHelper.getWritableDatabase();
		
		Cursor cursor = sqldb.query(EventDatabaseHelper.EVENT_TABLE_NAME, new String[] 
				{EventDatabaseHelper.UID, EventDatabaseHelper.EVENTTYPE, EventDatabaseHelper.COMMENTARY},
				null, null, null, null, null);
        while (cursor.moveToNext()){
        	int id = cursor.getInt(cursor.getColumnIndex(EventDatabaseHelper.UID));
        	String type = cursor.getString(cursor.getColumnIndex(EventDatabaseHelper.EVENTTYPE));
            String commentary = cursor.getString(cursor.getColumnIndex(EventDatabaseHelper.COMMENTARY));
            Event event = new Event(id, type, commentary);

            //Поиск по Birthday
            Birthday birthday = eventDAO.selectBirthdayByEventId(id, sqldb);
            event.setBirthday(birthday);

            //Поиск по Demobee
            Demobee demobee = eventDAO.selectDemobeeByEventId(id, sqldb);
            event.setDemobee(demobee);

            //Поиск по CustomEvent
            CustomEvent customEvent = eventDAO.selectCustomEventByEventId(id, sqldb);
            event.setCustomEvent(customEvent);

            //Поиск по Holiday
            Holiday holiday = eventDAO.selectHolidayByEventId(id, sqldb);
            event.setHoliday(holiday);

            listOfEvents.add(event);
        	Log.i("INFO", "ROW " + id + " HAS Type " + type + " HAS Commentary " + commentary);
        }

        HashMap<String,String> item;
        for (Event event : listOfEvents){
            item = new HashMap<String, String>();
            item.put("line1", event.getType());
            if (event.getBirthday() != null)
                item.put("line2", event.getBirthday().getWhom());
            else if (event.getDemobee() != null)
                item.put("line2", event.getDemobee().getWhom());
            else if (event.getCustomEvent() != null)
                item.put("line2", event.getCustomEvent().getTitle());
            else if (event.getHoliday() != null)
                item.put("line2", event.getHoliday().getType());
            list.add(item);
        }

        sa = new SimpleAdapter(this, list, android.R.layout.two_line_list_item ,
             new String[] { "line1","line2" },
             new int[] {android.R.id.text1, android.R.id.text2});
        setListAdapter( sa );
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_of_events, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()){
    		case R.id.add_event:
    			Intent addEventIntent = new Intent(ListOfEventsActivity.this, AddEventActivity.class);
    			startActivity(addEventIntent);
    	}
        return super.onOptionsItemSelected(item);
    }
}
