package Service;
import Database.JDBCuntil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import model.Model_Card;

public class CardService {

  public List<Model_Card> getAllCards() {
        List<Model_Card> list = new ArrayList<>();
        String sql = "SELECT * FROM sanpham";

        try (Connection con = JDBCuntil.getconection()) {
      
            if (con == null) {
                System.out.println("Kết nối cơ sở dữ liệu không thành công!");
                return list;  
            }

            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    int ma = rs.getInt("MaSanPham");
                    String ten = rs.getString("TenSanPham");
                    String loai = rs.getString("LoaiSanPham");
                    double gia = rs.getDouble("Gia");
                    int soLuong = rs.getInt("SoLuong");
                    String moTa = rs.getString("MoTa");
                    String path = rs.getString("Path");

                    ImageIcon icon = null;
                    if (path != null && !path.isEmpty()) {
                        icon = new ImageIcon(path); 
                    }

                    Model_Card card = new Model_Card(ma, ten, loai, gia, soLuong, moTa, path, icon);
                    list.add(card);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
