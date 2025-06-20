package model;

public class Discount {
    private String maGiamGia;
    private String tenGiamGia;
    private double phanTramGiam;
    private double giamToiDa;
    private java.sql.Date ngayBatDau;
    private java.sql.Date ngayKetThuc;
    private int soLanSuDung;
    private int soLanDaSuDung;

    public Discount() {}

    public Discount(String maGiamGia, String tenGiamGia, double phanTramGiam, double giamToiDa,
                    java.sql.Date ngayBatDau, java.sql.Date ngayKetThuc,
                    int soLanSuDung, int soLanDaSuDung) {
        this.maGiamGia = maGiamGia;
        this.tenGiamGia = tenGiamGia;
        this.phanTramGiam = phanTramGiam;
        this.giamToiDa = giamToiDa;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.soLanSuDung = soLanSuDung;
        this.soLanDaSuDung = soLanDaSuDung;
    }

    public String getMaGiamGia() {
        return maGiamGia;
    }

    public void setMaGiamGia(String maGiamGia) {
        this.maGiamGia = maGiamGia;
    }

    public String getTenGiamGia() {
        return tenGiamGia;
    }

    public void setTenGiamGia(String tenGiamGia) {
        this.tenGiamGia = tenGiamGia;
    }

    public double getPhanTramGiam() {
        return phanTramGiam;
    }

    public void setPhanTramGiam(double phanTramGiam) {
        this.phanTramGiam = phanTramGiam;
    }

    public double getGiamToiDa() {
        return giamToiDa;
    }

    public void setGiamToiDa(double giamToiDa) {
        this.giamToiDa = giamToiDa;
    }

    public java.sql.Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(java.sql.Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public java.sql.Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(java.sql.Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public int getSoLanSuDung() {
        return soLanSuDung;
    }

    public void setSoLanSuDung(int soLanSuDung) {
        this.soLanSuDung = soLanSuDung;
    }

    public int getSoLanDaSuDung() {
        return soLanDaSuDung;
    }

    public void setSoLanDaSuDung(int soLanDaSuDung) {
        this.soLanDaSuDung = soLanDaSuDung;
    }
}
