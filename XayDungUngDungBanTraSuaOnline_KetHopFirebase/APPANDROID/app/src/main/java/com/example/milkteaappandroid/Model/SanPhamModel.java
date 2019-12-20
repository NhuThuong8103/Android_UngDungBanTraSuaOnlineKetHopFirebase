package com.example.milkteaappandroid.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.milkteaappandroid.Controller.Interfaces.sanphammoiInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SanPhamModel implements Serializable, Parcelable {
    public  String maSanPham;
    public long gia;
    public  String hinhanhsanpham;
    public  String motasanpham;
    public  String tensanpham;


    protected SanPhamModel(Parcel in) {
        maSanPham = in.readString();
        gia = in.readLong();
        hinhanhsanpham = in.readString();
        motasanpham = in.readString();
        tensanpham = in.readString();
    }

    public static final Creator<SanPhamModel> CREATOR = new Creator<SanPhamModel>() {
        @Override
        public SanPhamModel createFromParcel(Parcel in) {
            return new SanPhamModel(in);
        }

        @Override
        public SanPhamModel[] newArray(int size) {
            return new SanPhamModel[size];
        }
    };

    public long getGia() {
        return gia;
    }

    public void setGia(long gia) {
        this.gia = gia;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getHinhanhsanpham() {
        return hinhanhsanpham;
    }

    public void setHinhanhsanpham(String hinhanhsanpham) {
        this.hinhanhsanpham = hinhanhsanpham;
    }

    public String getMotasanpham() {
        return motasanpham;
    }

    public void setMotasanpham(String motasanpham) {
        this.motasanpham = motasanpham;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }


    DatabaseReference nodeRoot;

    public SanPhamModel() {
        nodeRoot= FirebaseDatabase.getInstance().getReference();
    }

    public  void getDanhSachSanPhamMoi(final sanphammoiInterface sanphammoiInterface){

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshotSanPham =dataSnapshot.child("sanpham");
                for(DataSnapshot valueSanPham : dataSnapshotSanPham.getChildren()){
                    for (DataSnapshot value : valueSanPham.getChildren()){
                        SanPhamModel sanPhamModel =value.getValue(SanPhamModel.class);
                        sanPhamModel.setMaSanPham(value.getKey());
                        //Log.d("kiemtra:",value.getKey());
                        sanphammoiInterface.getDanhSachSanPhamMoiModel(sanPhamModel);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        nodeRoot.addListenerForSingleValueEvent(valueEventListener);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(maSanPham);
        dest.writeLong(gia);
        dest.writeString(hinhanhsanpham);
        dest.writeString(motasanpham);
        dest.writeString(tensanpham);
    }
}
