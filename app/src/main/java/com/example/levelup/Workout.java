package com.example.levelup;

public class Workout {
    private String exerciseName;
    private int setsAmount;
    private double cargoKg;
    private Rest time;
    private int[] performedReps;
    private double exerciseLevel;
    private int[] oldPerformedReps;

    public Workout(String _exerciseName, int _setsAmount, double _cargoKg, Rest _time, int[] _performedReps, double _exerciseLevel){
        exerciseName = _exerciseName;
        setsAmount = _setsAmount;
        cargoKg = _cargoKg;
        time = _time;
        performedReps = _performedReps;
        exerciseLevel = _exerciseLevel;
        oldPerformedReps = _performedReps.clone();
    }

    public String getExerciseName(){
        return exerciseName;
    }

    public int getSetsAmount(){
        return setsAmount;
    }

    public double getCargoKg(){
        return cargoKg;
    }

    public Rest getTime(){
        return time;
    }

    public int[] getPerformedReps(){
        return performedReps;
    }

    public double getExerciseLevel(){
        return exerciseLevel;
    }

    public void updateLevelAndPerformedReps(){
        assignLevel();
    }

    private void assignLevel(){
        for(int i = 0; i < oldPerformedReps.length; i++){
            int addedReps = performedReps[i] - oldPerformedReps[i];
            if(addedReps >= 1){
                for(int c = 0; c < addedReps; c++)
                    exerciseLevel += 0.3;
            }else if(addedReps == 0)
                exerciseLevel += 0.1;
        }
    }
}
