package model;

import java.util.Date;

public class Revenue {
    private int month; 
    private double revenue;
    private Date ngayMua;

    public Revenue(int month, double revenue) {
        this.month = month;
        this.revenue = revenue;
    } 
      public Revenue(Date ngayMua, double revenue) {
        this.ngayMua = ngayMua;
        this.revenue = revenue;
    }

public Date getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(Date ngayMua) {
        this.ngayMua = ngayMua;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
}
