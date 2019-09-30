package com.michelle.android.hiit;

import java.io.Serializable;

public class WorkoutStep implements Serializable {
    String label;
    int time;

    public WorkoutStep(String label, int time) {
        this.label = label;
        this.time = time;
    }
}
