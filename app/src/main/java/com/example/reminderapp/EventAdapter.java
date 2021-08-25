package com.example.reminderapp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder>
{

    private EventHandler eventHandler;
    private List<Event> eventlist;

    public EventAdapter(EventHandler eventHandler, List<Event> eventlist) {
        this.eventHandler = eventHandler;
        this.eventlist = eventlist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override // set values on views
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        myViewHolder.priorityLevel.setOnSeekBarChangeListener(null);
        myViewHolder.enable.setOnCheckedChangeListener(null);

        Date date=new Date(eventlist.get(i).getDate());
        myViewHolder.accountimage.setImageResource(R.mipmap.ic_launcher_account);
        myViewHolder.taskTitle.setText(eventlist.get(i).getTitle());
        myViewHolder.taskDescription.setText(eventlist.get(i).getNote());
        myViewHolder.taskDueDate.setText(getDateString(date));
        myViewHolder.priorityLevel.setProgress(eventlist.get(i).getPriority());
        myViewHolder.enable.setChecked(eventlist.get(i).getStatus());
        myViewHolder.delete.setImageResource(R.drawable.ic_cancel_red_24dp);

        final int j = i;
        myViewHolder.priorityLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int newPriority = myViewHolder.priorityLevel.getProgress();
                eventlist.get(j).setPriority(newPriority);
                eventHandler.onClickUpdate(j);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });


        myViewHolder.rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventHandler.onItemClick(j);
            }
        });



        myViewHolder.enable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Boolean newStatus = myViewHolder.enable.isChecked();
                eventlist.get(j).setStatus(newStatus);
                eventHandler.onClickUpdate(j);
            }
        });


        myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventlist.remove(j);
               eventHandler.onClickUpdate(j);

            }

        });


    }

    private String getDateString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd MMM");
        return dateFormat.format(date);
    }

    @Override
    public int getItemCount() {
        return eventlist.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView accountimage;
        private TextView taskTitle;
        private TextView taskDescription;
        private TextView taskDueDate;
        private SeekBar priorityLevel;
        private Switch enable;
        private ImageButton delete;
        private View rowView;

        public MyViewHolder(View rowView) {
            super(rowView);
            this.rowView=rowView;
            accountimage = rowView.findViewById(R.id.accountImage);
            taskTitle = rowView.findViewById(R.id.taskTitle);
            taskDescription = rowView.findViewById(R.id.taskDescription);
            taskDueDate = rowView.findViewById(R.id.taskDueDate);
            priorityLevel = rowView.findViewById(R.id.priorityLevel);
            enable = rowView.findViewById(R.id.enable);
            delete = rowView.findViewById(R.id.delete);
            priorityLevel.setMax(4);
        }
    }

    interface EventHandler{
        void onItemClick(int index);
        void onClickUpdate(int index);
    }
}
