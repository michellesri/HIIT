package com.michelle.android.hiit;

import java.io.Serializable;
import java.util.ArrayList;

public class PresetStep implements Serializable {
    String title;
    String description;
    ArrayList<WorkoutStep> workoutList;

    public PresetStep(String title, String description, ArrayList<WorkoutStep> workoutList) {
        this.title = title;
        this.description = description;
        this.workoutList = workoutList;
    }
}
