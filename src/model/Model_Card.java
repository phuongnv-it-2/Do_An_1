package model;

import javax.swing.Icon;

public class Model_Card {

    private int maSanPham;
    private String tenSanPham;
    private String loaiSanPham;
    private double gia;
    private String moTa;
    private String path;
    private Icon icon;

    public Model_Card() {
    }

    public Model_Card(int maSanPham, String tenSanPham, String loaiSanPham,
                      double gia, String moTa, String path, Icon icon) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.loaiSanPham = loaiSanPham;
        this.gia = gia;
        this.moTa = moTa;
        this.path = path;
        this.icon = icon;
    }

    // Constructor mới với các tham số chỉ có Icon và String
    public Model_Card(Icon icon, String tenSanPham, String loaiSanPham) {
        this.icon = icon;
        this.tenSanPham = tenSanPham;
        this.loaiSanPham = loaiSanPham;
        // Gán giá trị mặc định cho các thuộc tính còn lại
        this.gia = 0.0;
        this.moTa = "";
        this.path = "";
    }

    public int getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getLoaiSanPham() {
        return loaiSanPham;
    }

    public void setLoaiSanPham(String loaiSanPham) {
        this.loaiSanPham = loaiSanPham;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }
    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
}
