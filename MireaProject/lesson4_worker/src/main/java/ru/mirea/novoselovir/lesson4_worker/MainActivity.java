package ru.mirea.novoselovir.lesson4_worker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WorkRequest workRequest = new OneTimeWorkRequest.Builder(BackgroundWorker.class).build();
        WorkManager.getInstance(this).enqueue(workRequest);
    }
}

