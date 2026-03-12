package ru.mirea.novoselovir.simplefragmentapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {
    private Fragment fragment1, fragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main); // для ландшафта
        } else {
            setContentView(R.layout.activity_main); // для портрета
        }

        fragment1 = new FirstFragment();
        fragment2 = new SecondFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();

        Button btnFirstFragment = findViewById(R.id.btnFirstFragment);
        Button btnSecondFragment = findViewById(R.id.btnSecondFragment);

        if (btnFirstFragment != null) {
            btnFirstFragment.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (findViewById(R.id.fragmentContainerView) != null) {
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainerView, fragment1)
                                .commit();
                    }
                }
            });
        }

        if (btnSecondFragment != null) {
            btnSecondFragment.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (findViewById(R.id.fragmentContainerView) != null) {
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainerView, fragment2)
                                .commit();
                    }
                }
            });
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main);
        } else {
            setContentView(R.layout.activity_main);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();

        Button btnFirstFragment = findViewById(R.id.btnFirstFragment);
        Button btnSecondFragment = findViewById(R.id.btnSecondFragment);

        if (btnFirstFragment != null) {
            btnFirstFragment.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (findViewById(R.id.fragmentContainerView) != null) {
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainerView, fragment1)
                                .commit();
                    }
                }
            });
        }

        if (btnSecondFragment != null) {
            btnSecondFragment.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (findViewById(R.id.fragmentContainerView) != null) {
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainerView, fragment2)
                                .commit();
                    }
                }
            });
        }
    }
}