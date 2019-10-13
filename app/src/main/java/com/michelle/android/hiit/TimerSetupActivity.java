package com.michelle.android.hiit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TimerSetupActivity extends AppCompatActivity {

    private WorkoutStep warmUp = new WorkoutStep("Warm Up", 2);

    ArrayList<WorkoutStep> workoutSteps = new ArrayList<>();

    private WorkoutStep coolDown = new WorkoutStep("Cool Down", 5);

    Button startBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_setup);

        final WorkoutStep work = new WorkoutStep("Work", 3);
        workoutSteps.add(work);
        WorkoutStep rest = new WorkoutStep("Rest", 5);
        workoutSteps.add(rest);
        WorkoutStep rounds = new WorkoutStep("Rounds", 8);
        workoutSteps.add(rounds);

        setWorkoutStep(warmUp, findViewById(R.id.warm_up_container));

        LinearLayout steps = findViewById(R.id.steps);
        for (int i = 0; i < workoutSteps.size(); i++) {
            setWorkoutStep(workoutSteps.get(i), steps.getChildAt(i));
        }
        setWorkoutStep(coolDown, findViewById(R.id.cool_down_container));

        startBtn = findViewById(R.id.start_btn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<WorkoutStep> steps = new ArrayList<>();
                steps.add(warmUp);
                steps.addAll(workoutSteps);
                steps.add(coolDown);

                Intent intent = new Intent(TimerSetupActivity.this, TimerActivity.class);
                intent.putExtra(TimerActivity.EXTRA_STEPS, steps);
                startActivity(intent);

            }
        });
    }

    private void setWorkoutStep(WorkoutStep workoutStep, View parent) {
        ((TextView) parent.findViewById(R.id.timer_item_label)).setText(workoutStep.label);
        ((TextView) parent.findViewById(R.id.timer_item_time)).setText(TimerUtils.formatTime(workoutStep.time));
    }

}
