package com.example.kalkulator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText angka1 = findViewById(R.id.input1);
        EditText angka2 = findViewById(R.id.input2);
        Button btn_hitung = findViewById(R.id.buttonhitung);
        RadioGroup operator = findViewById(R.id.grupradio);

        btn_hitung.setOnClickListener(v -> {
            Integer nilai_angka1 = tryParseInt(angka1.getText().toString());
            Integer nilai_angka2 = tryParseInt(angka2.getText().toString());

            if (nilai_angka1 != null && nilai_angka2 != null) {
                String hasil = doHitungHasil(nilai_angka1, nilai_angka2, operator);

                // Mulai Activity baru dan kirim hasil perhitungan
                Intent intent = new Intent(this, Hasil.class);
                intent.putExtra("EXTRA_HASIL", hasil);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Input tidak valid", Toast.LENGTH_SHORT).show();
                }
            });
        }

    private String doHitungHasil(int angka1, int angka2, RadioGroup operator) {
        int hitungHasil = 0;
        String pilihOperator = null;

        // Dapatkan RadioButton yang dipilih dari RadioGroup
        int selectedId = operator.getCheckedRadioButtonId();

        // Jika tidak ada tombol yang dipilih, tampilkan Toast
        if (selectedId == -1) {
            Toast.makeText(this, "Silahkan pilih operasi terlebih dahulu", Toast.LENGTH_SHORT).show();
            return null;
        }

        // Dapatkan RadioButton berdasarkan ID yang dipilih
        RadioButton selectedRadioButton = findViewById(selectedId);
        String operatorText = selectedRadioButton.getText().toString();

        // Tentukan operasi berdasarkan teks dari RadioButton
        switch (operatorText.trim()) {
            case "+":
                hitungHasil = angka1 + angka2;
                break;
            case "-":
                hitungHasil = angka1 - angka2;
                break;
            case "x":
                hitungHasil = angka1 * angka2;
                break;
            case ":":
                if (angka2 != 0) {
                    hitungHasil = angka1 / angka2;
                } else {
                    Toast.makeText(this, "Tidak bisa membagi dengan nol", Toast.LENGTH_SHORT).show();
                    return null;
                }
                break;
            default:
                Toast.makeText(this, "Operasi tidak valid", Toast.LENGTH_SHORT).show();
                return null;
        }

        return String.valueOf(hitungHasil);
    }

    private Integer tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }






}