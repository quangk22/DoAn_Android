package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterClass extends BaseAdapter {
    Context context;
    ArrayList<Lop> list;
    SQLiteDatabase database;
    String DATABASE_NAME = "DoAn_QLSV.db";

    public AdapterClass(Context context, ArrayList<Lop> list) { 
        this.context = context;
        this.list = list;
    }
    public void setData(ArrayList<Lop> newData) {
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activty_class, null);

        TextView txtMaLop = row.findViewById(R.id.textMaLop);
        TextView txtTenLop = row.findViewById(R.id.textTenLop);
        Button btnXoaLop = row.findViewById(R.id.buttonXoa);
        Button btnSuaLop = row.findViewById(R.id.buttonSua);
        Button btnXemDS = row.findViewById(R.id.buttonXemListDS);

        Lop classs = list.get(position);
        txtMaLop.setText(classs.maLop + "");
        txtTenLop.setText(classs.tenLop );


        btnXemDS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ListStudentsClassActivity.class);
                intent.putExtra("MA_LOP", classs.maLop);
                context.startActivity(intent);
            }
        });
        btnXoaLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có muốn xóa không");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(String.valueOf(classs.maLop));
                    }

                    private void delete(String maLop) {
                        database = Database.initDatabase((Activity) context, DATABASE_NAME);
                        database.delete("lop", " malop = ?", new String[]{maLop + ""});
                        Cursor cursor = database.rawQuery("Select * from lop", null);
                        list.clear();
                        while (cursor.moveToNext()) {
                            int ma = cursor.getInt(0);
                            String lop = cursor.getString(1);

                            list.add(new Lop(ma, lop));
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
        btnSuaLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context ,UpdateLopActivity.class);
                intent.putExtra("malop",classs.maLop);
                context.startActivity(intent);
            }
        });
        return row;
    }
}
