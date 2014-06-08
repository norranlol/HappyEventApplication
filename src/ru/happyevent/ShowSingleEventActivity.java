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
import org.joda.time.DateTime;
import ru.dao.EventDAO;
import ru.dao.EventDatabaseHelper;
import ru.date.DateCalculator;
import ru.date.DateCalculatorImpl;
import ru.date.DateDifference;
import ru.model.*;

import java.util.regex.Pattern;

public class ShowSingleEventActivity extends Activity {

    private EventDatabaseHelper eventDBHelper;
    private SQLiteDatabase sqldb;
    private EventDAO eventDAO = new EventDAO();
    private Event findEvent = null;
    private DateCalculator dateCalculator = new DateCalculatorImpl();

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
        private TextView textViewOfTime;

        public FragmentShowBirthday(){}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.show_event_birthday_fragment, container, false);
            textViewOfWhom = (TextView) view.findViewById(R.id.textViewWhomBirthdayOutputField);
            textViewOfDateOfBirth = (TextView) view.findViewById(R.id.textViewDateOfBirthOutputField);
            textViewOfCommentary = (TextView) view.findViewById(R.id.textViewShowCommentaryOfBirthField);
            textViewOfTime = (TextView) view.findViewById(R.id.textViewTimeLostFieldBirthday);
            String whom = findEvent.getBirthday().getWhom();
            String dateOfBirth = findEvent.getBirthday().getDateOfBirth();
            String commentary = findEvent.getCommentary();
            textViewOfWhom.setText(whom);
            textViewOfDateOfBirth.setText(dateOfBirth);
            textViewOfCommentary.setText(commentary);
            String[] mas = dateOfBirth.split(Pattern.quote("-"));
            int day = Integer.parseInt(mas[0]);
            int month = Integer.parseInt(mas[1]);
            if (DateTime.now().getMonthOfYear() < month)
                mas[2] = String.valueOf(DateTime.now().getYear());
            else mas[2] = String.valueOf(DateTime.now().getYear() + 1);
            dateOfBirth = "";
            for (int i = 0; i < mas.length; i++){
                if (i != mas.length -1)
                    dateOfBirth += mas[i] + "-";
                else dateOfBirth += mas[i];
            }
            DateDifference dateDifference = dateCalculator.calculateDifferenceBeetweenFutureAndCurrentDates(dateOfBirth);
            String dateDiffString = dateDifference.getYears() + " лет(год), " + dateDifference.getMonths() + " месяцев(яц), " +
                    dateDifference.getWeeks() + " недель(я), " + dateDifference.getDays() + " дней(день).";
            textViewOfTime.setText(dateDiffString);
            return view;
        }


    }

    public class FragmentShowDemobee extends Fragment {

        private TextView textViewOfWhom;
        private TextView textViewOfDateOfDemobee;
        private TextView textViewOfCommentary;
        private TextView textViewOfTime;

        public FragmentShowDemobee(){}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.show_event_demobee_fragment, container, false);
            textViewOfWhom = (TextView) view.findViewById(R.id.textViewWhomDemobeeOutputField);
            textViewOfDateOfDemobee = (TextView) view.findViewById(R.id.textViewDateOfDemobeeOutputField);
            textViewOfCommentary = (TextView) view.findViewById(R.id.textViewShowCommentaryOfDemobeeField);
            textViewOfTime = (TextView) view.findViewById(R.id.textViewTimeLostFieldDemobee);
            String whom = findEvent.getDemobee().getWhom();
            String dateOfDemobee = findEvent.getDemobee().getDateOfDemobee();
            String commentary = findEvent.getCommentary();
            textViewOfWhom.setText(whom);
            textViewOfDateOfDemobee.setText(dateOfDemobee);
            textViewOfCommentary.setText(commentary);
            DateDifference dateDifference = dateCalculator.calculateDifferenceBeetweenFutureAndCurrentDates(dateOfDemobee);
            String dateDiffString = dateDifference.getYears() + " лет(год), " + dateDifference.getMonths() + " месяцев(яц), " +
                    dateDifference.getWeeks() + " недель(я), " + dateDifference.getDays() + " дней(день).";
            textViewOfTime.setText(dateDiffString);
            return view;
        }
    }

    public class FragmentShowCustomEvent extends Fragment {

        private TextView textViewOfTitle;
        private TextView textViewOfDateOfCustom;
        private TextView textViewOfCommentary;
        private TextView textViewOfTime;

        public FragmentShowCustomEvent(){}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.show_event_other_fragment, container, false);
            textViewOfTitle = (TextView) view.findViewById(R.id.textViewTitleCustomOutputField);
            textViewOfDateOfCustom = (TextView) view.findViewById(R.id.textViewDateOfCustomOutputField);
            textViewOfCommentary = (TextView) view.findViewById(R.id.textViewShowCommentaryOfCustomField);
            textViewOfTime = (TextView) view.findViewById(R.id.textViewTimeLostFieldCustomEvent);
            String title = findEvent.getCustomEvent().getTitle();
            String date = findEvent.getCustomEvent().getDateOfCustomEvent();
            String commentary = findEvent.getCommentary();
            textViewOfTitle.setText(title);
            textViewOfDateOfCustom.setText(date);
            textViewOfCommentary.setText(commentary);
            DateDifference dateDifference = dateCalculator.calculateDifferenceBeetweenFutureAndCurrentDates(date);
            String dateDiffString = dateDifference.getYears() + " лет(год), " + dateDifference.getMonths() + " месяцев(яц), " +
                    dateDifference.getWeeks() + " недель(я), " + dateDifference.getDays() + " дней(день).";
            textViewOfTime.setText(dateDiffString);
            return view;
        }
    }


    public class FragmentShowHoliday extends Fragment {

        private TextView textViewOfTypeHoliday;
        private TextView textViewOfCommentary;
        private TextView textViewOfTime;

        public FragmentShowHoliday(){}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.show_event_holiday_fragment, container, false);
            textViewOfTypeHoliday = (TextView) view.findViewById(R.id.textViewTypeOfHolidayOutputField);
            textViewOfCommentary = (TextView) view.findViewById(R.id.textViewShowCommentaryOfHolidayField);
            textViewOfTime = (TextView) view.findViewById(R.id.textViewTimeLostFieldHoliday);
            String type = findEvent.getHoliday().getType();
            String commentary = findEvent.getCommentary();
            String dateOfHoliday = findEvent.getHoliday().getDateOfHoliday();
            String[] mas = dateOfHoliday.split(Pattern.quote("-"));
            int day = Integer.parseInt(mas[0]);
            int month = Integer.parseInt(mas[1]);
            if (DateTime.now().getMonthOfYear() < month)
                mas[2] = String.valueOf(DateTime.now().getYear());
            else mas[2] = String.valueOf(DateTime.now().getYear() + 1);
            dateOfHoliday = "";
            for (int i = 0; i < mas.length; i++){
                if (i != mas.length -1)
                    dateOfHoliday += mas[i] + "-";
                else dateOfHoliday += mas[i];
            }
            textViewOfTypeHoliday.setText(type);
            textViewOfCommentary.setText(commentary);
            DateDifference dateDifference = dateCalculator.calculateDifferenceBeetweenFutureAndCurrentDates(dateOfHoliday);
            String dateDiffString = dateDifference.getYears() + " лет(год), " + dateDifference.getMonths() + " месяцев(яц), " +
                    dateDifference.getWeeks() + " недель(я), " + dateDifference.getDays() + " дней(день).";
            textViewOfTime.setText(dateDiffString);
            return view;
        }
    }
}
