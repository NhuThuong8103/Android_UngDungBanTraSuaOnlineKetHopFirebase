package com.example.milkteaappandroid.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.milkteaappandroid.Model.SanPhamChinaModel;
import com.example.milkteaappandroid.Model.SanPhamDat;
import com.example.milkteaappandroid.Model.SanPhamModel;
import com.example.milkteaappandroid.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ChiTietSanPham_Activity extends AppCompatActivity {

    SanPhamChinaModel sanPhamChinaModel;
    SanPhamModel sanPhamModel;

    TextView tbxTensp,tbxgia,tbxMota,tbxSL;
    ImageView hinhanh;
    ImageButton button,buttonAdd,buttonRemove;
    Button thanhToan;
    int sl= 0;
    float gia=0;
    RadioGroup gr;
    RadioButton radioButton;
    public static List<SanPhamDat> sanPhamDatList= new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_goods);

        tbxTensp = findViewById(R.id.tbxTenSp);
        tbxgia = findViewById(R.id.tbxGia);
        tbxMota = findViewById(R.id.tbxMoTa);
        hinhanh = findViewById(R.id.imageHinhSp);
        button = findViewById(R.id.btnBack);
        buttonAdd = findViewById(R.id.imgButtonAdd);
        buttonRemove = findViewById(R.id.imgButtonRemove);
        tbxSL = findViewById(R.id.tbxSL);
        thanhToan = findViewById(R.id.btnThanhToan);
        gr = findViewById(R.id.gr);

        sanPhamChinaModel = getIntent().getParcelableExtra("sanPham");
        if(sanPhamChinaModel==null){

            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                sanPhamModel = (SanPhamModel) getIntent().getSerializableExtra("sanPhamMoi"); //Obtaining data
            }
            //sanPhamModel= getIntent().getParcelableExtra("c");//sanPhamMoi

            Log.d("ck",sanPhamModel.getHinhanhsanpham()+"");
            gr.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if(i == R.id.rdL)
                    {
                        tbxgia.setText(sanPhamModel.getGia()+"");

                    }
                    if (i==R.id.rdM)
                    {
                        tbxgia.setText((sanPhamModel.getGia()+sanPhamModel.getGia()*0.2)+"");
                    }
                    sl= 0;
                    gia =0;
                    tbxSL.setText(sl+"");
                    thanhToan.setText(gia+"");
                }
            });

            //Hàm này để quay lại trang danh sách sản phẩm
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            //Hàm này để tăng số lượng sản phẩm
            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(sl >=0)
                    {
                        sl+=1;
                        tbxSL.setText(sl+"");
                        gia = sl* Float.parseFloat(tbxgia.getText().toString());
                        thanhToan.setText(gia+"₫");
                    }
                }
            });
            //Hàm này để giảm số lượng
            buttonRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sl>0)
                    {
                        sl-=1;
                        tbxSL.setText(sl+"");
                        gia = sl*Float.parseFloat(tbxgia.getText().toString());
                        thanhToan.setText(gia+"₫");
                    }
                }
            });

        }
        else {
            gr.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if (i == R.id.rdL) {
                        tbxgia.setText(sanPhamChinaModel.getGia() + "");

                    }
                    if (i == R.id.rdM) {
                        tbxgia.setText((sanPhamChinaModel.getGia() + sanPhamChinaModel.getGia() * 0.2) + "");
                    }
                    sl = 0;
                    gia = 0;
                    tbxSL.setText(sl + "");
                    thanhToan.setText(gia + "");
                }
            });

            //Hàm này để quay lại trang danh sách sản phẩm
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            //Hàm này để tăng số lượng sản phẩm
            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sl >= 0) {
                        sl += 1;
                        tbxSL.setText(sl + "");
                        gia = sl * Float.parseFloat(tbxgia.getText().toString());
                        thanhToan.setText(gia + "₫");
                    }
                }
            });
            //Hàm này để giảm số lượng
            buttonRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sl > 0) {
                        sl -= 1;
                        tbxSL.setText(sl + "");
                        gia = sl * Float.parseFloat(tbxgia.getText().toString());
                        thanhToan.setText(gia + "₫");
                    }
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(sanPhamChinaModel==null){
            Log.d("ktra",sanPhamModel.getMaSanPham()+"sss");
            tbxTensp.setText(sanPhamModel.getTensanpham());
            tbxgia.setText(sanPhamModel.getGia()+"");
            tbxMota.setText(sanPhamModel.getMotasanpham());
            StorageReference storageHinhAnh = FirebaseStorage.getInstance().getReference().child("hinhanhsp").child(sanPhamModel.getHinhanhsanpham());
            storageHinhAnh.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    // Use the bytes to display the image
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    //Bitmap bitmap = BitmapFactory.
                    hinhanh.setImageBitmap(bitmap);
                }
            });
            thanhToan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SanPhamDat sanPhamDat= new SanPhamDat();

                    sanPhamDat.setMaSanPham(sanPhamModel.getMaSanPham());

                    sanPhamDat.setTenSanPham(sanPhamModel.getTensanpham());

                    sanPhamDat.setGiaSanPham(sanPhamModel.getGia());

                    sanPhamDat.setHinhAnhSanPham(sanPhamModel.getHinhanhsanpham());

                    // get selected radio button from radioGroup
                    int selectedId = gr.getCheckedRadioButtonId();

                    // find the radiobutton by returned id
                    radioButton = (RadioButton) findViewById(selectedId);

                    sanPhamDat.setSize( radioButton.getText()+"");

                    sanPhamDat.setSoLuong(Integer.parseInt(tbxSL.getText()+""));

                    sanPhamDatList.add(sanPhamDat);

                    //xoas gio hang roi luu lai
                    SharedPreferences.Editor editor = getSharedPreferences("GIOHANG", Context.MODE_PRIVATE).edit();
                    editor.clear();
                    editor.commit();

                    //luu gio hang
                    String connectionsJSONString = new Gson().toJson(sanPhamDatList);

                    editor.putString("GIOHANG", connectionsJSONString);

                    editor.commit();

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ChiTietSanPham_Activity.this);

                    alertDialog.setTitle("Thông báo!");

                    alertDialog.setIcon(R.drawable.ic_info_outline_black_24dp);

                    alertDialog.setMessage("Thêm vào giỏ hàng thành công <3");

