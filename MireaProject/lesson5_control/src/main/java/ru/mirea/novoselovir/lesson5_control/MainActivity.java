package ru.mirea.novoselovir.lesson5_control;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.mirea.novoselovir.lesson5_control.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private ActivityMainBinding binding;

    // КАМЕРА
    private boolean isWorkCamera = false;
    private Uri imageUri;

    // АКСЕЛЕРОМЕТР
    private SensorManager sensorManager;
    private Sensor accelerometer;

    // АУДИО
    private final String TAG = MainActivity.class.getSimpleName();
    private boolean isWorkAudio = false;
    private String fileName = null;
    private Button recordButton;
    private Button playButton;
    private MediaRecorder recorder;
    private MediaPlayer player;
    boolean isStartRecording = true;
    boolean isStartPlaying = true;

    private static final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ========= РАЗРЕШЕНИЯ =========
        requestPermissions();

        // ========= КАМЕРА =========
        ActivityResultCallback<ActivityResult> callback = result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                binding.imageView.setImageURI(imageUri);
            }
        };

        ActivityResultLauncher<Intent> cameraLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        callback
                );

        binding.imageView.setOnClickListener(v -> {
            if (!isWorkCamera) return;

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            try {
                File file = createImageFile();
                String authority = getPackageName() + ".fileprovider";
                imageUri = FileProvider.getUriForFile(this, authority, file);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                cameraLauncher.launch(intent);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // ========= АКСЕЛЕРОМЕТР =========
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // ========= АУДИО =========
        recordButton = binding.recordButton;
        playButton = binding.playButton;
        playButton.setEnabled(false);

        fileName = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC),
                "record.3gp").getAbsolutePath();

        recordButton.setOnClickListener(v -> {
            if (!isWorkAudio) return;

            if (isStartRecording) {
                recordButton.setText("Stop recording");
                playButton.setEnabled(false);
                startRecording();
            } else {
                recordButton.setText("Start recording");
                playButton.setEnabled(true);
                stopRecording();
            }
            isStartRecording = !isStartRecording;
        });

        playButton.setOnClickListener(v -> {
            if (isStartPlaying) {
                playButton.setText("Stop playing");
                recordButton.setEnabled(false);
                startPlaying();
            } else {
                playButton.setText("Start playing");
                recordButton.setEnabled(true);
                stopPlaying();
            }
            isStartPlaying = !isStartPlaying;
        });
    }

    // ========= РАЗРЕШЕНИЯ =========
    private void requestPermissions() {
        String[] permissions = {
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
        };

        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length == 0) return;

        for (int i = 0; i < permissions.length; i++) {
            if (permissions[i].equals(Manifest.permission.CAMERA)) {
                isWorkCamera = grantResults[i] == PackageManager.PERMISSION_GRANTED;
            }
            if (permissions[i].equals(Manifest.permission.RECORD_AUDIO)) {
                isWorkAudio = grantResults[i] == PackageManager.PERMISSION_GRANTED;
            }
        }
    }

    // ========= КАМЕРА =========
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        return File.createTempFile("IMG_" + timeStamp, ".jpg", dir);
    }

    // ========= АКСЕЛЕРОМЕТР =========
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        binding.textViewAzimuth.setText("Azimuth: " + x);
        binding.textViewPitch.setText("Pitch: " + y);
        binding.textViewRoll.setText("Roll: " + z);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    // ========= АУДИО =========
    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            Log.e(TAG, "Recording error");
        }
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    private void startPlaying() {
        player = new MediaPlayer();

        try {
            player.setDataSource(fileName);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e(TAG, "Playing error");
        }
    }

    private void stopPlaying() {
        player.release();
        player = null;
    }
}