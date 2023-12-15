package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterClassStudent extends BaseAdapter {
    Context context;
    ArrayList<Student> list;
    SQLiteDatabase database;
    String DATABASE_NAME = "DoAn_QLSV.db";

    public AdapterClassStudent(Context context, ArrayList<Student> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.studen_class_activity,null);

        TextView txtMaSV = (TextView) row.findViewById(R.id.textMaSVC);
        TextView txtTenSV = (TextView) row.findViewById(R.id.textTenSVC);
        TextView txtMaLopStu = (TextView) row.findViewById(R.id.textMaLopsC);
        ImageView imgAnh = (ImageView) row.findViewById(R.id.imageStudentC);

        Student student = list.get(position);
        txtMaSV.setText(student.maSinhVien +"");
        txtTenSV.setText(student.tenSV);
        txtMaLopStu.setText(student.maLop + "");
        Bitmap bitmap = BitmapFactory.decodeByteArray(student.anh,0,student.anh.length);
        imgAnh.setImageBitmap(bitmap);


        return row;
    }
}
