package com.example.reminderapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Collections;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity implements EventAdapter.EventHandler {

    private List<Event> eventList = new ArrayList<>();

    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;

    private MainViewModel viewModel;

    public static final String TITLE_KEY = "com.example.reminderapp.title";
    public static final String NOTE_KEY = "com.example.reminderapp.note";
    public static final String PRIORITY_KEY = "com.example.reminderapp.priority";
    public static final String DATE_KEY = "com.example.reminderapp.date";
    public static final String INDEX_KEY = "com.example.reminderapp.index";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        recyclerView = findViewById(R.id.myList);
        RadioGroup radioGroup = findViewById(R.id.sort);



        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventAdapter = new EventAdapter(this, viewModel.getLiveEventList().getValue());
        recyclerView.setAdapter(eventAdapter);



        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.sortPriority:
                       viewModel.sortByPrio();
                        updateUI();
                        break;
                    case R.id.sortDate:
                       viewModel.sortByDate();
                        updateUI();
                        break;
                }
            }
        });



        viewModel.getLiveEventList().observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                updateUI();
            }
        });

        }

        public void updateUI() {
            eventAdapter.notifyDataSetChanged();
        }

        public class PriorityComperator implements Comparator<Event> {

            public int compare(Event event1, Event event2) {
                return Integer.valueOf(event2.getPriority()).compareTo(event1.getPriority());
            }
        }

        public class DateComperator implements Comparator<Event> {

            public int compare(Event event1, Event event2) {
                return event1.getDate().compareTo(event2.getDate());
            }
        }

        @Override
        public void onItemClick(int index) {
            Event event = viewModel.getEvent(index);
            Intent intent = new Intent(MainActivity.this,EventActivity.class);
            intent.putExtra(TITLE_KEY,event.getTitle());
            intent.putExtra(NOTE_KEY,event.getNote());
            intent.putExtra(PRIORITY_KEY,event.getPriority());
            intent.putExtra(DATE_KEY,event.getDate());
            intent.putExtra(INDEX_KEY,index);
            startActivity(intent);
        }

    @Override
    public void onClickUpdate(int index) {
        updateUI();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        String title = intent.getStringExtra(TITLE_KEY);
        String note = intent.getStringExtra(NOTE_KEY);
        Date date= new Date(intent.getLongExtra(DATE_KEY,0));            //Date geçirme işini hallet.
        int priority = intent.getIntExtra(PRIORITY_KEY,0);
        int index= intent.getIntExtra(INDEX_KEY,0);

        viewModel.UpdateEvent(index,title,note,date,priority);

    }

    }
