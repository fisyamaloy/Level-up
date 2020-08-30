package com.example.levelup;

import java.util.ArrayList;
import java.util.List;

public class WorkoutsList {
    private static List<Workout> workouts = new ArrayList<>();

    public static Workout get(int pos){
        return workouts.get(pos);
    }

    public static void add(Workout workout){
        workouts.add(workout);
    }

    public static int getSize(){
        return workouts.size();
    }

    public static void clear(){workouts = new ArrayList<>();}

    public static void delete(int pos){
        workouts.remove(pos);
    }
}
