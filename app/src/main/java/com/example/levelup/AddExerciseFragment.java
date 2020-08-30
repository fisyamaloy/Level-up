package com.example.levelup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Printer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddExerciseFragment extends Fragment {
    private TextView time;
    private int seconds;
    private int minutes;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_exercise, container, false);

        time = view.findViewById(R.id.textView);
        SeekBar seekBar = view.findViewById(R.id.SeekBarId);
        seekBar.setOnSeekBarChangeListener(changeListener);
        Button adding = view.findViewById(R.id.AddingId);
        adding.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(componentsAreNotEmpty()) {
                    AddWorkoutToWorkoutListAndCache();
                    Toast.makeText(getContext(), "Workout was added", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(), "Some fields are empty", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    private boolean componentsAreNotEmpty(){
        return  !getComponentText(R.id.editTextSets).isEmpty() &&
                !getComponentText(R.id.textInputEditText).isEmpty() &&
                !getComponentText(R.id.editTextKg).isEmpty();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void AddWorkoutToWorkoutListAndCache(){
        String name = getComponentText(R.id.textInputEditText);
        int setsAmount = Integer.parseInt(getComponentText(R.id.editTextSets));
        double kg = Double.parseDouble(getComponentText(R.id.editTextKg));
        Rest time = new Rest(minutes, seconds);

        int[] performedReps = getPerformedReps(setsAmount);

        Workout workout = new Workout(name, setsAmount, kg, time, performedReps, 0);
        WorkoutsList.add(workout);

        Cache cache = new Cache();
        cache.saveWorkout(workout);

        cache.workoutListInitialisation();
    }

    private String getComponentText(int id){
        EditText component = view.findViewById(id);
        return component.getText().toString().trim();
    }

    private int[] getPerformedReps(int size){
        return new int[size];
    }

    private SeekBar.OnSeekBarChangeListener changeListener = new SeekBar.OnSeekBarChangeListener(){
        @SuppressLint("SetTextI18n")
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
            seconds = (5 * progress) % 60;
            minutes = (5 * progress) / 60;

            if (seconds != 0 && seconds != 5)
                time.setText("" + minutes + ":" + seconds);
            else
                time.setText("" + minutes + ":0" + seconds);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}