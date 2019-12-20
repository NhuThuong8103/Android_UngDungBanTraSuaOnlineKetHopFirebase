package com.example.milkteaappandroid.View;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.milkteaappandroid.Adapters.GioHangAdapter;
import com.example.milkteaappandroid.Model.QuanModel;
import com.example.milkteaappandroid.Model.SanPhamDat;
import com.example.milkteaappandroid.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GioHang extends Fragment {


    SearchView searchView;
    ImageView imageMap;
    ListView listGioHang;
    DatabaseReference databaseReference;
    GioHangAdapter gioHangAdapter;
    ProgressBar progressBarGioHang;
    Context context;
    public GioHang() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_gio_hang, container, false);

        context=v.getContext();

        searchView=v.findViewById(R.id.search_view);

        imageMap=v.findViewById(R.id.imgMap);

        listGioHang=v.findViewById(R.id.products_listview);

        progressBarGioHang=v.findViewById(R.id.progressBarGioHang);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference= FirebaseDatabase.getInstance().getReference().child("thongtinquan");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        QuanModel quanModel=dataSnapshot.getValue(QuanModel.class);
                        Intent intent = new Intent(getActivity(),MapActivity.class);
                        intent.putExtra("latitudequan",quanModel.getLatitude());
                        intent.putExtra("longitudequan",quanModel.getLongitude());
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });
            }
        });

        //String connectionsJSONString = context.getSharedPreferences(context.MODE_PRIVATE).getString("GIOHANG", null);
        SharedPreferences sharedPreferences= context.getSharedPreferences("GIOHANG",context.MODE_PRIVATE);

        if(sharedPreferences !=null) {
            String connectionsJSONString = sharedPreferences.getString("JSONGIOHANG", "");
            Type type = new TypeToken<List<SanPhamDat>>() {}.getType();
            List<SanPhamDat> connections = new Gson().fromJson(connectionsJSONString, type);

            if(connections !=null) {
                gioHangAdapter = new GioHangAdapter(getContext(), connections);//,ChiTietSanPham_Activity.sanPhamDatList);

                listGioHang.setAdapter(gioHangAdapter);
            }
        }

        progressBarGioHang.setVisibility(View.GONE);

    }


}
