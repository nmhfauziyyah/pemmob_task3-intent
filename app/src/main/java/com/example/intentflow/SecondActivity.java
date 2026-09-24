package com.example.intentflow;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);

        TextView tvNama = findViewById(R.id.tv_res_nama);
        TextView tvNrp = findViewById(R.id.tv_res_nrp);
        TextView tvJurusan = findViewById(R.id.tv_res_jurusan);
        TextView tvSemester = findViewById(R.id.tv_res_semester);
        TextView tvPhone = findViewById(R.id.tv_res_phone);
        TextView tvGender = findViewById(R.id.tv_res_gender);
        ImageView ivProfile = findViewById(R.id.iv_profile_result);
        Button btnEdit = findViewById(R.id.btn_edit);
        androidx.cardview.widget.CardView btnCall = findViewById(R.id.btn_action_phone);
        Button btnWeb = findViewById(R.id.btn_web_its);

        Intent intent = getIntent();
        if (intent != null) {
            tvNama.setText(intent.getStringExtra("EXTRA_NAMA"));
            tvNrp.setText(intent.getStringExtra("EXTRA_NRP"));
            tvJurusan.setText(intent.getStringExtra("EXTRA_JURUSAN"));
            tvSemester.setText(intent.getStringExtra("EXTRA_SEMESTER"));
            tvPhone.setText(intent.getStringExtra("EXTRA_PHONE"));
            tvGender.setText(intent.getStringExtra("EXTRA_GENDER"));

            String imgUriStr = intent.getStringExtra("EXTRA_IMG");
            if (imgUriStr != null) {
                ivProfile.setImageURI(Uri.parse(imgUriStr));
            }
        }

        btnCall.setOnClickListener(v -> {
            String phone = tvPhone.getText().toString().trim();
            // Menyesuaikan format nomor (menghapus karakter non-digit)
            phone = phone.replaceAll("[^\\d]", "");
            
            // Mengubah 08... menjadi 628... agar sesuai standar wa.me
            if (phone.startsWith("0")) {
                phone = "62" + phone.substring(1);
            }
            
            String url = "https://wa.me/" + phone;
            Intent waIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(waIntent);
        });

        btnWeb.setOnClickListener(v -> {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.its.ac.id"));
            startActivity(webIntent);
        });

        btnEdit.setOnClickListener(v -> finish());
    }
}
