package ru.mirea.novoselovir.lesson4;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ru.mirea.novoselovir.lesson4.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private	ActivityMainBinding	binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        binding.buttonMirea.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                binding.editViewMirea.setText("moy shifr nomer_");
                Log.d(MainActivity.class.getSimpleName(), "onClickListener");
            }
        });
    }

}
