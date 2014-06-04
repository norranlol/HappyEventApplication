package ru.happyevent;

import android.content.Intent;
import ru.dao.EventDAO;
import ru.dao.EventDatabaseHelper;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import ru.date.DateValidator;
import ru.model.Holiday;

public class AddEventActivity extends ActionBarActivity {

    public AddEventActivity(){}
		
	private boolean flagOfType = false;
    private final static String none = "";
    private final static String birthday = "День рождения";
    private final static String demobee = "Дембель";
    private final static String holiday = "Праздник";
    private final static String other = "Другое";
	String[] data = {none, birthday, demobee, holiday, other};
	
	private EventDatabaseHelper eventDBHelper;
	private SQLiteDatabase sqldb;
	private EventDAO eventDAO = new EventDAO();
    private DateValidator dateValidator = new DateValidator();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_event);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner spinner = (Spinner) findViewById(R.id.spinnerOfTypeOfEvent);
		spinner.setAdapter(adapter);
        spinner.setPrompt("Тип события");
		spinner.setSelection(0);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				((TextView)parent.getChildAt(0)).setTextSize(20);
					if (position == 0 && !flagOfType){
						FragmentNone fragment = new FragmentNone();
						FragmentManager manager = getFragmentManager();
						FragmentTransaction tx = manager.beginTransaction();
						tx.add(R.id.layoutToReplace, fragment);
						tx.commit();
						flagOfType = true;
					}
					else changeFragment(parent, position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		eventDBHelper = new EventDatabaseHelper(this);
		sqldb = eventDBHelper.getWritableDatabase();
		System.out.println("");
	}
	
	private void changeFragment(AdapterView<?> parent, int fragmentNumber){
		Fragment fragment = null;
		if (fragmentNumber == 0)
			fragment = new FragmentNone();
		else if (fragmentNumber == 1){
			fragment = new FragmentBirthday();
			((TextView)parent.getChildAt(0)).setTextColor(Color.BLUE);
		}
		else if (fragmentNumber == 2){
			fragment = new FragmentDemobee();
			((TextView)parent.getChildAt(0)).setTextColor(Color.RED);
		}
		else if (fragmentNumber == 3){
			fragment = new FragmentHoliday();
			((TextView)parent.getChildAt(0)).setTextColor(Color.YELLOW);
		}
		else if (fragmentNumber == 4){
			fragment = new FragmentCustomEvent();
			((TextView)parent.getChildAt(0)).setTextColor(Color.GREEN);
		}
		FragmentTransaction tx = getFragmentManager().beginTransaction();
		tx.replace(R.id.layoutToReplace, fragment);
		tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		tx.addToBackStack(null);
		tx.commit();
	}	
	
	public class FragmentBirthday extends Fragment {
		
		public FragmentBirthday(){}
		
		private Button buttonOfRecord;
		private EditText whomEditText;
		private EditText dateEditText;
		private EditText commentaryEditText;
		
		@Override
		 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		  View view = inflater.inflate(R.layout.add_event_birthday_fragment, container, false);
		  buttonOfRecord = (Button) view.findViewById(R.id.buttonAddEventBirthday);
		  buttonOfRecord.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				whomEditText = (EditText) findViewById(R.id.editTextOfWhomBirthday);
				dateEditText = (EditText) findViewById(R.id.editTextOfDateOfBirth);
				commentaryEditText = (EditText) findViewById(R.id.editTextOfCommentaryBirthday);
				String whomString = whomEditText.getText().toString();
				String date = dateEditText.getText().toString();
				String commentary = commentaryEditText.getText().toString();
				if (whomString.equals(""))
					Toast.makeText(AddEventActivity.this, R.string.not_enter_whom_birthday, 
							Toast.LENGTH_LONG).show();
                else if (whomString.length() > 100)
                    Toast.makeText(AddEventActivity.this, R.string.field_whom_too_long,
                            Toast.LENGTH_LONG).show();
				else if (date.equals(""))
					Toast.makeText(AddEventActivity.this, R.string.not_enter_date_of_birth, 
							Toast.LENGTH_LONG).show();
                else if (!dateValidator.validateDateOnMask(date))
                    Toast.makeText(AddEventActivity.this, R.string.date_not_matching_mask,
                            Toast.LENGTH_LONG).show();
                else if (!dateValidator.validateDatesOnValidMeans(date))
                    Toast.makeText(AddEventActivity.this, R.string.date_not_valid_means,
                            Toast.LENGTH_LONG).show();
                else if (!dateValidator.ifDateIsEarlierThenCurrent(date))
                    Toast.makeText(AddEventActivity.this, R.string.date_later_then_current,
                            Toast.LENGTH_LONG).show();
                else if (commentary.length() > 500)
                    Toast.makeText(AddEventActivity.this, R.string.field_commentary_too_long,
                            Toast.LENGTH_LONG).show();
				else if ((!whomString.equals("")) && (!date.equals(""))){
					eventDAO.insertInBirthdayTable("День Рождения", commentary, whomString, date, sqldb);
                    whomEditText.setText("");
                    dateEditText.setText("");
                    commentaryEditText.setText("");
                    Intent goToList = new Intent(AddEventActivity.this, ListOfEventsActivity.class);
                    startActivity(goToList);
				}
			}
		});
		  return view;
		 }
	}
	
	public class FragmentDemobee extends Fragment {
		
		private Button buttonOfRecord;
		private EditText whomEditText;
		private EditText dateEditText;
		private EditText commentaryEditText;
		
		public FragmentDemobee(){}
		
		@Override
		 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			  View view = inflater.inflate(R.layout.add_event_demobee_fragment, container, false);
			  buttonOfRecord = (Button) view.findViewById(R.id.buttonAddEventDemobee);
			  buttonOfRecord.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					whomEditText = (EditText) findViewById(R.id.editTextOfWhomDemobee);
					dateEditText = (EditText) findViewById(R.id.editTextOfDateOfDemobee);
					commentaryEditText = (EditText) findViewById(R.id.editTextOfCommentaryDemobee);
					String whomString = whomEditText.getText().toString();
					String date = dateEditText.getText().toString();
					String commentary = commentaryEditText.getText().toString();
					if (whomString.equals(""))
						Toast.makeText(AddEventActivity.this, R.string.not_enter_whom_demobee, 
								Toast.LENGTH_LONG).show();
                    else if (whomString.length() > 100)
                        Toast.makeText(AddEventActivity.this, R.string.field_whom2_too_long,
                                Toast.LENGTH_LONG).show();
					else if (date.equals(""))
						Toast.makeText(AddEventActivity.this, R.string.not_enter_date_of_demobee, 
								Toast.LENGTH_LONG).show();
                    else if (!dateValidator.validateDateOnMask(date))
                        Toast.makeText(AddEventActivity.this, R.string.date_not_matching_mask,
                                Toast.LENGTH_LONG).show();
                    else if (!dateValidator.validateDatesOnValidMeans(date))
                        Toast.makeText(AddEventActivity.this, R.string.date_not_valid_means,
                                Toast.LENGTH_LONG).show();
                    else if (!dateValidator.ifDateIsLaterThenCurrent(date))
                        Toast.makeText(AddEventActivity.this, R.string.date_earlier_then_current,
                                Toast.LENGTH_LONG).show();
                    else if (commentary.length() > 500)
                        Toast.makeText(AddEventActivity.this, R.string.field_commentary_too_long,
                                Toast.LENGTH_LONG).show();
					else if ( (!whomString.equals("")) && (!date.equals(""))){
                        eventDAO.insertInDemobeeTable("Дембель", commentary, whomString, date, sqldb);
                        whomEditText.setText("");
                        dateEditText.setText("");
                        commentaryEditText.setText("");
                        Intent goToList = new Intent(AddEventActivity.this, ListOfEventsActivity.class);
                        startActivity(goToList);
					}
				}
			});
		  return view;
		 }		
	}
	
	public class FragmentNone extends Fragment {
		
		public FragmentNone(){}
		
		@Override
		 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		  View view = inflater.inflate(R.layout.add_event_none_fragment, null);
		  return view;
		 }	
	}
	
	public class FragmentCustomEvent extends Fragment {
		
		private Button buttonOfRecord;
		private EditText titleEditText;
		private EditText dateEditText;
		private EditText commentaryEditText;
		
		public FragmentCustomEvent(){}
		
		@Override
		 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			  View view = inflater.inflate(R.layout.add_event_other_fragment, container, false);
		      buttonOfRecord = (Button) view.findViewById(R.id.buttonAddEventOther);
		      buttonOfRecord.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					titleEditText = (EditText) findViewById(R.id.editTextOfTitleEvent);
					dateEditText = (EditText) findViewById(R.id.editTextOfDateOfEvent);
					commentaryEditText = (EditText) findViewById(R.id.editTextOfCommentaryOfOther);
					String title = titleEditText.getText().toString();
					String date = dateEditText.getText().toString();
					String commentary = commentaryEditText.getText().toString();
					if (title.equals(""))
						Toast.makeText(AddEventActivity.this, R.string.not_enter_title_of_event, Toast.LENGTH_LONG).show();
                    else if (title.length() > 40)
                        Toast.makeText(AddEventActivity.this, R.string.field_title_too_long, Toast.LENGTH_LONG).show();
					else if (date.equals(""))
						Toast.makeText(AddEventActivity.this, R.string.not_enter_date_of_event, Toast.LENGTH_LONG).show();
                    else if (!dateValidator.validateDateOnMask(date))
                        Toast.makeText(AddEventActivity.this, R.string.date_not_matching_mask,
                                Toast.LENGTH_LONG).show();
                    else if (!dateValidator.validateDatesOnValidMeans(date))
                        Toast.makeText(AddEventActivity.this, R.string.date_not_valid_means,
                                Toast.LENGTH_LONG).show();
                    else if (!dateValidator.ifDateIsLaterThenCurrent(date))
                        Toast.makeText(AddEventActivity.this, R.string.date_earlier_then_current2,
                                Toast.LENGTH_LONG).show();
                    else if (commentary.length() > 500)
                        Toast.makeText(AddEventActivity.this, R.string.field_commentary_too_long,
                                Toast.LENGTH_LONG).show();
					else if ( (!title.equals("")) && (!date.equals("")) ){
                        eventDAO.insertInCustomEventTable("Другое Событие", commentary, title, date, sqldb);
                        titleEditText.setText("");
                        dateEditText.setText("");
                        commentaryEditText.setText("");
                        Intent goToList = new Intent(AddEventActivity.this, ListOfEventsActivity.class);
                        startActivity(goToList);
                    }
				}
			});
		  return view;
		 }			
	}

	public class FragmentHoliday extends Fragment {
		
		String[] data = {none, newYear, christmas, oldNewYear, dayOfSaintVal,
				dayOfDefender, womanDay, dayOfSpringAndWork, dayOfVictory};

        private final static String none = "";
        private final static String newYear = "Новый Год";
        private final static String christmas = "Рождество";
        private final static String oldNewYear = "Старый Новый Год";
        private final static String dayOfSaintVal = "День Святого Валентина";
        private final static String dayOfDefender = "День Защитника Отечества";
        private final static String womanDay = "Международный Женский День";
        private final static String dayOfSpringAndWork = "Праздник Весны и Труда";
        private final static String dayOfVictory = "День Победы";
		
		private Button buttonOfRecord;
        private EditText commentaryEditText;
		
		public FragmentHoliday(){}
		
		@Override
		 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		  View view = inflater.inflate(R.layout.add_event_holiday_fragment, container, false);
		  buttonOfRecord = (Button) view.findViewById(R.id.buttonAddEventHoliday);
		  ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddEventActivity.this, android.R.layout.simple_spinner_item, data);
		  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  Spinner spinner = (Spinner) view.findViewById(R.id.spinnerOfHolidayEvent);
		  spinner.setAdapter(adapter);
          spinner.setPrompt("Праздник");
		  spinner.setSelection(0);
		  spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		  buttonOfRecord.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Spinner spinner = (Spinner) findViewById(R.id.spinnerOfHolidayEvent);
				long position = spinner.getSelectedItemId();
                commentaryEditText = (EditText) findViewById(R.id.editTextOfCommentaryOfHoliday);
                String commentary = commentaryEditText.getText().toString();
				if (position == 0)
					Toast.makeText(AddEventActivity.this, R.string.not_enter_type_of_holiday, 
							Toast.LENGTH_LONG).show();
                else if (commentary.length() > 500)
                    Toast.makeText(AddEventActivity.this, R.string.field_commentary_too_long,
                            Toast.LENGTH_LONG).show();
				else if (position != 0){
                    String selectedItemTitle = spinner.getSelectedItem().toString();
                    Holiday holidayHelper = new Holiday();
                    String dateOfHoliday = holidayHelper.getDateOfHolidayByTitleOfHoliday(selectedItemTitle);
                    eventDAO.insertInHolidayTable("Праздник", commentary, selectedItemTitle, dateOfHoliday, sqldb);
                    spinner.setSelection(0, true);
                    commentaryEditText.setText("");
                    Intent goToList = new Intent(AddEventActivity.this, ListOfEventsActivity.class);
                    startActivity(goToList);
                }
            }
		});
		  return view;
		 }	
		
	}
}
