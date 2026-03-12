package ru.mirea.novoselovir.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class MyProgressDialogFragment extends DialogFragment {

    private ProgressDialog progressDialog;
    private Handler handler = new Handler();
    private int progress = 0;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progress += 1;
                progressDialog.setProgress(progress);

                if (progress < 100) {
                    handler.postDelayed(this, 100);
                }
            }
        }, 100);

        return progressDialog;
    }
}