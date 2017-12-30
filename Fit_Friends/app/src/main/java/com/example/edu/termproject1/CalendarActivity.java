package com.example.edu.termproject1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.andexert.calendarlistview.library.DatePickerController;
import com.andexert.calendarlistview.library.DayPickerView;
import com.andexert.calendarlistview.library.SimpleMonthAdapter;
//This code is from git hub, but i never got to use it
public class CalendarActivity extends AppCompatActivity
                                implements DatePickerController {
    private DayPickerView dayPickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        dayPickerView = (DayPickerView) findViewById(R.id.pickerView);
        dayPickerView.setController(this);

    }
    @Override
    public int getMaxYear()
    {
        return 2050;
    }
    @Override
    public void onDayOfMonthSelected(int year, int month, int day)
    {
        Log.e("Day Selected", day + " / " + month + " / " + year);
    }

    @Override
    public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {
       // Log.e("Date range selected", selectedDays.getFirst().toString() + " --> " + selectedDays.getLast().toString());
    }
}
