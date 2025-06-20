package Service;
import Database.JDBCuntil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.ImageIcon;
import model.ModelProducts;
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
                    String moTa = rs.getString("MoTa");
                    String path = rs.getString("Path");

                    ImageIcon icon = null;
                    if (path != null && !path.isEmpty()) {
                        icon = new ImageIcon(path); 
                    }

                    Model_Card card = new Model_Card(ma, ten, loai, gia, moTa, path, icon);
                    list.add(card);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
  public List<Model_Card> getProductsByCategory(String category) {
        List<Model_Card> allCards = getAllCards();
        if (category == null || category.equals("Tất cả")) {
            return allCards; // Trả về toàn bộ danh sách nếu loại là "Tất cả" hoặc null
        }
  return allCards.stream()
                .filter(card -> card.getLoaiSanPham() != null && card.getLoaiSanPham().equals(category))
                .collect(Collectors.toList());
    }
}
