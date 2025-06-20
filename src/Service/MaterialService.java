package Service;

import Database.JDBCuntil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.ImageIcon;
import model.NguyenLieu;

public class MaterialService {

    public List<NguyenLieu> getAllMaterials() {
        List<NguyenLieu> list = new ArrayList<>();
        String sql = "SELECT * FROM nguyenlieu";

        try (Connection con = JDBCuntil.getconection()) {
            if (con == null) {
                System.out.println("Kết nối cơ sở dữ liệu không thành công!");
                return list;
            }

            try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String ten = rs.getString("ten");
                    String loai = rs.getString("loai"); 
                    String donVi = rs.getString("donvi");
                    double soLuong = rs.getDouble("soluong");
                    java.sql.Date hanSuDung = rs.getDate("hansudung");
                    java.sql.Date ngayNhap = rs.getDate("ngaynhap");
                    double giaNhap = rs.getDouble("gianhap");
                    String nhaCungCap = rs.getString("nhacungcap");
                    String anh = rs.getString("anh");

                    ImageIcon icon = null;
                    if (anh != null && !anh.isEmpty()) {
                        icon = new ImageIcon(anh);
                    }

                    NguyenLieu material = new NguyenLieu(id, ten, loai, donVi, soLuong, hanSuDung, ngayNhap, giaNhap, nhaCungCap, anh);
                    list.add(material);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
 public List<NguyenLieu> getMaterialsByCategory(String category) {
        List<NguyenLieu> allMaterials = getAllMaterials();
        if (category == null || category.equals("Tất cả")) {
            return allMaterials; // Trả về toàn bộ danh sách nếu loại là "Tất cả" hoặc null
        }
        return allMaterials.stream()
                .filter(material -> material.getLoai() != null && material.getLoai().equals(category))
                .collect(Collectors.toList());
    }
}
