package ru.happyevent;

import ru.dao.EventDAO;
import ru.dao.EventDatabaseHelper;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import ru.model.*;

import java.util.ArrayList;
import java.util.List;

public class ListOfEventsActivity extends ActionBarActivity {
	
	private EventDatabaseHelper eventDBHelper;
    private EventDAO eventDAO = new EventDAO();
	private SQLiteDatabase sqldb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_events);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        
		eventDBHelper = new EventDatabaseHelper(this);
		sqldb = eventDBHelper.getWritableDatabase();
		
		Cursor cursor = sqldb.query(EventDatabaseHelper.EVENT_TABLE_NAME, new String[] 
				{EventDatabaseHelper.UID, EventDatabaseHelper.EVENTTYPE, EventDatabaseHelper.COMMENTARY},
				null, null, null, null, null);
        List<Event> listOfEvents = new ArrayList<Event>();
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
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_list_of_events, container, false);
            return rootView;
        }
    }

}
