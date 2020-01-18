package com.example.teamtrackingapptest2;

import java.util.ArrayList;

public class eventsadd {
    String events;
    ArrayList<String> selection;

    public eventsadd(String events, ArrayList<String> selection) {
        this.events = events;
        this.selection = selection;
    }


    public ArrayList<String> getSelection() {
        return selection;
    }

    public void setSelection(ArrayList<String> selection) {
        this.selection = selection;
    }

    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }
}
