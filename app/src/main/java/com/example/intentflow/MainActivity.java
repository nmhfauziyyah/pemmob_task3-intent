package com.example.intentflow;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText edtNama, edtNrp, edtPhone;
    private Spinner spJurusan, spSemester;
    private RadioGroup rgGender;
    private ImageView ivProfilePicker;
    private Uri selectedImageUri;

    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    ivProfilePicker.setImageURI(selectedImageUri);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        edtNama = findViewById(R.id.edt_nama);
        edtNrp = findViewById(R.id.edt_nrp);
        edtPhone = findViewById(R.id.edt_phone);
        spJurusan = findViewById(R.id.sp_jurusan);
        spSemester = findViewById(R.id.sp_semester);
        rgGender = findViewById(R.id.rg_gender);
        ivProfilePicker = findViewById(R.id.iv_profile_picker);
        Button btnLanjut = findViewById(R.id.btn_lanjut);

        // Setup Spinners
        ArrayAdapter<CharSequence> adapterJurusan = ArrayAdapter.createFromResource(this,
                R.array.jurusan_array, android.R.layout.simple_spinner_item);
        adapterJurusan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJurusan.setAdapter(adapterJurusan);

        ArrayAdapter<CharSequence> adapterSemester = ArrayAdapter.createFromResource(this,
                R.array.semester_array, android.R.layout.simple_spinner_item);
        adapterSemester.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSemester.setAdapter(adapterSemester);

        ivProfilePicker.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryLauncher.launch(intent);
        });

        btnLanjut.setOnClickListener(v -> validateAndSubmit());
    }

    private void validateAndSubmit() {
        String nama = edtNama.getText().toString().trim();
        String nrp = edtNrp.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        int genderId = rgGender.getCheckedRadioButtonId();

        if (selectedImageUri == null) {
            Toast.makeText(this, R.string.error_photo, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(nama)) {
            edtNama.setError(getString(R.string.error_required));
            return;
        }
        if (TextUtils.isEmpty(nrp)) {
            edtNrp.setError(getString(R.string.error_required));
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            edtPhone.setError(getString(R.string.error_required));
            return;
        }
        if (genderId == -1) {
            Toast.makeText(this, R.string.error_gender, Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton rbSelected = findViewById(genderId);
        String gender = rbSelected.getText().toString();
        String jurusan = spJurusan.getSelectedItem().toString();
        String semester = spSemester.getSelectedItem().toString();

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("EXTRA_NAMA", nama);
        intent.putExtra("EXTRA_NRP", nrp);
        intent.putExtra("EXTRA_PHONE", phone);
        intent.putExtra("EXTRA_JURUSAN", jurusan);
        intent.putExtra("EXTRA_SEMESTER", semester);
        intent.putExtra("EXTRA_GENDER", gender);
        intent.putExtra("EXTRA_IMG", selectedImageUri.toString());
        startActivity(intent);
    }
}
