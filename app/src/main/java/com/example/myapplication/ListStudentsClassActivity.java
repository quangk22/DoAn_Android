package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListStudentsClassActivity extends AppCompatActivity {
    String DATABASE_NAME = "DoAn_QLSV.db";
    SQLiteDatabase database;
    ListView listStudentClass;
    ArrayList<Student> arrStudents;
    AdapterClassStudent adapter;
    Button btnThemStudent;
    EditText search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_students_class);
        listStudentClass = (ListView) findViewById(R.id.listSinhVienClass) ;
        Intent intent = getIntent();
        int maLopHoc = intent.getIntExtra("MA_LOP", -1);
        arrStudents = new ArrayList<>();
        adapter = new AdapterClassStudent(ListStudentsClassActivity.this,arrStudents);
        listStudentClass.setAdapter(adapter);

        database = Database.initDatabase(ListStudentsClassActivity.this,DATABASE_NAME);

        Cursor cursor = database.rawQuery("SELECT * FROM sinhvien WHERE malop = ?", new String[]{String.valueOf(maLopHoc)});
        arrStudents.clear();
        for (int i = 0; i < cursor.getCount() ; i++) {
            cursor.moveToPosition(i);
            int masv = cursor.getInt(0);
            int maLop = cursor.getInt(1);
            String tensv = cursor.getString(2);
            byte[] anh = cursor.getBlob(3);
            arrStudents.add(new Student(masv,maLop,tensv,anh));
        }


        adapter.notifyDataSetChanged();

    }
    public void QuayLai(View view) {
        Intent intent = new Intent(this, ManagerClassActivity.class);
        startActivity(intent);
        finish();
    }
}