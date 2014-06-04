package ru.happyevent;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ru.dao.EventDAO;
import ru.dao.EventDatabaseHelper;
import ru.model.*;

public class ShowSingleEventActivity extends Activity {

    private EventDatabaseHelper eventDBHelper;
    private SQLiteDatabase sqldb;
    private EventDAO eventDAO = new EventDAO();
    private Event findEvent = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_event);

        eventDBHelper = new EventDatabaseHelper(this);
        sqldb = eventDBHelper.getWritableDatabase();

        String type = getIntent().getStringExtra("type");
        String idExtra = getIntent().getStringExtra("id");
        int id = 0;
        if (idExtra == null)
            id = 0;
        else
            id = Integer.parseInt(idExtra);

        if (type.equals("День Рождения")){
            findEvent = eventDAO.selectEventByEventId(id, sqldb);
            Birthday birthday = eventDAO.selectBirthdayByEventId(id, sqldb);
            changeFragment(birthday);
            findEvent.setBirthday(birthday);
        } else if (type.equals("Дембель")){
            findEvent = eventDAO.selectEventByEventId(id ,sqldb);
            Demobee demobee = eventDAO.selectDemobeeByEventId(id ,sqldb);
            changeFragment(demobee);
            findEvent.setDemobee(demobee);
        } else if (type.equals("Другое Событие")){
            findEvent = eventDAO.selectEventByEventId(id, sqldb);
            CustomEvent customEvent = eventDAO.selectCustomEventByEventId(id, sqldb);
            changeFragment(customEvent);
            findEvent.setCustomEvent(customEvent);
        } else if (type.equals("Праздник")){
            findEvent = eventDAO.selectEventByEventId(id, sqldb);
            Holiday holiday = eventDAO.selectHolidayByEventId(id, sqldb);
            changeFragment(holiday);
            findEvent.setHoliday(holiday);
        }
    }

    public void changeFragment(Object object){
        if (object instanceof Birthday){
            FragmentShowBirthday fragment = new FragmentShowBirthday();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction tx = manager.beginTransaction();
            tx.add(R.id.layoutToReplaceByType, fragment);
            tx.commit();
        } else if (object instanceof Demobee){
            FragmentShowDemobee fragment = new FragmentShowDemobee();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction tx = manager.beginTransaction();
            tx.add(R.id.layoutToReplaceByType, fragment);
            tx.commit();
        } else if (object instanceof CustomEvent){
            FragmentShowCustomEvent fragment = new FragmentShowCustomEvent();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction tx = manager.beginTransaction();
            tx.add(R.id.layoutToReplaceByType, fragment);
            tx.commit();
        } else if (object instanceof Holiday){
            FragmentShowHoliday fragment = new FragmentShowHoliday();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction tx = manager.beginTransaction();
            tx.add(R.id.layoutToReplaceByType, fragment);
            tx.commit();
        }
    }

    public class FragmentShowBirthday extends Fragment {

        private TextView textViewOfWhom;
        private TextView textViewOfDateOfBirth;
        private TextView textViewOfCommentary;

        public FragmentShowBirthday(){}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.show_event_birthday_fragment, container, false);
            textViewOfWhom = (TextView) view.findViewById(R.id.textViewWhomBirthdayOutputField);
            textViewOfDateOfBirth = (TextView) view.findViewById(R.id.textViewDateOfBirthOutputField);
            textViewOfCommentary = (TextView) view.findViewById(R.id.textViewShowCommentaryOfBirthField);
            String whom = findEvent.getBirthday().getWhom();
            String dateOfBirth = findEvent.getBirthday().getDateOfBirth();
            String commentary = findEvent.getCommentary();
            textViewOfWhom.setText(whom);
            textViewOfDateOfBirth.setText(dateOfBirth);
            textViewOfCommentary.setText(commentary);
            return view;
        }


    }

    public class FragmentShowDemobee extends Fragment {

        private TextView textViewOfWhom;
        private TextView textViewOfDateOfDemobee;
        private TextView textViewOfCommentary;

        public FragmentShowDemobee(){}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.show_event_demobee_fragment, container, false);
            textViewOfWhom = (TextView) view.findViewById(R.id.textViewWhomDemobeeOutputField);
            textViewOfDateOfDemobee = (TextView) view.findViewById(R.id.textViewDateOfDemobeeOutputField);
            textViewOfCommentary = (TextView) view.findViewById(R.id.textViewShowCommentaryOfDemobeeField);
            String whom = findEvent.getDemobee().getWhom();
            String dateOfDemobee = findEvent.getDemobee().getDateOfDemobee();
            String commentary = findEvent.getCommentary();
            textViewOfWhom.setText(whom);
            textViewOfDateOfDemobee.setText(dateOfDemobee);
            textViewOfCommentary.setText(commentary);
            return view;
        }
    }

    public class FragmentShowCustomEvent extends Fragment {

        private TextView textViewOfTitle;
        private TextView textViewOfDateOfCustom;
        private TextView textViewOfCommentary;

        public FragmentShowCustomEvent(){}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.show_event_other_fragment, container, false);
            textViewOfTitle = (TextView) view.findViewById(R.id.textViewTitleCustomOutputField);
            textViewOfDateOfCustom = (TextView) view.findViewById(R.id.textViewDateOfCustomOutputField);
            textViewOfCommentary = (TextView) view.findViewById(R.id.textViewShowCommentaryOfCustomField);
            String title = findEvent.getCustomEvent().getTitle();
            String date = findEvent.getCustomEvent().getDateOfCustomEvent();
            String commentary = findEvent.getCommentary();
            textViewOfTitle.setText(title);
            textViewOfDateOfCustom.setText(date);
            textViewOfCommentary.setText(commentary);
            return view;
        }
    }


    public class FragmentShowHoliday extends Fragment {

        private TextView textViewOfTypeHoliday;
        private TextView textViewOfCommentary;

        public FragmentShowHoliday(){}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.show_event_holiday_fragment, container, false);
            textViewOfTypeHoliday = (TextView) view.findViewById(R.id.textViewTypeOfHolidayOutputField);
            textViewOfCommentary = (TextView) view.findViewById(R.id.textViewShowCommentaryOfHolidayField);
            String type = findEvent.getHoliday().getType();
            String commentary = findEvent.getCommentary();
            textViewOfTypeHoliday.setText(type);
            textViewOfCommentary.setText(commentary);
            return view;
        }
    }
}
