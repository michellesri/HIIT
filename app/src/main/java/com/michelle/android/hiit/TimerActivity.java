package com.michelle.android.hiit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TimerActivity extends AppCompatActivity {

    public static final String EXTRA_STEPS = "steps";

    private Handler handler = new Handler();
    private Runnable runnable;
    private int time = 0;

    private boolean isPlay = false;

    private ImageView playPauseBtn;
    private TextView timeDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        playPauseBtn = findViewById(R.id.play_pause_button);
        timeDisplay = findViewById(R.id.time_display);
        setupPlayPauseBtn();
        renderTimer();

        Intent intent = getIntent();
        ArrayList<WorkoutStep> steps = (ArrayList<WorkoutStep>) intent.getSerializableExtra(EXTRA_STEPS);

    }

    private void setupPlayPauseBtn() {
        playPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("click");
                if (!isPlay) {
                    playPauseBtn.setImageResource(R.drawable.ic_pause_48dp);
                    startTimer();
                    isPlay = true;
                } else {
                    playPauseBtn.setImageResource(R.drawable.ic_play_48dp);
                    stopTimer();
                    isPlay = false;
                }
            }
        });
    }

    private void scheduleTimer() {
        renderTimer();
        runnable = new Runnable() {
            @Override
            public void run() {
                time += 1;
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
        // stop timer in case it wasn't stopped before. theoretically, this shouldn't happen
        stopTimer();
        scheduleTimer();
    }

    private void renderTimer() {
        timeDisplay.setText(TimerUtils.renderTimer(time));
    }
}
