package com.example.milkteaappandroid.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milkteaappandroid.Model.SanPhamModel;
import com.example.milkteaappandroid.R;
import com.example.milkteaappandroid.View.ChiTietSanPham_Activity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.List;

public class AdapterRecycler_SPMoi extends RecyclerView.Adapter<AdapterRecycler_SPMoi.ViewHolder> {
    List<SanPhamModel>sanPhamModelList;
    int resource;
    Context context;
    public AdapterRecycler_SPMoi(Context context,List<SanPhamModel>sanPhamModelList, int resource){
        this.sanPhamModelList=sanPhamModelList;
        this.resource=resource;
        this.context=context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenSanPham;
        TextView txtGia;
        ImageView imgSanPham;
        ImageView btnCTSP;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenSanPham=itemView.findViewById(R.id.txttenspgiohang);
            txtGia =itemView.findViewById(R.id.txtgiagiohang);
            imgSanPham=itemView.findViewById(R.id.img_spmoi);
            btnCTSP=itemView.findViewById(R.id.img_ctsp);
        }
    }

    @NonNull
    @Override
    public AdapterRecycler_SPMoi.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        ViewHolder  viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterRecycler_SPMoi.ViewHolder holder, int position) {
        final SanPhamModel sanPhamModel = sanPhamModelList.get(position);

        holder.txtTenSanPham.setText(sanPhamModel.getTensanpham());
        holder.txtGia.setText(sanPhamModel.getGia()+"₫");
        if(sanPhamModel.getHinhanhsanpham().length()>0){
            StorageReference storageAnhSP = FirebaseStorage.getInstance().getReference().child("hinhanhsp").child(sanPhamModel.getHinhanhsanpham());
            long ONE_MEGABYTE= 1024 * 1024;
            storageAnhSP.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    holder.imgSanPham.setImageBitmap(bitmap);
                    Log.d("anh","sss");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("anh","sssdd");
                }
            });

            holder.imgSanPham.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent SpOrder = new Intent(context, ChiTietSanPham_Activity.class);
                    SpOrder.putExtra("sanPhamMoi", (Serializable) sanPhamModel);
                    //SpOrder.putExtra("sanPhamMoi", sanPhamModel);
                    context.startActivity(SpOrder);
                }
            });
            holder.btnCTSP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent SpOrder = new Intent(context, ChiTietSanPham_Activity.class);
                    SpOrder.putExtra("sanPhamMoi", (Serializable) sanPhamModel);
                    context.startActivity(SpOrder);
                }
            });
            holder.txtTenSanPham.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent SpOrder = new Intent(context, ChiTietSanPham_Activity.class);
                    SpOrder.putExtra("sanPhamMoi", (Parcelable) sanPhamModel);
                    context.startActivity(SpOrder);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return sanPhamModelList.size() ;
    }


}
