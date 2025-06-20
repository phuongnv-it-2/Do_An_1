package model;

public class NguyenLieu {

    private int id;
    private String ten;
    private String loai;              // ➕ Thêm trường loại
    private String donVi;
    private double soLuong;
    private java.sql.Date hanSuDung;
    private java.sql.Date ngayNhap;
    private double giaNhap;
    private String nhaCungCap;
    private String anh;

    public NguyenLieu() {
    }

    public NguyenLieu(int id, String ten, String loai, String donVi, double soLuong,
                      java.sql.Date hanSuDung, java.sql.Date ngayNhap,
                      double giaNhap, String nhaCungCap, String anh) {
        this.id = id;
        this.ten = ten;
        this.loai = loai;
        this.donVi = donVi;
        this.soLuong = soLuong;
        this.hanSuDung = hanSuDung;
        this.ngayNhap = ngayNhap;
        this.giaNhap = giaNhap;
        this.nhaCungCap = nhaCungCap;
        this.anh = anh;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public double getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(double soLuong) {
        this.soLuong = soLuong;
    }

    public java.sql.Date getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(java.sql.Date hanSuDung) {
        this.hanSuDung = hanSuDung;
    }

    public java.sql.Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(java.sql.Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public String getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(String nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }
}
