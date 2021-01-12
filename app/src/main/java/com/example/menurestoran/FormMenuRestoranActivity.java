package com.example.menurestoran;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Date;

public class FormMenuRestoranActivity extends AppCompatActivity {

    Button btnSimpan;
    TextInputLayout tilDeskripsi,tilNamaTempat,tilDaerah;
    EditText edtTgl;
    Spinner spJenisMenuRestoran;
    Date tanggalMenuRestoran;
    final String[] tipeFavorite = {MenuRestoran.MAKANAN,MenuRestoran.MINUMAN};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_menu_restoran);
        inisialisasiView();
    }

    private void inisialisasiView() {
        btnSimpan = findViewById(R.id.btn_simpan);
        btnSimpan.setOnClickListener(view -> simpan());
        edtTgl = findViewById(R.id.edt_tgl);
        edtTgl.setOnClickListener(view -> pickDate());
        tilDeskripsi = findViewById(R.id.til_deskripsi);
        tilNamaTempat= findViewById(R.id.til_namaTempat);
        tilDaerah= findViewById(R.id.til_Daerah);
        spJenisMenuRestoran = findViewById(R.id.spn_jenis);
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                tipeFavorite
        );
        spJenisMenuRestoran.setAdapter(adapter);
        spJenisMenuRestoran.setSelection(0);
    }

    private void simpan() {
        if (isDataValid()) {
            MenuRestoran tr = new MenuRestoran();

            tr.setNamaTempat(tilNamaTempat.getEditText().getText().toString());
            tr.setJenis(spJenisMenuRestoran.getSelectedItem().toString());
            tr.setDaerah(tilDaerah.getEditText().getText().toString());
            tr.setDeskripsi(tilDeskripsi.getEditText().getText().toString());
            tr.setTanggal(tanggalMenuRestoran);
            SharedPreferenceUtility.addMenuRestoran(this,tr);
            Toast.makeText(this,"Data berhasil disimpan",Toast.LENGTH_SHORT).show();

            // Kembali ke layar sebelumnya setelah 500 ms (0.5 detik)
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 500);


        }
    }

    private boolean isDataValid() {
        if (tilDeskripsi.getEditText().getText().toString().isEmpty()
                || tilNamaTempat.getEditText().getText().toString().isEmpty()
                || tilDaerah.getEditText().getText().toString().isEmpty()

        ) {
            Toast.makeText(this,"Lengkapi semua isian",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    /*
        Dipanggil saat user ingin mengisi tanggal transaksi
        Menampilkan date picker dalam popup dialog
     */
    private void pickDate() {
        final Calendar c = Calendar.getInstance();
        int thn = c.get(Calendar.YEAR);
        int bln = c.get(Calendar.MONTH);
        int tgl = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (DatePickerDialog.OnDateSetListener) (view, yyyy, mm, dd) -> {
                    edtTgl.setText(dd + "-" + (mm + 1) + "-" + yyyy);
                    c.set(yyyy,mm,dd);
                    tanggalMenuRestoran = c.getTime();
                },
                thn, bln, tgl);
        datePickerDialog.show();
    }

}