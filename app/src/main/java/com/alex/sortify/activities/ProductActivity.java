package com.alex.sortify.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alex.sortify.R;

public class ProductActivity extends AppCompatActivity {

    private TextView msgRecyclingTextView;
    private TextView titleTextView;
    private TextView recyclerAddressTextView;
    private TextView phoneTextView;
    private ImageView productImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product);  // Ensure your XML is saved as activity_product.xml

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        msgRecyclingTextView = findViewById(R.id.msgRecycling);
        titleTextView = findViewById(R.id.title);
        recyclerAddressTextView = findViewById(R.id.recyclerAddress);
        phoneTextView = findViewById(R.id.phone);
        productImageView = findViewById(R.id.image);

        // Set values from Java code
        msgRecyclingTextView.setText("Produkt jest recyclingowany!!");
        titleTextView.setText("Butelka bez wody");

        recyclerAddressTextView.setText("Warszawa, ul. Zielona 12\nKraków, ul. Recyklingowa 8");
        phoneTextView.setText("+48 788 788 788");

        productImageView.setImageResource(R.drawable.icon_bio); // Ensure drawable exists
    }
}
