package com.alex.sortify.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alex.sortify.R;

public class ProductActivity extends AppCompatActivity {
    private final int REQUEST_CODE = 1001;

    private TextView msgRecycling;
    private TextView productTitle;
    private TextView recyclerAddress;
    private TextView contactPhone;
    private ImageView productImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product); // Ensure this matches your XML filename

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            // Show explanation to the user
            new AlertDialog.Builder(this)
                    .setTitle("Wymagany dostęp do aparatu")
                    .setMessage("Aby wyświetlić podgląd na żywo z kamery, aplikacja potrzebuje dostępu do aparatu. Proszę udziel zgody.")
                    .setPositiveButton("Zezwól", (dialog, which) -> requestCameraPermission())
                    .setNegativeButton("Odmów", (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();
        } else {
            requestCameraPermission();
        }



        // Apply system insets to root layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) -> {
            var systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();
        populateData();
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA}, REQUEST_CODE);
    }
    
    private void initializeViews() {
        msgRecycling = findViewById(R.id.msgRecycling);
        productTitle = findViewById(R.id.title);
        recyclerAddress = findViewById(R.id.recyclerAddress);
        contactPhone = findViewById(R.id.phone);
        productImage = findViewById(R.id.image); // Ensure ImageView in XML has android:id="@+id/image"
    }

    private void populateData() {
        msgRecycling.setText("Produkt jest recyclingowany!!");
        productTitle.setText("Butelka bez wody");
        recyclerAddress.setText("Warszawa, ul. Zielona 12\nKraków, ul. Recyklingowa 8");
        contactPhone.setText("+48 788 788 788");
        productImage.setImageResource(R.drawable.icon_bio); // Confirm drawable exists
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        }
    }
}
