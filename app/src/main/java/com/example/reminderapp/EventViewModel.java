package com.example.reminderapp;

import android.os.Bundle;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

public class EventViewModel extends ViewModel {
    public static final String BUNDLE_TITLE = "com.example.reminderapp.TITLE";
    public static final String BUNDLE_NOTE = "com.example.reminderapp.NOTE";
    public static final String BUNDLE_PRIORITY = "com.example.reminderapp.PRIORIY";
    public static final String BUNDLE_DATE = "com.example.reminderapp.DATE";

    private MutableLiveData<String> liveTitle,liveNote;
    private MutableLiveData<Long> liveDate;
    private MutableLiveData<Integer> livePriority;

    public EventViewModel() {
        liveTitle = new MutableLiveData<>();
        liveNote = new MutableLiveData<>();
        livePriority = new MutableLiveData<>();
        liveDate = new MutableLiveData<>();

        liveTitle.setValue("");
        liveNote.setValue("");
        livePriority.setValue(0);
        liveTitle.setValue("");

    }

    public MutableLiveData<String> getLiveTitle() {
        return liveTitle;
    }
    public MutableLiveData<String> getLiveNote() {
        return liveNote;
    }
    public MutableLiveData<Integer> getLivePriority() {
        return livePriority;
    }
    public MutableLiveData<Long> getLiveDate() {
        return liveDate;
    }

    public void setTime (Long date) {
        liveDate.setValue(date);
    }

    public void setTitle (String title) {
        liveTitle.setValue(title);
    }

    public void setNote (String note) {
        liveNote.setValue(note);
    }

    public void setPriority (int priority) {
        if(priority<=4 && priority>=0){
        livePriority.setValue(priority);
        }

    }

    public void writeToBundle(Bundle bundle) {
        bundle.putString(BUNDLE_TITLE, getLiveTitle().getValue());
        bundle.putString(BUNDLE_NOTE, getLiveNote().getValue());
        bundle.putLong(BUNDLE_DATE, getLiveDate().getValue()); //dateyi koyma
        bundle.putInt(BUNDLE_PRIORITY, getLivePriority().getValue());

    }

    public void readFromBundle(Bundle bundle) {
        setTitle(bundle.getString(BUNDLE_TITLE));
        setNote(bundle.getString(BUNDLE_NOTE));
        setPriority(bundle.getInt(BUNDLE_PRIORITY));
        setTime(bundle.getLong(BUNDLE_DATE));
    }

}
