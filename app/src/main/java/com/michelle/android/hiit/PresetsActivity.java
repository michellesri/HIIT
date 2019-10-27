package com.michelle.android.hiit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PresetsActivity extends Activity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<PresetStep> presetSteps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presets);
        recyclerView = findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        initializePresetSteps();
        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(presetSteps);
        recyclerView.setAdapter(mAdapter);
    }

    public void initializePresetSteps() {
        ArrayList<WorkoutStep> lowerBodySteps = new ArrayList<>();
        WorkoutStep work = new WorkoutStep("work", 5);
        lowerBodySteps.add(work);
        lowerBodySteps.add(work);
        PresetStep lowerBodyHiit = new PresetStep("Lower body HIIT", "Work x 2", lowerBodySteps);

        ArrayList<WorkoutStep> morningCardioSteps = new ArrayList<>();
        WorkoutStep rest = new WorkoutStep("rest", 3);
        morningCardioSteps.add(rest);
        morningCardioSteps.add(rest);
        PresetStep morningCardio = new PresetStep("Morning Cardio", "Rest x 2", morningCardioSteps);
        presetSteps.add(lowerBodyHiit);
        presetSteps.add(morningCardio);

        PresetStep customizablePreset = new PresetStep("Customize", "Create your own workout", new ArrayList<WorkoutStep>());
        presetSteps.add(customizablePreset);
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private ArrayList<PresetStep> mDataset;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public CardView cardView;
            public TextView presetTitle;
            public TextView presetDescription;
            public MyViewHolder(CardView v) {
                super(v);
                cardView = v;
                presetTitle = v.findViewById(R.id.preset_title);
                presetDescription = v.findViewById(R.id.preset_description);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(ArrayList<PresetStep> myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
            // create a new view
            CardView v = (CardView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.presets_item, parent, false);
            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            final PresetStep currentPreset = presetSteps.get(position);
            holder.presetTitle.setText(currentPreset.title);
            holder.presetDescription.setText(currentPreset.description);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PresetsActivity.this, TimerSetupActivity.class);
                    intent.putExtra("PRESET_STEP", currentPreset);
                    startActivity(intent);
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
