package com.example.levelup;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class WorkoutFragment extends Fragment {

    private View view;
    private ListView namesListView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_workout, container, false);

        WorkoutsList.clear();
        Cache cache = new Cache();
        cache.workoutListInitialisation();

        List<String> names = getExercisesNamesList();
        fullListView(names);

        namesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), ExerciseInfoActivity.class);
                intent.putExtra("index", i);
                startActivity(intent);
            }
        });

        return view;
    }

    private List<String> getExercisesNamesList(){
        List<String> names = new ArrayList<>();
        for(int i = 0; i < WorkoutsList.getSize(); i++){
            Workout workout = WorkoutsList.get(i);
            names.add(workout.getExerciseName());
        }
        return names;
    }

    private void fullListView(List<String> names){
        namesListView = view.findViewById(R.id.namesId);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), R.layout.exrecisesnames, names);
        namesListView.setAdapter(adapter);
    }
}