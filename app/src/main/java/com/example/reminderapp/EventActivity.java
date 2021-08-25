package com.example.reminderapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

public class EventActivity extends AppCompatActivity {

    private EventViewModel viewModel;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        viewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        index = getIntent().getIntExtra(MainActivity.INDEX_KEY,0);

        if(savedInstanceState != null){
            viewModel.readFromBundle(savedInstanceState);
        }else{
            viewModel.setTime(getIntent().getLongExtra(MainActivity.DATE_KEY, Calendar.getInstance().getTimeInMillis()));
            viewModel.setTitle(getIntent().getStringExtra(MainActivity.TITLE_KEY));
            viewModel.setNote(getIntent().getStringExtra(MainActivity.NOTE_KEY));
            viewModel.setPriority(getIntent().getIntExtra(MainActivity.PRIORITY_KEY,0));
        }

        CalendarView calendarview = findViewById(R.id.calendarView2);
        Button saveButton = findViewById(R.id.savabutton);
        EditText titleEdit = findViewById(R.id.TitleText);
        EditText noteEdit = findViewById(R.id.NoteText);
        SeekBar priorityBar = findViewById(R.id.eventSeekBar);

        titleEdit.setText(viewModel.getLiveTitle().getValue());
        noteEdit.setText(viewModel.getLiveNote().getValue());
        priorityBar.setProgress(viewModel.getLivePriority().getValue());

        calendarview.setDate(viewModel.getLiveDate().getValue());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent(EventActivity.this, MainActivity.class);

                resultIntent.putExtra(MainActivity.TITLE_KEY, viewModel.getLiveTitle().getValue());
                resultIntent.putExtra(MainActivity.NOTE_KEY,viewModel.getLiveNote().getValue());
                resultIntent.putExtra(MainActivity.PRIORITY_KEY,viewModel.getLivePriority().getValue());
                resultIntent.putExtra(MainActivity.DATE_KEY,viewModel.getLiveDate().getValue());
                resultIntent.putExtra(MainActivity.INDEX_KEY,index);

                // reuse the activity if on top the backstack instead of creating a new activity
                resultIntent.setFlags(FLAG_ACTIVITY_SINGLE_TOP);
                // finish before startactivity to remove this activity from the backstack
                finish();
                startActivity(resultIntent);

                // interestingly one could go with the following as an alternative:
                // resultIntent.setFlags(FLAG_ACTIVITY_SINGLE_TOP || FLAG_ACTIVITY_CLEAR_TOP);
                // startActivity(resultIntent);
                // no need to finish here since FLAG_ACTIVITY_CLEAR_TOP takes care of it automatically.
            }
        });

        calendarview.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = new GregorianCalendar(year,month,dayOfMonth);
               viewModel.setTime(calendar.getTimeInMillis());
            }
        });

        priorityBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    viewModel.setPriority(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        titleEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            viewModel.setTitle(s.toString());
            }
        });

        noteEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            viewModel.setNote(s.toString());
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        viewModel.writeToBundle(outState);
    }

}
