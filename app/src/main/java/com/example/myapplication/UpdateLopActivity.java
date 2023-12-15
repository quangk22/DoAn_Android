package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpdateLopActivity extends AppCompatActivity {
    String DATABASE_NAME = "DoAn_QLSV.db";
    SQLiteDatabase database;
    EditText edtMaLopThemSua,edtTenLopThemSua;
    Button btnThemLopSua,btnHuySua;
    int maLop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_lop);
        addConTronl();
        loadData();
        addEvent();
    }

    private void addEvent() {
        btnThemLopSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update();
            }

            private void Update() {
                String maLopValue = edtMaLopThemSua.getText().toString();
                String tenLop = edtTenLopThemSua.getText().toString();
                ContentValues contentValues = new ContentValues();
                contentValues.put("malop", maLopValue);
                contentValues.put("tenlop", tenLop);
                database = Database.initDatabase(UpdateLopActivity.this, DATABASE_NAME);
                database.update("lop", contentValues, "malop = ?", new String[]{String.valueOf(maLop)});
                Intent intent = new Intent(UpdateLopActivity.this, ManagerClassActivity.class);
                startActivity(intent);
            }
        });
        btnHuySua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateLopActivity.this, ManagerClassActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addConTronl() {
        edtMaLopThemSua = (EditText) findViewById(R.id.editTextMaLopSua);
        edtTenLopThemSua = (EditText) findViewById(R.id.editTextTenLopSua);
        btnThemLopSua = (Button) findViewById(R.id.buttonThemClassSua);
        btnHuySua = (Button) findViewById(R.id.buttonHuySua);
    }
    private void loadData() {
        Intent intent = getIntent();
        maLop = intent.getIntExtra("malop", -1);
        if (maLop != -1) {
            database = Database.initDatabase(UpdateLopActivity.this, DATABASE_NAME);
            Cursor cursor = database.rawQuery("SELECT * FROM lop WHERE malop = ? ", new String[]{maLop + ""});

            // Kiểm tra xem Cursor có dữ liệu hay không
            if (cursor != null && cursor.moveToFirst()) {
                String ma = cursor.getString(0);
                String lop = cursor.getString(1);

                edtMaLopThemSua.setText(ma);
                edtTenLopThemSua.setText(lop);
            } else {
                Log.e("UpdateLopActivity", "Cursor rỗng hoặc không có dữ liệu");
            }

            // Đóng Cursor sau khi sử dụng
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}