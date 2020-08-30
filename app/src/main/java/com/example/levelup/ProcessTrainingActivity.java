package com.example.levelup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ProcessTrainingActivity extends AppCompatActivity {
    private int positionOfSelectedName;
    private int exerciseApproach = 0;
    private int approachesAmount;
    private int[] performedReps;
    private Rest rest;
    private int minutes;
    private int seconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_training);

        setColorOnImage(R.id.imageAlarmRest);
        fieldsInitialisation();

        setText(R.id.queueSet, (exerciseApproach + 1) + "/" + approachesAmount);
        setText(R.id.reps, "" + performedReps[exerciseApproach]);
        setTextToTimer();
    }

    private void setColorOnImage(int id){
        ImageView lineColorCode = (ImageView)findViewById(id);
        int color = Color.parseColor("#AAA8A8");
        lineColorCode.setColorFilter(color);
    }

    private void fieldsInitialisation() {
        Bundle arguments = getIntent().getExtras();
        positionOfSelectedName = Integer.parseInt(arguments.get("index").toString().trim());

        Workout workout = WorkoutsList.get(positionOfSelectedName);
        approachesAmount = workout.getSetsAmount();
        performedReps = workout.getPerformedReps();

        rest = workout.getTime();
        minutes = rest.minutes;
        seconds = rest.seconds;
    }

    private void setText(int id, String row) {
        TextView text = findViewById(id);
        text.setText(row);
    }

    private void setTextToTimer() {
        if (seconds >= 10)
            setText(R.id.alarmRestId, minutes + ":" + seconds);
        else
            setText(R.id.alarmRestId, minutes + ":0" + seconds);
    }

    public void buttonPlusClicked(View v) {
        setText(R.id.reps, ++performedReps[exerciseApproach] + "");
    }

    public void buttonMinusClicked(View v) {
        if (performedReps[exerciseApproach] > 0)
            setText(R.id.reps, --performedReps[exerciseApproach] + "");
    }

    public void finishApproach(View v) {
        minutes = rest.minutes;
        seconds = rest.seconds;
        exerciseApproach++;

        if (exerciseApproach < approachesAmount - 1) {
            setClickActions();
        } else if (approachIsPenultimate()) {
            Button finishBut = findViewById(R.id.finishSetId);
            finishBut.setText("Завершить тренировку");
            setClickActions();
        } else {
            WorkoutsList.get(positionOfSelectedName).updateLevelAndPerformedReps();
            Cache cache = new Cache();
            cache.updateWorkouts();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void setClickActions() {
        setText(R.id.queueSet, (exerciseApproach + 1) + "/" + approachesAmount);
        setText(R.id.reps, performedReps[exerciseApproach] + "");
        setButtonEnable(false);
        runTimer();
    }

    private boolean approachIsPenultimate() {
        return exerciseApproach == approachesAmount - 1;
    }

    private void setButtonEnable(boolean turn) {
        Button button = findViewById(R.id.finishSetId);
        button.setEnabled(turn);
    }

    private void runTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (seconds > 0 || minutes > 0) {
                    if (seconds > 0) {
                        seconds--;
                    } else {
                        minutes--;
                        seconds = 59;
                    }
                    setTextToTimer();
                    handler.postDelayed(this, 1000);
                } else {
                    setButtonEnable(true);
                }
            }
        });
    }
}