package com.example.levelup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import androidx.annotation.RequiresApi;
import java.util.ArrayList;
import java.util.List;

public class Cache extends Activity {
    private String key = "workoutz";
    private SharedPreferences sPref;
    @SuppressLint("StaticFieldLeak")
    private static Activity activity;

    public static void setActivityForPreferences(Activity _activity) {
        activity = _activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void workoutListInitialisation(){
        List<String> dataFile = getListWhereEveryElemIsNewRow();
        try {
            for(int i = 0; i < dataFile.size(); i += 6){
                String name = dataFile.get(i);

                int sets = Integer.parseInt(dataFile.get(i + 1).trim());

                double cargoKg = Double.parseDouble(dataFile.get(i + 2));
                Rest rest = getRestFromCacheData(dataFile.get(i + 3).trim());
                int[] performedReps = getPerformedRepsFromCacheData(dataFile.get(i + 4).trim());

                double exerciseLevel = Double.parseDouble(dataFile.get(i + 5));
                WorkoutsList.add(new Workout(name, sets, cargoKg, rest, performedReps, exerciseLevel));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private List<String> getListWhereEveryElemIsNewRow(){
        sPref = activity.getPreferences(MODE_PRIVATE);
        String data = sPref.getString(key, "");

        StringBuilder line = new StringBuilder();
        List<String> dataFile = new ArrayList<>();
        assert data != null;
        for(int i = 0; i < data.length(); i++){
            if(data.charAt(i) != '\n')
                line.append(data.charAt(i));
            else{
                dataFile.add(line.toString().trim());
                line = new StringBuilder();
            }
        }
        return dataFile;
    }

    private Rest getRestFromCacheData(String row){
        String[] minutesAndSeconds = row.split("\\s+");
        int minutes = Integer.parseInt(minutesAndSeconds[0]);
        int seconds = Integer.parseInt(minutesAndSeconds[1]);

        return new Rest(minutes, seconds);
    }

    private int[] getPerformedRepsFromCacheData(String row){
        String[] perfReps = row.split("\\s+");
        int[] performedReps = new int[perfReps.length];
        for(int i = 0; i < performedReps.length; i++)
            performedReps[i] = Integer.parseInt(perfReps[i]);

        return performedReps;
    }

    public void saveWorkout(Workout workout){
        sPref = activity.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        String data = getOldWorkoutsAndNew(workout);
        editor.putString(key, data);
        editor.apply();
    }

    private String getOldWorkoutsAndNew(Workout workout){
        String data = sPref.getString(key, "");
        if(data == null)
            data = "";

        data += getData(workout);
        return data;
    }

    private String getData(Workout workout){
        StringBuilder workoutDataInString = new StringBuilder();

        workoutDataInString.append(workout.getExerciseName()).append("\n");
        workoutDataInString.append(workout.getSetsAmount()).append("\n");
        workoutDataInString.append(workout.getCargoKg()).append("\n");
        Rest rest = workout.getTime();
        workoutDataInString.append(rest.minutes).append(" ").append(rest.seconds).append("\n");
        int[] performedReps = workout.getPerformedReps();
        for(int reps : performedReps)
            workoutDataInString.append(reps).append(" ");
        workoutDataInString.append("\n");
        workoutDataInString.append(workout.getExerciseLevel()).append("\n");

        return workoutDataInString.toString();
    }

    public void updateWorkouts(){
        StringBuilder data = new StringBuilder();
        for(int i = 0; i < WorkoutsList.getSize(); i++)
            data.append(getData(WorkoutsList.get(i)));

        sPref = activity.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString(key, data.toString());
        editor.apply();
    }
}
