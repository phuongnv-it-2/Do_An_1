package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Revenue;

public class RevenueDao {

   public List<Revenue> getListRevenue() {
    Connection cons = JDBCuntil.getconection();
    String sql = "SELECT MONTH(ngayMua) AS month, SUM(gia * soluong) AS totalAmount " +
                 "FROM bill WHERE YEAR(ngayMua) = YEAR(CURDATE()) " +
                 "GROUP BY MONTH(ngayMua) ORDER BY month";

    List<Revenue> list = new ArrayList<>();
    try (PreparedStatement st = cons.prepareStatement(sql);
         ResultSet rs = st.executeQuery()) {
        double[] revenuesByMonth = new double[12];
        while (rs.next()) {
            int month = rs.getInt("month"); 
            double totalRevenue = rs.getDouble("totalAmount"); 
            revenuesByMonth[month - 1] = totalRevenue;
        }

       
        for (int i = 0; i < 12; i++) {
       
            Revenue revenue = new Revenue(i + 1, revenuesByMonth[i]);
            list.add(revenue);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list; 
}
 public List<Revenue> getListDayRevenue() {
    Connection cons = JDBCuntil.getconection();
    String sql = "SELECT ngayMua, SUM(gia * soluong) AS totalAmount " +
                 "FROM bill WHERE YEAR(ngayMua) = YEAR(CURDATE()) " +
                 "GROUP BY ngayMua ORDER BY ngayMua";

    List<Revenue> list = new ArrayList<>();
    try (PreparedStatement st = cons.prepareStatement(sql);
         ResultSet rs = st.executeQuery()) {

       
        while (rs.next()) {
            Date ngayMua = rs.getDate("ngayMua"); 
            double totalRevenue = rs.getDouble("totalAmount");

          
            Revenue revenue = new Revenue(ngayMua, totalRevenue);
            list.add(revenue);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list; 
}


}
