package com.michelle.android.hiit;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TimerSetupActivity extends AppCompatActivity {

    // plus minus
    // edit the label
    private WorkoutStep warmUp = new WorkoutStep("Warm Up", 2);
    ArrayList<WorkoutStep> workoutSteps = new ArrayList<>();
    private WorkoutStep coolDown = new WorkoutStep("Cool Down", 5);
    Button startBtn;
    Button addStepBtn;

    private RecyclerView recyclerView;
    private StepsAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_setup);

        initializeRecyclerViewAndWorkoutSteps();
        initializeItemTouchHelper();
        initializeOnClickListeners();


    }

    private void initializeRecyclerViewAndWorkoutSteps() {
        recyclerView = findViewById(R.id.steps_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new StepsAdapter();

        final WorkoutStep work = new WorkoutStep("Work", 3);
        workoutSteps.add(work);
        WorkoutStep rest = new WorkoutStep("Rest", 5);
        workoutSteps.add(rest);
        WorkoutStep rounds = new WorkoutStep("Rounds", 8);
        workoutSteps.add(rounds);

        Intent intent = getIntent();
        PresetStep presetStep = (PresetStep) intent.getSerializableExtra("PRESET_STEP");

        if (presetStep == null || !presetStep.workoutList.isEmpty()) {
            mAdapter.setDataset(presetStep.workoutList);
        } else {
            mAdapter.setDataset(workoutSteps);
        }

        recyclerView.setAdapter(mAdapter);

        setWorkoutStep(warmUp, findViewById(R.id.warm_up_container));
        setWorkoutStep(coolDown, findViewById(R.id.cool_down_container));
    }

    private void initializeItemTouchHelper() {
        ItemTouchHelper.Callback itemTouchHelperCallback = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(0, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                workoutSteps.remove(viewHolder.getAdapterPosition());
                mAdapter.setDataset(workoutSteps);
            }
        };

        ItemTouchHelper touchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    private void initializeOnClickListeners() {
        addStepBtn = findViewById(R.id.add_step_btn);
        addStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final WorkoutStep plank = new WorkoutStep("Plank", 5);
                workoutSteps.add(plank);
                mAdapter.setDataset(workoutSteps);
            }
        });

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

    private void setWorkoutStep(final WorkoutStep workoutStep, final View parent) {

        TextView label = parent.findViewById(R.id.timer_item_label);
        label.setText(workoutStep.label);

        label.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                workoutStep.label = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final TextView time = parent.findViewById(R.id.timer_item_time);
        time.setText(TimerUtils.formatTime(workoutStep.time));

        parent.findViewById(R.id.timer_item_plus_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workoutStep.time += 1;
                time.setText(TimerUtils.formatTime(workoutStep.time));
            }
        });

        parent.findViewById(R.id.timer_item_minus_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (workoutStep.time != 0) {

                    workoutStep.time -= 1;
                }
                time.setText(TimerUtils.formatTime(workoutStep.time));
            }
        });
    }

    public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.MyViewHolder> {
        private ArrayList<WorkoutStep> mDataset;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView label;
            public TextView timerItemTime;

            public ImageView plusBtn;
            public ImageView minusBtn;
            public MyViewHolder(ConstraintLayout v) {
                super(v);
                label = v.findViewById(R.id.timer_item_label);
                timerItemTime = v.findViewById(R.id.timer_item_time);
                plusBtn = v.findViewById(R.id.timer_item_plus_btn);
                minusBtn = v.findViewById(R.id.timer_item_minus_btn);
            }
        }

        public void setDataset(ArrayList<WorkoutStep> dataset) {
            this.mDataset = dataset;
            notifyDataSetChanged();
        }

        // Create new views (invoked by the layout manager)
        @Override
        public StepsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
            // create a new view
            ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.timer_item, parent, false);
            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            final WorkoutStep workoutStep = mDataset.get(position);
            holder.label.setText(workoutStep.label);
            holder.label.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    workoutStep.label = s.toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            holder.timerItemTime.setText(TimerUtils.formatTime(workoutStep.time));

            holder.plusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    workoutStep.time += 1;
                    holder.timerItemTime.setText(TimerUtils.formatTime(workoutStep.time));
                }
            });

            holder.minusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (workoutStep.time != 0) {
                        workoutStep.time -= 1;
                    }
                    holder.timerItemTime.setText(TimerUtils.formatTime(workoutStep.time));
                }
            });

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }

}
