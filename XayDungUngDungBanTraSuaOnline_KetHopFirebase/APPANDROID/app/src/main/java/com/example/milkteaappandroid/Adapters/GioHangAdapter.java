package com.example.milkteaappandroid.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.milkteaappandroid.Model.SanPhamDat;
import com.example.milkteaappandroid.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class GioHangAdapter extends ArrayAdapter<SanPhamDat> {

    public GioHangAdapter(@NonNull Context context, @NonNull List<SanPhamDat> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SanPhamDat sanPhamDat =getItem(position);

        if(convertView==null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.itemgiohang_layout, parent, false);

            final ImageView imageView=convertView.findViewById(R.id.imgspgiohang);
            TextView txttenspgiohang=convertView.findViewById(R.id.txttenspgiohang);
            TextView txtgiagiohang=convertView.findViewById(R.id.txtgiagiohang);
            TextView txtsizegiohang=convertView.findViewById(R.id.txtsizegiohang);
            TextView txtsoluonggiohang=convertView.findViewById(R.id.txtsoluonggiohang);

            txttenspgiohang.setText(sanPhamDat.getTenSanPham());
            txtgiagiohang.setText(sanPhamDat.getGiaSanPham()+"");
            txtsizegiohang.setText(sanPhamDat.getSize());
            txtsoluonggiohang.setText(sanPhamDat.getSoLuong()+"");

            StorageReference storageHinhAnh = FirebaseStorage.getInstance().getReference().child("hinhanhsp").child(sanPhamDat.getHinhAnhSanPham());
            storageHinhAnh.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    // Use the bytes to display the image
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    //Bitmap bitmap = BitmapFactory.
                    imageView.setImageBitmap(bitmap);
                }
            });
        }

        return convertView;
    }
}
