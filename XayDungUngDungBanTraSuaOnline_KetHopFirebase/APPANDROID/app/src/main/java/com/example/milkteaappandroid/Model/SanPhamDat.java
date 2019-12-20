package com.example.milkteaappandroid.Model;

public class SanPhamDat {
    public String maSanPham;

    public String tenSanPham;

    public Long giaSanPham;

    public String hinhAnhSanPham;

    public String Size;

    public int soLuong;

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public Long getGiaSanPham() {
        return giaSanPham;
    }

    public void setGiaSanPham(Long giaSanPham) {
        this.giaSanPham = giaSanPham;
    }

    public String getHinhAnhSanPham() {
        return hinhAnhSanPham;
    }

    public void setHinhAnhSanPham(String hinhAnhSanPham) {
        this.hinhAnhSanPham = hinhAnhSanPham;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public SanPhamDat() {
    }

    public SanPhamDat(String maSanPham, String tenSanPham, Long giaSanPham, String hinhAnhSanPham, String size, int soLuong) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.giaSanPham = giaSanPham;
        this.hinhAnhSanPham = hinhAnhSanPham;
        Size = size;
        this.soLuong = soLuong;
    }
}
