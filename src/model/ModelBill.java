
package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.jfree.data.json.impl.JSONObject;


public class ModelBill {
        private String maDonMua;
	private String TenSP;
	private int soluong;
	private int gia;
            private Date ngayMua;

    public ModelBill() {
    }
     private Date convertToSqlDate(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf.parse(dateString);
            return new Date(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
 public ModelBill(Object json) {
        if (json instanceof JSONObject) {
            JSONObject obj = (JSONObject) json;
            try {
                maDonMua = obj.get("maDonMua").toString();
                TenSP = obj.get("TenSP").toString();
                soluong = Integer.parseInt(obj.get("soluong").toString());
                gia = Integer.parseInt(obj.get("gia").toString());
                ngayMua = convertToSqlDate(obj.get("ngayMua").toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid input, expected JSONObject.");
        }
    }
 private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
    

    public ModelBill(String maDonMua, String TenSP, int soluong, int gia, Date ngayMua) {
        this.maDonMua = maDonMua;
  
  
        this.TenSP = TenSP;
        this.soluong = soluong;
        this.gia = gia;
        this.ngayMua = ngayMua;
    }

    public String getMaDonMua() {
        return maDonMua;
    }
    public String getTenSP() {
        return TenSP;
    }

    public int getSoluong() {
        return soluong;
    }

    public int getGia() {
        return gia;
    }

    public Date getNgayMua() {
        return ngayMua;
    }

    public void setMaDonMua(String maDonMua) {
        this.maDonMua = maDonMua;
    }
    public void setTenSP(String TenSP) {
        this.TenSP = TenSP;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public void setNgayMua(Date ngayMua) {
        this.ngayMua = ngayMua;
    }
	
}
