package com.alex.sortify.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.alex.sortify.R;

public class ResultDialog extends AppCompatDialogFragment {

    private ResultDialogListener listener;

    public interface ResultDialogListener {
        void onOkClicked();
    }

    public static ResultDialog newInstance(String message, ResultDialogListener listener) {
        ResultDialog dialog = new ResultDialog();
        dialog.listener = listener;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.product_details_dialog, null);

        Button closeBtn = view.findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(v -> {
            if (listener != null) listener.onOkClicked();
            dismiss();
        });

        builder.setView(view);
        return builder.create();
    }
}
