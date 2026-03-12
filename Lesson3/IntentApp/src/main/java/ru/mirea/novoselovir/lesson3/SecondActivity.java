package ru.mirea.novoselovir.lesson3;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.second), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView textView = findViewById(R.id.textView);

        String time = getIntent().getStringExtra("time");
        int square = getIntent().getIntExtra("square", 0);

        String result = "КВАДРАТ ЗНАЧЕНИЯ\n" +
                "МОЕГО шифра ПО СПИСКУ В ГРУППЕ СОСТАВЛЯЕТ "+square +
                ", а текущее время "+time;

        textView.setText(result);
    }
}