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

public class AdapterStudent extends BaseAdapter {
    Context context;
    ArrayList<Student> list;
    SQLiteDatabase database;
    String DATABASE_NAME = "DoAn_QLSV.db";

    public AdapterStudent(Context context, ArrayList<Student> list) {
        this.context = context;
        this.list = list;
    }
    public void setData(ArrayList<Student> newData) {
        this.list = newData;
        notifyDataSetChanged();
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
        View row = layoutInflater.inflate(R.layout.activity_student,null);

        TextView txtTenSV = (TextView) row.findViewById(R.id.textTenSV);
        TextView txtMaSV = (TextView) row.findViewById(R.id.textMaSV);
        TextView txtMaLopStu = (TextView) row.findViewById(R.id.textMaLops);
        ImageView imgAnh = (ImageView) row.findViewById(R.id.imageStudent);
        Button btnSua = (Button) row.findViewById(R.id.buttonSuaStu);
        Button btnXoa = (Button) row.findViewById(R.id.buttonXoa);

        Student student = list.get(position);
        txtMaSV.setText(student.maSinhVien +"");
        txtTenSV.setText(student.tenSV);
        txtMaLopStu.setText(student.maLop + "");
        Bitmap bitmap = BitmapFactory.decodeByteArray(student.anh,0,student.anh.length);
        imgAnh.setImageBitmap(bitmap);


        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có muốn xóa không");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(String.valueOf(student.maSinhVien));
                    }

                    private void delete(String maSV) {
                        database = Database.initDatabase((Activity) context, DATABASE_NAME);
                        database.delete("sinhvien", " masv = ?", new String[]{maSV + ""});
                        Cursor cursor = database.rawQuery("Select * from sinhvien", null);
                        list.clear();
                        while (cursor.moveToNext()) {
                            int id = cursor.getInt(0);
                            int malop = cursor.getInt(1);
                            String ten  = cursor.getString(2);
                            byte[] anh = cursor.getBlob(3);


                            list.add(new Student(id,malop,ten,anh));
                        }
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context ,UpdateStudentActivity.class);
                intent.putExtra("ID",student.maSinhVien);
                context.startActivity(intent);
            }
        });
        return row;
    }
}
