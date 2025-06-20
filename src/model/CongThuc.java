package model;
public class CongThuc {
    private int maSanPham;        // Mã món ăn
    private int maNguyenLieu;     // Mã nguyên liệu
    private double soLuong;       // Số lượng sử dụng
    private String donVi;         // Đơn vị tính (gram, ml,...)

    public CongThuc(int maSanPham, int maNguyenLieu, double soLuong, String donVi) {
        this.maSanPham = maSanPham;
        this.maNguyenLieu = maNguyenLieu;
        this.soLuong = soLuong;
        this.donVi = donVi;
    }

    // Getter và Setter
    public int getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }

    public int getMaNguyenLieu() {
        return maNguyenLieu;
    }

    public void setMaNguyenLieu(int maNguyenLieu) {
        this.maNguyenLieu = maNguyenLieu;
    }

    public double getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(double soLuong) {
        this.soLuong = soLuong;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }
}
