package com.example.levelup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity{

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.NavigationId);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationView.setSelectedItemId(R.id.WorkoutId);

        Cache.setActivityForPreferences(this);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = getSelectedFragment(item);
                    getSupportFragmentManager().beginTransaction().replace(R.id.DefinedFragmentId,
                            selectedFragment).commit();
                    return true;
                }
            };

    private Fragment getSelectedFragment(@NonNull MenuItem item){
        Fragment selectedFragment = null;
        switch (item.getItemId()){
            case R.id.AlarmId:
                selectedFragment = new AlarmFragment();
                break;
            case R.id.WorkoutId:
                selectedFragment = new WorkoutFragment();
                break;
            case R.id.AddExerciseId:
                selectedFragment = new AddExerciseFragment();
                break;
        }
        return selectedFragment;
    }
}