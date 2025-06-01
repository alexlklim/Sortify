package com.alex.sortify.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.alex.sortify.R;
import com.alex.sortify.http.APIClient;
import com.alex.sortify.http.ProductDTO;
import com.alex.sortify.http.RetrofitClient;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ResultDialog extends AppCompatDialogFragment {
    private ResultDialogListener mListener;

    private String text;
    private Boolean isSuccess;
    TextView drText;
    ImageButton drImage;
    Button drBtnOk;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.product_details_dialog, null);
        builder.setView(view);
//
//        drText = view.findViewById(R.id.drText);
//        drImage = view.findViewById(R.id.drImage);
//        drBtnOk = view.findViewById(R.id.drBtnOk);
//
//        drText.setText(text);
//        if (isSuccess) {
//            drImage.setImageResource(R.drawable.ic_done);
//            drImage.setColorFilter(ContextCompat.getColor(requireContext(), R.color.result_success));
//        } else {
//            drImage.setImageResource(R.drawable.ic_error);
//            drImage.setColorFilter(ContextCompat.getColor(requireContext(), R.color.result_fail));
//        }
//
//        drBtnOk.setOnClickListener(v -> {
//            if (mListener != null) {
//                mListener.onOkClicked();
//            }
//            dismiss();
//        });

        return builder.create();
    }


    public static ResultDialog newInstance(String text, ResultDialogListener listener) {
        ResultDialog dialog = new ResultDialog();
        dialog.text = text;
        dialog.mListener = listener;
        return dialog;
    }


    public interface ResultDialogListener {
        void onOkClicked();
    }

    private void getDataAboutProduct() {
        APIClient apiClient = RetrofitClient.getRetrofitInstance().create(APIClient.class);



        Map<String, Object> updates = new HashMap<>();
        updates.put("id", productDTO.getId());
        updates.put("branch_id", roomDB.branchDAO().getBranchByName(branchSpinner.getSelectedItem().toString()).getId());
        updates.put("location_id", roomDB.locationDAO().getLocationByName(locationSpinner.getSelectedItem().toString()).getId());
        updates.put("liable_id", roomDB.employeeDAO().getEmployeeIdByFullName(liableSpinner.getSelectedItem().toString()));
        updates.put("receiver", receiverEditText.getText().toString());



        Call<Void> call = apiClient.putUpdatedProduct("Bearer " + token, updates);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "sendProductUpdateRequest is successful:");
                    requireActivity().runOnUiThread(() -> {
                        ResultDialog dialog = ResultDialog.newInstance(
                                "Product \n" + productDTO.getTitle() + " \nzostał przesunięty",
                                true,
                                MoveProductDialog.this);
                        dialog.show(getChildFragmentManager(), "MoveProductDialog");
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e(TAG, "sendProductUpdateRequest onFailure", t);
            }
        });
    }


    private void sendGetFullProductRequest(String code) {
        APIClient apiClient = RetrofitClient.getRetrofitInstance().create(APIClient.class);
        Call<ProductDTO> call;
        call = apiClient.getProduct(code);


        call.enqueue(new Callback<ProductDTO>() {
            @Override
            public void onResponse(@NonNull Call<ProductDTO> call, @NonNull Response<ProductDTO> response) {
                if (response.isSuccessful()) {
                    if (action == 1) openMoveProductDialog(response.body());
                    else if (action == 2) openScrapProductDialog(response.body());

                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductDTO> call, @NonNull Throwable t) {
                Log.e(TAG, "sendGetFullProductRequest onFailure", t);
            }
        });
    }
}