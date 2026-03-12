package ru.mirea.novoselovir.thread;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ru.mirea.novoselovir.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ThreadProj";
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calc();
            }
        });
    }

    private void calc(){
        String pairs = binding.enterPairs.getText().toString();
        String days = binding.enterDays.getText().toString();

        final int totalPairs = Integer.parseInt(pairs);
        final int studyDays = Integer.parseInt(days);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, String.format("Поток запущен"));
                Thread.currentThread().setPriority(Thread.MIN_PRIORITY + 2);
                Log.d(TAG, "Приоритет потока: " + Thread.currentThread().getPriority());
                final double averagePairs = (double) totalPairs / studyDays;
                Log.d(TAG, "Выполнен поток - результат: " + averagePairs);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String result = String.format("Среднее количество пар в день: %.2f", averagePairs);
                        binding.Result.setText(result);
                    }
                });
            }
        }).start();
    }


}


