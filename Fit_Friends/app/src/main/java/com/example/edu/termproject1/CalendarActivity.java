package com.example.edu.termproject1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.andexert.calendarlistview.library.DatePickerController;
import com.andexert.calendarlistview.library.DayPickerView;
import com.andexert.calendarlistview.library.SimpleMonthAdapter;

import java.util.Calendar;
import java.util.Date;

import pl.rafman.scrollcalendar.ScrollCalendar;
import pl.rafman.scrollcalendar.contract.DateWatcher;
import pl.rafman.scrollcalendar.contract.MonthScrollListener;
import pl.rafman.scrollcalendar.contract.OnDateClickListener;
import pl.rafman.scrollcalendar.data.CalendarDay;

//This code is from git hub, but i never got to use it
public class CalendarActivity extends AppCompatActivity {
    Workout workout;
    private Calendar selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        //gets exercise
        Intent i = getIntent();
        workout  =(Workout)getIntent().getSerializableExtra("copy");


        ScrollCalendar scrollCalendar = (ScrollCalendar) findViewById(R.id.scrollCalendar);
        scrollCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onCalendarDayClicked(int year, int month, int day) {
                doOnCalendarDayClicked(year, month, day);

                String dateCombo;
                int day1 = selected.get(Calendar.DAY_OF_MONTH);
                int month1 =selected.get(Calendar.MONTH) + 1;
                int year1 = selected.get(Calendar.YEAR);
                dateCombo = Integer.toString(day1) + "/" + Integer.toString(month1) + "/" + Integer.toString(year1);
                workout.setDate(dateCombo);

                Intent intent = new Intent(getApplicationContext(), WorkoutDetailCopy.class);
                intent.putExtra("copy", workout);
                startActivity(intent);
            }
        });
        scrollCalendar.setDateWatcher(new DateWatcher() {
            @Override
            public int getStateForDate(int year, int month, int day) {
                //    CalendarDay.DEFAULT,
                //    CalendarDay.DISABLED,
                //    CalendarDay.TODAY,
                //    CalendarDay.UNAVAILABLE,
                //    CalendarDay.SELECTED,
                return  doGetStateForDate(year, month, day);
            }
        });
        scrollCalendar.setMonthScrollListener(new MonthScrollListener() {
            @Override
            public boolean shouldAddNextMonth(int lastDisplayedYear, int lastDisplayedMonth) {
                // return false if you don't want to show later months
                return true;
            }
            @Override
            public boolean shouldAddPreviousMonth(int firstDisplayedYear, int firstDisplayedMonth) {
                // return false if you don't want to show previous months
                return false;
            }
        });
    }
    private void doOnCalendarDayClicked(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);


        if (selected != null && selected.equals(calendar)) {
            selected = null;
        } else {
            selected = calendar;
        }
    }
    private int doGetStateForDate(int year, int month, int day) {
        if (isSelected(selected, year, month, day)) {
            return CalendarDay.SELECTED;
        }
        if (isToday(year, month, day)) {
            return CalendarDay.TODAY;
        }

        return CalendarDay.DEFAULT;
    }
    private boolean isSelected(Calendar selected, int year, int month, int day) {
        if (selected == null) {
            return false;
        }
        //noinspection UnnecessaryLocalVariable
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, selected.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, selected.get(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, selected.get(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long millis = calendar.getTimeInMillis();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        long millis2 = calendar.getTimeInMillis();

        return millis == millis2;
    }
    private boolean isToday(int year, int month, int day) {
        //noinspection UnnecessaryLocalVariable
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // Today in milliseconds
        long today = calendar.getTime().getTime();

        // Given day in milliseconds
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        long calendarMillis = calendar.getTime().getTime();

        return today == calendarMillis;
    }
}
