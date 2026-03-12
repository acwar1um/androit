package ru.mirea.novoselovir.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onClickShowDialog(View view){
        AlertDialogFragment dialogFragment = new AlertDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "mirea");
    }

    public void onClickShowTime(View view) {
        new MyTimeDialogFragment().show(getSupportFragmentManager(), "time");
    }
    public void onClickShowDate(View view) {
        new MyDateDialogFragment().show(getSupportFragmentManager(), "date");
    }
    public void onClickShowProgress(View view) {
        new MyProgressDialogFragment().show(getSupportFragmentManager(), "date");
    }
    public void onTimeClicked(int hour1, int minute){
        String time = hour1 + ":" + minute;
        Toast.makeText(getApplicationContext(), "Ваше время: " + time,
                Toast.LENGTH_LONG).show();

    }

    public void onDateClicked(int year, int month, int day){
        String date = day+"."+(month+1)+"."+year;
        Toast.makeText(getApplicationContext(), "Ваша дата: " + date,
                Toast.LENGTH_LONG).show();

    }

    public void onOkClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"Иду дальше\"!",
                Toast.LENGTH_LONG).show();
    }
    public void onCancelClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"Нет\"!",
                Toast.LENGTH_LONG).show();
    }
    public void onNeutralClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"На паузе\"!",
                Toast.LENGTH_LONG).show();
    }

}