//                    alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            return;
//                        }
//                    });

                    alertDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //return;
                        }
                    });

                    alertDialog.show();

                }
            });
        }else {
            tbxTensp.setText(sanPhamChinaModel.getTensanpham());
            tbxgia.setText(sanPhamChinaModel.getGia().toString());
            tbxMota.setText(sanPhamChinaModel.getMotasanpham());
            StorageReference storageHinhAnh = FirebaseStorage.getInstance().getReference().child("hinhanhsp").child(sanPhamChinaModel.getHinhanhsanpham());
            storageHinhAnh.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    // Use the bytes to display the image
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    //Bitmap bitmap = BitmapFactory.
                    hinhanh.setImageBitmap(bitmap);
                }
            });
            thanhToan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SanPhamDat sanPhamDat= new SanPhamDat();

                    sanPhamDat.setMaSanPham(sanPhamChinaModel.getMaSanPham());

                    sanPhamDat.setTenSanPham(sanPhamChinaModel.getTensanpham());

                    sanPhamDat.setGiaSanPham(sanPhamChinaModel.getGia());

                    sanPhamDat.setHinhAnhSanPham(sanPhamChinaModel.getHinhanhsanpham());

                    // get selected radio button from radioGroup
                    int selectedId = gr.getCheckedRadioButtonId();

                    // find the radiobutton by returned id
                    radioButton = (RadioButton) findViewById(selectedId);

                    sanPhamDat.setSize( radioButton.getText()+"");

                    sanPhamDat.setSoLuong(Integer.parseInt(tbxSL.getText()+""));

                    sanPhamDatList.add(sanPhamDat);

                    //xoas gio hang roi luu lai
                    SharedPreferences.Editor editor = getSharedPreferences("GIOHANG", Context.MODE_PRIVATE).edit();
                    editor.clear();
                    editor.commit();

                    //luu gio hang
                    String connectionsJSONString = new Gson().toJson(sanPhamDatList);

                    editor.putString("JSONGIOHANG", connectionsJSONString);

                    editor.commit();

                    //thông báo
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ChiTietSanPham_Activity.this);

                    alertDialog.setTitle("Thông báo!");

                    alertDialog.setIcon(R.drawable.ic_info_outline_black_24dp);

                    alertDialog.setMessage("Thêm vào giỏ hàng thành công <3");

//            alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    return;
//                }
//            });

                    alertDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //return;
                        }
                    });

                    alertDialog.show();

                }
            });
        }
    }

}

