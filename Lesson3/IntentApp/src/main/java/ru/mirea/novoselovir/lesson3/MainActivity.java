package ru.mirea.novoselovir.lesson3;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Date;

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

    public void onSend(View view){
        long dateInMillis = System.currentTimeMillis();
        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String dateString = sdf.format(new Date(dateInMillis));

        EditText yourNumber = findViewById(R.id.editTextNumber);

        int number = Integer.parseInt(yourNumber.getText().toString());
        int square = number*number;


        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("square", square);
        intent.putExtra("time", dateString);
        startActivity(intent);
    }


}