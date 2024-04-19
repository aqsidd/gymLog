package com.example.gymlog;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymlog.database.GymLogRepository;
import com.example.gymlog.database.entities.GymLog;
import com.example.gymlog.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private GymLogRepository repository;
    String mExercise = "";

    double mWeight = 0.0;

    int mReps = 0;

    int loggedInUserId = -1;

    public static final String TAG = "DAC_GYMLOG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = GymLogRepository.getRepository(getApplication());

        binding.logDisplayTextView.setMovementMethod(new ScrollingMovementMethod());
        updateDisplay();

        binding.logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "IT WORKED", Toast.LENGTH_SHORT).show();
                getInformationFromDisplay();
                insertGymLogRecord();
                updateDisplay();
            }
        });

    }

    private void insertGymLogRecord(){
        if(mExercise.isEmpty()){
            return;
        }

        GymLog log = new GymLog(mExercise, mWeight, mReps, loggedInUserId);
        repository.insertGymLog(log);

    }


    private void updateDisplay(){
        ArrayList<GymLog> allLogs = repository.getAllLogs();
        if(allLogs.isEmpty()){
            binding.logDisplayTextView.setText(R.string.nothing_to_show_time_to_hit_the_gym);
        }
        StringBuilder sb = new StringBuilder();
        for (GymLog log: allLogs){
            sb.append(log);
        }
        binding.logDisplayTextView.setText(sb.toString());


//        String currentInfo = binding.logDisplayTextView.getText().toString();
//        Log.d(TAG, "current info: "+currentInfo);
//        String newDisplay = String.format(Locale.US, "Exercise:%s%nWeight:%.2f%nReps:%d%n=====%n%s",mExercise,mWeight,mReps,currentInfo);
//        binding.logDisplayTextView.setText(newDisplay);
//        Log.i(TAG, repository.getAllLogs().toString());

    }

    private void getInformationFromDisplay(){

        mExercise = binding.exerciseInputEditText.getText().toString();

        try{
            mWeight = Double.parseDouble(binding.weightInputEditText.getText().toString());
        } catch (NumberFormatException e){
            Log.d(TAG, "ERROR1");
        }


        try{
            mReps = Integer.parseInt(binding.repsInputEditText.getText().toString());
        } catch (NumberFormatException e){
            Log.d(TAG, "ERROR2");
        }


    }











}