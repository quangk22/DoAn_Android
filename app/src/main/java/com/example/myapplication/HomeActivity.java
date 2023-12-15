package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    TextView nameUser;
    Button btnClass, btnStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        anhXa();

        if(intent != null){
            String username = intent.getStringExtra("USERNAME");
            if(username != null){
                    nameUser.setText(username);
            }
        }
        btnClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ManagerClassActivity.class);
                startActivity(intent);
                finish(); // Đóng màn hình chính khi đăng xuất
            }
        });
        btnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ManagerStudentsActivity.class);
                startActivity(intent);
                finish(); // Đóng màn hình chính khi đăng xuất
            }
        });

    }

    private void anhXa() {
        nameUser = (TextView) findViewById(R.id.textName);
        btnClass = (Button) findViewById(R.id.buttonQLL);
        btnStudent =(Button) findViewById(R.id.buttonQlSV);
    }

    public void logout(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        Toast.makeText(getApplication(),"Đăng xuất thành công",Toast.LENGTH_LONG).show();
        finish(); // Đóng màn hình chính khi đăng xuất
    }

}