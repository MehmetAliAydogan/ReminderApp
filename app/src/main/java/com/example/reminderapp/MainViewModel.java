package com.example.reminderapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.ViewModel;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.MutableLiveData;

public class MainViewModel extends ViewModel {

    private MutableLiveData<List<Event>> liveEventList;
    private List<Event> eventList = new ArrayList<>();
    private Event event;
    private Date d;

    public MainViewModel(){

        try {
            d = new SimpleDateFormat("dd/MM/yyyy").parse("06/04/2019");
            event = new Event("CS310 Midterm1", "Midterm at LO65 from 9am", d, 4, true);
            eventList.add(event);

            d = new SimpleDateFormat("dd/MM/yyyy").parse("07/04/2019");
            event = new Event("Room meeting", "Meet Mr. Mbape before things get worse", d, 4, true);
            eventList.add(event);

            d = new SimpleDateFormat("dd/MM/yyyy").parse("06/05/2019");
            event = new Event("CS310 Midterm1 Objection", "Room LO65 from 10am ", d, 3, false);
            eventList.add(event);

            d = new SimpleDateFormat("dd/MM/yyyy").parse("09/04/2019");
            event = new Event("CS308 Project presentation", "4th sprint user-inteface design", d, 4, true);
            eventList.add(event);

            d = new SimpleDateFormat("dd/MM/yyyy").parse("31/03/2019");
            event = new Event("Judiy's Funeral", "buy flowers", d, 3, false);
            eventList.add(event);

            d = new SimpleDateFormat("dd/MM/yyyy").parse("02/04/2019");
            event = new Event("Avengers release", "Invite roommates to watch it", d, 2, true);
            eventList.add(event);

            d = new SimpleDateFormat("dd/MM/yyyy").parse("05/05/2019");
            event = new Event("Job application", "Ware the black suit", d, 4, false);
            eventList.add(event);

            d = new SimpleDateFormat("dd/MM/yyyy").parse("06/04/2019");
            event = new Event("Google's interview", "Focus on software engineering nothing else matters", d, 1, true);
            eventList.add(event);

            d = new SimpleDateFormat("dd/MM/yyyy").parse("06/04/2019");
            event = new Event("Summer school starts", "Nothing special", d, 4, true);
            eventList.add(event);

            d = new SimpleDateFormat("dd/MM/yyyy").parse("08/05/2019");
            event = new Event("MS application deadline", "now or never", d, 4, true);
            eventList.add(event);

            d = new SimpleDateFormat("dd/MM/yyyy").parse("29/03/2019");
            event = new Event("Make plane reservation", "Most preferably Turkish Airlines", d, 3, true);
            eventList.add(event);

            d = new SimpleDateFormat("dd/MM/yyyy").parse("06/04/2019");
            event = new Event("Trip to Africa", "Dont miss your plane", d, 4, false);
            eventList.add(event);

            d = new SimpleDateFormat("dd/MM/yyyy").parse("06/05/2019");
            event = new Event("See the dentist", "Midterm at LO65 from 9am", d, 2, true);
            eventList.add(event);

            d = new SimpleDateFormat("dd/MM/yyyy").parse("04/04/2019");
            event = new Event("CS310 Midterm2", "Midterm at LO65 from 9am", d, 4, true);
            eventList.add(event);

            d = new SimpleDateFormat("dd/MM/yyyy").parse("06/04/2019");
            event = new Event("Call Dad", "Remind him of fees", d, 3, true);
            eventList.add(event);

            d = new SimpleDateFormat("dd/MM/yyyy").parse("06/05/2019");
            event = new Event("Shopping", "With loved ones", d, 1, false);
            eventList.add(event);

            d = new SimpleDateFormat("dd/MM/yyyy").parse("01/04/2019");
            event = new Event("fundraiser", "Give whatever you have", d, 4, true);
            eventList.add(event);

            d = new SimpleDateFormat("dd/MM/yyyy").parse("28/05/2019");
            event = new Event("CS310 Final", "All topics includes", d, 1, true);
            eventList.add(event);

            d = new SimpleDateFormat("dd/MM/yyyy").parse("06/04/2019");
            event = new Event("Date with Juliet", "Hilton hotel, Mecidiyekoy", d, 4, false);
            eventList.add(event);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        liveEventList = new MutableLiveData<>();
        liveEventList.setValue(eventList);

    }

    public MutableLiveData<List<Event>> getLiveEventList() {
        return liveEventList;
    }

    public List<Event> getEventList() {
        return liveEventList.getValue();
    }

    public Event getEvent(int index) {
        return liveEventList.getValue().get(index);
    }

    public void setLiveEventList(MutableLiveData<List<Event>> liveEventList) {
        this.liveEventList = liveEventList;
    }

    public void selectEvent(int index){
        List<Event> events = liveEventList.getValue();
        Event event = events.get(index);
        //phone.setChecked(!phone.isChecked());
        liveEventList.setValue(events);
    }

    public void  UpdateEvent(int index, String title,String note,Date time,int priority){
        List<Event> events = liveEventList.getValue();
        Event event = events.get(index);
        event.setTitle(title);
        event.setNote(note);
        event.setDate(time);
        event.setPriority(priority);
        liveEventList.setValue(events);
    }

    public void deleteEvent(int index){
        List<Event> events = liveEventList.getValue();
        Event event = events.get(index);
        events.remove(index);
        liveEventList.setValue(events);
    }

    public void sortByPrio(){
        List<Event> events = liveEventList.getValue();
        Collections.sort(events, new PriorityComperator());
        liveEventList.setValue(events);
    }

    public void sortByDate(){
        List<Event> events = liveEventList.getValue();
        Collections.sort(events, new DateComperator());
        liveEventList.setValue(events);
    }

    public class PriorityComperator implements Comparator<Event> {

        public int compare(Event event1, Event event2) {
            return Integer.valueOf(event2.getPriority()).compareTo(event1.getPriority());
        }
    }

    public class DateComperator implements Comparator<Event> {

        public int compare(Event event1, Event event2) {
            Date date1 = new Date(event1.getDate());
            Date date2 = new Date(event2.getDate());
            return date1.compareTo(date2);
        }
    }


    // saving large data as a bundle is not recommended.
    // thus we omit the bundle methods here.
    // large data has to be maintained by use of databases.
    // note that this activity will lose data with system kills.
}
