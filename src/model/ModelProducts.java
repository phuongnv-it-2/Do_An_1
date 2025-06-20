package model;

import java.util.List;

public class ModelProducts {
    private int id;                // MaSanPham
    private String name;          // TenSanPham
    private String category;      // LoaiSanPham
    private double price;         // Gia
    private String moTa;          // MoTa
    private String path;          // Path (đường dẫn ảnh)
        private List<CongThuc> congThucList;

    public ModelProducts(int id, String name, String category, double price, String moTa, String path) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.moTa = moTa;
        this.path = path;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public String getMoTa() {
        return moTa;
    }

    public String getPath() {
        return path;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    public List<CongThuc> getCongThucList() {
        return congThucList;
    }
      public void setCongThucList(List<CongThuc> congThucList) {
        this.congThucList = congThucList;
    }
}