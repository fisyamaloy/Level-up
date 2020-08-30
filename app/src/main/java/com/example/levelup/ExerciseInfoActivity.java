package com.example.levelup;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ExerciseInfoActivity extends AppCompatActivity {

    int positionOfSelectedName;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_exercise);

        setColorOnImage(R.id.imageAlarm);
        setColorOnImage(R.id.imageLevel);

        Bundle arguments = getIntent().getExtras();
        assert arguments != null;
        positionOfSelectedName = Integer.parseInt(arguments.get("index").toString().trim());

        Workout workout = WorkoutsList.get(positionOfSelectedName);
        setFieldsTexts(workout);
    }

    private void setColorOnImage(int id){
        ImageView lineColorCode = findViewById(id);
        int color = Color.parseColor("#AAA8A8");
        lineColorCode.setColorFilter(color);
    }

    private void setFieldsTexts(Workout workout){
        setText(R.id.sets, "Количество подходов: " + workout.getSetsAmount());
        setText(R.id.kg, "Отягощение: " + workout.getCargoKg() + " кг");

        String reps = getLastPerformedRepsByText(workout.getPerformedReps());
        setText(R.id.lastResults, "Последние результаты: " + reps);

        Rest rest = workout.getTime();
        if(rest.seconds != 0 && rest.seconds != 5)
            setText(R.id.alarmText,rest.minutes + ":" + rest.seconds);
        else
            setText(R.id.alarmText,rest.minutes + ":0" + rest.seconds);

        int level = (int)workout.getExerciseLevel();
        setText(R.id.levelText, "" + level);
    }

    private void setText(int id, String row){
        TextView textView = findViewById(id);
        textView.setText(row);
    }

    private String getLastPerformedRepsByText(int[] lastPerformedReps){
        String lastResults = "";
        for (int rep : lastPerformedReps)
            lastResults += rep + " ";
        return lastResults;
    }

    public void startButtonClicked(View v){
        Intent intent = new Intent(this, ProcessTrainingActivity.class);
        intent.putExtra("index", positionOfSelectedName);
        startActivity(intent);
    }

    public void clickOnButtonImage(View v){
        deleteExercise();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        displayMessage();
    }

    public void deleteExercise(){
        WorkoutsList.delete(positionOfSelectedName);
        Cache cache = new Cache();
        cache.updateWorkouts();
    }

    private void displayMessage(){
        Toast.makeText(
                getApplicationContext(), "Exercise was deleted",
                Toast.LENGTH_SHORT
        ).show();
    }
}