package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ManagerStudentsActivity extends AppCompatActivity {
    String DATABASE_NAME = "DoAn_QLSV.db";
    SQLiteDatabase database;
    ListView listStudent;
    ArrayList<Student> arrStudents;
    AdapterStudent adapter;
    Button btnThemStudent;
    EditText search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_students);
        listStudent = (ListView) findViewById(R.id.listViewStudent);
        btnThemStudent = (Button) findViewById(R.id.buttonThemStudent) ;
        search = (EditText) findViewById(R.id.editTextSearchStu) ;
        arrStudents = new ArrayList<>();
        adapter = new AdapterStudent(ManagerStudentsActivity.this,arrStudents);
        listStudent.setAdapter(adapter);

        database = Database.initDatabase(ManagerStudentsActivity.this,DATABASE_NAME);

        Cursor cursor = database.rawQuery("SELECT * FROM sinhvien",null );
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
        btnThemStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerStudentsActivity.this, InsertStudentActivity.class);
                startActivity(intent);
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Không cần xử lý trước khi thay đổi văn bản
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Xử lý khi văn bản thay đổi
                filterData(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Không cần xử lý sau khi thay đổi văn bản
            }
        });
    }
    public void QuayLai(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
    private void filterData(String searchText) {
        ArrayList<Student> filteredList = new ArrayList<>();

        for (Student student : arrStudents) {
            if (student.tenSV.toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(student);
            }
        }

        // Cập nhật danh sách hiển thị
        adapter.setData(filteredList);
    }
}