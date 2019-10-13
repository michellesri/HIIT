package com.michelle.android.hiit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TimerActivity extends AppCompatActivity {

    public static final String EXTRA_STEPS = "steps";

    private Handler handler = new Handler();
    private Runnable runnable;
    private int time = 0;
    private int currentIntStep = 1;

    private boolean isPlay = false;

    private ImageView playPauseBtnIv;
    private TextView timerActivityTv;
    private TextView currentStepTv;
    private TextView timeDisplayTv;

    private ArrayList<WorkoutStep> steps;
    private WorkoutStep currentWorkoutStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        playPauseBtnIv = findViewById(R.id.play_pause_button);
        timerActivityTv = findViewById(R.id.timer_activity);
        timeDisplayTv = findViewById(R.id.time_display);
        currentStepTv = findViewById(R.id.current_step);
        setupPlayPauseBtn();
        renderTimer();

        Intent intent = getIntent();
        steps = (ArrayList<WorkoutStep>) intent.getSerializableExtra(EXTRA_STEPS);
        renderStepsInTimer(steps, currentIntStep);
        time = currentWorkoutStep.time;

    }

    private void setupPlayPauseBtn() {
        playPauseBtnIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPlay) {
                    playPauseBtnIv.setImageResource(R.drawable.ic_pause_48dp);
                    resumeTimer();
                    isPlay = true;
                } else {
                    playPauseBtnIv.setImageResource(R.drawable.ic_play_48dp);
                    stopTimer();
                    isPlay = false;
                }
            }
        });
    }

    private void scheduleTimer() {
        if (time < 0) {
            stopTimer();
            if (currentIntStep > steps.size()) {
                return;
            }
            renderStepsInTimer(steps, currentIntStep);
            startTimer();
            return;
        }
        renderTimer();
        runnable = new Runnable() {
            @Override
            public void run() {
                time -= 1;
                System.out.println(time);
                scheduleTimer();
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    private void stopTimer() {
        handler.removeCallbacks(runnable);
    }

    private void startTimer() {
        time = currentWorkoutStep.time;
        resumeTimer();
    }

    private void resumeTimer() {
        // stop timer in case it wasn't stopped before. theoretically, this shouldn't happen
        stopTimer();
        scheduleTimer();
    }

    private void renderTimer() {
        timeDisplayTv.setText(TimerUtils.formatTime(time));
    }

    private void renderStepsInTimer(ArrayList<WorkoutStep> steps, int currentIntStep) {
        currentWorkoutStep = steps.get(currentIntStep - 1);
        String label = currentWorkoutStep.label;
        int time = currentWorkoutStep.time;

        timerActivityTv.setText(label);
        timeDisplayTv.setText(TimerUtils.formatTime(time));
        currentStepTv.setText(currentIntStep + "/" + steps.size());
        this.currentIntStep += 1;

    }
}
